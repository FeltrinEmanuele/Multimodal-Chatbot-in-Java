package ema.software.chatbot.config;

import dev.langchain4j.rag.content.injector.DefaultContentInjector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.util.Arrays.asList;

@Configuration
public class DefaultContentInjectorConfiguration {

    @Bean
    public DefaultContentInjector defaultContentInjector(){

        return DefaultContentInjector.builder()
                //.metadataKeysToInclude(asList("memoryId", "index"))
                .build();

    }
}
