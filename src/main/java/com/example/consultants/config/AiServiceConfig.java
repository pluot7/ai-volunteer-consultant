package com.example.consultants.config;

import com.example.consultants.aiservice.ConsultantService;
import com.example.consultants.tool.ReservationTool;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.service.AiServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class AiServiceConfig {

    @Bean
    public ConsultantService consultantService(
            ChatLanguageModel ollamaChatModel,
            StreamingChatLanguageModel ollamaStreamingChatModel,
            ChatMemoryProvider chatMemoryProvider,
            ContentRetriever contentRetriever,
            ReservationTool reservationTool) {

        return AiServices.builder(ConsultantService.class)
                .chatLanguageModel(ollamaChatModel)
                .streamingChatLanguageModel(ollamaStreamingChatModel)
                .chatMemoryProvider(chatMemoryProvider)
                .contentRetriever(contentRetriever)
                // TODO: qwen3:8b Q4_K_M 量化版不支持 Tools，先禁用
                // .tools(reservationTool)
                .build();
    }
}