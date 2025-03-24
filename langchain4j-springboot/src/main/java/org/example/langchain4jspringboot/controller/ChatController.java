package org.example.langchain4jspringboot.controller;

import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.community.model.dashscope.QwenStreamingChatModel;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.chat.response.StreamingChatResponseHandler;
import dev.langchain4j.service.TokenStream;
import org.example.langchain4jspringboot.config.AiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/ai")
public class ChatController {

    @Autowired
    QwenChatModel qwenChatModel;

    @Autowired
    ChatLanguageModel ollamaChatModel;

    @Autowired
    QwenStreamingChatModel qwenStreamingChatModel;

    @Autowired
    AiConfig.Assiatant assiatant;

    @Autowired
    AiConfig.AssiatantUnique assiantUnique;

    @RequestMapping("/chat")
    public String test(@RequestParam(defaultValue = "你好") String message) {
        String chat = qwenChatModel.chat(message);
        return chat;
    }

    @RequestMapping("/chat_ollama")
    public String chatOllama(@RequestParam(defaultValue = "你好")String message) {
        String chat = ollamaChatModel.chat(message);
        return chat;
    }

    @RequestMapping(value = "/stream", produces = "text/stream;charset=UTF-8")
    public Flux<String> stream(@RequestParam(defaultValue = "你好")String message) {
        Flux<String> flux = Flux.create(fluxSink -> {

            qwenStreamingChatModel.chat(message, new StreamingChatResponseHandler() {
                @Override
                public void onPartialResponse(String s) {
                    fluxSink.next(s);
                }

                @Override
                public void onCompleteResponse(ChatResponse chatResponse) {
                    fluxSink.complete();
                }

                @Override
                public void onError(Throwable throwable) {
                    fluxSink.error(throwable);
                }
            });
        });
        return flux;
    }

    @RequestMapping(value = "/memory_chat")
    public String memoryChat(@RequestParam(defaultValue = "我是amumu") String message) {
        return assiatant.chat(message);
    }

    @RequestMapping(value = "/memory_stream_chat", produces = "text/stream;charset=UTF-8")
    public Flux<String> memoryStreamChat(@RequestParam(defaultValue = "我是谁")String message) {
        TokenStream stream = assiatant.stream(message);

        return Flux.create(sink -> {
            stream.onPartialResponse(s -> sink.next(s))
                    .onCompleteResponse(s -> sink.complete())
                    .onError(sink::error)
                    .start();
        });
    }

    @RequestMapping(value = "/memoryId_chat")
    public String memoryIdChat(@RequestParam(defaultValue = "我是amumu") String message, Integer userId) {
        return assiantUnique.chat(userId, message);
    }

    @RequestMapping(value = "/memory_stream_chat", produces = "text/stream;charset=UTF-8")
    public Flux<String> memoryIdStreamChat(@RequestParam(defaultValue = "我是谁")String message, Integer userId) {
        TokenStream stream = assiantUnique.stream(userId, message);

        return Flux.create(sink -> {
            stream.onPartialResponse(s -> sink.next(s))
                    .onCompleteResponse(s -> sink.complete())
                    .onError(sink::error)
                    .start();
        });
    }
}
