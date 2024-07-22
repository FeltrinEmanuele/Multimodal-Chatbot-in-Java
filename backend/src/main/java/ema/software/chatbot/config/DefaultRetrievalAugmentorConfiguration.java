package ema.software.chatbot.config;

import dev.langchain4j.rag.DefaultRetrievalAugmentor;
import dev.langchain4j.rag.content.injector.DefaultContentInjector;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultRetrievalAugmentorConfiguration {


    private final EmbeddingStoreContentRetriever embeddingStoreContentRetriever;
    private final DefaultContentInjector defaultContentInjector;

    public DefaultRetrievalAugmentorConfiguration(EmbeddingStoreContentRetriever embeddingStoreContentRetriever, DefaultContentInjector defaultContentInjector){
        this.embeddingStoreContentRetriever = embeddingStoreContentRetriever;
        this.defaultContentInjector = defaultContentInjector;
    }

    @Bean
    public DefaultRetrievalAugmentor defaultRetrievalAugmentor(){

        return DefaultRetrievalAugmentor.builder()
                .contentRetriever(embeddingStoreContentRetriever)
                .contentInjector(defaultContentInjector)
                .build();

    }


}
