package com.chat.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chat.demo.dao.InviteCodeDao;
import com.chat.demo.dao.UserDao;
import com.chat.demo.entity.InviteCode;
import com.chat.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InviteCodeServiceImpl extends ServiceImpl<InviteCodeDao, InviteCode> {
    @Autowired
    InviteCodeDao inviteCodeDao;

}
