package ema.software.chatbot.controller;

import ema.software.chatbot.controller.dto.ChatRequest;
import ema.software.chatbot.controller.dto.ChatResponse;
import ema.software.chatbot.service.GenAIService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:4200")
@Slf4j
@RestController
@RequestMapping("/api/chat")
public class GenerativeController {

    private final GenAIService genAIService;

    public GenerativeController(GenAIService genAIService) {
        this.genAIService = genAIService;
    }


    @PostMapping("/extended")
    public ChatResponse getChatResponseExtended(@RequestBody ChatRequest request) {

        log.info("/extended Controller BEFORE getResponseExtended, memoryid = {}", request.memoryId());
        return genAIService.getResponseExtended(request);

    }




}
