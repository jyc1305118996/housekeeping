package com.haige.common.enums;

/**
 * @author Archie
 * @date 2019/10/19 0:05
 */
public enum StatusCodeEnum {

    /**
     * 成功返回状态
     */

    OK(200, "OK"),


    /**
     * 请求格式错误
     */

    BAD_REQUEST(400, "bad request"),

    /**
     * 未授权
     */

    UNAUTHORIZED(401, "unauthorized"),

    /**
     * 没有权限
     */

    FORBIDDEN(403, "forbidden"),


    /**
     * 请求的资源不存在
     */

    NOT_FOUND(404, "not found"),

    /**
     * 该http方法不被允许
     */

    NOT_ALLOWED(405, "method not allowed"),

    /**
     * 请求处理发送异常
     */

    PROCESSING_EXCEPTION(406, "Handling Exceptions"),

    /**
     * 请求处理未完成
     */

    PROCESSING_UNFINISHED(407, "To deal with unfinished"),


    /**
     * 登录过期
     */

    BEOVERDUE(408, "登陆过期"),


    /**
     * 用户未登录
     */

    NOT_LOGIN(409, "未登录"),


    /**
     * 这个url对应的资源现在不可用
     */

    GONE(410, "gone"),

    /**
     * 请求类型错误
     */

    UNSUPPORTED_MEDIA_TYPE(415, "unsupported media type"),

    /**
     * 校验错误时用
     */

    UNPROCESSABLE_ENTITY(422, "unprocessable entity"),

    /**
     * 请求过多
     */

    TOO_MANY_REQUEST(429, "too many request");

    private int code;

    private String value;


    StatusCodeEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
