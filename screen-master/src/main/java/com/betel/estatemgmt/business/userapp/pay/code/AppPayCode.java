package com.betel.estatemgmt.business.userapp.pay.code;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: AppPayCode <br/>
 * Author: zhouye  <br/>
 * Date: 2017/9/18 14:11 <br/>
 * Version: 1.0 <br/>
 */
public interface AppPayCode {
    /**
     * 是否缴费为空
     */
    String EXPENSETYPE_NULL = "P0009";

    /**
     * 房屋不存在
     */
    String HOUSE_DELETE = "H1042";

    /**
     * 非房屋成员
     */
    String NOT_HOUSE_MEMBER = "H1045";

    /**
     * 账单编号为空
     */
    String BILL_NO_NULL = "P0008";

    /**
     * 账单不存在
     */
    String BILL_DELETE = "P0006";

    /**
     * 账单未缴费
     */
    String BILL_NOT_PAY = "P0007";

    /**
     * 账单已缴费
     */
    String BILL_IS_PAY = "P0010";

    /**
     * 账单待缴费
     */
    String BILL_WAIT_PAY = "P0011";

    /**
     * 已索要票据
     */
    String BILL_COMMAND = "P0012";
    /**
     * 收据还未送出，请先索要
     */
    String BILL_NOT_SEND="P0013";

    /**
     * 账单过了预交费时间，不能删除
     */
    String BILL_OVER_PREPAY = "P0014";
}
