package com.betel.estatemgmt.business.web.feedback.code;

/**
 * <p>
 * 意见反馈管理返回码
 * </p>
 * ClassName: FeedbackCode <br/>
 * Author: xiayanxin <br/>
 * Date: 2017/7/31 13:39 <br/>
 * Version: 1.0 <br/>
 */
public interface FeedbackCode {
    /**
     * 反馈人昵称格式错误
     */
    String NAME_RULE_WRONG = "F0001";

    /**
     * App版本格式错误
     */
    String APPVERSION_RULE_WRONG = "F0002";

    /**
     * 时间格式错误
     */
    String TIME_RULE_WRONG = "F0003";

    /**
     * 结束时间不能早于起始时间
     */
    String ENDTIME_EARLY_STARTTIME = "F0004";

    /**
     * 反馈编号不能为空
     */
    String FEEDBACK_ID_NULL = "F0005";

    /**
     * 反馈已被删除
     */
    String FEEDBACK_IS_DELETE = "F0006";
}
