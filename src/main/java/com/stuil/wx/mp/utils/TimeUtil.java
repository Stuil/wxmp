package com.stuil.wx.mp.utils;

import com.alibaba.fastjson.JSONObject;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.lang.Times;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @description: 时间工具类
 */
public class TimeUtil {

    //============================借助Calendar类获取今天、昨天、本周、上周、本年及特定时间的开始时间和结束时间（返回类型为date类型）========================

    /**
     * 获取当天开始时间
     *
     * @return
     */
    public static Date getDayBegin() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);//0点
        cal.set(Calendar.MINUTE, 0);//0分
        cal.set(Calendar.SECOND, 0);//0秒
        cal.set(Calendar.MILLISECOND, 0);//0毫秒
        return cal.getTime();
    }


    /**
     * 获取当天结束时间
     *
     * @return
     */
    public static Date getDayEnd() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 23);//23点
        cal.set(Calendar.MINUTE, 59);//59分
        cal.set(Calendar.SECOND, 59);//59秒
        return cal.getTime();
    }


    /**
     * 获取昨天开始时间
     *
     * @return
     */
    public static Date getBeginDayOfYesterday() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getDayBegin());//当天开始时间
        cal.add(Calendar.DAY_OF_MONTH, -1);//当天月份天数减1
        return cal.getTime();
    }


    /**
     * 获取昨天结束时间
     *
     * @return
     */
    public static Date getEndDayOfYesterday() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getDayEnd());//当天结束时间
        cal.add(Calendar.DAY_OF_MONTH, -1);//当天月份天数减1
        return cal.getTime();
    }


    /**
     * 获取明天开始时间
     *
     * @return
     */
    public static Date getBeginDayOfTomorrow() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getDayBegin());//当天开始时间
        cal.add(Calendar.DAY_OF_MONTH, 1);//当天月份天数加1
        return cal.getTime();
    }


    /**
     * 获取明天结束时间
     *
     * @return
     */
    public static Date getEndDayOfTomorrow() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getDayEnd());//当天结束时间
        cal.add(Calendar.DAY_OF_MONTH, 1);//当天月份天数加1
        return cal.getTime();
    }


    /**
     * 获取某个日期的开始时间
     *
     * @param d
     * @return
     */
    public static Timestamp getDayStartTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) {
            calendar.setTime(d);
        }
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }


    /**
     * 获取某个日期的结束时间
     *
     * @param d
     * @return
     */
    public static Timestamp getDayEndTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) {
            calendar.setTime(d);
        }
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Timestamp(calendar.getTimeInMillis());
    }


    /**
     * 获取某个日期的月开始时间
     *
     * @param d
     * @return
     */
    public static Timestamp getMonthStartTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) {
            calendar.setTime(d);
        }
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                1, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 获取某个日期的月的 结束时间
     *
     * @param d
     * @return
     */
    public static Timestamp getMonthEndTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) {
            calendar.setTime(d);
        }
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Timestamp(calendar.getTimeInMillis());
    }


    /**
     * 获取本周的开始时间
     *
     * @return
     */
    @SuppressWarnings("unused")
    public static Date getBeginDayOfWeek() {
        Date date = new Date();
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
            dayOfWeek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayOfWeek);
        return getDayStartTime(cal.getTime());
    }


    /**
     * 获取本周的结束时间
     *
     * @return
     */
    public static Date getEndDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDayOfWeek());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEndSta = cal.getTime();
        return getDayEndTime(weekEndSta);
    }


    /**
     * 获取上周开始时间
     */
    @SuppressWarnings("unused")
    public static Date getBeginDayOfLastWeek() {
        Date date = new Date();
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek - 7);
        return getDayStartTime(cal.getTime());
    }


    /**
     * 获取上周的结束时间
     *
     * @return
     */
    public static Date getEndDayOfLastWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDayOfLastWeek());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEndSta = cal.getTime();
        return getDayEndTime(weekEndSta);
    }


    /**
     * 获取今年是哪一年
     *
     * @return
     */
    public static Integer getNowYear() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return Integer.valueOf(gc.get(1));
    }


    /**
     * 获取本月是哪一月
     *
     * @return
     */
    public static int getNowMonth() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return gc.get(2) + 1;
    }


    /**
     * 获取本月的开始时间
     *
     * @return
     */
    public static Date getBeginDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1, 1);
        return getDayStartTime(calendar.getTime());
    }


    /**
     * 获取本月的结束时间
     *
     * @return
     */
    public static Date getEndDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1, 1);
        int day = calendar.getActualMaximum(5);
        calendar.set(getNowYear(), getNowMonth() - 1, day);
        return getDayEndTime(calendar.getTime());
    }


    /**
     * 获取上月的开始时间
     *
     * @return
     */
    public static Date getBeginDayOfLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 2, 1);
        return getDayStartTime(calendar.getTime());
    }


    /**
     * 获取上月的结束时间
     *
     * @return
     */
    public static Date getEndDayOfLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 2, 1);
        int day = calendar.getActualMaximum(5);
        calendar.set(getNowYear(), getNowMonth() - 2, day);
        return getDayEndTime(calendar.getTime());
    }


    /**
     * 获取本年的开始时间
     *
     * @return
     */
    public static Date getBeginDayOfYear() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, getNowYear());
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DATE, 1);
        return getDayStartTime(cal.getTime());
    }


    /**
     * 获取本年的结束时间
     *
     * @return
     */
    public static Date getEndDayOfYear() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, getNowYear());
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DATE, 31);
        return getDayEndTime(cal.getTime());
    }

    /**
     * 获取指定年的开始时间
     *
     * @return
     */
    public static Date getBeginDayOfYear(Integer year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DATE, 1);
        return getDayStartTime(cal.getTime());
    }


    /**
     * 获取指定年的结束时间
     *
     * @return
     */
    public static Date getEndDayOfYear(Integer year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DATE, 31);
        return getDayEndTime(cal.getTime());
    }

    /**
     * 两个日期相减得到的天数
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static int getDiffDays(Date beginDate, Date endDate) {
        if (beginDate == null || endDate == null) {
            throw new IllegalArgumentException("getDiffDays param is null!");
        }
        long diff = (endDate.getTime() - beginDate.getTime()) / (1000 * 60 * 60 * 24);
        int days = new Long(diff).intValue();
        return days;
    }


    /**
     * 两个日期相减得到的毫秒数
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static long dateDiff(Date beginDate, Date endDate) {
        long date1ms = beginDate.getTime();
        long date2ms = endDate.getTime();
        return date2ms - date1ms;
    }


    /**
     * 获取两个日期中的最大日起
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static Date max(Date beginDate, Date endDate) {
        if (beginDate == null) {
            return endDate;
        }
        if (endDate == null) {
            return beginDate;
        }
        if (beginDate.after(endDate)) {//beginDate日期大于endDate
            return beginDate;
        }
        return endDate;
    }


    /**
     * 获取两个日期中的最小日期
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static Date min(Date beginDate, Date endDate) {
        if (beginDate == null) {
            return endDate;
        }
        if (endDate == null) {
            return beginDate;
        }
        if (beginDate.after(endDate)) {
            return endDate;
        }
        return beginDate;
    }


    /**
     * 获取某月该季度的第一个月
     *
     * @param date
     * @return
     */
    public static Date getFirstSeasonDate(Date date) {
        final int[] SEASON = {1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int sean = SEASON[cal.get(Calendar.MONTH)];
        cal.set(Calendar.MONTH, sean * 3 - 3);
        return cal.getTime();
    }


    /**
     * 返回某个日期下几天的日期
     *
     * @param date
     * @param i
     * @return
     */
    public static Date getNextDay(Date date, int i) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) + i);
        return cal.getTime();
    }


    /**
     * 返回某个日期前几天的日期
     *
     * @param date
     * @param i
     * @return
     */
    public static Date getFrontDay(Date date, int i) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) - i);
        return cal.getTime();
    }


    /**
     * 获取某年某月按天切片日期集合（某个月间隔多少天的日期集合）
     *
     * @param beginYear
     * @param beginMonth
     * @param k
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static List getTimeList(int beginYear, int beginMonth, int k) {
        List list = new ArrayList();
        Calendar begincal = new GregorianCalendar(beginYear, beginMonth, 1);
        int max = begincal.getActualMaximum(Calendar.DATE);
        for (int i = 1; i < max; i = i + k) {
            list.add(begincal.getTime());
            begincal.add(Calendar.DATE, k);
        }
        begincal = new GregorianCalendar(beginYear, beginMonth, max);
        list.add(begincal.getTime());
        return list;
    }


    /**
     * 获取某年某月到某年某月按天的切片日期集合（间隔天数的集合）
     *
     * @param beginYear
     * @param beginMonth
     * @param endYear
     * @param endMonth
     * @param k
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static List getTimeList(int beginYear, int beginMonth, int endYear, int endMonth, int k) {
        List list = new ArrayList();
        if (beginYear == endYear) {
            for (int j = beginMonth; j <= endMonth; j++) {
                list.add(getTimeList(beginYear, j, k));
            }
        } else {
            {
                for (int j = beginMonth; j < 12; j++) {
                    list.add(getTimeList(beginYear, j, k));
                }
                for (int i = beginYear + 1; i < endYear; i++) {
                    for (int j = 0; j < 12; j++) {
                        list.add(getTimeList(i, j, k));
                    }
                }
                for (int j = 0; j <= endMonth; j++) {
                    list.add(getTimeList(endYear, j, k));
                }
            }
        }
        return list;
    }


    //=================================时间格式转换==========================

    /**
     * date类型进行格式化输出（返回类型：String）
     *
     * @param date
     * @return
     */
    public static String dateFormat(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        return dateString;
    }


    /**
     * 将"2015-08-31 21:08:06"型字符串转化为Date
     *
     * @param str
     * @return
     * @throws ParseException
     */
    public static Date StringToDate(String str) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = (Date) formatter.parse(str);
        return date;
    }


    /**
     * 将CST时间类型字符串进行格式化输出
     *
     * @param str
     * @return
     * @throws ParseException
     */
    public static String CSTFormat(String str) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        Date date = (Date) formatter.parse(str);
        return dateFormat(date);
    }


    /**
     * 将long类型转化为Date
     *
     * @param str
     * @return
     * @throws ParseException
     */
    public static Date LongToDare(long str) throws ParseException {
        return new Date(str * 1000);
    }


    //====================================其他常见日期操作方法======================

    /**
     * 判断当前日期是否在[startDate, endDate]区间
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return
     * @author jqlin
     */
    public static boolean isEffectiveDate(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            return false;
        }
        long currentTime = new Date().getTime();
        if (currentTime >= startDate.getTime()
                && currentTime <= endDate.getTime()) {
            return true;
        }
        return false;
    }


    /**
     * 得到二个日期间的间隔天数
     *
     * @param secondString：后一个日期
     * @param firstString：前一个日期
     * @return
     */
    public static String getTwoDay(String secondString, String firstString) {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        long day = 0;
        try {
            Date secondTime = myFormatter.parse(secondString);
            Date firstTime = myFormatter.parse(firstString);
            day = (secondTime.getTime() - firstTime.getTime()) / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            return "";
        }
        return day + "";
    }


    /**
     * 时间前推或后推分钟,其中JJ表示分钟.
     *
     * @param StringTime：时间
     * @param minute：分钟（有正负之分）
     * @return
     */
    public static String getPreTime(String StringTime, String minute) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String mydate1 = "";
        try {
            Date date1 = format.parse(StringTime);
            long Time = (date1.getTime() / 1000) + Integer.parseInt(minute) * 60;
            date1.setTime(Time * 1000);
            mydate1 = format.format(date1);
        } catch (Exception e) {
            return "";
        }
        return mydate1;
    }


    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd
     *
     * @param strDate
     * @return
     */
    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }


    /**
     * 得到一个时间延后或前移几天的时间
     *
     * @param nowdate：时间
     * @param delay：前移或后延的天数
     * @return
     */
    public static String getNextDay(String nowdate, String delay) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String mdate = "";
            Date d = strToDate(nowdate);
            long myTime = (d.getTime() / 1000) + Integer.parseInt(delay) * 24 * 60 * 60;
            d.setTime(myTime * 1000);
            mdate = format.format(d);
            return mdate;
        } catch (Exception e) {
            return "";
        }
    }


    /**
     * 判断是否闰年
     *
     * @param ddate
     * @return
     */
    public static boolean isLeapYear(String ddate) {
        /**
         * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年
         * 3.能被4整除同时能被100整除则不是闰年
         */
        Date d = strToDate(ddate);
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(d);
        int year = gc.get(Calendar.YEAR);
        if ((year % 400) == 0) {
            return true;
        } else if ((year % 4) == 0) {
            if ((year % 100) == 0) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }


    /**
     * 返回美国时间格式
     *
     * @param str
     * @return
     */
    public static String getEDate(String str) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(str, pos);
        String j = strtodate.toString();
        String[] k = j.split(" ");
        return k[2] + k[1].toUpperCase() + k[5].substring(2, 4);
    }


    /**
     * 判断二个时间是否在同一个周
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameWeekDates(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
        if (0 == subYear) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR)) {
                return true;
            }
        } else if (1 == subYear && 11 == cal2.get(Calendar.MONTH)) {
            // 如果12月的最后一周横跨来年第一周的话则最后一周即算做来年的第一周
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR)) {
                return true;
            }
        } else if (-1 == subYear && 11 == cal1.get(Calendar.MONTH)) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 产生周序列,即得到当前时间所在的年度是第几周
     *
     * @return
     */
    public static String getSeqWeek() {
        Calendar c = Calendar.getInstance(Locale.CHINA);
        String week = Integer.toString(c.get(Calendar.WEEK_OF_YEAR));
        if (week.length() == 1)
            week = "0" + week;
        String year = Integer.toString(c.get(Calendar.YEAR));
        return year + "年第" + week + "周";
    }


    /**
     * 获得一个日期所在的周的星期几的日期，如要找出2002年2月3日所在周的星期一是几号
     *
     * @param sdate：日期
     * @param num：星期几（星期天是一周的第一天）
     * @return
     */
    public static String getWeek(String sdate, String num) {
        // 再转换为时间
        Date dd = strToDate(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(dd);
        if (num.equals("1")) // 返回星期一所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        else if (num.equals("2")) // 返回星期二所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        else if (num.equals("3")) // 返回星期三所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        else if (num.equals("4")) // 返回星期四所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        else if (num.equals("5")) // 返回星期五所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        else if (num.equals("6")) // 返回星期六所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        else if (num.equals("0")) // 返回星期日所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
    }


    /**
     * 根据一个日期，返回是星期几的字符串
     *
     * @param sdate
     * @return
     */
    public static String getWeek(String sdate) {
        // 再转换为时间
        Date date = strToDate(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // int hour=c.get(Calendar.DAY_OF_WEEK);
        // hour中存的就是星期几了，其范围 1~7
        // 1=星期日 7=星期六，其他类推
        return new SimpleDateFormat("EEEE").format(c.getTime());
    }


    /**
     * 根据一个日期，返回是星期几的字符串
     *
     * @param sdate
     * @return
     */
    public static String getWeekStr(String sdate) {
        String str = "";
        str = getWeek(sdate);
        if ("1".equals(str)) {
            str = "星期日";
        } else if ("2".equals(str)) {
            str = "星期一";
        } else if ("3".equals(str)) {
            str = "星期二";
        } else if ("4".equals(str)) {
            str = "星期三";
        } else if ("5".equals(str)) {
            str = "星期四";
        } else if ("6".equals(str)) {
            str = "星期五";
        } else if ("7".equals(str)) {
            str = "星期六";
        }
        return str;
    }


    /**
     * 两个时间之间的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long getDays(String date1, String date2) {
        if (date1 == null || date1.equals(""))
            return 0;
        if (date2 == null || date2.equals(""))
            return 0;
        // 转换为标准时间
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        Date mydate = null;
        try {
            date = myFormatter.parse(date1);
            mydate = myFormatter.parse(date2);
        } catch (Exception e) {
        }
        long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        return day;
    }


    /**
     * 形成如下的日历 ， 根据传入的一个时间返回一个结构 星期日 星期一 星期二 星期三 星期四 星期五 星期六 下面是当月的各个时间
     * 此函数返回该日历第一行星期日所在的日期
     *
     * @param sdate
     * @return
     */
    public static String getNowMonth(String sdate) {
        // 取该时间所在月的一号
        sdate = sdate.substring(0, 8) + "01";

        // 得到这个月的1号是星期几
        Date date = strToDate(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int u = c.get(Calendar.DAY_OF_WEEK);
        String newday = getNextDay(sdate, (1 - u) + "");
        return newday;
    }


    /**
     * 根据用户传入的时间表示格式，返回当前时间的格式 如果是yyyyMMdd，注意字母y不能大写
     *
     * @param sformat
     * @return
     */
    public static String getUserDate(String sformat) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(sformat);
        String dateString = formatter.format(currentTime);
        return dateString;
    }


    /**
     * 返回一个i位数的随机数
     *
     * @param i
     * @return
     */
    public static String getRandom(int i) {
        Random jjj = new Random();
        // int suiJiShu = jjj.nextInt(9);
        if (i == 0)
            return "";
        String jj = "";
        for (int k = 0; k < i; k++) {
            jj = jj + jjj.nextInt(9);
        }
        return jj;
    }


    /**
     * 取得数据库主键 生成格式为yyyymmddhhmmss+k位随机数
     *
     * @param k：表示是取几位随机数，可以自己定
     * @return
     */
    public static String getNo(int k) {
        return getUserDate("yyyyMMddhhmmss") + getRandom(k);
    }

    /**
     * 计算两个日期相差的自然日天数，区分正负
     *
     * @param startDay 格式yyyy-MM-dd
     * @param endDay   格式yyyy-MM-dd
     * @return
     */
    public static int diffNaturalDay(String startDay, String endDay) {
        try {
            long epochDay1 = LocalDate.parse(startDay).toEpochDay();
            long epochDay2 = LocalDate.parse(endDay).toEpochDay();
            return (int) (epochDay2 - epochDay1);
        } catch (Exception e) {
            throw Lang.makeThrow("日期字符串非法:%s-%s", startDay, endDay);
        }
    }

    /**
     * 计算两个时间戳的自然日天数，区分正负
     *
     * @param startTimestamp
     * @param endTimestamp
     * @return
     */
    public static int diffNaturalDay(long startTimestamp, long endTimestamp) {
        return diffNaturalDay(Times.format("yyyy-MM-dd", Times.ts2D(startTimestamp)), Times.format("yyyy-MM-dd", Times.ts2D(endTimestamp)));
    }

    /**
     * 计算时间戳与当前时间戳的自然日天数，区分正负
     *
     * @param timestamp
     * @return
     */
    public static int diffNaturalDay(long timestamp) {
        return diffNaturalDay(Times.format("yyyy-MM-dd", Times.ts2D(timestamp)), Times.format("yyyy-MM-dd", new Date()));
    }


    /**
     * 获取days后的零点时间戳
     *
     * @param days 天数 如负数就向前推
     * @return 时间戳秒
     */
    public static long nextDayZero(int days) {
        try {
            String day = Times.nextDay(days);
            return Times.parse("yyyy-MM-dd", day).getTime() / 1000;
        } catch (ParseException e) {
            return 0;
        }
    }

    /**
     * 安全的解析时间
     *
     * @param fmt   格式
     * @param value 时间字符串
     * @param df    默认值
     * @return
     */
    public static Date safeParse(String fmt, String value, Date df) {
        try {
            if(Strings.isBlank(value)){
                return df;
            }
            return Times.parse(fmt, value);
        } catch (Exception e) {
            return df;
        }
    }

    /**
     * 返回两时间戳间的年份
     *
     * @param startTimestamp
     * @param endTimestamp
     * @return
     */
    public static List<String> getYears(long startTimestamp, long endTimestamp) {
        List<String> years = new ArrayList<>();
        if (startTimestamp > endTimestamp) {
            return years;
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(Times.ts2D(startTimestamp));
        int startYear = cal1.get(Calendar.YEAR);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(Times.ts2D(endTimestamp));
        int endYear = cal2.get(Calendar.YEAR);
        for (int y = startYear; y <= endYear; y++) {
            years.add(String.valueOf(y));
        }
        return years;
    }

    /**
     * 获取startDateStr与endDateStr之间的天数字符串集合
     *
     * @param startDateStr
     * @param endDateStr
     * @return
     */
    public static List<String> getRangDateDay(String startDateStr, String endDateStr) {
        List<String> pastDaysList = new ArrayList<>();
        try {
            //转换成日期
            LocalDate startDate = LocalDate.parse(startDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate endDate = LocalDate.parse(endDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            //计算之间相差天数
            //天数之差这里取包含关系，2019-10-01至2019-10-07是7天
            int days = (int) (endDate.toEpochDay() - startDate.toEpochDay()) +1;
            for (int i = 0; i < days; i++) {
                pastDaysList.add(startDate.plusDays(i).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }
        } catch (Exception e) {
        }
        return pastDaysList;
    }

    /**
     * 获取startDateStr与endDateStr之间的天数字符串集合
     *
     * @param startDateStr
     * @param endDateStr
     * @param excludeFuture 是否剔除超过今天的天
     * @return
     */
    public static List<String> getRangDateDay(String startDateStr, String endDateStr, boolean excludeFuture) {
        List<String> rangDateDay = getRangDateDay(startDateStr, endDateStr);
        if (excludeFuture) {
            List<String> futureDay = getRangDateDay(Times.nextDay(1), endDateStr);
            rangDateDay.removeAll(futureDay);
        }
        return rangDateDay;
    }

    /**
     * 获取某天的起始秒数
     *
     * @param dayStr
     * @return
     */
    public static Long dayStartTimestamp(String dayStr) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Times.D(dayStr));
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        return calendar.getTimeInMillis() / 1000;
    }

    /**
     * 获取某天的截止秒数
     *
     * @param dayStr
     * @return
     */
    public static Long dayEndTimestamp(String dayStr) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Times.D(dayStr));
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        return calendar.getTimeInMillis() / 1000;
    }

    public  void po() {
        Map<String, Object> map1 = new HashMap<>();
        map1.put("a", "1");
        Map<String, Object> map2 = new HashMap<>();
        map2.put("a", "2");
        Map<String, Object> map3 = new HashMap<>();
        map3.put("a", "1");
        List<MapsDto> list = new ArrayList<>();
        MapsDto dto=new MapsDto();
        MapsDto dto2=new MapsDto();
        MapsDto dto3=new MapsDto();
        dto.setMaps(map1);
        dto2.setMaps(map2);
        dto3.setMaps(map3);
        list.add(dto);
        list.add(dto2);
        list.add(dto3);
        Map<String, List<MapsDto>> map = list.stream().collect(Collectors.groupingBy
                (s));
        List<List<MapsDto>> mapList2=new ArrayList<>();
        List<List<Map<String, Object>>> mapList3=new ArrayList<>();
        for (Map.Entry<String, List<MapsDto>> entry : map.entrySet()) {
            mapList2.add(entry.getValue());
        }
        for (List<MapsDto> mapsDtos : mapList2) {
            List<Map<String, Object>> mapList=new ArrayList<>();
            mapsDtos.forEach(i->{
                mapList.add(i.getMaps());
            });
            mapList3.add(mapList);
        }
        System.out.println(JSONObject.toJSONString(mapList3));
    }
    Function<MapsDto, String> s = new Function<MapsDto, String>() {

        @Override
        public String apply(MapsDto t) {
            return t.getMaps().get("a").toString();
        }
    };
    public static void main(String[] args) {
        int a=10;
        List<String> list=new ArrayList<>();
        for (int i = 0; i < a; i++) {
            list.add("add"+i);
        }
        String collect = list.stream().collect(Collectors.joining(","));
        System.out.println(list);
        System.out.println(collect);
    }
}
