package com.knowledgemesh.document.repository;

import com.knowledgemesh.document.entity.DocumentChunk;
import com.knowledgemesh.retrieval.model.SearchResult;

import java.util.List;

public interface VectorSearchRepository {
    List<SearchResult> findSimilar(
            float[] embedding,
            int limit
    );
}
