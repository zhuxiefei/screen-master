package com.betel.estatemgmt.business.web.remind.code;

/**
 * <p>
 * 设置提醒天数返回码
 * </p>
 * ClassName: RemindCode <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/9/13 17:32 <br/>
 * Version: 1.0 <br/>
 */
public interface RemindCode {
    /**
     * 支付提醒天数为空
     */
    String PAYREMIND_NULL_ERROR = "R0249";

    /**
     * 超期提醒天数为空
     */
    String OVERREMIND_NULL_ERROR = "R0248";

    /**
     * 配置名为空
     */
    String CONFIG_NULL_ERROR = "R0250";
}
