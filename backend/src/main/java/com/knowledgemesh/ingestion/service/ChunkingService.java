package com.knowledgemesh.ingestion.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.knowledgemesh.common.constants.ChunkConstants.CHUNK_OVERLAP;
import static com.knowledgemesh.common.constants.ChunkConstants.CHUNK_SIZE;

@Service
public class ChunkingService {

//    private static final int CHUNK_SIZE = 1000;
//    private static final int OVERLAP = 200;

    public List<String> chunk(String text) {
        List<String> chunks = new ArrayList<>();
        int start = 0;
        while (start < text.length()) {
            int end = Math.min(start + CHUNK_SIZE, text.length());
            if (end < text.length()) {
                while (end > start && !Character.isWhitespace(text.charAt(end))) {
                    end--;
                }
            }
            chunks.add(
                    text.substring(start, end)
            );
            start += (CHUNK_SIZE - CHUNK_OVERLAP);
        }
        return chunks;
    }
}