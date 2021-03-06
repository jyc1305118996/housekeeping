package com.haige.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author Archie
 * @date 2019/11/20 23:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmitServiceDTO {
    private String orderId;
    private String serviceTime;
    private String iphone;
    private String name;
}
