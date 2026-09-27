package com.knowledgemesh.retrieval.service;

import com.knowledgemesh.document.entity.DocumentChunk;
import com.knowledgemesh.document.repository.DocumentChunkRepository;
import com.knowledgemesh.document.repository.VectorSearchRepository;
import com.knowledgemesh.embedding.service.EmbeddingService;
import com.knowledgemesh.retrieval.model.SearchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SemanticSearchService {
    private final EmbeddingService embeddingService;
    private final DocumentChunkRepository chunkRepository;
    private final VectorSearchRepository vectorSearchRepository;

    public List<SearchResult> search(String query, int limit) {
        float[] queryEmbedding = embeddingService.generateEmbedding(query);
        return vectorSearchRepository.findSimilar(queryEmbedding, limit);
    }
}
