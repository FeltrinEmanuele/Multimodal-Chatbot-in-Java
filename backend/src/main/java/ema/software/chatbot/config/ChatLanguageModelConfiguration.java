package ema.software.chatbot.config;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.huggingface.HuggingFaceChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiChatModelName;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatLanguageModelConfiguration {


    public ChatLanguageModel chatLanguageModelOpenAI() {
        return OpenAiChatModel.builder()
                .apiKey("INSERT YOUR API KEY")
                .modelName(OpenAiChatModelName.GPT_3_5_TURBO)
                .temperature(0.1)
                .build();
    }

    @Bean
    public ChatLanguageModel chatLanguageModelOllamaMeta() {
        return OllamaChatModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("llama3")
                .build();
    }




    public ChatLanguageModel chatLanguageModelHuggingFaceMeta() {
        return HuggingFaceChatModel.builder()
                .modelId("meta-llama/Meta-Llama-3-8B-Instruct")
                .accessToken("INSERT YOUR ACCESS TOKEN")
                .temperature(0.25)
                .build();
    }




    public ChatLanguageModel chatLanguageModelHuggingFaceMistral() {
        return HuggingFaceChatModel.builder()
                .modelId("mistralai/Mistral-7B-Instruct-v0.3")
                .accessToken("INSERT YOUR ACCESS TOKEN")
                .temperature(0.3)
                .build();
    }

}
