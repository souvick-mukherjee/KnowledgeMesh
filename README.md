# KnowledgeMesh

An enterprise-grade Retrieval-Augmented Generation (RAG) platform built with Spring Boot, PostgreSQL, Ollama, and local LLMs.

The goal of this project is to create a centralized AI-powered knowledge assistant capable of ingesting and searching information from multiple enterprise sources such as PDFs, GitHub repositories, Jira tickets, Confluence pages, and internal documentation.

---

## Overview

Organizations store knowledge across multiple systems, making it difficult to locate relevant information quickly.

This project aims to solve that problem by combining:

* Document ingestion
* Semantic search
* Vector embeddings
* Hybrid retrieval
* Local LLM inference
* Source citations
* Confidence scoring

into a unified knowledge retrieval platform.

---

## Architecture

```text
Documents (PDFs, GitHub, Jira, Confluence)
                    │
                    ▼
            Content Extraction
                    │
                    ▼
              Text Chunking
                    │
                    ▼
           Embedding Generation
                    │
                    ▼
          PostgreSQL + pgvector
                    │
                    ▼
            Semantic Retrieval
                    │
                    ▼
             Local LLM (Ollama)
                    │
                    ▼
         Answer + Sources + Confidence
```

---

## Tech Stack

### Backend

* Java 21
* Spring Boot
* Spring Data JPA
* Flyway
* PostgreSQL
* pgvector

### AI / ML

* Ollama
* Qwen
* Nomic Embed Text

### Document Processing

* Apache PDFBox

### Future Integrations

* GitHub
* Jira
* Confluence
* Slack

---

## Features

### Current

* PDF document upload
* PDF text extraction
* Text chunking
* PostgreSQL persistence
* Flyway database migrations

### Planned

* Embedding generation using Ollama
* Vector storage with pgvector
* Semantic search
* Retrieval-Augmented Generation (RAG)
* Hybrid Search (BM25 + Vector Search)
* Metadata filtering
* Query rewriting
* Result reranking
* Source citations
* Confidence scoring
* GitHub integration
* Jira integration
* Agent-based retrieval workflows

---

## Project Structure

```text
backend
│
├── document
│   ├── controller
│   ├── dto
│   ├── entity
│   ├── repository
│   └── service
│
├── ingestion
│
├── embedding
│
├── retrieval
│
├── llm
│
├── chat
│
├── config
│
└── common
```

---

## Getting Started

### Prerequisites

* Java 21
* Maven
* PostgreSQL
* Ollama

### Clone Repository

```bash
git clone https://github.com/<your-username>/enterprise-knowledge-agent.git
cd enterprise-knowledge-agent
```

### Create Database

```sql
CREATE DATABASE knowledge_agent;
```

### Configure Application

Update:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/knowledge_agent
    username: postgres
    password: postgres
```

### Run Application

```bash
mvn spring-boot:run
```

---

## Development Roadmap

### Phase 1 - Document Ingestion

* [x] Database setup
* [x] Flyway migrations
* [x] PDF text extraction
* [x] Chunking service
* [ ] Upload API

### Phase 2 - Embeddings

* [ ] Ollama integration
* [ ] Embedding generation
* [ ] Vector storage

### Phase 3 - Retrieval

* [ ] Semantic search
* [ ] Similarity ranking
* [ ] Top-K retrieval

### Phase 4 - RAG

* [ ] Context construction
* [ ] Prompt orchestration
* [ ] LLM response generation

### Phase 5 - Enterprise Features

* [ ] Hybrid retrieval
* [ ] Source attribution
* [ ] Confidence scoring
* [ ] Metadata filtering

### Phase 6 - Agentic Workflows

* [ ] GitHub connector
* [ ] Jira connector
* [ ] Multi-tool retrieval agents

---

## Why This Project?

Most RAG demonstrations stop at:

```text
Upload PDF
↓
Ask Question
↓
Get Answer
```

This project focuses on building a production-oriented enterprise knowledge platform that demonstrates:

* Retrieval-Augmented Generation
* Vector databases
* Search systems
* Agentic workflows
* Enterprise integrations
* Backend architecture

---

## License

MIT License
