package com.jacksonfang.common;

/**
 * @author Jackson Fang
 *         Date: 2018/11/15
 *         Time: 9:04
 */
public enum RestCode {
    OK(0, "OK"),
    UNKNOWN_ERROR(1, "服务异常"),
    WRONG_PAGE(10100, "页码不存在"),
    INVALID_KEY(40010,"无效的 key"),
    WRONG_EMAIL_OR_PASSWORD(40011,"邮箱或密码错误"),
    USER_NOT_LOGIN(40012,"用户未登录");

    public final int code;
    public final String msg;

    RestCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
