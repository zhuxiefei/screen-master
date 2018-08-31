package com.betel.estatemgmt.business.web.housetype.constant;

/**
 * <p>
 * 户型维护常量
 * </p>
 * ClassName: HouseTypeConstant <br/>
 * Author: geyf  <br/>
 * Date: 2017/6/20 11:31 <br/>
 * Version: 1.0 <br/>
 */
public interface HouseTypeConstant {

    /**
     * 格式：不含\<>%'"
     */
    String HOUSETYPE_FORMAT = "[^\\\\<>%'\"]{1,20}";

    /**
     * 字符长度限制
     */
    int HOUSETYPE_LENGTH = 20;

    /**
     * 关键词长度限制
     */
    int KEYWORD_LENGTH = 20;

    /**
     * 全户型
     */
    String HOUSETYPE_ALL = "全户型";

    /**
     * 所有
     */
    String ALL = "所有";

    /**
     * 功能区域名称长度限制
     */
    int FUNCTIONNAME_LENGTH = 10;
}
