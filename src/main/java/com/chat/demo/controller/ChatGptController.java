package com.chat.demo.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.chat.demo.entity.ChatTemplate;
import com.chat.demo.req.ChatReq;
import com.chat.demo.service.impl.ChatTemplateServiceImpl;
import com.chat.demo.service.impl.SseStreamListenerImpl;
import com.plexpt.chatgpt.ChatGPTStream;
import com.plexpt.chatgpt.entity.chat.Message;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


/**
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/api")
@Api(value = "chat请求", tags = "ChatGptController")
@Slf4j
public class ChatGptController {

    @Autowired
    ChatTemplateServiceImpl chatTemplateService;

    @PostMapping(value = "/chat")
    @ApiOperation(value = "调用openai接口，返回流信息", notes = "调用openai接口，返回流信息", httpMethod = "POST")
    public SseEmitter chat(@RequestBody ChatReq chatReqs) {

        if (StringUtils.isEmpty(chatReqs) || CollectionUtils.isEmpty(chatReqs.getMessages())) {
            throw new IllegalArgumentException("参数有误");
        }
        StpUtil.checkLogin();

        ChatTemplate chatTemplate = chatTemplateService.lambdaQuery().eq(ChatTemplate::getId, chatReqs.getTemplateId()).one();
        if (null != chatTemplate) {
            log.info("已内置角色,{}", chatTemplate);
            chatReqs.getMessages().add(Message.ofSystem(chatTemplate.getTemplate()));
        }

        ChatGPTStream chatGPTStream = SpringUtil.getBean(ChatGPTStream.class);
        SseEmitter sseEmitter = new SseEmitter(-1L);
        SseStreamListenerImpl listener = new SseStreamListenerImpl(sseEmitter);

        chatGPTStream.streamChatCompletion(chatReqs.getMessages(), listener);
        return sseEmitter;
    }

}
