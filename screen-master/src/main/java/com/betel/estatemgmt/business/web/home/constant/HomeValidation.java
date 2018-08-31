package com.betel.estatemgmt.business.web.home.constant;

/**
 * Created by zhangjian on 2017/8/7.
 */
public interface HomeValidation {
    /**
     * yyyy-MM 格式校验
     */
    String START_END_TIME_RULE="(19|20)[0-9][0-9]-(0[1-9]|1[0-2])";
    /**
     * 时间类型
     */
    String TIME_TYPE="yyyy-MM";
    /**
     * 月差
     */
    int MONTH_DIFF=5;
    /**
     * 常量：收据状态：1 未提供  2 已索要
     */
    int QUANTITY_TWO=2;

}
