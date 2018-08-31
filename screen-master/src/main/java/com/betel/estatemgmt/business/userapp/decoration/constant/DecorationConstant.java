package com.betel.estatemgmt.business.userapp.decoration.constant;

/**
 * <p>
 * 装修模块常量
 * </p>
 * ClassName: DecorationConstant <br/>
 * Author: Cui.xx  <br/>
 * Date: 2018/1/23 18:38 <br/>
 * Version: 1.0 <br/>
 */
public interface DecorationConstant {

    /**
     * 装修申请已取消
     */
    String DECORATION_STATUS_CANCEL = "4";

    /**
     * 装修申请处理中
     */
    String DECORATION_STATUS_SOLVING = "1";

    /**
     * 装修申请已同意
     */
    String DECORATION_STATUS_AGREE = "2";

    /**
     * 装修申请已拒绝
     */
    String DECORATION_STATUS_REFUSE = "3";

    /**
     * 装修许可证已打印
     */
    int PRINTED_STATUS = 2;

    /**
     * 装修许可证未打印
     */
    int UNPRINT_STATUS = 1;
}
