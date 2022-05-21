package cn.edu.gxust.blogex.common.utils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * @author zhaoyijie
 * @since 2022/4/4 21:24
 */
public class DateUtils {

    public static SimpleDateFormat formatDatetimeFull() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public static SimpleDateFormat formatDatetimeHour() {
        return new SimpleDateFormat("yyyy-MM-dd HH:00:00");
    }

    public static SimpleDateFormat formatDateLine() {
        return new SimpleDateFormat("yyyy-MM-dd");
    }

    public static SimpleDateFormat formatDateMonth() {
        return new SimpleDateFormat("yyyy-MM");
    }

    public static SimpleDateFormat formatDate() {
        return new SimpleDateFormat("yyyyMMdd");
    }

    public static SimpleDateFormat formatDateColon() {
        return new SimpleDateFormat("yyyy:MM:dd");
    }

    /**
     * Date转LocalDateTime
     */
    public static LocalDateTime date2LocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        //LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), zoneId);
        return instant.atZone(zoneId).toLocalDateTime();
    }

    /**
     * LocalDateTime转Date
     */
    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);
        return Date.from(zonedDateTime.toInstant());
    }


    /**
     * 比较两个时间
     *
     * @param source 来源日期
     * @param target 目标日期
     * @return 0: source=target, 1: source>target, -1: source<target
     */
    public static int compareDate(Date source, Date target) {
        return source.compareTo(target);
    }

    /**
     * 获取当天的零点时间戳
     *
     * @return 当天的零点时间戳
     */
    public static long getTodayStartTime() {
        //设置时区
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取当天最后一秒时间戳
     *
     * @return 当天的23点59分59秒时间戳
     */
    public static long getTodayEndTime() {
        //设置时区
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTimeInMillis();
    }

    public static Date getTodayStartDate() {
        //设置时区
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Date getTodayEndDate() {
        //设置时区
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    public static String getYesterdayStartDate() {
        //设置时区
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        calendar.add(Calendar.DATE, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date time = calendar.getTime();
        return formatDatetimeFull().format(time);
    }

    public static String getYesterdayEndDate() {
        //设置时区
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        calendar.add(Calendar.DATE, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date time = calendar.getTime();
        return formatDatetimeFull().format(time);
    }

    public static String getYesterdayDate() {
        //设置时区
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        calendar.add(Calendar.DATE, -1);
        return formatDateLine().format(calendar.getTime());
    }

    public static String getYesterdayDateStr() {
        //设置时区
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        calendar.add(Calendar.DATE, -1);
        return formatDate().format(calendar.getTime());
    }

    public static String getBeforeYesterdayDate() {
        //设置时区
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        calendar.add(Calendar.DATE, -2);
        return formatDateLine().format(calendar.getTime());
    }

    public static Date getWeekAgoDate() {
        //设置时区
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        calendar.add(Calendar.DATE, -7);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Date getDayAgoDate() {
        //设置时区
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        calendar.add(Calendar.DATE, -1);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static String getYesterdayLastHourDate() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        calendar.add(Calendar.DATE, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date time = calendar.getTime();
        return formatDatetimeFull().format(time);
    }

    public static String getTodayLastHourDate() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date time = calendar.getTime();
        return formatDatetimeFull().format(time);
    }

    public static String getLastWeekMondayBegin(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeekMonday(date));
        cal.add(Calendar.DATE, -7);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return formatDatetimeFull().format(cal.getTime());
    }

    public static String getLastWeekSundayEnd(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeekMonday(date));
        cal.add(Calendar.DATE, -1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return formatDatetimeFull().format(cal.getTime());
    }

    public static Date getThisWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        return cal.getTime();
    }

    public static List<String> getThisWeekColonList(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);//周一
        List<String> resultList = new ArrayList<>();
        //先把周一加上去
        resultList.add(formatDateColon().format(cal.getTime()));
        //再把后六天加上去
        for (int i = 0; i < 6; i++) {
            cal.add(Calendar.DATE, 1);
            resultList.add(formatDateColon().format(cal.getTime()));
        }
        return resultList;
    }

    public static List<String> getThisWeekLineList(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);//周一
        List<String> resultList = new ArrayList<>();
        //先把周一加上去
        resultList.add(formatDateColon().format(cal.getTime()));
        //再把后六天加上去
        for (int i = 0; i < 6; i++) {
            cal.add(Calendar.DATE, 1);
            resultList.add(formatDateLine().format(cal.getTime()));
        }
        return resultList;
    }

    public static List<String> generateWeekList() {
        List<String> dateList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        for (int i = 0; i < 7; i++) {
            calendar.add(Calendar.DATE, -1);
            dateList.add(formatDateLine().format(calendar.getTime()));
        }
        return dateList;
    }

    public static String beforeSixMonth() {
        //注意后面有一个空格
        Calendar calStart = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        calStart.add(Calendar.MONTH, -6);//上一月
        return formatDateLine().format(calStart.getTime());
    }

    public static String lastMonthStart() {
        //注意后面有一个空格
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd ");
        Calendar calStart = Calendar.getInstance();
        calStart.add(Calendar.MONTH, -1);//上一月
        calStart.set(Calendar.DAY_OF_MONTH, 1);
        return format.format(calStart.getTime()) + "00:00:00";
    }

    public static String lastMonthEnd() {
        //注意后面有一个空格
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd ");
        Calendar calEnd = Calendar.getInstance();
        calEnd.add(Calendar.MONTH, -1);
        calEnd.set(Calendar.DAY_OF_MONTH, calEnd.getActualMaximum(Calendar.DAY_OF_MONTH));
        return format.format(calEnd.getTime()) + "23:59:59";
    }

    public static String getYearAndMonth() {
        //注意后面有一个空格
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Calendar calStart = Calendar.getInstance();
        calStart.set(Calendar.DAY_OF_MONTH, 1);
        return format.format(calStart.getTime());
    }

    /**
     * 判断一个时间是否处于一个时间区间内
     *
     * @param date      待判断的时间对象
     * @param startHour 起止小时
     * @param endHour   终止小时
     * @return true or false
     * @author zhaoyijie
     */
    public static boolean isInTimeInterval(Date date, int startHour, int endHour) {
        Date startDate = initDateByDay(startHour, 0, 0);
        Date endDate = initDateByDay(endHour - 1, 59, 59);
        return (date.after(startDate) && date.before(endDate));
    }

    /**
     * 将日历翻到当天的某小时某分某秒
     *
     * @param minute 分
     * @param second 秒
     * @param hour   小时
     * @return 当天的某小时某分某秒Date对象
     * @author zhaoyijie
     */
    public static Date initDateByDay(int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        return calendar.getTime();
    }

    /**
     * 获取昨天yyyyMMdd日期的时间
     *
     * @author zhaoyijie
     */
    public static String formatYYYYMMDDYesterday() {
        return formatDate().format(new Date(new Date().getTime() - 24 * 3600 * 1000));
    }


    /**
     * 获取这个月第一天的yyyyMMdd格式的时间
     *
     * @author zhaoyijie
     */
    public static String getFirstDayOfMonth() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date date = calendar.getTime();
        return formatDate().format(date);
    }

    /**
     * 获取这个月最后一天的yyyyMMdd格式的时间
     *
     * @author zhaoyijie
     */
    public static String getLastDayOfMonth() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, 1);//加一个月的时间
        calendar.set(Calendar.DAY_OF_MONTH, 0);//当前天数设置为第一天
        Date date = calendar.getTime();
        return formatDate().format(date);
    }

    /**
     * 获取这个月第一天的yyyyMMdd格式的时间
     *
     * @author zhaoyijie
     */
    public static String getFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return formatDate().format(calendar.getTime());
    }

    /**
     * 获取这个月最后一天的yyyyMMdd格式的时间
     *
     * @author zhaoyijie
     */
    public static String getLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);//加一个月的时间
        calendar.set(Calendar.DAY_OF_MONTH, 0);//当前天数设置为第一天
        return formatDate().format(calendar.getTime());
    }

    /**
     * 获取某个月的天数
     *
     * @author zhaoyijie
     */
    public static int getTotalDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
}
