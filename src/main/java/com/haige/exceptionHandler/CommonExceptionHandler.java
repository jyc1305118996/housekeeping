package com.haige.exceptionHandler;

import com.haige.common.enums.StatusCode;
import com.haige.common.bean.ResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.ArrayList;

/**
 * @author Archie
 * @date 2019/10/13 22:34
 */
@ControllerAdvice
@ResponseBody
@Slf4j
@Component
public class CommonExceptionHandler {
    /**
     * 处理参数绑定失败异常
     * @param ex
     * @return
     */
    @ExceptionHandler(WebExchangeBindException.class)
    public ResultInfo<String> paramError(WebExchangeBindException ex){
        boolean isHas = ex.hasErrors();
        ResultInfo<String> errorResultInfo = new ResultInfo<String>();
        if (isHas){
            ArrayList<String> errors = new ArrayList<>(0);
            ex.getFieldErrors()
                    .forEach(fieldError -> {
                        String field = fieldError.getField();
                        String defaultMessage = fieldError.getDefaultMessage();
                        errors.add(field + defaultMessage);
                    });
            errorResultInfo.setCode(StatusCode.BAD_REQUEST.getCode());
            errorResultInfo.setMessage(StatusCode.BAD_REQUEST.getValue());
            log.error("异常信息是:{}", errors.toString());
            errorResultInfo.setMessage( errors.toString());
        }
        return errorResultInfo;
    }

    @ExceptionHandler(Exception.class)
    public ResultInfo<String> exception(Exception ex){
        log.error("异常信息:{}", ex.getMessage(), ex);
        ResultInfo<String> errorResultInfo = new ResultInfo<String>();
        errorResultInfo.setCode(StatusCode.BAD_REQUEST.getCode());
        errorResultInfo.setMessage("请求出错啦");
        return errorResultInfo;
    }
//    @ExceptionHandler(RuntimeException.class)
//    public ResultInfo<String> exception(RuntimeException ex){
//        log.error("异常信息:{}", ex.getMessage(), ex);
//        ResultInfo<String> errorResultInfo = new ResultInfo<String>();
//        errorResultInfo.setCode(StatusCode.BAD_REQUEST.getCode());
//        errorResultInfo.setMessage(ex.getMessage());
//        return errorResultInfo;
//    }
}
