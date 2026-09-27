package com.knowledgemesh.retrieval.model;

public record SearchResult(
        Long chunkId,
        Long documentId,
        String content,
        Integer chunkIndex,
        double similarity
) {
}