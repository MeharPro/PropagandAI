package com.elssieAi.PropagandAi.Config.Advisors;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.preretrieval.query.expansion.MultiQueryExpander;
import org.springframework.ai.rag.preretrieval.query.transformation.CompressionQueryTransformer;
import org.springframework.ai.rag.preretrieval.query.transformation.QueryTransformer;
import org.springframework.ai.rag.preretrieval.query.transformation.TranslationQueryTransformer;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.ai.vectorstore.filter.Filter.Expression;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;

import java.util.List;

@Configuration
public class RetrievalAugmentationAdvisorConfig {

//    private String sourceFileName = "default_file_name";

//    public void setSourceFileName(String sourceFileName) {
//        this.sourceFileName = sourceFileName;
//    }

    @Bean
    public RetrievalAugmentationAdvisor retrievalAugmentationAdvisor(ChatClient.Builder chatClientBuilder, @Qualifier("applicationTaskExecutor") TaskExecutor taskExecutor, VectorStore vectorStore) {
//        FilterExpressionBuilder b = new FilterExpressionBuilder();
//        Expression expression = b.eq("sourceFile", sourceFileName).build();

        var documentRetriever = VectorStoreDocumentRetriever.builder()
                .vectorStore(vectorStore)
//                .filterExpression(expression)
                .similarityThreshold(0.30)
                .topK(3)
                .build();

        List<QueryTransformer> queryTransformers = List.of(
                TranslationQueryTransformer.builder()
                        .chatClientBuilder(chatClientBuilder.build().mutate())
                        .targetLanguage("english")
                        .build(),

                CompressionQueryTransformer.builder()
                        .chatClientBuilder(chatClientBuilder)
                        .build()
        );

        var queryExpander = MultiQueryExpander.builder()
                .chatClientBuilder(chatClientBuilder.build().mutate())
                .numberOfQueries(3)
                .build();


        return RetrievalAugmentationAdvisor.builder()
                .documentRetriever(documentRetriever)
                .queryTransformers(queryTransformers)
                .queryExpander(queryExpander)
                .taskExecutor(taskExecutor)
                .build();
    }
}