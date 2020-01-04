package com.haige.db.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInfoDO {
    private Integer goodsId;

    private String goodsName;

    private String goodsRemarks;

    private String goodsTitle;

    private Integer goodsFreq;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date goodsExpreDate;

    private Byte goodsPersonType;

    private Byte goodsPersonFreq;

    private Byte goodsPersonNum;

    private String goodsAdvise;

    private String goodsActiveDesc;

    private String goodsRestrictDesc;

    private Date goodsCreateTime;

    private Integer goodsCreateUser;

    private Date goodsUpdateTime;

    private Integer goodsUpdateUser;

    private BigDecimal goodsPrice;

    private BigDecimal goodsDiscountPrice;


    private String goodsCoverUrl;

    private List<FileInfoDO> files;
}