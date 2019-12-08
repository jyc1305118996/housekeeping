package com.haige.integration.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

/**
 * @author Archie
 * @date 2019/10/18 1:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendMessageParam {
    /**
     * 接受人的手机号
     */
    private String iphone;
    /**
     * 发送短信类型
     */
    private String type;

    private SmsTemplate smsTemplate;


    /**
     * 模板参数
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SmsTemplate{
        /**
         * 模板代码
         */
        private String TemplateCode;

        /**
         * 模板参数
         */
        private HashMap<String, String> param;
    }
}
