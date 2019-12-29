package com.haige.web.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author : Archie
 *     <p>create at: 2019-12-22 11:33
 *     <p>description: order 明细
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateServiceOrderDetailRequest {

  @NotNull
  private Integer serveId;
  private String concatIphone;
  private String concatName;

  private Integer status;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date time;

}
