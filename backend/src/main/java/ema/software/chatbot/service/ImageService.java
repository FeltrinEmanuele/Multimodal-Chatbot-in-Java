package ema.software.chatbot.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    void processImage(MultipartFile file, int memoryId) throws IOException;
}
