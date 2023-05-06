package com.chat.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chat.demo.entity.ChatTemplate;
import com.chat.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


@Mapper
public interface ChatTemplateDao extends BaseMapper<ChatTemplate> {

}
