package com.knowledgemesh.document.dto;

public record UploadDocumentResponse(
        Long documentId,
        int chunkCount
) {
}