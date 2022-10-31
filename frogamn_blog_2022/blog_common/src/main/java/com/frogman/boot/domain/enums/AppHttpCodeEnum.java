package com.frogman.boot.domain.enums;

public enum AppHttpCodeEnum {
    // 成功
    SUCCESS(200,"操作成功"),
    // 登录
    NEED_LOGIN(401,"需要登录后操作"),
    NO_OPERATOR_AUTH(403,"无权限操作"),
    SYSTEM_ERROR(500,"出现错误"),
    USERNAME_EXIST(501,"用户名已存在"),
    PHONE_NUMBER_EXIST(502,"手机号已存在"),
    EMAIL_EXIST(503, "邮箱已存在"),
    REQUIRE_USERNAME(504, "必需填写用户名"),
    REQUIRE_PASSWORD(504, "必需填写密码"),
    REQUIRE_NICKNAME(504, "必需填写昵称"),
    REQUIRE_EMAIL(504, "必需填写邮箱"),
    LOGIN_ERROR(505,"用户名或密码错误"),
    CONTENT_IS_EMPTY(400,"内容不能为空"),
    FILE_TYPE_NOT_ALLOWED(507,"文件类型不允许"),
    TAG_ALREADY_EXIST(407,"标签输入有误或已经存在");
    int code;
    String msg;

    AppHttpCodeEnum(int code, String errorMessage){
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
