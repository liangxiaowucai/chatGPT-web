package com.chat.demo.req;

import com.plexpt.chatgpt.entity.chat.Message;
import lombok.Data;

import java.util.List;

/**
 * @author lsf
 * @date 2023年05月06日 5:27 PM
 */
@Data
public class ChatReq {
    /**
     * 目前支持三中角色参考官网，进行情景输入：https://platform.openai.com/docs/guides/chat/introduction
     */

    private String templateId;

    private List<Message> messages;
}
