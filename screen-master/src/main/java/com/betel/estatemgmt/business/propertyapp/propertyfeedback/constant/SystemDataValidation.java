package com.betel.estatemgmt.business.propertyapp.propertyfeedback.constant;

/**
 * <p>
 * 系统数据校验
 * </p>
 * ClassName: SystemDataValidation <br/>
 * Author: xiayanxin <br/>
 * Date: 2017/5/17 15:05 <br/>
 * Version: 1.0 <br/>
 */
public interface SystemDataValidation {

    /**
     * 文字意见最大长度
     */
    Integer FEEDBACK_CONTENT_SIZE = 500;

    /**
     * 反馈信息状态之未读
     */
    Integer FEEDBACK_STATUS_UNREAD = 1;

    /**
     * 反馈信息状态之已读
     */
    Integer FEEDBACK_STATUS_READ = 2;

    /**
     * 反馈信息状态之已删除
     */
    Integer FEEDBACK_STATUS_DELETE = 3;

    /**
     * 数字2
     */
    int TWO = 2;

    Integer USER_APP_TYPE = 1;

    Integer PROPERTY_APP_TYPE = 2;
}
