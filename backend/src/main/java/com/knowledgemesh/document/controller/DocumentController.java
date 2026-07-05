package com.knowledgemesh.document.controller;

import com.knowledgemesh.document.dto.UploadDocumentResponse;
import com.knowledgemesh.ingestion.service.DocumentProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentProcessor documentProcessor;

    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    ) public UploadDocumentResponse upload(
            @RequestParam("file")
            MultipartFile file
    ) {
        return documentProcessor.process(file);
    }
}
