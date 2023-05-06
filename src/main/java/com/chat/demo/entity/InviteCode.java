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
public class InviteCode {
//    // 主键
//    @ApiModelProperty(value = "主键Id", required = false)
//    private Integer id;
    // 用户名
    @ApiModelProperty(value = "邀请码", required = false)
    private String code;
    @ApiModelProperty(value = "父邀请码", required = false)
    private String superCode;
    @ApiModelProperty(value = "短信验证码", required = false)
    private String smsCode;
    // 密码
    @ApiModelProperty(value = "用户Id", required = false)
    private String userId;

}

