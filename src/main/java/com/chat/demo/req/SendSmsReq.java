package com.chat.demo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author apeto
 * @create 2023/4/11 20:29
 */
@Data
public class SendSmsReq {

    @ApiModelProperty(value = "手机账号", required = false)
    private String phoneNum;

}
