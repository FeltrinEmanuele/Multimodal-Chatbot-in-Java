package ema.software.chatbot.service;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.DocumentTransformer;
import dev.langchain4j.data.document.parser.apache.tika.ApacheTikaDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentBySentenceSplitter;
import dev.langchain4j.data.document.splitter.DocumentSplitters;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.data.segment.TextSegmentTransformer;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
@Service
public class FileServiceImpl implements FileService{

    private final EmbeddingModel embeddingModel;
    private final EmbeddingStore<TextSegment> embeddingStore;


    public FileServiceImpl(EmbeddingStore<TextSegment> embeddingStore, EmbeddingModel embeddingModel){
        this.embeddingModel = embeddingModel;
        this.embeddingStore = embeddingStore;
    }




    @Override
    public void saveToEmbeddingDatabase(MultipartFile file, int id) throws IOException {


        Document document = transformToDocument(file);

        log.info("BEFORE putMetadataInDocument, document now has this metadata: {}", document.metadata());

        document = putMetadataInDocument(document, id, "memoryId");

        ingestDocument(document,1000,200);

    }

    @Override
    public Document transformToDocument(MultipartFile file) throws IOException {
        // Read the file content directly from the MultipartFile
        InputStream inputStream = file.getInputStream();

        // Use ApacheTikaDocumentParser to parse the InputStream and create the Document
        ApacheTikaDocumentParser parser = new ApacheTikaDocumentParser();
        return parser.parse(inputStream);

    }

    @Override
    public Document putMetadataInDocument(Document document, int id, String keyword) {
        document.metadata().put(keyword, id);
        log.info("putMetadataInDocument, document now has this metadata: {}", document.metadata());
        return document;
    }

    @Override
    public void ingestDocument(Document document, int segmentSize, int overlapSize) {

        //DocumentSplitter documentSplitter = new DocumentBySentenceSplitter(segmentSize, overlapSize);

        EmbeddingStoreIngestor embeddingStoreIngestor = EmbeddingStoreIngestor.builder()
            .documentSplitter(splitDocument(document, segmentSize, overlapSize))
            //.documentSplitter(documentSplitter)
            .embeddingModel(embeddingModel)
            .embeddingStore(embeddingStore)
            .build();

        embeddingStoreIngestor.ingest(document);
        log.info("ingestDocument, document and now has this metadata: {}", document.metadata());
    }

    @Override
    public DocumentSplitter splitDocument(Document document, int segmentSize, int overlapSize) {

        // Splitting the document
        List<TextSegment> segments = DocumentSplitters.recursive(segmentSize, overlapSize).split(document);

        // Log each TextSegment's metadata
        for (TextSegment segment : segments) {
            log.info("TEST - NOT IN THE REAL CODE, TextSegment metadata after splitting: {}", segment.metadata());
        }

        return DocumentSplitters.recursive(segmentSize, overlapSize);
    }



}
