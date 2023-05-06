package com.chat.demo.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.chat.demo.common.Common;
import com.chat.demo.common.ResultCodeEnum;
import com.chat.demo.entity.ChatTemplate;
import com.chat.demo.entity.InviteCode;
import com.chat.demo.entity.User;
import com.chat.demo.req.*;
import com.chat.demo.result.R;
import com.chat.demo.service.impl.ChatTemplateServiceImpl;
import com.chat.demo.service.impl.UserServiceImpl;
import com.chat.demo.util.ExpireTimeUtils;
import com.chat.demo.util.PasswordUtils;
import com.chat.demo.vo.ChatTemplateVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/template")
@Api(value = "chat模版请求", tags = "ChatTemplateController")
public class ChatTemplateController {
    @Autowired
    private ChatTemplateServiceImpl chatTemplateService;

    /**
     * 模版查询
     *
     * @return UserResult
     */
    @GetMapping("/list")
    @SaCheckLogin
    @ApiOperation(value = "模版list查询", notes = "注册用户")
    @Transactional
    public SaResult list() {
        List<ChatTemplateVo> result = new ArrayList<>();
        List<ChatTemplate> list4Db = chatTemplateService.list();
        if (CollectionUtils.isEmpty(list4Db)) {
            return SaResult.ok().setData(result);
        }
        for (ChatTemplate chatTemplate : list4Db) {
            ChatTemplateVo chatTemplateVo = new ChatTemplateVo();
            BeanUtils.copyProperties(chatTemplate, chatTemplateVo);
            result.add(chatTemplateVo);
        }
        return SaResult.ok().setData(result);
    }


    /**
     * 模版查询
     *
     * @return UserResult
     */
    @PostMapping("/add")
    @SaCheckLogin
    @ApiOperation(value = "模版add", notes = "注册模版")
    @Transactional
    public SaResult add(@RequestBody ChatTemplateQueryReq req) {

        ChatTemplate chatTemplate = new ChatTemplate();
        chatTemplate.setRoleCode(req.getRoleCode());
        chatTemplate.setTemplate(req.getTemplate());
        chatTemplateService.save(chatTemplate);

        return SaResult.ok();
    }

    /**
     * 模版查询
     *
     * @return UserResult
     */
    @PostMapping("/addBatch")
    @SaCheckLogin
    @ApiOperation(value = "模版批量add", notes = "注册模版")
    @Transactional
    public SaResult addBatch(@RequestBody List<ChatTemplateQueryReq> reqList) {

        List<ChatTemplate> list = new ArrayList<>();
        for (ChatTemplateQueryReq req : reqList) {
            ChatTemplate chatTemplate = new ChatTemplate();
            chatTemplate.setRoleCode(req.getRoleCode());
            chatTemplate.setTemplate(req.getTemplate());
            list.add(chatTemplate);
        }

        if (!CollectionUtils.isEmpty(list)) {
            chatTemplateService.saveBatch(list);
        }
        return SaResult.ok();
    }


}
