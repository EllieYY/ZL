package com.sk.zl.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Description : 日期工具
 * @Author : Ellie
 * @Date : 2019/2/27
 */
public class DateUtil {
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String MINUTE_PATTERN = "yyyy-MM-dd HH:mm";
    public static final String HOUR_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String MONTH_PATTERN = "yyyy-MM";
    public static final String YEAR_PATTERN = "yyyy";
    public static final String MINUTE_ONLY_PATTERN = "mm";
    public static final String HOUR_ONLY_PATTERN = "HH";

    /** 字符串转换成Date */
    public static Date dateParse(String dateTimeString, String pattern) throws ParseException{
        if(pattern.isEmpty()){
            pattern = DateUtil.DATE_PATTERN;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.parse(dateTimeString);
    }

    /** Date转字符串 */
    public static String dateFormat(Date date, String pattern) throws ParseException{
        if(pattern.isEmpty()){
            pattern = DateUtil.DATE_PATTERN;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /** 获取当前时间 */
    public static Date getNow() {
        Date currentTime = new Date();
        return currentTime;
    }

    /** 统计两个日期之间的天数，不包含今天 */
    public static int daysBetweenDate(Date startDate, String endDate) throws ParseException {
        Date dateStart = dateParse(dateFormat(startDate, DATE_PATTERN), DATE_PATTERN);
        Date dateEnd = dateParse(endDate, DATE_PATTERN);
        return (int) ((dateEnd.getTime() - dateStart.getTime())/1000/60/60/24);
    }

    /** 当天是一年中的第几天 */
    public static int daysOfTodayInYear() {
        Date date = new Date();
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(date);
        return startCalendar.get(Calendar.DAY_OF_YEAR);
    }
}
