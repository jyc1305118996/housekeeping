package com.haige.web.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


/**
 * @author Archie
 * @date 2019/10/20 17:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComboVO {
    @NotEmpty
    @NotNull
    private String comboName;
    @NotEmpty
    @NotNull
    private String comboDesc;
    @NotEmpty
    @NotNull
    private String comboPrice;
    @NotEmpty
    @NotNull
    private String comboNum;
    @NotEmpty
    @NotNull
    private String comboType;
    @NotEmpty
    @NotNull
    private String comboInfo;
    @NotEmpty
    @NotNull
    private String isDel;
    @NotEmpty
    @NotNull
    private String expirationDate;
    @NotEmpty
    @NotNull
    private String comboPict;
}
