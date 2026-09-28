package com.knowledgemesh.chat.controller;

import com.knowledgemesh.chat.dto.RagResponse;
import com.knowledgemesh.chat.service.RagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {
    private final RagService ragService;

    @GetMapping
    RagResponse chat(@RequestParam String question) {
        return ragService.answer(question);
    }
}
