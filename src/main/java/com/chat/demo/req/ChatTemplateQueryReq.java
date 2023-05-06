package com.chat.demo.req;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author lsf
 * @date 2023年05月06日 5:15 PM
 */
@Data
public class ChatTemplateQueryReq {
    @ApiModelProperty(value = "角色", required = false)
    private String roleCode;

    @ApiModelProperty(value = "模版", required = false)
    private String template;
}
