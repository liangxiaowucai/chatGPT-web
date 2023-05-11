package com.chat.demo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lsf
 * @date 2023年05月06日 11:47 AM
 */
@Data
public class SaveUserReq {
    @ApiModelProperty(value = "用户名", required = true)
    private String phoneNumber;
    // 密码
    @ApiModelProperty(value = "密码", required = true)
    private String password;
    // 验证码
    @ApiModelProperty(value = "邀请码 ", required = false)
    private String superCode;
    // 验证码
    @ApiModelProperty(value = "短信码 ", required = false)
    private String smsCode;

}
