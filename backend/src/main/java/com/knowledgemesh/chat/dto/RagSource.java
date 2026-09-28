package com.knowledgemesh.chat.dto;

public record RagSource(
        Long documentId,
        Integer chunkIndex,
        Double similarity
) {
}