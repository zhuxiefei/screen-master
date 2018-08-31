package com.betel.estatemgmt.business.web.material.controller.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * 日期工具类
 * </p>
 * Author: zhangjian  <br/>
 * Date: 2017/6/28 13:53 <br/>
 * Version: 1.0 <br/>
 */
public class DateUtil {
    private static final Logger LOG = LoggerFactory.getLogger(DateUtil.class);

    /**
     * <p>
     * 判断两个日期大小
     * </p>
     *
     * @param productDate
     * @param warrantyDate
     * @return true  生产日期大于截止日期  如：2017-12-11>2017-12-01
     * Author: zhangjian  <br/>
     * Date: 2017/6/28 13:53 <br/>
     * Version: 1.0 <br/>
     */
    public static boolean compareDate(String productDate, String warrantyDate) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date pDate = df.parse(productDate);
            Date wDate = df.parse(warrantyDate);
            if (pDate.getTime() > wDate.getTime()) {
                return true;
            } else if (pDate.getTime() < wDate.getTime()) {
                return false;
            }
        } catch (Exception e) {
            LOG.error("=========web/material/v1/时间 格式错误 error!=========", e);
            return false;
        }
        return false;
    }


//   public static void main(String[] args) {
//       System.out.println(compare_date("2017-12-10", "2017-12-20"));
//   }
}
