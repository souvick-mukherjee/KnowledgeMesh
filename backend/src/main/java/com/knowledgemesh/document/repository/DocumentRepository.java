package com.knowledgemesh.document.repository;

import com.knowledgemesh.document.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}
