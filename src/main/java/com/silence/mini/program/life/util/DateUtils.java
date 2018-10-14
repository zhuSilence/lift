package com.silence.mini.program.life.util;

import org.joda.time.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>类名：DateUitls</p>
 * <p>说明：<br>日期时间通用管理类</p>
 * <p>作者： SiberXu</p>
 * <p>时间： 2015-7-31 下午01:14:50</p>
 */
public class DateUtils {

    public final static String DFyyyyMM = "yyyy-MM";
    public final static String DFyyyyMMdd = "yyyy-MM-dd";
    public final static String DFyyyyMMddHH = "yyyy-MM-dd HH";
    public final static String DFyyyyMMddHHmm = "yyyy-MM-dd HH:mm";
    public final static String DFyyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
    public final static String DFyyyyMMddHHmmss2 = "yyyyMMddHHmmss";
    public final static String DFyyyyMMddHHmmssS = "yyyy-MM-dd HH:mm:ss.S";
    public final static String DFHHmmss = "HH:mm:ss";
    public final static String DFHHmmssSSS = "HH:mm:ss.SSS";

    /**
     * <p>标题: getCurDate<p>
     * <p>说明: <br>获取当前时间</P>
     *
     * @return 当前时间
     */
    public static Date getCurDate() {

        return new Date();
    }

    public static DateTime getDateTime(Date date) {

        return new DateTime(date);
    }

    /**
     * 获取当前小时
     * @return
     */
    public static int getCurHour() {

        DateTime dateTime =  new DateTime();

        return dateTime.getHourOfDay();
    }

    /**
     * 格式化当前日期
     *
     * @return
     */
    public static String formatDate() {

        return formatDate(getCurDate());
    }

    /**
     * 按指定格式当前日期
     *
     * @param strFormat
     * @return
     */
    public static String formatDate(String strFormat) {

        return formatDate(getCurDate(), strFormat);
    }

    /**
     * 格式化日期
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date) {

        DateTime dateTime = new DateTime(date);
        return dateTime.toString(DFyyyyMMddHHmmss);
    }

    /**
     * 格式化日期
     *
     * @param date
     * @param strFormat
     * @return
     */
    public static String formatDate(Date date, String strFormat) {

        String strDate;
        if (strFormat == null) {
            strDate = formatDate(date);
        } else {
            DateTime dateTime = new DateTime(date);
            strDate = dateTime.toString(strFormat);
        }
        return strDate;
    }

    /**
     * <p>标题: compare<p>
     * <p>说明: <br>比较两个时间大小</P>
     *
     * @param date1
     * @param date2
     * @return date1大于date2 返回1，date1等于date2 返回0, date1少于date2 返回-1
     */
    public static int compare(Date date1, Date date2) {

        return date1.compareTo(date2);
    }

    /**
     * <p>标题: daysOfTwo<p>
     * <p>说明: <br>获取两个日期相差的天数</P>
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int daysOfTwo(Date date1, Date date2) {

        LocalDate start = new LocalDate(getDateTime(date1));
        LocalDate end = new LocalDate(getDateTime(date2));

        return Days.daysBetween(start, end).getDays();
    }

    /**
     * 计算两个日期之前的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int daysOfTwoByTimeMillis(Date date1, Date date2) {

        long begin = getTimeMillis(date1);
        long end = getTimeMillis(date2);
        int days = (int) Math.ceil((end - begin) / (24 * 3600 * 1000.0));
        return days > 0 ? days : 0;
    }

    /**
     * 获取时间截
     *
     * @return
     */
    public static Long getTimeMillis() {

        DateTime dateTime = new DateTime();
        return dateTime.getMillis();
    }

    /**
     * 获取时间截
     *
     * @param date
     * @return
     */
    public static Long getTimeMillis(Date date) {

        DateTime dateTime = new DateTime(date);
        return dateTime.getMillis();
    }

    /**
     * 时间截转转成日期
     *
     * @param millis
     * @return
     */
    public static Date millisToDate(Long millis) {

        DateTime dateTime = new DateTime(millis);
        return dateTime.toDate();
    }

