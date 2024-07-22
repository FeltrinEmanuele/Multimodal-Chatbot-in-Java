package ema.software.chatbot.config;

import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.huggingface.HuggingFaceEmbeddingModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModelName;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmbeddingModelConfiguration {




    public EmbeddingModel embeddingModelOpenAI() {
        return OpenAiEmbeddingModel.builder()
                .apiKey("INSERT YOUR API KEY")
                .modelName(OpenAiEmbeddingModelName.TEXT_EMBEDDING_ADA_002)
                .build();
    }



    @Bean
    public EmbeddingModel embeddingModelOllama() {
        return OllamaEmbeddingModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("mxbai-embed-large")
                .build();
    }



    public EmbeddingModel embeddingModelHuggingFace() {
        return HuggingFaceEmbeddingModel.builder()
                .modelId("sentence-transformers/all-MiniLM-L6-v2")
                .accessToken("INSERT YOUR ACCESS TOKEN")
                .waitForModel(true)
                .build();
    }



}
