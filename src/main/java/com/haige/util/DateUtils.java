package com.haige.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author Archie
 * @date 2019/10/19 0:41
 */
public class DateUtils {
    private final static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * 获取当前时间的标准格式 2019-10-19 0:43:32
     *
     * @return
     */
    public static String nowOfDateTime() {
        return LocalDateTime.now().format(dateTimeFormatter);
    }

    /**
     * 获取当前时间的标准格式 2019-10-19
     *
     * @return
     */
    public static String nowOfDate() {
        return LocalDate.now().format(dateFormatter);
    }

    public static LocalDateTime convertToDateTime(String localDateTime) {
        return LocalDateTime.parse(localDateTime, dateTimeFormatter);
    }

    public static LocalDate convertToDate(String localDate) {
        return LocalDate.parse(localDate, dateFormatter);
    }

    public static String convertToString(LocalDateTime localDateTime) {
        return localDateTime.format(dateTimeFormatter);
    }
    public static String convertToString(LocalDate localDate) {
        return localDate.format(dateFormatter);
    }

    public static String dateToString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = simpleDateFormat.format(date);
        return time;
    }

    /**
     * 时间转换为季度
     * @param date (yyyy-MM-dd HH:mm:ss)
     * @return
     */
    public static String dateToQuarter(String date) {
        LocalDateTime dateTime = DateUtils.convertToDateTime(date);
        LocalDateTime convert = DateUtils.convert(dateTime);
        return DateUtils.convertToString(convert);
    }
    public static LocalDateTime convert(LocalDateTime localDateTime){
        int monthValue = localDateTime.getMonthValue();
        int year = localDateTime.getYear();
        if (monthValue > 1 && monthValue < 3){
            monthValue = 1;
        }else if (monthValue >= 3 && monthValue < 6){
            monthValue = 3;
        }else if(monthValue >= 6 && monthValue < 9){
            monthValue = 6;
        }else{
            monthValue = 9;
        }
        return LocalDateTime.of(year, monthValue, 1, 0, 0);
    }
}
