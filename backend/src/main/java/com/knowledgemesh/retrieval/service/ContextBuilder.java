package com.knowledgemesh.retrieval.service;

import com.knowledgemesh.retrieval.model.SearchResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContextBuilder {
    public String build(List<SearchResult> results) {
        StringBuilder context = new StringBuilder();

        for(int i=0; i<results.size(); i++) {
            SearchResult result = results.get(i);

            context.append("[Source ")
                    .append(i+1)
                    .append("]\n");
            context.append(result.content())
                    .append("\n\n");
        }
        return context.toString();
    }
}
