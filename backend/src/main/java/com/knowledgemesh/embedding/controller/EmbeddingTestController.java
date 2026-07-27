package com.knowledgemesh.embedding.controller;

import com.knowledgemesh.embedding.service.EmbeddingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class EmbeddingTestController {

    private final EmbeddingService embeddingService;

    @GetMapping("/embedding")
    public int embeddingLength(
            @RequestParam String text
    ) {

        return embeddingService
                .generateEmbedding(text)
                .length;
    }
}