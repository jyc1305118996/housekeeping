package com.haige.web.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一结果返回
 * @author Archie
 * @date 2019/10/13 22:35
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultInfo<T> {
    private Integer code;

    private String message;

    private T data;
}
