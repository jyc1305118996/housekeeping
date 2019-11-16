package com.haige.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Archie
 * @date 2019/11/16 11:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayDTO {
    private String orderId;
    private String appid;
}
