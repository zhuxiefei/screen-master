package com.betel.estatemgmt.business.propertyapp.assign.constant;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: AssignConstant <br/>
 * Author: Cui.xx  <br/>
 * Date: 2017/12/22 14:03 <br/>
 * Version: 1.0 <br/>
 */
public interface AssignConstant {

    /**
     * 未指派
     */
    String UNASSIGN = "1";

    /**
     * 已指派
     */
    String ASSIGNED = "2";

    /**
     * 维修单已取消
     */
    int REPAIR_CANCEL = 4;

    /**
     * 维修单已完成
     */
    int REPAIR_FINISH = 3;

    /**
     * 维修单已接单
     */
    int REPAIR_RECEVIED = 2;

    /**
     * APP管理员
     */
    int OPERATOR_TYPE_APP = 2;

    /**
     * WEB管理员
     */
    int OPERATOR_TYPE_WEB = 1;

    /**
     * 指派维修单
     */
    int ORDER_ASSIGN = 3;

    /**
     * 新增维修单
     */
    int ORDER_ADD = 1;

    /**
     * 取消维修单
     */
    int ORDER_CANCEL = 2;

    /**
     * 删除维修单
     */
    int ORDER_FINISH = 4;

    /**
     * 维修单被物管app删除
     */
    int REPAIR_DELETE_ADMIN = 2;
}
