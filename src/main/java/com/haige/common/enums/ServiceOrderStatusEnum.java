package com.haige.common.enums;

/**
 * @author Archie
 * @date 2019/11/23 14:32
 */
public enum ServiceOrderStatusEnum {
    /**
     *  订单确认
     */
    CONFIRM("1000"),
    /**
     * 订单服务完成
     */
    FINISH("2000"),
    /**
     * 订单关闭
     */
    CLOSED("3000");
    private String status;

    ServiceOrderStatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
