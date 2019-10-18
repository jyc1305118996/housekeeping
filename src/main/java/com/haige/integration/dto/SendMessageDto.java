package com.haige.integration.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Archie
 * @date 2019/10/18 1:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendMessageDto {
    private String iphone;
    private String message;
}
