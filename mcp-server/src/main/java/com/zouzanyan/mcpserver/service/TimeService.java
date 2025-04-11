package com.zouzanyan.mcpserver.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * @author: zouzanyan
 * @description: TODO
 * @date: 2025/4/11 15:02
 * @version: 1.0
 */
@Service
public class TimeService {

    private static final Logger logger = LoggerFactory.getLogger(WService.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @Tool(description = "获取当前时间")
    public String getCurrentTime() {
        logger.info("获取当前时间");
        try {
            String currentTime = dateFormat.format(new Date());
            return "当前时间：" + currentTime;
        } catch (Exception e) {
            logger.error("获取当前时间时发生错误", e);
            return "抱歉：获取当前时间时发生错误！";
        }
    }

}
