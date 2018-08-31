package com.betel.estatemgmt.business.web.home.code;

/**
 * Created by zhangjian on 2017/8/7.
 */
public interface HomeCode {
    /**
     * 开始时间格式不合法：
     * 开始时间格式必须满足yyyy-MM
     */
    String START_TIME_RULE_IS_NOT_LEGAL = "H20001";
    /**
     * 结束时间格式不合法:
     * 发帖结束时间格式必须满足yyyy-MM
     */
    String END_TIME_RULE_IS_NOT_LEGAL = "H20002";
    /**
     * 开始时间不能大于结束时间
     */
    String START_TIME_MORE_THAN_END_TIME = "H20003";
    /**
     * 开始时间不能和结束时间的差距大于6个月
     */
    String  START_TIME_AND__END_TIME_DIDDERENCE="H20004";

}
