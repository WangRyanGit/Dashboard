package com.ibb.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil
{
    // public static java.sql.Date JavaDateToSqlDate(java.util.Date date)
    // {
    // return new java.sql.Date(date.getTime());
    // }

    public static java.util.Date SqlDateToJavaDate(java.sql.Date date)
    {
        return new java.util.Date(date.getTime());
    }

    public static Timestamp JavaDateToTimestamp(java.util.Date date)
    {
        return new Timestamp(date.getTime());
    }

    static DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
    static DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static DateFormat format3 = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static Date StringToDate(String str, String hour)
    {
        Date date = null;
        try
        {
            Calendar c = Calendar.getInstance();
            date = format1.parse(str);
            int h = Integer.parseInt(hour);
            c.setTime(date);
            c.add(Calendar.HOUR_OF_DAY, h);
            date = c.getTime();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return date;
    }

    public static String DateToString(Date date) throws ParseException {
        return format2.format(date);
    }

    public static String TimeToString(Date date) throws ParseException {
        return format3.format(date);
    }

    public static Date StringToTime(String local) throws ParseException {
        return format3.parse(local);
    }

    public static Date addTime(Date date, int day, int hour, int minute) // 今天的零点钟
    {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, day);
        calendar.add(Calendar.HOUR_OF_DAY, hour);
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    public static long startTime() // 今天的零点钟
    {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long start = calendar.getTime().getTime();
        return start;
    }

    public static long endTime() // 明天的零点钟
    {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long time = calendar.getTime().getTime();
        return time;
    }

    public static String AKToString(Date date)
    {
        return AKToString(date, "yyyy-MM-dd");
    }

    public static String AKToString(Date date, String format)
    {
        SimpleDateFormat formater = new SimpleDateFormat(format);
        return formater.format(date);
    }

    //获取当前日期
    public static Date getToday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 0);
        date = calendar.getTime();
        return date;
    }
    //获取后一个月日期
    public static Date getOneMONTH(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH,1);
        date = calendar.getTime();
        return date;
    }
    //获取后三个月日期
    public static Date getThreeMONTH(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH,3);
        date = calendar.getTime();
        return date;
    }
    //获取后六个月日期
    public static Date getSixMONTH(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH,6);
        date = calendar.getTime();
        return date;
    }
    //获取后12个月日期
    public static Date getTwelveMONTH(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH,12);
        date = calendar.getTime();
        return date;
    }

    //获取后四个小时日期
    public static Date getFourHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY,4);
        date = calendar.getTime();
        return date;
    }

    //获取后三天日期
    public static Date getThreeDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,3);
        date = calendar.getTime();
        return date;
    }

    /*
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String s) throws ParseException{
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

}
