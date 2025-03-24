package org.example.langchain4jspringboot.config;

import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.service.UserMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.spi.CharsetProvider;

@Configuration
public class AiConfig {

    public interface Assiatant {
        String chat(String message);
        // 流式响应
        TokenStream stream(String message);
    }

    @Bean
    public Assiatant assiant(ChatLanguageModel chatLanguageModel,
                             StreamingChatLanguageModel qwenStreamingChatModel, QwenChatModel qwenChatModel) {
        ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);

        // 为Assistant创建动态代理对象 chat -> 对话内容存储ChatMemory -> 聊天记录ChatMemory取出来 -> 放入到当前对话中
        Assiatant assiatant = AiServices.builder(Assiatant.class)
                .chatLanguageModel(qwenChatModel)
                .streamingChatLanguageModel(qwenStreamingChatModel)
                .chatMemory(chatMemory)
                .build();

        return assiatant;
    }

    public interface AssiatantUnique {
        String chat(@MemoryId int memoryId, @UserMessage String message);
        // 流式响应
        TokenStream stream(@MemoryId int memoryId, @UserMessage String message);
    }

    @Bean
    public AssiatantUnique assiantUnique(ChatLanguageModel chatLanguageModel,
                             StreamingChatLanguageModel qwenStreamingChatModel, QwenChatModel qwenChatModel) {

        AssiatantUnique assiatant = AiServices.builder(AssiatantUnique.class)
                .chatLanguageModel(qwenChatModel)
                .streamingChatLanguageModel(qwenStreamingChatModel)
                .chatMemoryProvider(memoryId ->
                        MessageWindowChatMemory.builder().maxMessages(10)
                                .id(memoryId).build())
                .build();

        return assiatant;
    }

    @Bean
    public AssiatantUnique assiatantUniqueStore(ChatLanguageModel chatLanguageModel,
                                                StreamingChatLanguageModel qwenStreamingChatModel, QwenChatModel qwenChatModel) {
        PersistentChatMemoryStore store = new PersistentChatMemoryStore();

        ChatMemoryProvider chatMemoryProvider = memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId)
                .chatMemoryStore(store)
                .maxMessages(10)
                .build();

        AssiatantUnique assiatant = AiServices.builder(AssiatantUnique.class)
                .chatLanguageModel(qwenChatModel)
                .streamingChatLanguageModel(qwenStreamingChatModel)
                .chatMemoryProvider(chatMemoryProvider)
                .build();
        return assiatant;
    }
}
