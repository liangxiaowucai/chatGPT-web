package com.chat.demo.common;

import lombok.Getter;

@Getter
public enum ResultCodeEnum {
    SUCCESS(true, 1, "成功"),
    FAIL(false, -1, "失败"),
    UNKNOWN_ERROR(false, 2, "未知错误"),
    PARAM_ERROR(false, 3, "参数错误"),
    NULL_POINT(false, 4, "空指针"),
    HTTP_CLIENT_ERROR(false, 5, "http客户端异常"),
    CLOSE_NOTICE(false, 6, "关闭通知"),
    USER_NOT_EXIST(false, 7, "用户不存在"),
    USER_EXPIRE(false, 8, "用户过期"),
    USER_EXIST(false, 9, "用户已登录"),
    HEART(false, 10, "心跳信息"),
    USER_PWS_NOT_CORRECT(false, 11, "用户名和密码不正确"),
    USER_REGISTER_FAIL(false, 12, "用户注册失败"),
    USER_UPDATE_FAIL(false, 13, "用户修改失败"),
    USER_REGISTER_FAIL_USERNAME(false, 14, "用户注册失败,用户名重复，可以使用手机号登录"),
    USER_QUERY_FAIL(false, 15, "用户查询失败，请确认用户名是否正确"),
    USER_DISABLE_FAIL(false, 16, "用户已被停用，请联系管理员"),
    USER_SEND_COUNT_DONE(false, 17, "用户免费次数已用完"),

    //    SMS
    SMS_CODE_NOT_EXPIRE_ERROR(false, 1001, "验证码未过期，请耐心等待！"),
    SMS_CODE_EXPIRE_ERROR(false, 1002, "验证码已过期，请重新发送！"),
    SMS_PHONE_ERROR(false, 1003, "手机号格式错误"),
    SMS_INVALID_ERROR(false, 1004, "手机验证码错误"),

    //权限,
    USER_ADD_COUNT_INVALID(false, 2001, "用户权限不足，增加不了次数");

    // 响应是否成功
    private Boolean success;
    // 响应状态码
    private Integer code;
    // 响应信息
    private String message;

    ResultCodeEnum(boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
}
