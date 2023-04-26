package com.chat.demo.config;

import com.plexpt.chatgpt.ChatGPTStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class chatGptConfig {

    @Bean
    public ChatGPTStream chatGPTStream(){
        ChatGPTStream chatGPTStream = ChatGPTStream.builder()
                .timeout(600)
                .apiKeyList(Arrays.asList(
                    "你的key"
                ))
                // .proxy(proxy)
                .apiHost("https://api.openai.com/")
                .build()
                .init();
        return chatGPTStream;
    }
}
