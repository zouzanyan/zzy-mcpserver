package com.zouzanyan.mcpclient.controller;

import io.modelcontextprotocol.client.McpAsyncClient;
import jakarta.annotation.Nullable;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author: zouzanyan
 * @description: TODO
 * @date: 2025/4/10 10:51
 * @version: 1.0
 */
@RestController
public class ChatController {


    @Autowired
    @Qualifier("openAiChatClient")
    private ChatClient chatClient;

    @Resource
    OpenAiChatModel openAiChatModel;


    @GetMapping("/ai/generate")
    public Map<String, Object> generate(@RequestParam(value = "message", defaultValue = "告诉我一个笑话") String message) {
        String content = chatClient.prompt(message).call().content();
        return Map.of("generation", content != null ? content : "");
    }

//    @GetMapping("/ai/generateStream")
//    public Flux<ChatResponse> generateStream(@RequestParam(value = "message", defaultValue = "告诉我一个笑话") String message) {
////        Prompt prompt = new Prompt(new UserMessage(message));
//        return chatClient.prompt(message).stream().chatResponse();
//    }


    @GetMapping("/ai/generateStream")
    public SseEmitter streamChat(@RequestParam String message) {
        // 创建 SSE 发射器，设置超时时间（例如 1 分钟）
        SseEmitter emitter = new SseEmitter(60_000L);
        // 创建 Prompt 对象
        Prompt prompt = new Prompt(new UserMessage(message));
        // 订阅流式响应
        openAiChatModel.stream(prompt).subscribe(response -> {
            try {
                String content = response.getResult().getOutput().getText();
                System.out.print(content);
                // 发送 SSE 事件
                emitter.send(SseEmitter.event().data(content).id(String.valueOf(System.currentTimeMillis())).build());
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        }, emitter::completeWithError, emitter::complete);
        // 处理客户端断开连接
        emitter.onCompletion(() -> {
            // 可在此处释放资源
            System.out.println("SSE connection completed");
        });
        emitter.onTimeout(() -> {
            emitter.complete();
            System.out.println("SSE connection timed out");
        });
        return emitter;
    }

}
