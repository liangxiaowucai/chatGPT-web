package com.chat.demo.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

/**
 * @author lsf
 * @date 2023年05月06日 5:18 PM
 */
@Data
@ToString
public class ChatTemplateVo {
    // 主键
    @ApiModelProperty(value = "主键Id", required = false)
    private Integer id;
    // 用户名
    @ApiModelProperty(value = "角色", required = false)
    private String roleCode;

    @ApiModelProperty(value = "0启用 1停用", required = false)
    private Integer status;

    @ApiModelProperty(value = "创建时间 ", required = false)
    @TableField(fill = FieldFill.INSERT)
    private Timestamp createTime;

    @ApiModelProperty(value = "更新时间 ", required = false)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Timestamp updateTime;
}
