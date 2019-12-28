package com.haige.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Archie
 * @date 2019/11/24 20:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInfoDTO {
    private Integer goodsId;

    private String goodsName;

    private String goodsRemarks;

    private String goodsTitle;

    private Integer goodsFreq;

    private String goodsAdvise;

    private Date goodsExpreDate;

    private String goodsActiveDesc;

    private String goodsRestrictDesc;

    private BigDecimal goodsPrice;

    private BigDecimal goodsDiscountPrice;

}
