package com.haige.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Archie
 * @date 2020/1/11 17:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDTO {
    private Short ubdUse;

    private String ubdPoliceName;

    private String ubdFixedPhone;
}
