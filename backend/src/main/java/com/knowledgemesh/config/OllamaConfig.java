package com.knowledgemesh.config;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class OllamaConfig {
    @Bean
    EmbeddingModel embeddingModel(
            @Value("${ai.ollama.base-url}") String baseUrl,
            @Value("${ai.embedding.model}") String modelName
    ) {

        return OllamaEmbeddingModel.builder()
                .baseUrl(baseUrl)
                .modelName(modelName)
                .build();
    }

    @Bean
    public ChatModel chatLanguageModel(
            @Value("${ai.ollama.base-url}") String baseUrl,
            @Value("${ai.chat.model}") String modelName
    ) {
        return OllamaChatModel.builder()
                .baseUrl(baseUrl)
                .modelName(modelName)
                .timeout(Duration.ofMinutes(5))
                .build();
    }
}
