package com.knowledgemesh.ingestion.service;

import com.knowledgemesh.document.dto.UploadDocumentResponse;
import com.knowledgemesh.document.entity.Document;
import com.knowledgemesh.document.entity.DocumentChunk;
import com.knowledgemesh.document.repository.DocumentChunkRepository;
import com.knowledgemesh.document.repository.DocumentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DocumentProcessor {
    private final PdfExtractor pdfExtractor;
    private final ChunkingService chunkingService;
    private final DocumentRepository documentRepository;
    private final DocumentChunkRepository chunkRepository;


    public UploadDocumentResponse process(
            MultipartFile file
    ) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException(
                    "Uploaded file is empty"
            );
        }

        if (!file.getOriginalFilename().endsWith(".pdf")) {
            throw new IllegalArgumentException(
                    "Only PDF files are supported"
            );
        }

        // 1. Extract text from PDF
        String text = pdfExtractor.extractText(file);

        // 2. Chunk the text
        List<String> chunks = chunkingService.chunk(text);

        // 3. save document
        Document document = documentRepository.save(
                Document.builder()
                        .fileName(file.getOriginalFilename())
                        .uploadedAt(LocalDateTime.now())
                        .build()
        );

        // 4. save chunks
        for (int i = 0; i < chunks.size(); i++) {
            DocumentChunk chunk = DocumentChunk.builder()
                    .document(document)
                    .content(chunks.get(i))
                    .chunkIndex(i)
                    .build();
            chunkRepository.save(chunk);
        }
        return new UploadDocumentResponse(
                document.getId(),
                chunks.size()
        );

    }
}
