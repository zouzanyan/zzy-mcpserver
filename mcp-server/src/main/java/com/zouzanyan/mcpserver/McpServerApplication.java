package com.zouzanyan.mcpserver;

import com.zouzanyan.mcpserver.service.CurrencyService;
import com.zouzanyan.mcpserver.service.TimeService;
import com.zouzanyan.mcpserver.service.WService;
import com.zouzanyan.mcpserver.service.WeatherService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class McpServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(McpServerApplication.class, args);
    }

    @Bean
    public ToolCallbackProvider weatherTools(WeatherService weatherService, WService wService, TimeService timeService, CurrencyService currencyService) {
        return MethodToolCallbackProvider.builder().toolObjects(weatherService, wService, timeService, currencyService).build();
    }
}
