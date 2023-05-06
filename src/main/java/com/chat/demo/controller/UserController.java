package com.chat.demo.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.basic.SaBasicUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.chat.demo.common.Common;
import com.chat.demo.common.ResultCodeEnum;
import com.chat.demo.entity.InviteCode;
import com.chat.demo.entity.User;
import com.chat.demo.req.*;
import com.chat.demo.result.R;
import com.chat.demo.service.impl.InviteCodeServiceImpl;
import com.chat.demo.service.impl.UserServiceImpl;
import com.chat.demo.util.ExpireTimeUtils;
import com.chat.demo.util.PasswordUtils;
import com.chat.demo.util.TokenUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Update;
import org.omg.CORBA.COMM_FAILURE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/users")
@Api(value = "用户相关请求", tags = "UserController")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    /**
     * 用户登录
     *
     * @param user 用户信息
     * @return UserResult
     */
    @PostMapping("/login")
    @ApiOperation(value = "登录", notes = "登录", httpMethod = "POST")
    public SaResult login(@RequestBody LoginUserReq user) throws NoSuchAlgorithmException {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, user.getUsername());
        List<User> users = userService.list(queryWrapper);

        if (users != null && users.size() == 1) {
            User user4Db = users.get(0);
            if (Common.DISABLED.equals(user4Db.getStatus())) {
                return SaResult.error().code(ResultCodeEnum.USER_DISABLE_FAIL.getCode()).setMsg(ResultCodeEnum.USER_DISABLE_FAIL.getMessage());
            }
            if (ExpireTimeUtils.isExpire(user4Db.getCreateTime(), user4Db.getType())) {
                if (user4Db.getSendCount() <= 0) {
                    return SaResult.error().code(ResultCodeEnum.USER_SEND_COUNT_DONE.getCode()).setMsg(ResultCodeEnum.USER_SEND_COUNT_DONE.getMessage());
                }
                return SaResult.error().code(ResultCodeEnum.USER_EXPIRE.getCode()).setMsg(ResultCodeEnum.USER_EXPIRE.getMessage());
            }

            // 验证密码是否正确
            boolean flag = PasswordUtils.verifyPassword(user.getPassword(), user4Db.getPassword());
            if (flag) {
                StpUtil.login(user4Db.getId());
                return SaResult.ok().setData(user4Db);
            }
        }
        return SaResult.error().code(ResultCodeEnum.USER_PWS_NOT_CORRECT.getCode()).setMsg(ResultCodeEnum.USER_PWS_NOT_CORRECT.getMessage());
    }

    @GetMapping("/logout")
    public SaResult logout() {
        // 退出登录会清除三个地方的数据：
        //   1、Redis中保存的 Token 信息
        //   2、当前请求上下文中保存的 Token 信息
        //   3、Cookie 中保存的 Token 信息（如果未使用Cookie模式则不会清除）
        StpUtil.logout();

        // 返回给前端
        return SaResult.ok();
    }

    /**
     * 用户注册
     *
     * @param req 用户数据
     * @return UserResult
     */
    @PostMapping("/register")
    @ApiOperation(value = "注册用户", notes = "注册用户")
    @Transactional
    public SaResult save(@RequestBody SaveUserReq req) throws NoSuchAlgorithmException {
        // 判断用户名是否重复
        List<User> list = userService.getByUsername(req.getPhoneNumber());
        if (list.size() != 0) {
            return SaResult.error().code(ResultCodeEnum.USER_REGISTER_FAIL_USERNAME.getCode()).setMsg(ResultCodeEnum.USER_REGISTER_FAIL_USERNAME.getMessage());
        }

        String smsCode = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6);
        String invitationCode = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6);
        User user = new User();
        //如果邀请码为空，则散客
        if (!StringUtils.isBlank(req.getSuperCode())) {
            user.setSuperCode(req.getSuperCode());
        }
        user.setUsername(req.getPhoneNumber());
        user.setPassword(PasswordUtils.hashPassword(req.getPassword()));
        user.setCode(invitationCode);
        user.setSmsCode(smsCode);
        user.setSendCount(ExpireTimeUtils.DEFAULT_SEND_COUNT);
        user.setEndTime(ExpireTimeUtils.getEndTime(ExpireTimeUtils.TYPE_DAY));
        userService.save(user);

        InviteCode inviteCode = new InviteCode();
        inviteCode.setSuperCode(req.getSuperCode());
        inviteCode.setCode(invitationCode);
        inviteCode.setUserId(user.getId() + "");
        inviteCode.setSmsCode(smsCode);

        return SaResult.ok().setData(inviteCode);
    }


    /**
     * 用户修改
     *
     * @param req 用户数据
     * @return UserResult
     */
    @PostMapping("/update")
    @SaCheckLogin
    @ApiOperation(value = "更改用户", notes = "更改用户")
    public R update(@RequestBody UpdateUserReq req) {
        User user = new User();
        if (null == req.getId()) {
            List<User> list = userService.getByUsername(req.getUsername());
            if (CollectionUtils.isEmpty(list)) {
                return R.error().code(ResultCodeEnum.USER_UPDATE_FAIL.getCode()).message(ResultCodeEnum.USER_UPDATE_FAIL.getMessage());
            }
            user.setId(list.get(0).getId());
        }
        user.setUsername(req.getUsername());
        user.setPassword(req.getPassword());
        List<User> list = userService.lambdaQuery().ge(User::getSuperCode, user.getSuperCode()).list();
        if (!CollectionUtils.isEmpty(list)) {
            int type = ExpireTimeUtils.TYPE_DAY;
            if (list.size() >= 10) {
                type = ExpireTimeUtils.TYPE_WEEK;
            }
            if (list.size() >= 50) {
                type = ExpireTimeUtils.TYPE_MONTH;
            }

            if (list.size() >= 100) {
                type = ExpireTimeUtils.TYPE_YEAR;
            }
            user.setType(type);
        }

        if (!userService.updateById(user)) {
            log.warn("{}", req);
            return R.error().code(ResultCodeEnum.USER_UPDATE_FAIL.getCode()).message(ResultCodeEnum.USER_UPDATE_FAIL.getMessage());
        }
        return R.ok().data("uid", user.getId());
    }

    /**
     * 用户修改
     *
     * @param req 用户数据
     * @return UserResult
     */
    @PostMapping("/queryInviteUserCount")
    @SaCheckLogin
    @ApiOperation(value = "查询邀请人数", notes = "查询邀请人数")
    public R queryInviteUserCount(@RequestBody QueryInviteUserCountReq req) {
        User user = userService.getById(req.getId());
        if (null == user) {
            return R.error().code(ResultCodeEnum.USER_QUERY_FAIL.getCode()).message(ResultCodeEnum.USER_QUERY_FAIL.getMessage());
        }
        if (Common.DISABLED.equals(user.getStatus())) {
            return R.error().code(ResultCodeEnum.USER_DISABLE_FAIL.getCode()).message(ResultCodeEnum.USER_DISABLE_FAIL.getMessage());
        }
        List<User> list = userService.lambdaQuery().ge(User::getSuperCode, user.getSuperCode()).list();
        return R.ok().data("count", list.size());
    }

    /**
     * @param code 用户发送次数
     * @return UserResult
     */
    @PostMapping("/addSendCount/{code}")
    @SaCheckLogin
    @ApiOperation(value = "增加发送次数", notes = "增加发送次数，code为父邀请码，每次增加5次")
    public SaResult addSendCount(@PathVariable String code) {
        userService.addSendCountByCode(code);
        return SaResult.ok();
    }


    /**
     * 根据id删除用户
     *
     * @param id 用户的id
     * @return UserResult
     */
    @DeleteMapping("/{id}")
    @SaCheckLogin
    @ApiOperation(value = "删除用户", notes = "逻辑删除用户")
    public SaResult delete(@PathVariable Integer id) {
        User u = new User();
        u.setId(id);
        u.setStatus(1);
        userService.updateById(u);
        return SaResult.ok();
    }

    /**
     * 根据id查询用户
     *
     * @param id 用户的id
     * @return UserResult
     */
    @GetMapping("/{id}")
    @SaCheckLogin
    @ApiOperation(value = "获取用户", notes = "获取用户")
    public SaResult queryUserById(@PathVariable Integer id) {
        User user = userService.getById(id);
        return SaResult.ok().setData(user);
    }
}
