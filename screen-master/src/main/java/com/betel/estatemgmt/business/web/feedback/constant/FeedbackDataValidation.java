package com.betel.estatemgmt.business.web.feedback.constant;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: FeedbackDataValidation <br/>
 * Author: xiayanxin <br/>
 * Date: 2017/7/31 13:49 <br/>
 * Version: 1.0 <br/>
 */
public interface FeedbackDataValidation {
    /**
     * 昵称长度和格式校验
     */
    String FEEDBACK_NAME_RULE = "[^\\\\<>%'\"]{1,15}";

    /**
     * app版本长度和格式校验
     */
    String APP_VERSION_RULE = "[^\\\\<>%'\"]{1,30}";
}
