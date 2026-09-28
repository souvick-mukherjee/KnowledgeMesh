# KnowledgeMesh

> Enterprise Knowledge Retrieval & RAG Platform

KnowledgeMesh is a Java/Spring Boot-based enterprise knowledge retrieval platform that ingests documents, generates vector embeddings, stores them in PostgreSQL with pgvector, and uses semantic retrieval with a local LLM to produce grounded answers.

The project is being built incrementally to explore how reliable, production-oriented Retrieval-Augmented Generation (RAG) systems are designed.

## Current Status

### Implemented

- PDF document upload
- PDF text extraction with Apache PDFBox
- Overlapping text chunking
- PostgreSQL persistence
- Flyway database migrations
- PostgreSQL `pgvector`
- Local embeddings with Ollama + `nomic-embed-text`
- 768-dimensional vector storage
- Cosine-similarity semantic search
- Compact search-result DTOs
- Local LLM integration with Ollama + Qwen
- Basic RAG pipeline
- Context construction from retrieved chunks
- Grounded question answering
- Basic source metadata in responses

### In Progress

- Document deduplication
- Better source/document metadata
- Page-level citations
- Retrieval-quality improvements

### Planned

- Hybrid lexical + vector retrieval
- Result fusion
- Reranking
- Metadata filtering
- Query rewriting
- Confidence estimation
- Retrieval/answer evaluation
- Knowledge graph integration
- GitHub, Jira, Confluence and Slack connectors
- Agent/tool-based workflows
- Production observability

## Architecture

```text
PDF Upload
    |
    v
PDF Text Extraction
(Apache PDFBox)
    |
    v
Text Chunking
    |
    +--------------------+
    |                    |
    v                    v
PostgreSQL          Ollama Embeddings
Document/Chunks     nomic-embed-text
                         |
                         v
                     pgvector
                         |
                         |
User Question            |
    |                    |
    v                    |
Query Embedding          |
    |                    |
    +---------> Semantic Search
                    |
                    v
              Top-K Chunks
                    |
                    v
             Context Builder
                    |
                    v
              Ollama / Qwen
                    |
                    v
          Grounded Answer + Sources
```

## Technology Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Backend | Spring Boot |
| Persistence | Spring Data JPA / Hibernate |
| Database | PostgreSQL |
| Vector Search | PostgreSQL + pgvector |
| Migrations | Flyway |
| PDF Processing | Apache PDFBox |
| Embeddings | Ollama + `nomic-embed-text` |
| LLM | Ollama + Qwen |
| AI Integration | LangChain4j |
| Containers | Docker / Docker Compose |

## Project Structure

```text
enterprise-knowledge-agent/
├── README.md
├── docker-compose.yml
├── docs/
├── backend/
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/knowledgemesh/
│       │   ├── document/
│       │   ├── ingestion/
│       │   ├── embedding/
│       │   ├── retrieval/
│       │   ├── llm/
│       │   ├── chat/
│       │   ├── config/
│       │   └── common/
│       └── resources/
│           ├── application.yml
│           └── db/migration/
└── frontend/
```

## How the Current RAG Pipeline Works

### 1. Ingestion

A PDF is uploaded through:

```http
POST /api/documents/upload
Content-Type: multipart/form-data
```

The backend:

1. Validates the PDF.
2. Extracts text using PDFBox.
3. Splits the text into overlapping chunks.
4. Generates an embedding for every chunk.
5. Stores the document, chunks and embeddings in PostgreSQL.

### 2. Embeddings

The current embedding model is:

```text
nomic-embed-text
```

Embeddings are 768-dimensional and stored as:

```sql
embedding vector(768)
```

### 3. Semantic Retrieval

A user question is converted into an embedding and compared against stored chunk embeddings using cosine distance.

```text
Question
   |
   v
Query Embedding
   |
   v
Vector Similarity Search
   |
   v
Top-K Relevant Chunks
```

The search API returns metadata and content rather than exposing the raw embedding vector.

### 4. Generation

Retrieved chunks are assembled into context and passed to the local LLM with instructions to:

- use only the supplied context
- avoid inventing information
- state when the context does not contain enough information

Example:

```http
GET /api/chat?question=What technologies has Souvick used for backend development?
```

Example response:

```json
{
  "answer": "...",
  "sources": [
    {
      "documentId": 4,
      "chunkIndex": 0,
      "similarity": 0.70
    }
  ]
}
```

## API Endpoints

### Upload

```http
POST /api/documents/upload
```

Example:

```bash
curl -X POST http://localhost:8080/api/documents/upload   -F "file=@resume.pdf"
```

