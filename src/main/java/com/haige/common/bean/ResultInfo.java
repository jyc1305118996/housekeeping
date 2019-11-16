package com.haige.common.bean;

import com.haige.common.enums.StatusCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

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


    public ResultInfo(StatusCodeEnum statusCodeEnum) {
        this.code = statusCodeEnum.getCode();
        this.setMessage(statusCodeEnum.getValue());
    }

    public ResultInfo() {
    }
    public static <R> ResultInfo<R> buildSuccess(R r){
        ResultInfo<R> resultInfo = new ResultInfo<>(StatusCodeEnum.OK);
        resultInfo.setData(r);
        return resultInfo;
    }
    public static  ResultInfo<String> buildFailed(String r){
        ResultInfo<String> resultInfo = new ResultInfo<>(StatusCodeEnum.OK);
        resultInfo.setData(r);
        return resultInfo;
    }
}
