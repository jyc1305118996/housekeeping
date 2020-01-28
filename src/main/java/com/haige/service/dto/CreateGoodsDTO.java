package com.haige.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haige.db.entity.FileInfoDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Archie
 * @date 2020/1/27 14:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateGoodsDTO {
    private String goodsName;

    private String goodsRemarks;

    private String goodsTitle;

    private Integer goodsFreq;

    private Date goodsExpreDate;

    private String goodsAdvise;

    private BigDecimal goodsPrice;

    private BigDecimal goodsDiscountPrice;

    private String goodsCoverUrl;

    private String goodsType;
}
