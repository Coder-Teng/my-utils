package com.mine.utils;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>Title: DateUtil</p>
 * <p>Description: 日期工具类</p>
 * <p>Company: 福建升腾资讯有限公司</p>
 *
 * @author XieNengYan
 * @version 1.0
 * @date 2020-03-24 15:46
 */
public class DateUtil {

    /**
     * 格式
     */
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 格式
     */
    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * 支持的时间格式
     */
    private static final String[] PARSE_PATTERNS = new String[]{
            "yyyyMM",
            "yyyy-MM",
            "yyyy/MM",
            "yyyyMMdd",
            "yyyy-MM-dd",
            "yyyy/MM/dd",
            "yyyyMMddHHmmss",
            "yyyyMMdd HHmmss",
            "yyyy-MM-dd HH:mm:ss",
            "yyyy/MM/dd HH:mm:ss"
    };

    /**
     * 解析日期时间，符合各个时间类型的解析
     *
     * @param dateStr 时间字符
     * @return Date对象
     */
    public static Date parseDate(String dateStr) {
        try {
            return DateUtils.parseDateStrictly(dateStr, PARSE_PATTERNS);
        } catch (ParseException e) {
            throw new RuntimeException("解析日期出错:" + dateStr + e.getMessage());
        }
    }

    /**
     * 比较两个时间的大小
     *
     * @param sourceDate  需要比较的时间
     * @param compareDate 被比较的时间
     * @return sourceDate >  compareDate 返回 true
     */
    public static boolean compareDate(Date sourceDate, Date compareDate) {
        return sourceDate.compareTo(compareDate) > 0;
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     *
     * @param date1 时间1
     * @param date2 时间2
     * @return 相差天数
     */
    public static int differentDaysByMillisecond(Date date1, Date date2) {
        return (int) ((date1.getTime() - date2.getTime()) / (1000 * 3600 * 24));
    }

    /**
     * 时间转字符
     *
     * @param date 时间
     * @return 时间字符
     */
    public static String dateToStr(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat df = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        return df.format(date);
    }


    /**
     * 时间转字符
     *
     * @param date   时间
     * @param format 格式
     * @return 时间字符
     */
    public static String dateToStr(Date date, String format) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    /**
     * 字符转时间
     *
     * @param dateStr 时间字符
     * @return 时间
     */
    public static Date strToDate(String dateStr) {
        SimpleDateFormat df = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        Date date = new Date();
        try {
            date = df.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 字符转时间
     *
     * @param dateStr 时间字符
     * @param format  格式
     * @return 时间
     */
    public static Date strToDate(String dateStr, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        Date date = new Date();
        try {
            date = df.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 当前天数+1天
     * @param inDate 初始日期
     * @param interval 天数
     * @return 加完之后的天数
     */
    public static Date arroundIntervalDay(Date inDate, int interval) {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD);
        Calendar calender = Calendar.getInstance();
        calender.setTime(inDate);
        calender.add(Calendar.DATE, interval);
        return calender.getTime();
    }
}
