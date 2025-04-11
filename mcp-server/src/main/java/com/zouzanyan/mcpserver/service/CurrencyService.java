package com.zouzanyan.mcpserver.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Date;

/**
 * @author: zouzanyan
 * @description: TODO
 * @date: 2025/4/11 15:11
 * @version: 1.0
 */
@Service
public class CurrencyService {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyService.class);
    private static final String BASE_URL = "https://api.coingecko.com/api/v3";

    private final RestClient restClient;

    public CurrencyService() {
        this.restClient = RestClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader("Accept", "application/json")
//                .defaultHeader("User-Agent", "Your-App-Name/1.0") // 建议修改为你的应用名称
                .build();
    }

    // 定义 CoinGecko API 返回价格数据的 DTO
    public record CoinPrice(String id, String symbol, String name, MarketData market_data) {
    }

    public record MarketData(CurrentPrice current_price) {
    }

    public record CurrentPrice(double usd) {
    }

    @Tool(description = "获取虚拟币价格")
    public String getPrice(@ToolParam(description = "虚拟币符号") String symbol) {
        logger.info("需要查询的虚拟币价格: {}", symbol);
        try {
            // CoinGecko API 使用的是 ID 而不是符号，我们需要将符号转换为 ID
            // 这里为了简化，直接假设传入的是 CoinGecko API 可识别的 ID (例如: bitcoin, ethereum)
            // 在实际应用中，你可能需要一个映射表或者调用 CoinGecko API 获取币种列表进行转换

            CoinPrice coinPrice = this.restClient.get()
                    .uri("/coins/{id}", symbol.toLowerCase())
                    .retrieve()
                    .body(CoinPrice.class);

            if (coinPrice != null && coinPrice.market_data() != null && coinPrice.market_data().current_price() != null) {
                return symbol.toUpperCase() + " 的价格 (USD): " + coinPrice.market_data().current_price().usd();
            } else {
                return "无法获取 " + symbol.toUpperCase() + " 的价格信息。";
            }

        } catch (Exception e) {
            logger.error("获取 " + symbol.toUpperCase() + " 价格时发生错误: ", e);
            return "获取 " + symbol.toUpperCase() + " 价格失败。";
        }
    }

    // 你可以添加更多对接 CoinGecko API 的方法，例如获取列表、历史数据等
}