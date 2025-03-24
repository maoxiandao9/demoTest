package org.example.langchain4jspringboot.config;

import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.service.*;
import org.example.langchain4jspringboot.service.ToolsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.spi.CharsetProvider;

@Configuration
public class AiConfig {

    public interface Assiatant {
        String chat(String message);
        // 流式响应
        TokenStream stream(String message);

        @SystemMessage("""
                您是“Tuling”航空公司的客户聊天支持代理。请以友好、乐于助人且愉快的方式来回复。
                您正在通过在线聊天系统与客户互动。
                在提供有关预订或取消预订的信息之前，您必须始终从用户处获取以下信息：预订号、客户姓名。
                请讲中文。
                今天的日期是 {{current_date}}.
                """)
        TokenStream stream(@UserMessage String message, @V("current_date") String currentDate);
    }

    @Bean
    public Assiatant assiant(ChatLanguageModel chatLanguageModel,
                             StreamingChatLanguageModel qwenStreamingChatModel, QwenChatModel qwenChatModel,
                             ToolsService toolsService) {
        ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);

        // 为Assistant创建动态代理对象 chat -> 对话内容存储ChatMemory -> 聊天记录ChatMemory取出来 -> 放入到当前对话中
        Assiatant assiatant = AiServices.builder(Assiatant.class)
                .tools(toolsService)
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
