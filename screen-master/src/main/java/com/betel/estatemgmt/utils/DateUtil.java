/*
 *   ©2016 ALL Rights Reserved DHX
 *  　　   ┏┓   ┏┓
 *  　　 ┏━┛┻━━━┛┻━┓
 *   　　┃         ┃
 *   　　┃    ━    ┃
 *   　　┃  ┳┛ ┗┳  ┃
 *   　　┃         ┃
 *   　　┃    ┻    ┃
 *   　　┗━┓     ┏━┛
 *         ┃    ┃  Code is far away from bug with the animal protecting
 *         ┃    ┃    神兽保佑,代码无bug
 *         ┃    ┗━━━━━┓
 *         ┃          ┣┓
 *         ┃          ┏┛
 *         ┗┓┓┏━━━━┓┓┏┛
 *          ┃┫┫    ┃┫┫
 *          ┗┻┛    ┗┻┛
 *   ━━━━━━感觉萌萌哒━━━━━━
 *
 */

package com.betel.estatemgmt.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * 时间工具类
 * </p>
 * ClassName: GetDateUtil <br/>
 * Author: Du.Hx  <br/>
 * Date: 2017/5/17 13:23 <br/>
 * Version: 2.0 <br/>
 */
public class DateUtil {

    /**
     * 缺省日期格式
     */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 缺省时间格式
     */
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

    /**
     * 缺省长日期格式,精确到秒
     */
    public static final String DEFAULT_DATETIME_FORMAT_SEC = "yyyy-MM-dd HH:mm:ss";

    /**
     * <p>
     * 获得当前日期(年月日)，如2016-3-31
     * </p>
     * Author: Du.hx <br/>
     * Date: 2017/5/17 13:23
     *
     * @return 当前日期字符串
     */
    public static String getDate() {
        return today(DEFAULT_DATE_FORMAT);
    }

    /**
     * <p>
     * 获取当前时间(时分秒)，如 14:44:48
     * </p>
     * Author: Du.hx <br/>
     * Date: 2017/5/17 13:24
     *
     * @return 当前时间字符串
     */
    public static String getTime() {
        return today(DEFAULT_TIME_FORMAT);
    }

    /**
     * <p>
     * 获取当前日期时间(年月日时分秒)，如 2017-5-17 13:25:34
     * </p>
     * Author: Du.hx <br/>
     * Date: 2017/5/17 13:25
     *
     * @return 当前日期时间字符串
     */
    public static String getDateTime() {
        return today(DEFAULT_DATETIME_FORMAT_SEC);
    }

    /**
     * <p>
     * 根据输入的格式得到当前时间的字符串
     * </p>
     * Author: Du.hx <br/>
     * Date: 2017/5/17 13:26
     *
     * @param strFormat 时间格式
     * @return 时间字符串
     */
    public static String today(String strFormat) {/*-?|Code-Review|DHX|c0|?*/
        return toString(new Date(), strFormat);
    }

    /**
     * <p>
     * 根据格式将时间转为字符串
     * </p>
     * Author: Du.hx <br/>
     * Date: 2017/5/17 13:28
     *
     * @param date   时间
     * @param format 时间格式
     * @return 时间字符串
     */
    public static String toString(Date date, String format) {
        return getSimpleDateFormat(format).format(date);
    }

    /**
     * <p>
     * 根据格式将字符串转换成Date
     * </p>
     * Author: Du.hx <br/>
     * Date: 2017/5/17 13:29
     *
     * @param dateStr 时间字符串
     * @param format  时间格式
     * @return 时间
     */
    public static Date toDate(String dateStr, String format) throws ParseException {
        return getSimpleDateFormat(format).parse(dateStr);
    }

    /**
     * <p>
     * 将格式字符串转为格式对象
     * </p>
     * Author: Du.hx <br/>
     * Date: 2017/5/17 13:30
     *
     * @param strFormat 时间格式
     * @return 格式对象
     */
    private static SimpleDateFormat getSimpleDateFormat(String strFormat) {
        if (strFormat != null && !"".equals(strFormat.trim())) {
            return new SimpleDateFormat(strFormat);
        } else {
            return new SimpleDateFormat();
        }
    }

}
