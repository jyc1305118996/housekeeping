package com.haige.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShortMsgDO {
    private Long smiMesaggeId;

    private String smiSender;

    private String smiReceiver;

    private String smiContent;

    private String smiSenderTime;

    private String smiReceiverPhone;

    private String smiIp;

    private String smiType;

    private String smiState;

    private String smiBadReason;
}