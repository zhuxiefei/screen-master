package com.betel.estatemgmt.business.userapp.decoration.code;

/**
 * <p>
 * 装修模块返回码
 * </p>
 * ClassName: DecorationCode <br/>
 * Author: Cui.xx  <br/>
 * Date: 2018/1/23 18:17 <br/>
 * Version: 1.0 <br/>
 */
public interface DecorationCode {

    /**
     *  装修备注不能超过500个字
     */
    String DECORATION_DESC_FORMAT = "DE001";

    /**
     *  房屋id不能为空
     */
    String HOUSEID_NULL = "DE002";

    /**
     *  装修公司不能为空
     */
    String DECORATION_COMPANY_NULL = "DE003";

    /**
     *  装修开始时间不能为空
     */
    String START_TIME_NULL = "DE004";

    /**
     *  装修周期不能为空
     */
    String DECORATION_CYCLE_NULL = "DE005";

    /**
     *  装修申请记录id不能为空
     */
    String RECORDID_NULL = "DE006";

    /**
     *  装修状态不能为空
     */
    String STATUS_NULL = "DE007";

    /**
     *  拒绝理由不能为大于500个字
     */
    String REASON_FORMAT = "DE008";

    /**
     *  物业备注不能为大于500个字
     */
    String DESCRIPTION_FORMAT = "DE009";

    /**
     *  取消理由不能为大于500个字
     */
    String CANCELREASON_FORMAT = "DE010";

    /**
     *  不是拒絕狀態，不可提交
     */
    String NOT_REFUSE_STATUS = "DE011";

    /**
     *  装修申请单已被用户删除
     */
    String DECORATION_IS_DELETED = "DE012";

    /**
     *  房屋已被删除
     */
    String HOUSE_IS_DELETED = "DE013";

    /**
     *  申请状态已变更
     */
    String STATUS_IS_CHANGED = "DE014";

    /**
     * 楼宇被删除
     */
    String BUILDING_IS_DELETE = "DE015";

    /**
     * 单元被删除
     */
    String UNIT_IS_DELETE = "DE016";
}
