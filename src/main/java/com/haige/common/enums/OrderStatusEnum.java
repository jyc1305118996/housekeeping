package com.haige.common.enums;

/**
 * @author Archie
 * @date 2019/11/10 21:36
 */
public enum  OrderStatusEnum {
    /**
     * 已支付
     */
    PREPAID("100"),
    /**
     * 未支付
     */
    NON_PAYMENT("200"),

    /**
     * 支付失败
     */
    UNPAID("300"),
    /**
     * 订单关闭
     */
    CLOSED("400");

    private String orderStatus;

    OrderStatusEnum(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatus() {
        return orderStatus;
    }
}
