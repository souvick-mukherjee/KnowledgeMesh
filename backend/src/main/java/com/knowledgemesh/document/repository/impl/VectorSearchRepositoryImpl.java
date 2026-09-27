package com.knowledgemesh.document.repository.impl;

import com.knowledgemesh.document.entity.DocumentChunk;
import com.knowledgemesh.document.repository.VectorSearchRepository;
import com.knowledgemesh.retrieval.model.SearchResult;
import com.pgvector.PGvector;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VectorSearchRepositoryImpl implements VectorSearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<SearchResult> findSimilar(float[] embedding, int limit) {
        Session session = entityManager.unwrap(Session.class);
//        NativeQuery<DocumentChunk> query =
//            session.createNativeQuery(
//                """
//                SELECT *
//                FROM document_chunk
//                WHERE embedding IS NOT NULL
//                ORDER BY embedding <=> :embedding
//                LIMIT :limit
//                """,
//                DocumentChunk.class
//            );
//        query.setParameter("embedding", new PGvector(embedding));
//        query.setParameter("limit", limit);
//        return query.getResultList();
        Query<Object[]> query =
            session.createQuery(
                """
                SELECT
                    c.id,
                    c.document.id,
                    c.content,
                    c.chunkIndex,
                    1 - cosine_distance(c.embedding, :embedding)
                FROM DocumentChunk c
                WHERE c.embedding IS NOT NULL
                ORDER BY cosine_distance(c.embedding, :embedding)
                """,
                Object[].class
            );

        query.setParameter("embedding", embedding);
        query.setMaxResults(limit);

        return query.getResultList()
            .stream()
            .map(row -> new SearchResult(
                    (Long) row[0],
                    (Long) row[1],
                    (String) row[2],
                    (Integer) row[3],
                    ((Number) row[4]).doubleValue()
            ))
            .toList();
    }
}
