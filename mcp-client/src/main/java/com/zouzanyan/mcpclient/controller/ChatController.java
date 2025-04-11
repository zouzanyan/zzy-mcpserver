package com.zouzanyan.mcpclient.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Map;

/**
 * @author: zouzanyan
 * @description: TODO
 * @date: 2025/4/10 10:51
 * @version: 1.0
 */
@RestController
public class ChatController {


    @Autowired
    @Qualifier("ollamaChatClient")
    private ChatClient chatClient;


    @GetMapping("/ai/generate")
    public Map<String, Object> generate(@RequestParam(value = "message", defaultValue = "告诉我一个笑话") String message) {
        String content = chatClient.prompt(message).call().content();
        return Map.of("generation", content != null ? content : "");
    }

    @GetMapping("/ai/generateStream")
    public Flux<ChatResponse> generateStream(@RequestParam(value = "message", defaultValue = "告诉我一个笑话") String message) {
        Prompt prompt = new Prompt(new UserMessage(message));
        return chatClient.prompt(prompt).stream().chatResponse();
    }
}
