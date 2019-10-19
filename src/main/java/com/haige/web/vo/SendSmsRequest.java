package com.haige.web.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author Archie
 * @date 2019/10/19 0:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@NotNull
@NotEmpty
public class SendSmsRequest {
    private String iphone;
    private String type;
    private String ip;
}
