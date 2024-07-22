package ema.software.chatbot.config;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.rag.RetrievalAugmentor;
import dev.langchain4j.service.AiServices;
import ema.software.chatbot.service.RAGAssistant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RAGAssistantConfiguration {

    private final ChatLanguageModel chatLanguageModel;
    private final RetrievalAugmentor retrievalAugmentor;

    public RAGAssistantConfiguration(ChatLanguageModel chatLanguageModel, RetrievalAugmentor retrievalAugmentor){
        this.chatLanguageModel = chatLanguageModel;
        this.retrievalAugmentor =  retrievalAugmentor;
    }

    @Bean
    public RAGAssistant ragAssistant() {


        return AiServices.builder(RAGAssistant.class)
                .chatLanguageModel(chatLanguageModel)
                .retrievalAugmentor(retrievalAugmentor)
                .chatMemoryProvider(memoryId -> MessageWindowChatMemory.withMaxMessages(10))
                .build();
    }


}
