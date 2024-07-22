package ema.software.chatbot.service;

import ema.software.chatbot.controller.dto.ChatRequest;
import ema.software.chatbot.controller.dto.ChatResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GenAIServiceImpl implements GenAIService {


    private final RAGAssistant ragAssistant;

    public GenAIServiceImpl(RAGAssistant ragAssistant){
        this.ragAssistant = ragAssistant;
    }


    @Override
    public ChatResponse getResponseExtended(ChatRequest request) {
        log.info("getResponseExtended, memoryId = {}", request.memoryId());

        String response = ragAssistant.chat(request.memoryId(), request.question());
        log.info("getResponseExtended, generated response: {}", response);

        return new ChatResponse(response);
    }



}
