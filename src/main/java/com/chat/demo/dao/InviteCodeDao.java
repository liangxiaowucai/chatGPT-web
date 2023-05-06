package com.chat.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chat.demo.entity.InviteCode;
import com.chat.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface InviteCodeDao extends BaseMapper<InviteCode> {

}
