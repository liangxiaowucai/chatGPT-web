package com.chat.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.plexpt.chatgpt.entity.chat.ChatChoice;
import com.plexpt.chatgpt.entity.chat.ChatCompletionResponse;
import com.plexpt.chatgpt.entity.chat.Message;
import com.plexpt.chatgpt.listener.SseStreamListener;
import com.plexpt.chatgpt.util.SseHelper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.sse.EventSource;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@Slf4j
public class SseStreamListenerImpl extends SseStreamListener {
    SseEmitter sseEmitter1;

    public SseStreamListenerImpl(SseEmitter sseEmitter) {
        super(sseEmitter);
        this.sseEmitter1 = sseEmitter;
    }

    @Override
    public void onMsg(String message) {
        log.info(message);
        String data = message.replaceAll(" ", "&#32;").replaceAll("\\n", "&#33;");
        SseHelper.send(sseEmitter1, data);
    }

    @SneakyThrows
    @Override
    public void onEvent(EventSource eventSource, String id, String type, String data) {
        if (data.equals("[DONE]")) {
            this.onComplate.accept(this.lastMessage);
            this.sseEmitter1.send("[DONE]");
        } else {
            ChatCompletionResponse response = (ChatCompletionResponse) JSON.parseObject(data, ChatCompletionResponse.class);
            List<ChatChoice> choices = response.getChoices();
            if (choices != null && !choices.isEmpty()) {
                Message delta = ((ChatChoice) choices.get(0)).getDelta();
                String text = delta.getContent();
                if (text != null) {
                    this.lastMessage = this.lastMessage + text;
                    this.onMsg(text);
                }

            }
        }
    }

    @Override
    public void onError(Throwable throwable, String response) {
        SseHelper.complete(sseEmitter1);
    }

    @Override
    public void onClosed(EventSource eventSource) {
        super.onClosed(eventSource);
    }
}
