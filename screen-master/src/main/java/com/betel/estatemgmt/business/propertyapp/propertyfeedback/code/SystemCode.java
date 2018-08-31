package com.betel.estatemgmt.business.propertyapp.propertyfeedback.code;

/**
 * <p>
 * 系统返回码
 * </p>
 * ClassName: SystemCode <br/>
 * Author: xiayanxin <br/>
 * Date: 2017/5/17 14:59 <br/>
 * Version: 1.0 <br/>
 */
public interface SystemCode {

    /**
     * 文字意见为空
     */
    String FEEDBACK_CONTENT_NULL = "S0003";

    /**
     * 文字意见长度限制
     */
    String FEEDBACK_CONTENT_SIZE = "S0004";

    /**
     * 反馈类型不能为空
     */
    String FEEDBACK_TYPE_ISNULL = "S0110";

    /**
     * 反馈类型不正确
     */
    String FEEDBACK_TYPE_ERROR = "S0111";

    /**
     * 手机型号为空
     */
    String FEEDBACK_PHONE_ISNULL = "S0112";

    /**
     * 手机系统版本号为空
     */
    String FEEDBACK_PHONESYSTEM_ISNULL = "S0113";

    /**
     * APP版本为空
     */
    String FEEDBACK_APP_ISNULL = "S0114";
}
