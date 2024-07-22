package ema.software.chatbot.service;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class MultipartFileUtil {

    public static MultipartFile stringToMultipartFile(String content, String fileName) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
        return new MockMultipartFile(fileName, fileName, "text/plain", inputStream);
    }
}
