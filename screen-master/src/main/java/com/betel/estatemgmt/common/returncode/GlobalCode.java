package com.betel.estatemgmt.common.returncode;

/**
 * Created by zhangjian on 2018/1/8.
 */
public interface GlobalCode {
    /**
     * 统一ID未空错误
     */
    String Global_ID_NULL_ERROR="T1010";
    /**
     * 统一标题格式错误
     */
    String TITLE_RULE_ERROR="T2020";
    /**
     * 统一昵称格式错误
     */
    String NICK_NAME_RULE_ERROR="T3030";
    /**
     *统一开始时间格式错误
     */
    String START_TIME_RULE_ERROR="T4040";
    /**
     * 统一结束时间格式错误
     */
    String END_TIME_RULE_ERROR="T5050";
    /**
     * 统一开始时间不能大于结束时间错误
     */
    String START_TIME_THAN_END_TIME_ERROR="T6060";
    /**
     * 统一系统未定义状态码错误
     */
    String STATUS_CODE_IS_ERROR="T7070";
    /**
     * 参数为空错误
     */
    String PARAMETER_IS_NULL_ERROR="T8080";

    /**
     *  小区id为空
     */
    String ESTATE_ID_IS_NULL="T9090";
}
