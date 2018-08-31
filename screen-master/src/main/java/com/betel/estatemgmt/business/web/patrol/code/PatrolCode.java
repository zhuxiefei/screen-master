package com.betel.estatemgmt.business.web.patrol.code;

/**
 * <p>
 * Describe this class...
 * </p>
 *
 * @ClassName: PatrolCode <br/>
 * @author: jian.z  <br/>
 * @date: 2017/12/4 16:51 <br/>
 * @Version: 1.0
 */
public interface PatrolCode {
    /**
     * 巡检设备id为空
     */
    String EQUIPMENT_ID_IS_NULL = "X0001";
    /**
     * 巡检设备编号为空
     */
    String EQUIPMENT_NO_IS_NULL = "X0002";
    /**
     * 巡检设备编号格式不合法
     */
    String EQUIPMENT_NO_IS_NOT_LEGAL = "X0003";
    /**
     * 巡检设备编号已存在
     */
    String EQUIPMENT_NO_IS_EXIST = "X0004";
    /**
     * 巡检设备名称为空
     */
    String EQUIPMENT_NAME_IS_NULL = "X0005";
    /**
     * 巡检设备名称不含特殊字符，50个字
     */
    String EQUIPMENT_NAME_IS_NOT_LEGAL = "X0006";
    /**
     * 巡检设备位置为空
     */
    String EQUIPMENT_LOCATION_IS_NULL = "X0007";
    /**
     * 巡检设备位置不合法
     */
    String EQUIPMENT_LOCATION_IS_NOT_LEGAL = "X0008";
    /**
     * 巡检设备内容为空
     */
    String EQUIPMENT_DEC_IS_NULL = "X0009";
    /**
     * 巡检设备内容为空
     */
    String EQUIPMENT_DEC_LENGTH_IS_SUPPER_LONG = "X0010";
    /**
     * 巡检设备被删除
     */
    String EQUIPMENT_IS_NOT_EXIST = "X0011";
    /**
     * 巡检记录id为空
     */
    String RECORD_ID_IS_NULL = "X0012";
    /**
     * 巡检记录已不存在
     */
    String RECORD_IS_NOT_EXIST = "X0013";
    /**
     * 巡检人员姓名不合法
     */
    String EMPLOYEE_NAME_IS_NOT_LEGAL="X0014";
    /**
     * 巡检开始时间不合法
     */
    String START_TIME_IS_NOT_LEGAL="X0015";
    /**
     * 巡检开始时间不合法
     */
    String END_TIME_IS_NOT_LEGAL="X0016";
    /**
     * 巡检开始时间不能大于结束时间
     */
    String START_TIME_IS_MORE_THAN_END_TIME="X0017";
    /**
     * 保修截止日期不合法
     */
    String DEADLINE_ERROR="X0018";
    /**
     * 设备生产厂商
     */
    String EQUIPMENTPRODUCER_ERROR="X0019";
    /**
     * 生产厂商电话
     */
    String PRODUCERPHONE_ERROR="X0020";
    /**
     * 设备维修厂商
     */
    String EQUIPMENTOPERATOR_ERROR="X0021";
    /**
     * 维修厂商电话
     */
    String OPERATORPHONE_ERROR="X0022";
    /**
     *  您生产二维码的内容太长，无法生产二维码
     */
    String CREATE_QR_TOO_DATE="X0023";
    /**
     * 设备生产日期格式不合法
     */
    String EQUIPMENTCREATETIME_ERROR="X0024";
    /**
     * 保质期格式错误
     */
    String QUALITYPERIOD_RULE_ERROR="X0025";
    /**
     * 类型已绑定设备
     */
    String TYPE_HAS_WITH_EQUIPMENT="X0026";
    /**
     * 质保期数量不能大于1000
     */
    String QUALITYPERIOD_MORE_THAN_TEN_HUNDRED="X0027";
}

