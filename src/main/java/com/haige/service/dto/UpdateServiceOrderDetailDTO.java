package com.haige.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author : Archie
 *     <p>create at: 2019-12-22 11:33
 *     <p>description: order 明细
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateServiceOrderDetailDTO {

  private Integer serveId;
  private String concatIphone;
  private String concatName;
  private Integer status;
  private Date time;
}
