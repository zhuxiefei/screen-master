package com.betel.estatemgmt.business.web.expenses.constant;

/**
 * Created by zhangjian on 2017/9/17.
 */
public interface ExpensesValidation {
    /**
     * 房号校验规则
     */
    String HOUSE_NUM_RULE = "[^\\\\<>%'\"]{0,6}";
    /**
     * 户主姓名校验规则
     */
    String HOUSE_NAME_RULE = "[^\\\\<>%'\"]{0,10}";
    /**
     * yyyy-MM-dd 时间格式校验
     */
    String START_END_TIME_RULE = "((\\d{2}(([02468][048])|([13579][26]))[\\-\\s]?((((0?[13578])|(1[02]))[\\-\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\s]?((((0?[13578])|(1[02]))[\\-\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";

    /**
     * 预缴计费周期最大月数
     */
    Integer MAX_MONTHS = 24;

    /**
     * 未索要
     */
    Integer BILL_IS_NOT_SUO_YAO = 1;
    /**
     * app端已索要
     */
    Integer BILL_IS_YI_SUO_YAO = 2;
    /**
     * 收据已提供
     */
    Integer BILL_IS_DEMAND = 3;

    /**
     * 线下缴费标识
     */
    Integer NOT_ON_LINE = 1;
}