### Semantic Search

```http
GET /api/search?query=<question>&limit=5
```

Example:

```text
GET /api/search?query=backend technologies&limit=5
```

### RAG Chat

```http
GET /api/chat?question=<question>
```

Example:

```text
GET /api/chat?question=What technologies has Souvick used for backend development?
```

## Local Development

### Prerequisites

- Java 21
- Maven
- Docker / Docker Compose
- Ollama

### Start PostgreSQL

```bash
docker compose up -d
```

Current database configuration:

```text
Host: localhost
Port: 5433
Database: knowledge_agent
Username: postgres
Password: postgres
```

### Pull Ollama Models

```bash
ollama pull nomic-embed-text
ollama pull qwen3:8b
```

If the Qwen 8B model is too slow on local hardware, a smaller Qwen model can be used during development.

Verify:

```bash
ollama list
```

Ollama normally runs at:

```text
http://localhost:11434
```

### Run the Backend

Linux/macOS:

```bash
cd backend
./mvnw spring-boot:run
```

Windows:

```bash
cd backend
mvnw.cmd spring-boot:run
```

The backend normally runs at:

```text
http://localhost:8080
```

Flyway migrations run automatically on startup.

## Database Model

### Document

```text
Document
├── id
├── fileName
├── fileSize
├── contentType
├── status
└── uploadedAt
```

### DocumentChunk

```text
DocumentChunk
├── id
├── document_id
├── content
├── chunkIndex
└── embedding vector(768)
```

Relationship:

```text
Document 1 ─────── * DocumentChunk
```

## Roadmap

### Phase 1 — Foundation

- [x] Spring Boot backend
- [x] PostgreSQL
- [x] Flyway
- [x] PDF ingestion
- [x] PDF text extraction
- [x] Text chunking
- [x] Embedding generation
- [x] pgvector storage
- [x] Semantic search
- [x] Ollama LLM integration
- [x] Basic RAG

### Phase 2 — Retrieval Quality

- [ ] Content-based document deduplication
- [ ] Better chunking
- [ ] Source/document metadata
- [ ] Page-level metadata
- [ ] Hybrid lexical + vector retrieval
- [ ] Result fusion
- [ ] Reranking
- [ ] Retrieval evaluation dataset

### Phase 3 — Grounding & Reliability

- [ ] Stronger citations
- [ ] Page-level citations
- [ ] Confidence estimation
- [ ] Answer evaluation
- [ ] Retrieval evaluation
- [ ] Hallucination testing
- [ ] Observability

### Phase 4 — Enterprise Knowledge

- [ ] Metadata filtering
- [ ] Multiple document types
- [ ] GitHub integration
- [ ] Jira integration
- [ ] Confluence integration
- [ ] Slack integration
- [ ] Incremental synchronization
- [ ] Document versioning

### Phase 5 — Knowledge Graph

- [ ] Entity extraction
- [ ] Relationship extraction
- [ ] Knowledge graph storage
- [ ] Graph-based retrieval
- [ ] Graph + vector retrieval

### Phase 6 — Agentic Workflows

- [ ] Tool calling
- [ ] Retrieval tools
- [ ] GitHub/Jira tools
- [ ] Knowledge graph tools
- [ ] Multi-step workflows
- [ ] Agent execution tracing

## Design Principles

### Ground answers in evidence

The LLM is not treated as the source of truth. Retrieved documents provide the evidence used for generation.

### Keep retrieval and generation separate

```text
Embedding
    ↓
Retrieval
    ↓
Context Construction
    ↓
Generation
```

This keeps each stage independently testable.

### Prefer measurable improvements

Retrieval and generation changes should eventually be evaluated using a repeatable question set rather than only subjective inspection.

### Build locally first

Ollama provides local embeddings and generation, making experimentation inexpensive and keeping the initial AI pipeline inspectable.

### Evolve toward production architecture

The current implementation intentionally favors clarity. Deduplication, hybrid retrieval, reranking, evaluation, observability and enterprise integrations will be added incrementally.

## Learning Goals

This project is also a practical learning exercise covering:

- Embeddings
- Vector databases
- Similarity search
- Retrieval-Augmented Generation
- Prompt grounding
- Chunking strategies
- Hybrid retrieval
- Reranking
- Retrieval evaluation
- Knowledge graphs
- Agentic AI
- Tool calling
- Enterprise data integration

The goal is to understand the engineering decisions behind a reliable enterprise knowledge system, not simply to build a chatbot.

## Repository

https://github.com/souvick-mukherjee/KnowledgeMesh

## License

This project is currently intended as a personal learning and portfolio project.
