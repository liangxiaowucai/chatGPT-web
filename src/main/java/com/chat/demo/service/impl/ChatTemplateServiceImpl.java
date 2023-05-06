package com.chat.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chat.demo.dao.ChatTemplateDao;
import com.chat.demo.dao.UserDao;
import com.chat.demo.entity.ChatTemplate;
import com.chat.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatTemplateServiceImpl extends ServiceImpl<ChatTemplateDao, ChatTemplate> {
    @Autowired
    ChatTemplateDao chatTemplateDao;
}
