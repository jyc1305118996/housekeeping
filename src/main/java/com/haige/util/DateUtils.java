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

}
