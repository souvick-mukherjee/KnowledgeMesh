package com.knowledgemesh.embedding.service;

import dev.langchain4j.model.embedding.EmbeddingModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmbeddingService {
    private final EmbeddingModel embeddingModel;

    public float[] generateEmbedding(String text) {

        return embeddingModel
                .embed(text)
                .content()
                .vector();
    }
}
