package ema.software.chatbot.service;


import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import org.springframework.web.multipart.MultipartFile;
import dev.langchain4j.data.document.Document;
import java.io.IOException;


public interface FileService {

    void saveToEmbeddingDatabase(MultipartFile file, int id) throws IOException;

    Document transformToDocument(MultipartFile file) throws IOException;
    Document putMetadataInDocument(Document document, int id, String metadata);
    void ingestDocument(Document document, int segmentSize, int overlapSize);
    DocumentSplitter splitDocument(Document document, int segmentSize, int overlapSize);

}