    /**
     * <p>标题: daysOfTwo<p>
     * <p>说明: <br>获取两个日期相差的分钟数</P>
     *
     * @param date1 开始时间
     * @param date2 结整时间
     * @return
     */
    public static int minuteOfTwo(Date date1, Date date2) {

        LocalTime time1 = new DateTime(date1).toLocalTime();
        LocalTime time2 = new DateTime(date2).toLocalTime();
        return Minutes.minutesBetween(time1, time2).getMinutes();
    }

    /**
     * 获取当前时间几天后的日期
     *
     * @param day
     * @return
     */
    public static Date getAfterDate(int day) {

        DateTime dateTime = new DateTime();
        return dateTime.plusDays(day).toDate();
    }

    /**
     * 获取当前时间几天后的日期
     *
     * @param date 日期
     * @param day  天数
     * @return
     */
    public static Date getAfterDate(Date date, int day) {

        DateTime dateTime = new DateTime(date);
        return dateTime.plusDays(day).toDate();
    }

    /**
     * 获取当前时间几小时后的日期
     * @param hours
     * @return
     */
    public static Date getAfterHours(int hours) {

        DateTime dateTime = new DateTime();
        return dateTime.plusHours(hours).toDate();
    }

    /**
     * 获取当前时间几小时后的日期
     * @param hours
     * @param format
     * @return
     */
    public static String getAfterHours(int hours, String format) {

        DateTime dateTime = new DateTime();
        return dateTime.plusHours(hours).toString(format);
    }

    /**
     * 获取当前日期前几天的日期
     *
     * @param day
     * @return
     */
    public static Date getBeforDate(int day) {

        DateTime dateTime = new DateTime();
        return dateTime.minusDays(day).toDate();
    }

    /**
     * 获取当前日前前几天的日期
     * @param day
     * @param format
     * @return
     */
    public static String getBeforDateStr(int day, String format) {

        DateTime dateTime = new DateTime();
        return dateTime.minusDays(day).toString(format);
    }

    /**
     * 获取指定日期前几天的日期
     *
     * @param date
     * @param day
     * @return
     */
    public static Date getBeforDate(Date date, int day) {

        DateTime dateTime = new DateTime(date);
        return dateTime.minusDays(day).toDate();
    }

    /**
     * 获取当前时间几个月后的日期
     * @param date
     * @param month
     * @return
     */
    public static Date getAfterMonthDate(Date date, int month) {

        DateTime dateTime = new DateTime(date);
        return dateTime.plusMonths(month).toDate();
    }

    /**
     * 获取当前年的共有多少周
     *
     * @param year
     * @return
     */
    public static int getMaxWeekNumOfYear(int year) {

        Calendar c = new GregorianCalendar();
        c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);

