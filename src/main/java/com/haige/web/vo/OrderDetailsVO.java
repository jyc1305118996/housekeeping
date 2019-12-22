package com.haige.web.vo;

import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : Aaron
 *     <p>create at: 2019-12-22 11:33
 *     <p>description: order 明细
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsVO {

  private String orderId;

  private String goodsId;

  private String goodsName;

  private BigDecimal price;

  private Integer status;

  private Date time;
}
