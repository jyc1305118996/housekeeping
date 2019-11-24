package com.haige.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author Archie
 * @date 2019/11/24 20:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsDTO {
    private Integer goodsId;

    private String goodsName;

    private String goodsRemarks;

    private String goodsTitle;

    private Integer goodsFreq;

    private String goodsAdvise;

    private String goodsActiveDesc;

    private String goodsRestrictDesc;

    private BigDecimal goodsPrice;

    private BigDecimal goodsDiscountPrice;

}
