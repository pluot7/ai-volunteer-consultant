package com.example.consultants.config;

import com.example.consultants.aiservice.ConsultantService;
import com.example.consultants.tool.ReservationTool;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.service.AiServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
                .tools(reservationTool)
                .build();
    }
}