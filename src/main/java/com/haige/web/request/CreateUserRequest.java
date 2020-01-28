package com.haige.web.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author Archie
 * @date 2020/1/11 17:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    @NotNull
    @Length(min = 1)
    private Short ubdUse;
    @NotNull
    @Length(min = 1)
    @Pattern(regexp = "1[3|6|5|7|8][0-9]\\d{8}")
    private String ubdPoliceName;
    @NotNull
    @Length(min = 1)
    private String ubdFixedPhone;
}
