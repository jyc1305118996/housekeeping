package com.haige.common.bean;

import com.haige.common.enums.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通用结果返回类
 * @author Archie
 * @date 2019/10/19 0:03
 */
@Data
@AllArgsConstructor
public class ResultInfo<T> {

    private int code;

    private String message;

    private T data;

    private String count;


    public ResultInfo(StatusCode statusCode) {
        this.code = statusCode.getCode();
        this.setMessage(statusCode.getValue());
    }

    public ResultInfo() {
    }
}
