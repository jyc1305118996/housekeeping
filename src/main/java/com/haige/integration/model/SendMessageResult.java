package com.haige.integration.model;

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
public class SendMessageResult {
    private String smsStatus;
    private String badReason;
}
