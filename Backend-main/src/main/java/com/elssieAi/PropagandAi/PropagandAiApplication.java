package com.elssieAi.PropagandAi;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@SpringBootApplication
public class PropagandAiApplication {
    public static void main(String[] args) {
		SpringApplication.run(PropagandAiApplication.class, args);
	}
}

@Configuration
class AppConfig {

//	@Bean
//	public VectorStore vectorStore(JdbcTemplate jdbcTemplate, EmbeddingModel embeddingModel) {
//		return PgVectorStore.builder(jdbcTemplate, embeddingModel)
//				.dimensions(1536)                    // Optional: defaults to model dimensions or 1536
//				.distanceType(COSINE_DISTANCE)       // Optional: defaults to COSINE_DISTANCE
//				.indexType(HNSW)                     // Optional: defaults to HNSW
//				.initializeSchema(true)              // Optional: defaults to false
//				.schemaName("public")                // Optional: defaults to "public"
//				.vectorTableName("vector_store")     // Optional: defaults to "vector_store"
//				.maxDocumentBatchSize(10000)         // Optional: defaults to 10000
//				.build();
//	}

	@Bean
	ChatMemory chatMemory() {
		return new InMemoryChatMemory();
	}
}