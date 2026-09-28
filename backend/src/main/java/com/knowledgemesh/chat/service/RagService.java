package com.knowledgemesh.chat.service;

import com.knowledgemesh.chat.dto.RagResponse;
import com.knowledgemesh.chat.dto.RagSource;
import com.knowledgemesh.llm.service.LlmService;
import com.knowledgemesh.retrieval.model.SearchResult;
import com.knowledgemesh.retrieval.service.ContextBuilder;
import com.knowledgemesh.retrieval.service.SemanticSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RagService {
    private final SemanticSearchService searchService;
    private final ContextBuilder contextBuilder;
    private final LlmService llmService;

    public RagResponse answer(String question) {
        // 1. Retrieve relevant chunks
        List<SearchResult> results =
                searchService.search(question, 5);

        // 2. Build context
        String context =
                contextBuilder.build(results);

        // 3. Construct prompt
        String prompt = buildPrompt(question, context);

        // 4. Ask the LLM
        String answer = llmService.generate(prompt);

        List<RagSource> sources = results.stream()
                .map(result -> new RagSource(
                        result.documentId(),
                        result.chunkIndex(),
                        result.similarity()
                ))
                .toList();
        return new RagResponse(answer, sources);
    }

    private String buildPrompt(String question, String context) {
        return """
                You are an enterprise knowledge assistant.

                Answer the user's question using ONLY the
                information provided in the context below.

                If the answer cannot be found in the context,
                say that you do not have enough information.

                Do not invent information.

                Context:
                %s

                User Question:
                %s

                Answer:
                """.formatted(context, question);
    }
}
