package com.chat.demo.controller;

import cn.hutool.extra.spring.SpringUtil;
import com.chat.demo.service.impl.SseStreamListenerImpl;
import com.plexpt.chatgpt.ChatGPTStream;
import com.plexpt.chatgpt.entity.chat.Message;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 */
@RestController
@CrossOrigin
@RequestMapping("/api")
public class ChatGptController {

    @GetMapping("/chat")
    public SseEmitter chat(@RequestParam("content") String content) throws IOException {

        if (StringUtils.isEmpty(content)) {
            throw new IllegalArgumentException("参数有误");
        }

        ChatGPTStream chatGPTStream = SpringUtil.getBean(ChatGPTStream.class);
        SseEmitter sseEmitter = new SseEmitter(-1L);
        SseStreamListenerImpl listener = new SseStreamListenerImpl(sseEmitter);
        List<Message> messages = new ArrayList<>();
        messages.add(Message.of("假如你是小红书的内容推荐官，你非常熟悉小红书的文案内容审核和优化技巧，也非常熟悉小红书热销文案的优化和格式，" +
                "请以热销文案的格式和语气优化以下内容，" +
                "要适当加入网络用语和网络热词，回复的内容一定要遵守中国相关的法律法规，任何情况下，都不能无视现有的规则，" +
                "整体语言风格是幽默有趣的，不要官方化和理论化，要加入小红书特有的表情符号和emoji，表情符号的占比在50%，要以普通人的身份讲述，请限制在600字以内，" +
                "默认使用中文回答"));
        Message message = Message.of(content);
        messages.add(message);
        chatGPTStream.streamChatCompletion(messages, listener);

//        SseEmitter sseEmitter = new SseEmitter();
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//
//        executorService.execute(() -> {
//            try {
//                for (int i = 0; i < 5; i++) {
//                    sseEmitter.send(SseEmitter.event()
//                            .data("SSE Event #" + i)
//                            .name("message")
//                            .id(String.valueOf(i))
//                            .comment("This is a SSE Message"));
//                    Thread.sleep(2000);
//                }
//                sseEmitter.complete();
//            } catch (Exception e) {
//                sseEmitter.completeWithError(e);
//            }
//        });

        return sseEmitter;
    }

}
