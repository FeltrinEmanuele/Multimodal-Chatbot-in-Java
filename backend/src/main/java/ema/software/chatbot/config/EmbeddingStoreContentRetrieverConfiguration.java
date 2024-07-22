package ema.software.chatbot.config;

import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.filter.Filter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static dev.langchain4j.store.embedding.filter.MetadataFilterBuilder.metadataKey;

@Slf4j
@Configuration
public class EmbeddingStoreContentRetrieverConfiguration {

    private final EmbeddingStore<TextSegment> embeddingStore;
    private final EmbeddingModel embeddingModel;


    public EmbeddingStoreContentRetrieverConfiguration(EmbeddingStore<TextSegment> embeddingStore, EmbeddingModel embeddingModel) {
        this.embeddingStore = embeddingStore;
        this.embeddingModel = embeddingModel;
    }


    @Bean
    public EmbeddingStoreContentRetriever embeddingStoreContentRetriever(){
        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .dynamicFilter(query -> {

                    if (query.metadata() == null || query.metadata().chatMemoryId() == null) {
                        log.warn("BEAN - EmbeddingStoreContentRetriever, Query metadata or chatMemoryId is null");
                    }

                    String chatMemoryId = query.metadata().chatMemoryId().toString();

                    log.info("BEAN - EmbeddingStoreContentRetriever, Applying dynamic filter with memoryId: {}", chatMemoryId);
                    log.info("BEAN - EmbeddingStoreContentRetriever, actual return value: {}",( metadataKey("memoryId").isEqualTo(chatMemoryId)));
                    Filter filter = metadataKey("memoryId").isEqualTo(Integer.parseInt(chatMemoryId));
                    log.info("BEAN - EmbeddingStoreContentRetriever, Filter is: {}", filter);

                    return filter;

                })
                //.maxResults(10)
                //.minScore(0.2)
                .build();
    }


}
