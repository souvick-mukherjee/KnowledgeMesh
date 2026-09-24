package com.knowledgemesh.ingestion.service;

import com.knowledgemesh.document.dto.UploadDocumentResponse;
import com.knowledgemesh.document.entity.Document;
import com.knowledgemesh.document.entity.DocumentChunk;
import com.knowledgemesh.document.entity.DocumentStatus;
import com.knowledgemesh.document.repository.DocumentChunkRepository;
import com.knowledgemesh.document.repository.DocumentRepository;
import com.knowledgemesh.embedding.service.EmbeddingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DocumentProcessor {
    private final PdfExtractor pdfExtractor;
    private final ChunkingService chunkingService;
    private final DocumentRepository documentRepository;
    private final DocumentChunkRepository chunkRepository;
    private final EmbeddingService embeddingService;


    public UploadDocumentResponse process(
            MultipartFile file
    ) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException(
                    "Uploaded file is empty"
            );
        }

        if (file.getOriginalFilename() == null || !file.getOriginalFilename().endsWith(".pdf")) {
            throw new IllegalArgumentException(
                    "Only PDF files are supported"
            );
        }

        // 1. Extract text from PDF
        log.info("Processing document: {}", file.getOriginalFilename());
        String text = pdfExtractor.extractText(file);

        // 2. Chunk the text
        List<String> chunks = chunkingService.chunk(text);
        log.info(
                "Generated {} chunks for document '{}'",
                chunks.size(),
                file.getOriginalFilename()
        );

        // 3. save document
//        Document document = documentRepository.save(
//                Document.builder()
//                        .fileName(file.getOriginalFilename())
//                        .uploadedAt(LocalDateTime.now())
//                        .build()
//        );
        Document document = createDocument(file);

        // 4. save chunks
//        for (int i = 0; i < chunks.size(); i++) {
//            DocumentChunk chunk = DocumentChunk.builder()
//                    .document(document)
//                    .content(chunks.get(i))
//                    .chunkIndex(i)
//                    .build();
//            chunkRepository.save(chunk);
//        }
//        List<DocumentChunk> chunkEntities = new ArrayList<>();
//        for (int i = 0; i < chunks.size(); i++) {
//            chunkEntities.add(
//                    DocumentChunk.builder()
//                            .document(document)
//                            .chunkIndex(i)
//                            .content(chunks.get(i))
//                            .build()
//            );
//        }
//        chunkRepository.saveAll(chunkEntities);
        try {
            saveChunks(document, chunks);
            document.setStatus(DocumentStatus.READY);
        } catch (Exception ex) {
            document.setStatus(DocumentStatus.FAILED);
            throw ex;
        } finally {
            documentRepository.save(document);
        }

        return new UploadDocumentResponse(
                document.getId(),
                chunks.size()
        );
    }

    private Document createDocument(MultipartFile file) {
        return documentRepository.save(
                Document.builder()
                        .fileName(file.getOriginalFilename())
                        .fileSize(file.getSize())
                        .contentType(file.getContentType())
                        .status(DocumentStatus.PROCESSING)
                        .uploadedAt(LocalDateTime.now())
                        .build()
        );
    }

    private void saveChunks(Document document, List<String> chunks) {
        List<DocumentChunk> chunkEntities = new ArrayList<>();
        for (int i = 0; i < chunks.size(); i++) {
            String content = chunks.get(i);
            log.debug("Generating embedding for chunk {}", i);
            float[] embedding =
                    embeddingService.generateEmbedding(content);
            chunkEntities.add(
                    DocumentChunk.builder()
                            .document(document)
                            .chunkIndex(i)
                            .content(content)
                            .embedding(embedding)
                            .build()
            );
        }
        chunkRepository.saveAll(chunkEntities);
    }
}
