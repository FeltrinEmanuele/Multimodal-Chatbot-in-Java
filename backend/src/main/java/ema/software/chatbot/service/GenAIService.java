package ema.software.chatbot.service;

import ema.software.chatbot.controller.dto.ChatRequest;
import ema.software.chatbot.controller.dto.ChatResponse;

public interface GenAIService {

    ChatResponse getResponseExtended(ChatRequest request);

}
