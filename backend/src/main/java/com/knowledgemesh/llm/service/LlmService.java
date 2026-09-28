package com.knowledgemesh.llm.service;
import dev.langchain4j.model.chat.ChatModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LlmService {
    private final ChatModel chatModel;

    public String generate(String prompt) {
        return chatModel.chat(prompt);
    }
}
