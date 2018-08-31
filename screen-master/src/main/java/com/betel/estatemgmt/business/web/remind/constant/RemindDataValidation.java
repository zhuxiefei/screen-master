package com.betel.estatemgmt.business.web.remind.constant;

/**
 * <p>
 * 设置提醒天数常量
 * </p>
 * ClassName: RemindDataValidation <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/9/13 17:36 <br/>
 * Version: 1.0 <br/>
 */
public interface RemindDataValidation {
    /**
     * 支付提醒天数
     */
    String PAYMENT_REMIND_DAYS = "paymentRemindDays";

    /**
     * 超期提醒天数
     */
    String OVERDUE_REMIND_DAYS = "overdueRemindDays";

    /**
     * 维修收费标准
     */
    String REPAIR_CHARGE_STANDARD = "repairChargeStandard";

    /**
     * 系统通知sndType
     */
    String SENDTYPE = "smart02";

    /**
     * 缴费提醒snedNo
     */
    String PAYMENT_REMIND_SENDNO = "22";

    /**
     * 催缴提醒snedNo
     */
    String OVERDUE_REMIND_SENDNO = "23";

    /**
     * 缴费通知snedNo
     */
    String PAYMENT_NOTICE_SENDNO = "24";

    /**
     * 物业费标识
     */
    Integer PROPERTY_FEE = 1;

    /**
     * 公摊水电费标识
     */
    Integer PUBLIC_FEE = 2;

    /**
     * 生成单号自增数
     */
    Integer ADDNO_SEQUENCE = 0;

    /**
     * 生成单号自增长度
     */
    Integer ADDNO_LENGTH = 6;

    /**
     * 未缴费状态
     */
    Integer BILL_NOTPAY_STATUS = 1;

    /**
     * 缴费状态
     */
    Integer BILL_PAY_STATUS = 2;
}
