package com.haige.web.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haige.db.entity.FileInfoDO;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
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
public class OrderDetailVO {

  private String serveId;
  private String orderId;

  private String goodsId;

  private String goodsName;

  private BigDecimal price;

  private Integer status;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date time;

  //地址
  private String address;

  private List<FileInfoDO> files;

  private String number;
}
