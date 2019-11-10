package com.haige.integration.enums;

/**
 * @author Archie
 * @date 2019/11/10 16:42
 */
public class WXUrlEnums {
    /**
     * 获取访问token
     */
    public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/jscode2session";
    /**
     * 获取用户信息
     */
    public static final String USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo";
    /**
     * 统一下单接口
     */
    public static final String SUBMIT_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
}
