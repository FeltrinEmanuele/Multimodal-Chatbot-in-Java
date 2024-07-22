package ema.software.chatbot.service;

import ema.software.chatbot.controller.dto.ChatRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class ImageGeneratorServiceImpl implements ImageGeneratorService{

    @Override
    public byte[] generateImage(ChatRequest request) {



        log.info(request.question());
        // Extract the prompt from the request
        String prompt = request.question();

        // Set up the RestTemplate and headers
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Set up the body with the prompt
        Map<String, String> body = new HashMap<>();
        body.put("prompt", prompt);

        // Create the request entity
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(body, headers);


        log.info("Calling Python service to generate image with prompt: {}", prompt);
        log.info("Calling Python service to generate image with prompt: {}", requestEntity);
        // Call the Python service and get the generated image
        byte[] generatedImage = restTemplate.postForObject("http://localhost:5000/generateImage", requestEntity, byte[].class);

        // Return the generated image
        return generatedImage;
    }
}
