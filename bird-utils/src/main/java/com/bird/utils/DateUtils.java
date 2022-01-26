package com.bird.utils;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @Author lipu
 * @Date 2021/8/25 18:29
 * @Description 日期工具类
 */
public class DateUtils {
    /**
     * 日期格式化类
     */
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 日期格式化类
     */
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    private static final Integer WEEK = 7;
    private static final Integer DAY = 24;
    private static final Integer MONTH = 30;
    private static final Integer YEAR = 12;

    /**
     * @Author lipu
     * @Date 2021/8/25 18:33
     * @Description 获取时间断点 WEEK(7个点)
     */
    public static List<Point> getWeekPoint() {
        //获取今日凌晨12:00:00时间 也就是明日的00:00:00
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime tomorrow = localDateTime.withHour(0).withMinute(0).withSecond(0).plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM月dd日");
        List<Point> pointList = new ArrayList<>();
        //切分七个时间点
        for (int i = 0; i < WEEK; i++) {
            Point point=new Point();
            LocalDateTime beforePoint = tomorrow.plusDays(-1 - i);
            LocalDateTime afterPoint = tomorrow.plusDays(-i);
            String startStr = TIME_FORMATTER.format(beforePoint);
            String endStr = TIME_FORMATTER.format(afterPoint);

            //组装key与value
            String beforeStr = formatter.format(beforePoint);
            String afterStr = formatter.format(afterPoint);
            String key = beforeStr + "~" + afterStr;
            point.setKey(key);
            point.setStart(beforePoint);
            point.setEnd(afterPoint);
            point.setStartStr(startStr);
            point.setEndStr(endStr);
            point.setName(afterStr);
            pointList.add(point);
        }
        return pointList;
    }


    /**
     * @Author lipu
     * @Date 2021/8/25 20:10
     * @Description 获取时间断点 DAY(24个点)
     */
    public static List<Point> getDayPoint() {
        //获取今日凌晨12:00:00时间 也就是明日的00:00:00
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime tomorrow = localDateTime.withHour(0).withMinute(0).withSecond(0).plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd日HH时");
        List<Point> pointList = new ArrayList<>();
        //切分24个时间点
        for (int i = 0; i < DAY; i++) {
            Point point=new Point();
            LocalDateTime beforePoint = tomorrow.plusHours(-1 - i);
            LocalDateTime afterPoint = tomorrow.plusHours(-i);
            String startStr = TIME_FORMATTER.format(beforePoint);
            String endStr = TIME_FORMATTER.format(afterPoint);

            //组装key与value
            String beforeStr = formatter.format(beforePoint);
            String afterStr = formatter.format(afterPoint);
            String key = beforeStr + "~" + afterStr;
            point.setKey(key);
            point.setStart(beforePoint);
            point.setEnd(afterPoint);
            point.setStartStr(startStr);
            point.setEndStr(endStr);
            point.setName(afterStr);
            pointList.add(point);
        }
        return pointList;
    }

    /**
     * @Author lipu
     * @Date 2021/8/25 20:17
     * @Description 获取时间断点 MONTH(30个点)
     */
    public static List<Point> getMonthPoint() {
        //获取今日凌晨12:00:00时间 也就是明日的00:00:00
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime tomorrow = localDateTime.withHour(0).withMinute(0).withSecond(0).plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM月dd日");
        List<Point> pointList = new ArrayList<>();
        //切分30个时间点
        for (int i = 0; i < MONTH; i++) {
            Point point=new Point();
            LocalDateTime beforePoint = tomorrow.plusDays(-1 - i);
            LocalDateTime afterPoint = tomorrow.plusDays(-i);
            String startStr = TIME_FORMATTER.format(beforePoint);
            String endStr = TIME_FORMATTER.format(afterPoint);

            //组装key与value
            String beforeStr = formatter.format(beforePoint);
            String afterStr = formatter.format(afterPoint);
            String key = beforeStr + "~" + afterStr;
            point.setKey(key);
            point.setStart(beforePoint);
            point.setEnd(afterPoint);
            point.setStartStr(startStr);
            point.setEndStr(endStr);
            point.setName(afterStr);
            pointList.add(point);
        }
        return pointList;
    }


    /**
     * @Author lipu
     * @Date 2021/8/25 20:23
     * @Description 获取时间断点 YEAR(12个点)
     */
    public static List<Point> getYearPoint() {
        //获取今日凌晨12:00:00时间 也就是明日的00:00:00
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime tomorrow = localDateTime.withHour(0).withMinute(0).withSecond(0).plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月");
        List<Point> pointList = new ArrayList<>();
        //切分七个时间点
        for (int i = 0; i < YEAR; i++) {
            Point point=new Point();
            LocalDateTime beforePoint = tomorrow.plusMonths(-1 - i);
            LocalDateTime afterPoint = tomorrow.plusMonths(-i);
            String startStr = TIME_FORMATTER.format(beforePoint);
            String endStr = TIME_FORMATTER.format(afterPoint);

            //组装key与value
            String beforeStr = formatter.format(beforePoint);
            String afterStr = formatter.format(afterPoint);
            String key = beforeStr + "~" + afterStr;
            point.setKey(key);
            point.setStart(beforePoint);
            point.setEnd(afterPoint);
            point.setStartStr(startStr);
            point.setEndStr(endStr);
            point.setName(afterStr);
            pointList.add(point);
        }
        return pointList;
    }

    /**
     * @Author lipu
     * @Date 2021/11/2 18:43
     * @Description 时间戳转格式化字符串
     */
    public static String timeStampToStr(Long timeStamp){
        Date date=new Date(timeStamp);
        return DATE_FORMAT.format(date);
    }


    @Data
    public static class Point {
        private String name;
        private String key;
        private LocalDateTime start;
        private LocalDateTime end;
        private String startStr;
        private String endStr;
    }
}
