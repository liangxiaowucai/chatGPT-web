package com.chat.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chat.demo.dao.UserDao;
import com.chat.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> {
    @Autowired
    UserDao userDao;

    public List<User> getByUsername(String username) {
        return userDao.getByUsername(username);
    }

    public List<User> getDeleted() {
        return userDao.getDeleted();
    }

    public void addSendCountByCode(String code) {
        userDao.addSendCountByCode(code);
    }

    public void initSendCount() {
        userDao.initSendCount();
    }


}
