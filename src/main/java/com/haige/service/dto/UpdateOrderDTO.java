package com.haige.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author Archie
 * @date 2019/11/18 21:41
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderDTO {
    private String orderId;
    private String orderStatus;
}
