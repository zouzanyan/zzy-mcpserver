package com.zouzanyan.mcpclient.config;

import io.modelcontextprotocol.client.McpClient;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.mcp.customizer.McpAsyncClientCustomizer;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @author: zouzanyan
 * @description: TODO
 * @date: 2025/4/10 11:37
 * @version: 1.0
 */
@Configuration
public class ChatConfiguration {

    @Resource
    OpenAiChatModel openAiChatModel;
    @Resource
    ToolCallbackProvider tools;
    @Resource
    OllamaChatModel ollamaChatModel;


    @Bean("openAiChatClient")
    public ChatClient openAiChatClient() {
        return ChatClient.builder(openAiChatModel)
                .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
                .defaultTools(tools)
                .build();
    }

    @Bean("ollamaChatClient")
    public ChatClient ollamaChatClient() {
        return ChatClient.builder(ollamaChatModel)
                .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
                .defaultTools(tools)
                .build();
    }

}
