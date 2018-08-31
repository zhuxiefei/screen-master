package com.betel.estatemgmt.business.web.repair.code;

/**
 * <p>
 * 维修管理返回码
 * </p>
 * ClassName: RepairCode <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/9/13 16:13 <br/>
 * Version: 1.0 <br/>
 */
public interface RepairCode {
    /**
     * 房号长度超过6个字符
     */
    String HOUSENUM_SIZE_ERROR = "R0200";

    /**
     * 房号格式错误
     */
    String HOUSENUM_RULE_ERROR = "R0201";

    /**
     * 客户名称超过10个字符
     */
    String USERNAME_SIZE_ERROR = "R0202";

    /**
     * 客户名称格式错误
     */
    String USERNAME_RULE_ERROR = "R0203";

    /**
     * 时间格式错误
     */
    String TIMR_RULE_ERROR = "R0204";

    /**
     * 结束时间不能早于起始时间
     */
    String STARTTIME_LATE_ENDTIME = "R0205";

    /**
     * 报修单号长度超过22个字
     */
    String REPAIRID_SIZE_ERROR = "R0206";

    /**
     * 报修单号格式错误
     */
    String REPAIRID_RULE_ERROR = "R0207";

    /**
     * 楼宇id为空
     */
    String BUILDING_ID_NULL = "R0208";

    /**
     * 维修单号为空
     */
    String REPAIRID_NULL_ERROR = "R0209";

    /**
     * 维修单不存在
     */
    String REPAIR_NOT_EXIST = "R0210";

    /**
     * 房屋不存在
     */
    String HOUSE_NOT_EXIST = "R0211";

    /**
     * 报修人姓名为空
     */
    String ORDERCONTACT_NULL_ERROR = "R0212";

    /**
     * 报修人姓名格式错误
     */
    String ORDERCONTACT_RULE_ERROR = "R0213";

    /**
     * 报修人联系电话为空
     */
    String CONTACTPHONE_NULL_ERROR = "R0214";

    /**
     * 报修人联系电话格式错误
     */
    String CONTACTPHONE_RULE_ERROR = "R0215";

    /**
     * 维修内容长度超过100个字符
     */
    String REPAIRDESC_SIZE_ERROR = "R0216";

    /**
     * 维修人员为空
     */
    String OPERATOR_NULL_ERROR = "R0217";

    /**
     * 维修人员联系电话为空
     */
    String OPERATORPHONE_NULL_ERROR = "R0219";

    /**
     * 维修人员联系电话格式错误
     */
    String OPERATORPHONE_RULE_ERROR = "R0220";

    /**
     * 预约上门维修时间格式错误
     */
    String APPOINTTIME_RULE_ERROR = "R0221";

    /**
     * 维修收费标准过长
     */
    String REPAIRSTANDARD_SIZE_ERROR = "R0222";

    /**
     * 文件为空
     */
    String FILE_NULL_ERROR = "R0223";

    /**
     * 文件原始文件名长度过长
     */
    String FILENAME_SIZE_ERROR = "R0224";

    /**
     * 文件大小超过20M
     */
    String FILE_SIZE_ERROR = "R0225";

    /**
     * 文件格式错误
     */
    String FILE_RULE_ERROR = "R0226";

    /**
     * 维修人ID为空
     */
    String OPERATORID_NULL_ERROR = "R0227";

    /**
     * 维修人已被删除
     */
    String OPERATOR_IS_DELETE = "R0228";

    /**
     * 该维修单已被接单或已完成
     */
    String REPAIR_IS_ORDERORDONE = "R0229";

    /**
     * 该维修单已完成
     */
    String REPAIR_IS_DONE = "R0230";

    /**
     * 报修区域为空
     */
    String REPAIRAREA_NULL_ERROR = "R0231";

    /**
     * 报修类别为空
     */
    String REPAIRTYPE_NULL_ERROR = "R0232";

    /**
     * 房屋不能为空
     */
    String HOUSEID_NULL_ERROR = "R0233";

    /**
     * 楼宇被删除
     */
    String BUILDING_IS_DELETE = "R0234";

    /**
     * 单元被删除
     */
    String UNIT_IS_DELETE = "R0235";

    /**
     * 单元被删除
     */
    String ASSIGN_DESC_FORMAT_WRONG = "R0236";

    /**
     * 维修单被取消
     */
    String REPAIR_IS_CANCEL = "R0237";

    /**
     * 员工编号格式错误
     */
    String EMP_NO_RULE  = "R0038";

    /**
     * 员工姓名格式错误
     */
    String EMP_NAME_RULE  = "R0039";

    /**
     * 员工手机号格式错误
     */
    String EMP_PHONE_RULE  = "R0040";

    /**
     * 选中的部门已被删除
     */
    String DEPT_IS_NOT_EXIST="R0041";
}
