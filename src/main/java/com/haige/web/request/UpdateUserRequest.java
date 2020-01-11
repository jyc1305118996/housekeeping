package com.haige.web.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author Archie
 * @date 2020/1/11 17:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
    @NotNull
    private Integer ubdId;
    @NotNull
    private Short ubdUse;
    @NotNull
    private String ubdPoliceName;
    @NotNull
    private String ubdFixedPhone;
}
