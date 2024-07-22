package ema.software.chatbot.service;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;


public interface RAGAssistant {

    @SystemMessage(
            """
                   You are a helpful assistant that ONLY knows information that the user gave you.
                   You can ONLY use information provided from the user via Documents, Images, Audio, ecc..
                   If you can't answer the question using the data provided by the user, say that no information about that topic has been given to you.
            """
    )
    String chat(@MemoryId int memoryId, @UserMessage String userMessage);

}
