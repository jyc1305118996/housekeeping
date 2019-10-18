package com.haige.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Archie
 * @date 2019/10/19 0:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendSmsDTO {
    private String iphone;
    private String type;
    private String ip;
}
