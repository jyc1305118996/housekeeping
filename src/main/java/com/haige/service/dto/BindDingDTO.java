package com.haige.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author Archie
 * @date 2019/11/17 21:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BindDingDTO {
    private String iphone;
    private String checkCode;
}
