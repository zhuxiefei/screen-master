package com.betel.estatemgmt.business.web.task.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * <p>
 * 工作日志工具类
 * </p>
 * ClassName: WorkUtil <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/12/5 13:37 <br/>
 * Version: 1.0 <br/>
 */
public class WorkUtil {

    /**
     * <p>
     * 校验日期格式
     * </p>
     * Author: xiayanxin <br/>
     * Date: 2017/8/1 16:15
     *
     * @param time 入参
     * @return flag
     */
    public static boolean validateTime(String time){
        boolean flag = true;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            format.parse(time);
        } catch (ParseException e) {
            flag = false;
        }
        return flag;
    }


    /**
     * <p>
     * 校验时间差
     * </p>
     * Author: xiayanxin <br/>
     * Date: 2017/8/1 16:29
     *
     * @param startTime,endTime 入参
     * @return flag
     */
    public static boolean validateTimeDistance(String startTime,String endTime){
        boolean flag = true;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            long time1 = format.parse(startTime).getTime();
            long time2 = format.parse(endTime).getTime();
            if (time1 > time2){
                flag = false;
            }
        } catch (ParseException e) {}
        return flag;
    }
}
