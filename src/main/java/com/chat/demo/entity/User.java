package com.chat.demo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author lsf
 * @date 2023年04月28日 2:49 PM
 */

@Data
public class User {
    // 主键
    @ApiModelProperty(value = "主键Id", required = false)
    private Integer id;
    // 用户名
    @ApiModelProperty(value = "用户名", required = false)
    private String username;
    // 密码
    @ApiModelProperty(value = "密码", required = false)
    private String password;
    // 1表示管理员 2表示用户
    @ApiModelProperty(value = "用户类型 1表示管理员 2表示用户 ", required = false)
    private Integer identity;
    // 验证码
    @ApiModelProperty(value = "自己邀请码 ", required = false)
    private String code;
    @ApiModelProperty(value = "父邀请码 ", required = false)
    private String superCode;
    @ApiModelProperty(value = "短信验证码 ", required = false)
    private String smsCode;


    // 0启用 1停用 2过期
    @ApiModelProperty(value = "过期类型， 0启用 1停用 默认0 ", required = false)
    private Integer status;

    @ApiModelProperty(value = "创建时间 ", required = false)
    @TableField(fill = FieldFill.INSERT)
    private Timestamp createTime;

    @ApiModelProperty(value = "更新时间 ", required = false)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Timestamp updateTime;

    //1周2月3年
    @ApiModelProperty(value = "用户的type类型，用于校验用户时间是否过期，1周2月3年 ", required = false)
    private Integer type;

    @ApiModelProperty(value = "结束时间 ", required = false)
    private Timestamp endTime;

    @ApiModelProperty(value = "发送次数 ", required = false)
    private Integer sendCount = 10;
}

