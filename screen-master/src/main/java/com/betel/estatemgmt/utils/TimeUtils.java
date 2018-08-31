package com.betel.estatemgmt.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 时间工具类
 * </p>
 * Author: zhangjian  <br/>
 * Date: 2017/8/02 11:19 <br/>
 * Version: 1.0 <br/>
 */
public class TimeUtils {
    private static final Logger LOG = LoggerFactory.getLogger(TimeUtils.class);

    /**
     * <p>
     * 判断两个日期大小
     * </p>
     *
     * @param startTime
     * @param endTime
     * @param timeType  转换类型
     * @return true  生产日期大于截止日期  如：2017-12-11>2017-12-01
     * Author: zhangjian  <br/>
     * Date: 2017/6/28 13:53 <br/>
     * Version: 1.0 <br/>
     */
    public static boolean compareDate(String startTime, String endTime, String timeType) {
        SimpleDateFormat df = new SimpleDateFormat(timeType);
        if (StringUtil.isBlank(startTime) || StringUtil.isBlank(endTime)) {
            return false;
        }
        try {
            Date startDate = df.parse(startTime);
            Date endDate = df.parse(endTime);
            if (startDate.getTime() > endDate.getTime()) {
                return true;
            } else if (startDate.getTime() < endDate.getTime()) {
                return false;
            }
        } catch (Exception e) {
            LOG.error("=========web/v1/时间 格式错误 error!=========", e);
            return false;
        }
        return false;
    }

