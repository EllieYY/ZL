package com.sk.zl.utils;

import com.sk.zl.aop.excption.ServiceException;

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
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final Calendar CALENDAR = Calendar.getInstance();

    /** 字符串转换成Date */
    public static Date dateParse(String dateTimeString, String pattern) {
        if(pattern.isEmpty()){
            pattern = DateUtil.DATE_PATTERN;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(dateTimeString);
        } catch (Exception e) {
            throw new ServiceException(e.toString());
        }
    }

    /** Date转字符串 */
    public static String dateFormat(Date date, String pattern) {
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

    /**
     * 日期相加减天数
     * @param date 如果为Null，则为当前时间
     * @param days 加减天数
     * @param includeTime 是否包括时分秒,true表示包含
     * @return
     * @throws ParseException
     */
    public static Date dateAddDays(Date date, int days, boolean includeTime) {
        if(date == null){
            date = new Date();
        }
        try {
            if (!includeTime) {
                SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DATE_PATTERN);
                date = sdf.parse(sdf.format(date));
            }
        } catch (Exception e) {
            throw new ServiceException(e.toString());
        }

        CALENDAR.setTime(date);
        CALENDAR.add(Calendar.DATE, days);
        return CALENDAR.getTime();
    }

    /**
     * 时间加减月数
     * @param startDate 要处理的时间，Null则为当前时间
     * @param months 加减的月数
     * @return Date
     */
    public static Date dateAddMonths(Date startDate, int months) {
        if (startDate == null) {
            startDate = new Date();
        }
        CALENDAR.setTime(startDate);
        CALENDAR.set(Calendar.MONTH, CALENDAR.get(Calendar.MONTH) + months);
        return CALENDAR.getTime();
    }

    /**
     * 时间比较（如果myDate>compareDate返回1，<返回-1，相等返回0）
     * @param myDate 时间
     * @param compareDate 要比较的时间
     * @return int
     */
    public static int dateCompare(Date myDate, Date compareDate) {
        Calendar myCal = Calendar.getInstance();
        Calendar compareCal = Calendar.getInstance();
        myCal.setTime(myDate);
        compareCal.setTime(compareDate);
        return myCal.compareTo(compareCal);
    }


    /**
     * 将日期时间格式成日期对象，和dateParse互用
     * @param dateTime Date
     * @return Date
     * @throws ParseException
     */
    public static Date dateTimeToDate(Date dateTime) {
        CALENDAR.setTime(dateTime);
        CALENDAR.set(Calendar.HOUR_OF_DAY, 0);
        CALENDAR.set(Calendar.MINUTE, 0);
        CALENDAR.set(Calendar.SECOND, 0);
        CALENDAR.set(Calendar.MILLISECOND, 0);
        return CALENDAR.getTime();
    }


    /**
     * 取得月第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDateOfMonth(Date date) {
        CALENDAR.setTime(date);
        CALENDAR.set(Calendar.DAY_OF_MONTH, CALENDAR.getActualMinimum(Calendar.DAY_OF_MONTH));
        return CALENDAR.getTime();
    }

    /**
     * 月份个数
     *
     * @param
     * @return
     */
    public static int getMonthNumInYear() {
        return 12;
    }


    /**
     * 取得年第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDateOfYear(Date date) {
        CALENDAR.setTime(date);
        CALENDAR.set(Calendar.MONTH, CALENDAR.getActualMinimum(Calendar.MONTH));
        CALENDAR.set(Calendar.DAY_OF_MONTH, CALENDAR.getActualMinimum(Calendar.DAY_OF_MONTH));
        return CALENDAR.getTime();
    }

    public static int getYear(Date date) {
        CALENDAR.setTime(date);
        return CALENDAR.get(Calendar.YEAR);
    }

    /**
     * 取得季度第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDateOfSeason(Date date) {
        return getFirstDateOfMonth(getSeasonDate(date)[0]);
    }

    public enum SeasonName{
        FIRST(1),
        SECOND(2),
        THIRD(3),
        FOURTH(4);

        private int num;

        SeasonName(int num) {
            this.num = num;
        }
    }

    /**
     * 取得季度月
     *
     * @param date
     * @return
     */
    public static Date[] getSeasonDate(Date date) {
        Date[] season = new Date[3];

        CALENDAR.setTime(date);

        int nSeason = getSeason(date);
        if (nSeason == SeasonName.FIRST.num) {
            CALENDAR.set(Calendar.MONTH, Calendar.JANUARY);
            season[0] = CALENDAR.getTime();
            CALENDAR.set(Calendar.MONTH, Calendar.FEBRUARY);
            season[1] = CALENDAR.getTime();
            CALENDAR.set(Calendar.MONTH, Calendar.MARCH);
            season[2] = CALENDAR.getTime();
        } else if (nSeason == SeasonName.SECOND.num) {
            CALENDAR.set(Calendar.MONTH, Calendar.APRIL);
            season[0] = CALENDAR.getTime();
            CALENDAR.set(Calendar.MONTH, Calendar.MAY);
            season[1] = CALENDAR.getTime();
            CALENDAR.set(Calendar.MONTH, Calendar.JUNE);
            season[2] = CALENDAR.getTime();
        } else if (nSeason == SeasonName.THIRD.num) {
            CALENDAR.set(Calendar.MONTH, Calendar.JULY);
            season[0] = CALENDAR.getTime();
            CALENDAR.set(Calendar.MONTH, Calendar.AUGUST);
            season[1] = CALENDAR.getTime();
            CALENDAR.set(Calendar.MONTH, Calendar.SEPTEMBER);
            season[2] = CALENDAR.getTime();
        } else if (nSeason == SeasonName.FOURTH.num) {
            CALENDAR.set(Calendar.MONTH, Calendar.OCTOBER);
            season[0] = CALENDAR.getTime();
            CALENDAR.set(Calendar.MONTH, Calendar.NOVEMBER);
            season[1] = CALENDAR.getTime();
            CALENDAR.set(Calendar.MONTH, Calendar.DECEMBER);
            season[2] = CALENDAR.getTime();
        }
        return season;
    }

    /**
     *
     * 1 第一季度 2 第二季度 3 第三季度 4 第四季度
     *
     * @param date
     * @return
     */
    public static int getSeason(Date date) {

        int season = 0;
        CALENDAR.setTime(date);
        int month = CALENDAR.get(Calendar.MONTH);
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.FEBRUARY:
            case Calendar.MARCH:
                season = SeasonName.FIRST.num;
                break;
            case Calendar.APRIL:
            case Calendar.MAY:
            case Calendar.JUNE:
                season = SeasonName.SECOND.num;
                break;
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.SEPTEMBER:
                season = SeasonName.THIRD.num;
                break;
            case Calendar.OCTOBER:
            case Calendar.NOVEMBER:
            case Calendar.DECEMBER:
                season = SeasonName.FOURTH.num;
                break;
            default:
                break;
        }
        return season;
    }



    /** 统计两个日期之间的天数，不包含今天 */
    public static int daysBetweenDate(Date startDate, String endDate) {
        Date dateEnd = dateParse(dateFormat(startDate, DATE_PATTERN), DATE_PATTERN);
        Date dateStart = dateParse(endDate, DATE_PATTERN);

        return (int) ((dateEnd.getTime() - dateStart.getTime())/1000/60/60/24);
    }

    /** 当天是一年中的第几天 */
    public static int daysOfTodayInYear() {
        Date date = new Date();
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(date);
        return startCalendar.get(Calendar.DAY_OF_YEAR);
    }

    public static boolean isDateBetweenDates(Date target, Date start, Date end) {
        return (start.getTime() < target.getTime() && end.getTime() > target.getTime());
    }
}
