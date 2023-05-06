package com.chat.demo.config.schedule;

import com.chat.demo.entity.User;
import com.chat.demo.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@Slf4j
public class WebSocketConfig {

    @Autowired
    UserServiceImpl userService;

    @Scheduled(cron = "0 0 0 * * ? ")
    public void initSendCount() {
        log.info("开始执行初始化今日发送次数");
        userService.initSendCount();
        log.info("结束执行初始化今日发送次数");
    }
}