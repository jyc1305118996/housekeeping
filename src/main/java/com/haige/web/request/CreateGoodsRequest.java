package com.haige.web.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Archie
 * @date 2020/1/27 14:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateGoodsRequest {
    @NotNull
    private String goodsName;
    @NotNull
    private Integer goodsFreq;
    @NotNull
    private String goodsRemarks;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date goodsExpreDate;
    @NotNull
    private String goodsAdvise;
    @NotNull
    private BigDecimal goodsPrice;
    @NotNull
    private BigDecimal goodsDiscountPrice;
    private String goodsCoverUrl;
    @NotNull
    private String goodsType;
}
