package ema.software.chatbot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@Slf4j
public class AudioServiceImpl implements AudioService{


    private final FileService fileService;
    private final MultipartFileUtil multipartFileUtil;

    public AudioServiceImpl(FileService fileService, MultipartFileUtil multipartFileUtil){
        this.fileService = fileService;
        this.multipartFileUtil = multipartFileUtil;
    }



    @Override
    public void processAudio(MultipartFile file, int memoryId) throws IOException {


        log.info("/upload Controller BEFORE transcribeAudio, memoryid = {}", memoryId);
        Path tempFilePath = Files.createTempFile("audio", file.getOriginalFilename());
        file.transferTo(tempFilePath.toFile());
        // Call the Python service to transcribe the audio
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource(tempFilePath.toFile()));
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        log.info(requestEntity.toString());
        String transcription = restTemplate.postForObject("http://localhost:5000/transcribe", requestEntity, String.class);
        log.info(transcription);
        Files.delete(tempFilePath);  // Clean up the temporary file
        log.info("/upload Controller AFTER transcribeAudio, memoryid = {}, transcription = {}", memoryId, transcription);
        MultipartFile transcriptionFile = multipartFileUtil.stringToMultipartFile(transcription, "transcription.txt");
        fileService.saveToEmbeddingDatabase(transcriptionFile, memoryId);


    }
}
