package com.haige.integration.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Archie
 * @date 2019/10/19 11:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendMessageResponse {
    private String smsStatus;
    private String badReason;
    private String message;
}
