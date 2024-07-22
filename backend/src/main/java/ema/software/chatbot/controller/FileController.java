package ema.software.chatbot.controller;

import ema.software.chatbot.service.AudioService;
import ema.software.chatbot.service.FileService;
import ema.software.chatbot.service.ImageService;
import ema.software.chatbot.service.MultipartFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@CrossOrigin("http://localhost:4200")
@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;

    private final AudioService audioService;
    private final ImageService imageService;

    public FileController(FileService fileService, AudioService audioService, ImageService imageService){
        this.fileService = fileService;
        this.audioService = audioService;
        this.imageService = imageService;

    }

    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file, @RequestParam int memoryId) throws IOException {


        String fileType = file.getContentType();

        switch (fileType) {
            case "application/pdf":
                log.info("/upload Controller BEFORE saveToEmbeddingDatabase, memoryid = {}", memoryId);
                fileService.saveToEmbeddingDatabase(file, memoryId);
                log.info("/upload Controller AFTER saveToEmbeddingDatabase, memoryid = {}", memoryId);
                break;
            case "audio/wav":
                audioService.processAudio(file, memoryId);
                break;
            case "image/png", "image/jpeg":
                imageService.processImage(file, memoryId);
                break;
            case null:
                break;
            default:
                throw new UnsupportedOperationException("File type not supported: " + fileType);
        }

        return "Embeddings Saved";
    }



}
