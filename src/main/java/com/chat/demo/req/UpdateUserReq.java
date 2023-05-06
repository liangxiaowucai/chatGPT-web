package com.chat.demo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author lsf
 * @date 2023年05月06日 11:47 AM
 */
@Data
public class UpdateUserReq {

    // 主键
    @ApiModelProperty(value = "主键Id", required = false)
    private Integer id;

    @ApiModelProperty(value = "用户名", required = true)
    private String username;
    // 密码
    @ApiModelProperty(value = "密码", required = true)
    private String password;
    // 0启用 1停用 2过期
    @ApiModelProperty(value = "过期类型， 0启用 1停用 默认0 ", required = true)
    private Integer status;
}
