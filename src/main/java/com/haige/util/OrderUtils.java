package com.haige.util;

/**
 * @author Archie
 * @date 2019/12/8 11:14
 */
public class OrderUtils {
    public static String subOrderId(String OrderId, int start){
        return OrderId.substring(start);
    }
}
