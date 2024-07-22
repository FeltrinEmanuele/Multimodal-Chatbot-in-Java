package ema.software.chatbot.service;

import ema.software.chatbot.controller.dto.ChatRequest;

public interface ImageGeneratorService {
    byte[] generateImage(ChatRequest request);
}
