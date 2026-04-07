package com.example.consultants.config;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.ClassPathResourceDocumentLoader;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class DocumentInitializer implements CommandLineRunner {

    @Autowired
    private EmbeddingStoreIngestor embeddingStoreIngestor;

    @Override
    public void run(String... args) throws Exception {
        try {
            // 使用 ClassPathResource 加载 resources/content 目录下的PDF文档
            ClassPathResource resource = new ClassPathResource("content");
            
            if (!resource.exists()) {
                log.warn("未找到 content 目录，请将PDF文件放入 src/main/resources/content/ 目录");
                return;
            }

            List<Document> documents = ClassPathResourceDocumentLoader.loadDocuments(
                    "content",
                    new ApachePdfBoxDocumentParser()
            );

            if (documents.isEmpty()) {
                log.warn("未找到任何PDF文档，请将PDF文件放入 src/main/resources/content/ 目录");
                return;
            }

            log.info("开始向量化 {} 个文档...", documents.size());
            embeddingStoreIngestor.ingest(documents);
            log.info("文档向量化完成！");

        } catch (Exception e) {
            log.error("文档向量化失败: {}", e.getMessage(), e);
        }
    }
}