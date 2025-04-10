package com.zouzanyan.mcpserver.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

/**
 * @author: zouzanyan
 * @description: TODO
 * @date: 2025/4/9 14:53
 * @version: 1.0
 */
@Service
public class WService {
    private static final Logger logger = LoggerFactory.getLogger(WService.class);

    @Tool(description = "根据城市名称获取天气")
    public String getWeatherByCity(@ToolParam(description = "城市名称") String city) {
        if (Objects.isNull(city)) {
            return "抱歉：城市名称不能为空！";
        }
        // 模拟天气数据
        Map<String, String> mockData = Map.of(
                "西安", "特大暴雨",
                "北京", "小雨",
                "上海", "大雨"
        );
        return mockData.getOrDefault(city, "抱歉：未查询到对应城市！");
    }


}
