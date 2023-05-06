package com.chat.demo.req;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lsf
 * @date 2023年05月06日 11:47 AM
 */
@Data
public class LoginUserReq implements Serializable {
    // 用户名
    @ApiModelProperty(value = "用户名", required = true)
    private String username;
    // 密码
    @ApiModelProperty(value = "密码", required = true)
    private String password;
}
