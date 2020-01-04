package com.haige.db.entity;

import java.math.BigDecimal;

public class CouponDO {
    private Integer ucId;

    private Integer ucUserId;

    private String ucUserName;

    private BigDecimal ucCouponPrice;

    private Integer ucCouponId;

    private String ucCouponValidity;

    private String ucIsUse;

    public Integer getUcId() {
        return ucId;
    }

    public void setUcId(Integer ucId) {
        this.ucId = ucId;
    }

    public Integer getUcUserId() {
        return ucUserId;
    }

    public void setUcUserId(Integer ucUserId) {
        this.ucUserId = ucUserId;
    }

    public String getUcUserName() {
        return ucUserName;
    }

    public void setUcUserName(String ucUserName) {
        this.ucUserName = ucUserName == null ? null : ucUserName.trim();
    }

    public BigDecimal getUcCouponPrice() {
        return ucCouponPrice;
    }

    public void setUcCouponPrice(BigDecimal ucCouponPrice) {
        this.ucCouponPrice = ucCouponPrice;
    }

    public Integer getUcCouponId() {
        return ucCouponId;
    }

    public void setUcCouponId(Integer ucCouponId) {
        this.ucCouponId = ucCouponId;
    }

    public String getUcCouponValidity() {
        return ucCouponValidity;
    }

    public void setUcCouponValidity(String ucCouponValidity) {
        this.ucCouponValidity = ucCouponValidity == null ? null : ucCouponValidity.trim();
    }

    public String getUcIsUse() {
        return ucIsUse;
    }

    public void setUcIsUse(String ucIsUse) {
        this.ucIsUse = ucIsUse == null ? null : ucIsUse.trim();
    }
}