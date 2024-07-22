package ema.software.chatbot.controller;

import ema.software.chatbot.controller.dto.ChatRequest;
import ema.software.chatbot.controller.dto.ChatResponse;
import ema.software.chatbot.service.GenAIService;
import ema.software.chatbot.service.ImageGeneratorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:4200")
@Slf4j
@RestController
@RequestMapping("/generate")
public class ImageGeneratorController {

    private final ImageGeneratorService imageGeneratorService;
    private final GenAIService genAIService;

    public ImageGeneratorController(ImageGeneratorService imageGeneratorService, GenAIService genAIService){
        this.imageGeneratorService = imageGeneratorService;
        this.genAIService = genAIService;
    }

    @PostMapping("/image")
    public byte[] generateImage(@RequestBody ChatRequest request) {
        log.info("/generate/image Controller BEFORE generateImage, memoryId = {}", request.memoryId());
        log.info("/generate/image Controller BEFORE generateImage, question = {}", request.question());

        String prompt = "give me a short description of this: ";
        String newQuestion = prompt + request.question();
        String response = genAIService.getResponseExtended(new ChatRequest(newQuestion, request.memoryId())).response();
        log.info("Prompt passed into the image generation model /generate/image Controller AFTER generateImage, response = {}", response);
        return imageGeneratorService.generateImage(new ChatRequest(request.question() + response, request.memoryId()));
    }
}
