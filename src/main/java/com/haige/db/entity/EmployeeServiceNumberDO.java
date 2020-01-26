package com.haige.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Archie
 * @date 2020/1/25 17:33
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeServiceNumberDO {
    private String employeeName;
    private Integer number;
}
