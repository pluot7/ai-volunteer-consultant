package com.example.consultants.config;

import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.redis.RedisEmbeddingStore;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Slf4j
@Configuration
public class CommonConfig {

    @Autowired
    private ChatMemoryStore redisChatMemoryStore;

    // ================== Ollama 聊天模型 ==================
    @Bean
    public ChatLanguageModel ollamaChatModel() {
        return OllamaChatModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("qwen2.5:7b")
                .temperature(0.7)
                .timeout(Duration.ofMinutes(3))
                .build();
    }

    @Bean
    public StreamingChatLanguageModel ollamaStreamingChatModel() {
        return OllamaStreamingChatModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("qwen2.5:7b")
                .temperature(0.7)
                .timeout(Duration.ofMinutes(3))
                .build();
    }

    // ================== Ollama 嵌入模型 ==================
    @Bean
    public EmbeddingModel embeddingModel() {
        return OllamaEmbeddingModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("nomic-embed-text")
                .timeout(Duration.ofMinutes(2))
                .build();
    }

    // ================== Redis 向量存储 ==================
    @Bean
    public EmbeddingStore<TextSegment> redisEmbeddingStore() {
        return RedisEmbeddingStore.builder()
                .host("localhost")
                .port(6379)
                .dimension(768)  // nomic-embed-text的维度是768
                .build();
    }

    // ================== 聊天记忆提供者 ==================
    @Bean
    public ChatMemoryProvider chatMemoryProvider() {
        return memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId)
                .maxMessages(20)
                .chatMemoryStore(redisChatMemoryStore)
                .build();
    }

    // ================== 内容检索器 ==================
    @Bean
    public ContentRetriever contentRetriever(
            EmbeddingStore<TextSegment> redisEmbeddingStore,
            EmbeddingModel embeddingModel) {
        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(redisEmbeddingStore)
                .embeddingModel(embeddingModel)
                .maxResults(3)
                .minScore(0.5)
                .build();
    }

    // ================== 文档摄入器 ==================
    @Bean
    public EmbeddingStoreIngestor embeddingStoreIngestor(
            EmbeddingStore<TextSegment> redisEmbeddingStore,
            EmbeddingModel embeddingModel) {
        return EmbeddingStoreIngestor.builder()
                .documentSplitter(DocumentSplitters.recursive(500, 100))
                .embeddingModel(embeddingModel)
                .embeddingStore(redisEmbeddingStore)
                .build();
    }
}
