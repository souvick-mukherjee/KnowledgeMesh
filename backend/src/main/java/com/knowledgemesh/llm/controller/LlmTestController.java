package com.knowledgemesh.llm.controller;

import com.knowledgemesh.llm.service.LlmService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test/llm")
@RequiredArgsConstructor
public class LlmTestController {

    private final LlmService llmService;

    @GetMapping
    public String generate(
            @RequestParam String prompt
    ) {
        return llmService.generate(prompt);
    }
}
