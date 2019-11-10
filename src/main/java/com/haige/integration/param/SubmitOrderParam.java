package com.haige.integration.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Archie
 * @date 2019/11/10 17:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmitOrderParam {
    private String appid;
    private String mchId;
    private String deviceInfo;
    private String nonceStr;
    private String sign;
    private String signType;
    private String body;
    private String detail;
    private String attach;
    private String outTradeNo;
    private String feeType;
    private String totalFee;
    private String spbillCreateIp;
    private String timeStart;
    private String timeExpire;
    private String goodsTag;
    private String notifyUrl;
    private String tradeType;
    private String productId;
    private String limitPay;
    private String openid;
    private String receipt;
    private Object scene_info;
}
