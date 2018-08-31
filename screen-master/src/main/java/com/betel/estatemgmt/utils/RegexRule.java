package com.betel.estatemgmt.utils;

/**
 * Created by zhangjian on 2017/9/20.
 */
public interface RegexRule {
    /**
     * yyyy-MM 格式校验
     */
    String START_END_TIME_RULE="(19|20)[0-9][0-9]-(0[1-9]|1[0-2])";
    /**
     * yyyy-MM 格式校验
     */
    String START_END_TIME_RULE_Y_M = "(19|20)[0-9][0-9]-(0[1-9]|1[0-2])";
    /**
     * yyyy-MM-dd 时间格式校验
     */
    String START_END_TIME_RULE_Y_M_D = "((\\d{2}(([02468][048])|([13579][26]))[\\-\\s]?((((0?[13578])|(1[02]))[\\-\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\s]?((((0?[13578])|(1[02]))[\\-\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
    /**
     * yyyy-MM-dd hh:mm:ss
     */
    String START_END_TIME_RULE_Y_M_D_H_M_S = "^\\d{4}[-]([0][1-9]|(1[0-2]))[-]([1-9]|([012]\\d)|(3[01]))([ \\t\\n\\x0B\\f\\r])(([0-1]{1}[0-9]{1})|([2]{1}[0-4]{1}))([:])(([0-5]{1}[0-9]{1}|[6]{1}[0]{1}))([:])((([0-5]{1}[0-9]{1}|[6]{1}[0]{1})))$";
    /**
     * 名称格式校验
     */
    String NAME_RULE = "[^\\\\<>%'\"]{0,10}";
    /**
     * 不含有特殊字符格式校验
     */
    String SPECIAL_WORD_RULE = "[^\\\\<>%'\"]{1,50}";
    /**
     * 不含有特殊字符格式校验
     */
    String SPECIAL_WORD_RULE_TWENTY = "[^\\\\<>%'\"]{1,19}";
    /**
     * 标题校验
     */
    String TITLE_RULE = "[^\\\\<>%'\"]{1,60}";
    /**
     * 用户昵称格式
     */
    String USERNAME_RULE = "[^\\\\<>%'\"]{1,15}$";
    /**
     * 时间的格式
     */
    String TIME_FORMAT_Y_M_D = "yyyy-MM-dd";
    /**
     * 时间的格式yyyy-MM-dd HH:MM:SS 12时制
     */
    String TIME_FARMAT_Y_M_D_H_M_S = "yyyy-MM-dd hh:mm:ss";
    /**
     * 24时制
     */
    String TIME_FARMAT_YYYY_MM_DD_HH_MM_SS_24 = "yyyy-MM-dd HH:mm:ss";

    /**
     * 1-50汉字格式校验
     */
    String CHINA_WORD = "[^\\\\<>%'\"]{1,50}";
    /**
     * 1-5汉字的格式检验
     */
    String CHINESE_RULE = "^[\\u4e00-\\u9fa5 ]{1,5}$";

    int FIVE_THOUSAND = 500;

}