        return getWeekOfYear(c.getTime());
    }

    /**
     * <p>标题: getWeekOfYear<p>
     * <p>说明: TODO<br>获取当前时间为第几周</P>
     *
     * @param date
     * @return
     */
    public static int getWeekOfYear(Date date) {

        DateTime dateTime = new DateTime(date);
        return dateTime.getWeekOfWeekyear();
    }

    /**
     * 今天星期几
     * @return
     */
    public static int getDayOfWeek() {

        DateTime dateTime = new DateTime();
        return dateTime.getDayOfWeek();
    }

    /**
     * 获取指定时间是星期几
     * @param date
     * @return
     */
    public static int getDayOfWeek(Date date) {

        DateTime dateTime = new DateTime(date);
        return dateTime.getDayOfWeek();
    }

    /**
     * <p>标题: getWeekOfMonth<p>
     * <p>说明: TODO<br>获取当前时间为當月第几周</P>
     *
     * @param date
     * @return
     */
    public static int getWeekOfMonth(Date date) {

        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.SUNDAY);//设置每周第一天为星期天
        cal.setTime(date);
        return cal.get(Calendar.WEEK_OF_MONTH);
    }

    // 获取某年的第几周的开始日期
    public static Date getFirstDayOfWeek(int year, int week) {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, 1);

        Calendar cal = (GregorianCalendar) c.clone();
        cal.add(Calendar.DATE, week * 7);

        return getFirstDayOfWeek(cal.getTime());
    }

    // 获取某年的第几周的结束日期
    public static Date getLastDayOfWeek(int year, int week) {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, 1);

        Calendar cal = (GregorianCalendar) c.clone();
        cal.add(Calendar.DATE, week * 7);

        return getLastDayOfWeek(cal.getTime());
    }

    // 获取当前时间所在周的开始日期
    public static Date getFirstDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.SUNDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
        return c.getTime();
    }

    // 获取当前时间所在周的结束日期
    public static Date getLastDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.SUNDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
        return c.getTime();
    }

    /**
     * 当月最后一天的日期
     * @return
     */
    public static Date getLastDayOfMonty(){

        DateTime dateTime = new DateTime();
        return dateTime.dayOfMonth().withMaximumValue().toDate();
    }

    /**
     * 指定日期最后一天的日期
     * @param date
     * @return
     */
    public static Date getLastDayOfMonty(Date date){

        DateTime dateTime = new DateTime(date);
        return dateTime.dayOfMonth().withMaximumValue().toDate();
    }

    /**
     * 获取二个日期间共有几周
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int getDaysOfWeek(Date date1, Date date2) {
        int weeks = 0;
        int week1 = getWeekOfYear(date1);
        int week2 = getWeekOfYear(date2);
        if (week2 < week1) {
            Integer year1 = Integer.parseInt(formatDate(date1, "yyyy"));
            Integer maxWeeNum = getMaxWeekNumOfYear(year1);
            weeks = (maxWeeNum - week1 + 1) + week2;
        } else {
            weeks = week2 - week1 + 1;
        }
        return weeks;
    }


    /**
     * <p>标题: getDateByString<p>
     * <p>说明: TODO<br>根據傳入的日期字符串和格式串返回Date</P>
     *
     * @param str    yyyy-mm-dd 格式的字符串
     * @param format yyyy-mm-dd 格式的字符串
     * @return Date
     */
    public static Date getDateByString(String str, String format) {
        Date date = null;
        try {
            SimpleDateFormat myFormatter = new SimpleDateFormat(format);
            date = myFormatter.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * <p>标题: getAfterDateStr<p>
     * <p>说明: TODO<br>根據傳入的日期和一個整數,獲取日期后多少天的字串</P>
     *
     * @param date   yyyy-mm-dd 格式的字符串
     * @param count  日期后多少天
     * @param format 格式字符串
     * @return String 日期后多少天的字符串形式
     */
    public static String getAfterDateStr(Date date, int count, String format) {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(date);
        aCalendar.add(Calendar.DATE, +count);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(aCalendar.getTime());
    }

    /**
     * <p>标题: parse<p>
     * <p>说明: TODO<br>按format格式將str轉換成Date</P>
     *
     * @param str
     * @param format
     * @return
     */
    public static Date parse(String str, String format) {
        Date date = new Date();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }


    public static boolean isLessCurData(Date date, int minute) {

        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(date);
        aCalendar.add(Calendar.MINUTE, +minute);
        return aCalendar.getTime().after(new Date());
    }

    /**
     * 将时间段折分为按周为单位
     * @param begin
     * @param end
     * @return
     */
    public static List<Map<String, Long>> dateToWeekDate(Date begin, Date end) {
        Calendar start = Calendar.getInstance();
        start.setTime(begin);
        start.setMinimalDaysInFirstWeek(Calendar.SUNDAY); // 表示一月一号所在周就是第一周
        start.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY); // 设置为周日   这里周日表示第一天，如果begin_time 2015-05-18 则 周日为 2015-05-17
        start.set(Calendar.HOUR_OF_DAY, 0);// 设置时间为0 HOUR_OF_DAY表示24小时
        start.set(Calendar.MINUTE, 0);// 设置分钟
        start.set(Calendar.SECOND, 0); // 设置秒为零
        // 那么这里周的时间范围 为周日凌晨 开始 到周六的最后一秒结束。
        List<Map<String, Long>> weekList = new ArrayList<>();

        List<Long> TSBegins = new ArrayList<Long>();
        List<Long> TSEnds = new ArrayList<Long>();

        Long end_time = getTimeMillis(end);
        for (long p = start.getTimeInMillis(); p < end_time; p += 86400000 * 7) {
            if (TSBegins.size() == 0) {
                TSBegins.add(p);
            } else {
                TSBegins.add(p);
                TSEnds.add(TSBegins.get(TSBegins.size() - 1));
            }
        }
        if (TSBegins.size() > 0) {
            TSEnds.add(TSBegins.get(TSBegins.size() - 1) + 86400000 * 7);
        }

        for (int i = 0; i < TSBegins.size(); i++) {
            Map<String, Long> dateMap = new HashMap<>();
            dateMap.put("beginDate", TSBegins.get(i)/1000);
            dateMap.put("endDate", TSEnds.get(i)/1000);
            weekList.add(dateMap);
        }
        return weekList;
    }

    //test
    public static void main(String[] args) {

        System.out.println(daysOfTwo(parse("2017-04-19 00:00:00", "yyyy-MM-dd HH:mm:ss"), parse("2017-04-22 23:59:59", "yyyy-MM-dd HH:mm:ss")));
        long begin = getTimeMillis(parse("2017-04-19 00:00:00", "yyyy-MM-dd HH:mm:ss"));
        long end = getTimeMillis(parse("2017-04-22 23:59:59", "yyyy-MM-dd HH:mm:ss"));
        System.out.println(begin);
        System.out.println(end);
        System.out.println(Math.ceil((end - begin) / (24 * 3600 * 1000)));
        System.out.println((int) Math.ceil((end - begin) / (24 * 3600 * 1000.0)));
        System.out.println((end - begin) / (24 * 3600 * 1000.0));
        //System.out.println(DateUtils.getDayOfWeek(new Date()));

//		System.out.println(getDayOfWeekByString("2012-04-21"));
        //String format = "yyyy-MM-dd";
//		System.out.println(DateUtils.daysOfTwo(DateUtils.getDateByString("2012-04-18", format), DateUtils.getDateByString("2012-05-19", format)));
        //System.out.println(getAfterDateStr(new Date() , 2 , format));

        //System.out.println(parse("2012-04-12" , format));
//		Date d1 = getDateByString("2013-01-05", "yyyy-MM-dd");
//		Date d2 = getDateByString("2013-01-08", "yyyy-MM-dd");
        //System.out.println(getDaysOfWeek(d1, d2));

        //System.out.println(getWeekOfYear(d1));

//		//System.out.println(getMaxWeekNumOfYear(2006));
//		Calendar calendar = Calendar.getInstance(); 
//		calendar.setTime(d1); ///现在的日期 LZ 可以自定义一个
//		long timethis = calendar.getTimeInMillis(); 
//		calendar.setTime(d2); //date为自定义的
//		long timeend = calendar.getTimeInMillis(); 
//		long days = ((timethis - timeend) / (1000 * 60 * 60 * 24))/365;
//		System.out.println(days);
//		
//		System.out.println(formatDate(getFirstDayOfWeek(d2),"yyyy-MM-dd"));

//		Date beginDate = DateUtils.getFirstDayOfWeek(d1);
//		Date endDate = DateUtils.getLastDayOfWeek(d2);
//		
//		while (DateUtils.compare(beginDate, endDate)<=0) {
//			System.out.println(formatDate(beginDate,"yyyy-MM-dd"));
//			beginDate = DateUtils.getAfterDate(beginDate, 1);
//		}
        //Integer week =getDayOfWeek(getDateByString("2013-01-13", "yyyy-MM-dd"));
        //System.out.println(week);
    }
}
