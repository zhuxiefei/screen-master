package com.betel.estatemgmt.business.userapp.pay.constant;

/**
 * <p>
 * 缴费模块的常量
 * </p>
 * ClassName: PayConstant <br/>
 * Author: zhouye  <br/>
 * Date: 2017/9/19 9:14 <br/>
 * Version: 1.0 <br/>
 */
public interface PayConstant {
    /**
     * 物业费的收费标准
     */
    String CHARGESTANDARD = "propertyChargeStandard";

    /**
     * 支付方式 :支付宝
     */
    Integer ALIPAY = 3;

    /**
     * 账单已交费
     */
    Integer ALIPAY_STATUS = 2;
    /**
     * 支付方式 :weixin
     */
    Integer WEIXINPAY = 4;

    /**
     * 流水状态 :待支付
     */
    Integer PAY_STATUS = 2;

    /**
     * 公摊水电费 typeItem
     */
    int TYPEITEMWATER = 2;
    /**
     * 物业费typeItem
     */
    int TYPEITEMPUBLIC = 1;

    /**
     * 账单删除
     */
    int BILL_DELETE = 2;

    /**
     * 账单支付
     */
    int FLOW_SUSSES = 3;

    /**
     * 索要票据 数字2
     */
    int TWO = 2;

    /**
     * 已提供 3
     */
    int THREE = 3;

    /**
     *TRADE_SUCCESS
     */
    String TRADE_SUCCESS = "TRADE_SUCCESS";

    /**
     *UTF-8
     */
    String UTF = "UTF-8";

    /**
     * SUCCESS
     */
    String SUCCESS = "SUCCESS";

    /**
     * SUCCESS
     */
    String RESULT_CODE = "result_code";

    String SEND_TYPE = "smart02";

    Integer SEND_NO = 25;

    /**
     * 线上缴费标识
     */
    Integer ON_LINE = 0;
}