    /**
     * @param minDate 最小时间  2015-01
     * @param maxDate 最大时间 2015-10
     * @return 日期集合 格式为 年-月
     * @throws Exception
     */
    public static List<String> getMonthBetweenTwoTime(String minDate, String maxDate) {
        ArrayList<String> result = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        try {
            min.setTime(sdf.parse(minDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

        try {
            max.setTime(sdf.parse(maxDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

        Calendar curr = min;
        if (!curr.before(max)) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>时间开始比结束大");
            }
        }
        while (curr.before(max)) {
            result.add(sdf.format(curr.getTime()));
            curr.add(Calendar.MONTH, 1);
        }

        return result;
    }

    /**
     * 默认获取当前时间的前6个月：yyyy-MM
     *
     * @return
     */
    public static Map<String, Object> getDefaultCurTimeSixMonthAgo() {
        Map<String, Object> time = new HashMap<>();
        //定义日期格式
        SimpleDateFormat matter = new SimpleDateFormat("yyyy-MM");
        Calendar calendar = Calendar.getInstance();
        //将calendar装换为Date类型且是当前年月的上一个月
        calendar.add(Calendar.MONTH, -1);
        Date date = calendar.getTime();
        String time01 = matter.format(date);
        //获取当前时间的前6个月
        calendar.add(Calendar.MONTH, -5);
        Date date02 = calendar.getTime();
        String time02 = matter.format(date02);
        if (LOG.isDebugEnabled()) {
            LOG.debug(">>>>>>>>>>>>>>>>>>>开始日期>>>>>>>>>" + time01);
            LOG.debug(">>>>>>>>>>>>>>>>>>>结束日期>>>>>>>>>" + time01);
        }
        time.put("min", time02);
        time.put("max", time01);
        return time;
    }

    /**
     * 获取两个时间差
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static int getMmonthDifferenceBetweenTwoDates(String startTime, String endTime) {
        if (StringUtil.isBlank(startTime)) {
            return 0;
        }
        if (StringUtil.isBlank(endTime)) {
            return 0;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar bef = Calendar.getInstance();
        Calendar aft = Calendar.getInstance();
        try {
            if (!StringUtil.isBlank(startTime)) {
                bef.setTime(sdf.parse(startTime));
            }
            if (!StringUtil.isBlank(endTime)) {
                aft.setTime(sdf.parse(endTime));
            }

        } catch (ParseException e) {
            LOG.error(">>>>>>>>>>>>>>>>>>>获取两个日期之间差距报错>>>>>", e);
        }
        int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
        int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
        int value = Math.abs(month + result);
        if (LOG.isDebugEnabled()) {
            LOG.debug(">>>>>>>>>>>>>>>>两个日期之间差距" + value);
        }
        return value;
    }

    /**
     * 结束时间为空
     *
     * @param startTime
     * @return
     */
    public static List<String> getStartTimeSixMonth(String startTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

        Date date1 = null;
        try {
            date1 = sdf.parse(startTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        //加一个月
        List<String> times = new ArrayList<>();
        times.add(startTime);
        for (int i = 1; i < 6; i++) {
            calendar.add(Calendar.MONTH, i);
            Date date2 = calendar.getTime();
            String time11 = sdf.format(date2);
            times.add(time11);
            if (LOG.isDebugEnabled()) {
                LOG.debug(">>>>>>>>>>>>>>>>开始时间不为空时" + time11);
            }
            calendar = Calendar.getInstance();
            calendar.setTime(date1);
        }
        return times;
    }

    /**
     * 开始时间为空
     *
     * @param endTime
     * @return
     */
    public static List<String> getEndTimeSixMonth(String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

        Date date1 = null;
        try {
            date1 = sdf.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        //加一个月
        List<String> times = new ArrayList<>();
        times.add(endTime);
        for (int i = 1; i < 6; i++) {
            calendar.add(Calendar.MONTH, -i);
            Date date2 = calendar.getTime();
            String time11 = sdf.format(date2);
            times.add(time11);
            if (LOG.isDebugEnabled()) {
                LOG.debug(">>>>>>>>>>>>>>>>结束时间不为空时" + time11);
            }
            calendar = Calendar.getInstance();
            calendar.setTime(date1);
        }
        return times;
    }

//    /**
//     * @param args
//     * @throws ParseException
//     */
//    public static void main(String[] args) throws ParseException {
//        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date d1=sdf.parse("2012-09-08 10:10:10");
//        Date d2=sdf.parse("2012-09-15 00:00:00");
//        System.out.println(daysBetween(d1,d2));
//
//        System.out.println(daysBetween("2012-09-08 10:10:10","2012-09-15 00:00:00"));
//    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long betweenDays = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(betweenDays)) - 1;
    }

    /**
     * 字符串的日期格式的计算
     */
    public static int daysBetween(String smdate, String bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(bdate));
        long time2 = cal.getTimeInMillis();
        long betweenDays = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(betweenDays));
    }

    /**
     * 当日还剩多少秒
     *
     * @return
     */
    public static Long getSec() {
        Calendar curDate = Calendar.getInstance();
        Calendar nextDayDate = new GregorianCalendar(curDate.get(Calendar.YEAR), curDate.get(Calendar.MONTH), curDate.get(Calendar.DATE) + 1, 0, 0, 0);
        Long sec = (nextDayDate.getTimeInMillis() - curDate.getTimeInMillis()) / 1000;
        LOG.debug("当日还剩余" + (sec + "秒"));
        return sec;
    }


    /**
     * 获取过去或者未来 任意天内的日期数组
     *
     * @param intervals intervals天内
     * @return 日期数组
     */
    public static ArrayList<String> test(int intervals) {
        ArrayList<String> pastDaysList = new ArrayList<>();
        ArrayList<String> fetureDaysList = new ArrayList<>();
        for (int i = 0; i < intervals; i++) {
            pastDaysList.add(getPastDate(i));
            fetureDaysList.add(getFetureDate(i));
        }
        return pastDaysList;
    }

    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        if (LOG.isDebugEnabled()) {
            LOG.debug(result);
        }
        return result;
    }

    /**
     * 获取未来 第 past 天的日期
     *
     * @param past
     * @return
     */
    public static String getFetureDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today) + " 00:00:00";
        if (LOG.isDebugEnabled()) {
            LOG.debug("---------------当日的" + past + "天后时间：" + result);
        }
        return result;
    }

//    public static void main(String[] args) {
//        System.out.println(splitStrDate("  2018-01-03 00:00:00  "));
//    }

    /**
     * 根据格式获得相应的日期：如：2018-03-09
     *
     * @param strDate
     * @param strFormat
     * @return
     * @throws ParseException
     */
    public static Date getDate(String strDate, String strFormat) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(strFormat);
        Date date = simpleDateFormat.parse(strDate);
        return date;
    }

    /**
     * 根据格式转换日期为字符串
     *
     * @param date
     * @param strFormat
     * @return
     */
    public static String getStrDate(Date date, String strFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(strFormat);
        return simpleDateFormat.format(date);
    }

    /***
     * 截取日期格式为年月日：如2018-01-11
     * @param strDate
     * @return
     */
    public static String splitStrDate(String strDate) {
        if (!StringUtil.isBlank(strDate)) {
            String[] dataArray = strDate.trim().split(" ");
            return dataArray[0];
        }
        return null;
    }
}
