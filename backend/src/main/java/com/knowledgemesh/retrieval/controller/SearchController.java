package com.knowledgemesh.retrieval.controller;

import com.knowledgemesh.document.entity.DocumentChunk;
import com.knowledgemesh.retrieval.model.SearchResult;
import com.knowledgemesh.retrieval.service.SemanticSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    private final SemanticSearchService searchService;

    @GetMapping
    public List<SearchResult> search(
            @RequestParam String query,
            @RequestParam(defaultValue = "5") int limit) {

        return searchService.search(query, limit);
    }
}
