package com.haige.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Archie
 * @date 2020/1/13 22:28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportMonthDO {
    private String name;
    private int count;
}
