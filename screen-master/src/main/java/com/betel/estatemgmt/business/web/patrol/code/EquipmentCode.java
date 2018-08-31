package com.betel.estatemgmt.business.web.patrol.code;

/**
 * Created by zhangjian on 2018/1/24.
 */
public interface EquipmentCode {
    /**
     * 设备类型为空
     */
    String TYPE_NAME_NULL_ERROR="X1001";
    /**
     * 巡设备类型不含特殊字符，15个字
     */
    String TYPE_NAME_ROLE_ERROR = "X1002";
    /**
     * 维修内容长度超长
     */
    String REPAIRDESC_LENGTH_ERROR="X1003";
    /**
     * 维修费用格式错误
     */
    String REPAIREXPENSE_RULE_ERROR="X1004";
    /**
     * 维修时间格式错误
     */
    String REPAIRTIME_RULE_ERROR="X1005";
    /**
     * 类型名称已存在
     */
    String TYPE_NAME_IS_EXIST_ERROR="X1006";
    /**
     * 设备类型不存在(设备类型已被删除的提示)
     */
    String EQUIPMENT_TYPE_IS_NOT_EXIST="X1007";
}
