package com.example.consultants.config;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class DocumentInitializer implements CommandLineRunner {

    @Autowired
    private EmbeddingStoreIngestor embeddingStoreIngestor;

    @Autowired
    private ResourcePatternResolver resourcePatternResolver;

    @Override
    public void run(String... args) throws Exception {
        try {
            // 加载 content 目录下的所有 PDF 文件
            Resource[] resources = resourcePatternResolver.getResources("classpath:content/*.pdf");
            
            if (resources == null || resources.length == 0) {
                log.warn("未找到任何PDF文档，请将PDF文件放入 src/main/resources/content/ 目录");
                return;
            }

            List<Document> documents = new ArrayList<>();
            ApachePdfBoxDocumentParser parser = new ApachePdfBoxDocumentParser();
            
            for (Resource resource : resources) {
                if (resource.exists()) {
                    try {
                        Document doc = parser.parse(resource.getInputStream());
                        documents.add(doc);
                        log.info("加载文档: {}", resource.getFilename());
                    } catch (Exception e) {
                        log.warn("加载文档失败 {}: {}", resource.getFilename(), e.getMessage());
                    }
                }
            }

            if (documents.isEmpty()) {
                log.warn("未找到任何可用的PDF文档");
                return;
            }

            log.info("开始向量化 {} 个文档...", documents.size());
            embeddingStoreIngestor.ingest(documents);
            log.info("文档向量化完成！");

        } catch (IOException e) {
            log.error("加载文档失败: {}", e.getMessage(), e);
        } catch (Exception e) {
            log.error("文档向量化失败: {}", e.getMessage(), e);
        }
    }
}