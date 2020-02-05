package com.haige.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haige.db.entity.FileInfoDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInfoDTO {
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date goodsCreateTime;

    private Integer goodsCreateUser;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date goodsUpdateTime;

    private Integer goodsUpdateUser;

    private BigDecimal goodsPrice;

    private BigDecimal goodsDiscountPrice;

    private String goodsCoverUrl;
    private Object urls;

    private String goodsIsDel;

    private String goodsType;

    private List<FileInfoDO> files;
}