package ema.software.chatbot.config;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.chroma.ChromaEmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmbeddingStoreConfiguration {



    @Bean
    public EmbeddingStore<TextSegment> embeddingStoreInMemory(){
        return new InMemoryEmbeddingStore<>();
    }



    public EmbeddingStore<TextSegment> embeddingStoreChroma(){
        return ChromaEmbeddingStore.builder()
                .baseUrl("http://localhost:8000/")

                .build();
    }


    /*
    public EmbeddingStore<TextSegment> embeddingStorePGVector(){
        return PgVectorEmbeddingStore.builder()
                .host("http://localhost:5432/")
                .build();
    }
    */








}
