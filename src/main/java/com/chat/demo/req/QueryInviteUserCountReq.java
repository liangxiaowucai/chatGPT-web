package com.chat.demo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lsf
 * @date 2023年05月06日 11:47 AM
 */
@Data
public class QueryInviteUserCountReq {
    // 主键
    @ApiModelProperty(value = "主键Id", required = true)
    private Integer id;

}
