package com.knowledgemesh.document.repository;

import com.knowledgemesh.document.entity.DocumentChunk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DocumentChunkRepository extends JpaRepository<DocumentChunk, Long> {
    List<DocumentChunk> findByDocumentId(
            Long documentId
    );

//    @Query(
//        value = """
//            SELECT *
//            FROM document_chunk
//            WHERE embedding IS NOT NULL
//            ORDER BY embedding <=> CAST(:embedding AS vector)
//            LIMIT :limit
//            """,
//        nativeQuery = true
//    )
//    List<DocumentChunk> findSimilarChunks(
//            @Param("embedding") float[] embedding,
//            @Param("limit") int limit);
}
