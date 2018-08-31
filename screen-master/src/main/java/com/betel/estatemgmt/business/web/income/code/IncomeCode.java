package com.betel.estatemgmt.business.web.income.code;

/**
 * <p>
 * 收入明细code码
 * </p>
 * ClassName: IncomeCode <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/9/13 11:50 <br/>
 * Version: 1.0 <br/>
 */
public interface IncomeCode {
    /**
     * 房号长度超过6个字符
     */
    String HOUSENUM_SIZE_ERROR = "I0001";

    /**
     * 房号格式错误
     */
    String HOUSENUM_RULE_ERROR = "I0002";

    /**
     * 客户名称超过10个字符
     */
    String USERNAME_SIZE_ERROR = "I0003";

    /**
     * 客户名称格式错误
     */
    String USERNAME_RULE_ERROR = "I0004";

    /**
     * 时间格式错误
     */
    String TIMR_RULE_ERROR = "I0005";

    /**
     * 结束时间不能早于起始时间
     */
    String STARTTIME_LATE_ENDTIME = "I0006";

    /**
     * 楼宇id为空
     */
    String BUILDING_ID_NULL = "I0007";

    /**
     * 楼宇被删除
     */
    String BUILDING_IS_DELETE = "I0008";

    /**
     * 单元被删除
     */
    String UNIT_IS_DELETE = "I0009";
}
