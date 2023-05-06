package com.chat.demo.config;

import com.plexpt.chatgpt.ChatGPTStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class chatGptConfig {

    @Value("${openai.apiKey}")
    private String openAiKey;

    @Bean
    public ChatGPTStream chatGPTStream() {
        ChatGPTStream chatGPTStream = ChatGPTStream.builder()
                .timeout(600)
                .apiKeyList(Arrays.asList(
                        openAiKey
                ))
                // .proxy(proxy)
                .apiHost("https://api.openai.com/")
                .build()
                .init();
        return chatGPTStream;
    }
}
