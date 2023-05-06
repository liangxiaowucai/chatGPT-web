package com.chat.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chat.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


@Mapper
public interface UserDao extends BaseMapper<User> {
    @Select("SELECT id,username,password,identity,status FROM tb_user WHERE username = #{username} and status=0")
    List<User> getByUsername(String username);

    @Update("update tb_user set send_count=send_count+5 WHERE code = #{code} and status=0")
    void addSendCountByCode(String code);

    @Select("SELECT id,username,password,identity,status FROM tb_user ")
    List<User> getDeleted();

    @Update("update tb_user set send_count=5 WHERE status=0")
    void initSendCount();

}
