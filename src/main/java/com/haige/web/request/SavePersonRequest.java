package com.haige.web.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author Archie
 * @date 2019/10/13 22:17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SavePersonRequest {

    @NotNull
    @Length(min = 1)
    private String name;

    @NotNull
    private Integer age;
}
