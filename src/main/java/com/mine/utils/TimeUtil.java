package com.mine.utils;

/**
 * @author Teng
 * @create 2020-08-21
 */
public class TimeUtil {
    /**
     * 时间转换
     * @param time ms
     * @return x小时x分钟x秒
     */
    public static String formatTime(long time) {
        long hours = time / (1000 * 60 * 60);
        long minutes = (time - hours * (1000 * 60 * 60)) / (1000 * 60);
        long second = (time - hours * (1000 * 60 * 60) - minutes * (1000 * 60)) / 1000;
        String diffTime = hours + "小时" + minutes + "分钟" + second + "秒";
        return diffTime;
    }
}
