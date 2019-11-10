package com.haige.integration.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;

/**
 * @author Archie
 * @date 2019/11/10 17:01
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "xml")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmitOrderParam {
    /**
     * 小程序id
     */
    @XmlElement(name = "appid")
    private String appid;
    /**
     * 商户号
     */
    @XmlElement(name = "mch_id")
    private String mchId;
    /**
     *
     */
    @XmlElement(name = "device_info")
    private String deviceInfo = "WEB";
    /**
     * 随机字符串
     */
    @XmlElement(name = "nonce_str")
    private String nonceStr;
    /**
     * 签名
     */
    @XmlElement(name = "sign")
    private String sign;
    /**
     * 签名类型
     */
    @XmlElement(name = "sign_type")
    private String signType;
    /**
     * 商品描述
     */
    @XmlElement(name = "body")
    private String body;
    /**
     * 商品详情
     */
    @XmlElement(name = "detail")
    private String detail;
    /**
     * 附加数据
     */
    @XmlElement(name = "attach")
    private String attach;
    /**
     * 商户订单号
     */
    @XmlElement(name = "out_trade_no")
    private String outTradeNo;
    /**
     * 标价币种
     */
    @XmlElement(name = "fee_type")
    private String feeType;
    /**
     * 标价金额
     */
    @XmlElement(name = "total_fee")
    private String totalFee;
    /**
     * 用户终端ip
     */
    @XmlElement(name = "spbill_create_ip")
    private String spbillCreateIp;
    /**
     * 交易起始时间
     */
    @XmlElement(name = "time_start")
    private String timeStart;
    /**
     * 交易结束时间
     */
    @XmlElement(name = "time_expire")
    private String timeExpire;
    /**
     * 订单优惠标记
     */
    @XmlElement(name = "goods_tag")
    private String goodsTag;
    /**
     * 付款成功回调地址
     */
    @XmlElement(name = "notify_url")
    private String notifyUrl;
    /**
     * 交易类型
     */
    @XmlElement(name = "trade_type")
    private String tradeType;
    /**
     * 商品id
     */
    @XmlElement(name = "product_id")
    private String productId;
    /**
     * 指定支付方式
     */
    @XmlElement(name = "limit_pay")
    private String limitPay;
    /**
     * 用户标识
     */
    @XmlElement(name = "openid")
    private String openid;
    /**
     * 电子发票入口开放标识
     */
    @XmlElement(name = "receipt")
    private String receipt;
    /**
     * 场景信息
     */
    @XmlElement(name = "scene_info")
    private Object sceneInfo;
}