package com.betel.estatemgmt.business.propertyapp.check.code;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: CheckCode <br/>
 * Author: Cui.xx  <br/>
 * Date: 2017/12/5 17:31 <br/>
 * Version: 1.0 <br/>
 */
public interface CheckCode {

    /**
     * 未上传图片
     */
    String IMAGE_NULL = "C0001";

    /**
     * 图片数量超过限制（9个）
     */
    String IMAGE_OUT_OF_NUM = "C0002";

    /**
     * 设备不存在
     */
    String DEVICE_NOT_EXIT = "C0003";

    /**
     * 问题记录不能超过500个字
     */
    String DESC_OUT_SIZE = "C0004";
    /**
     * 时间为空
     */
    String TIME_NULL_ERROR = "C0005";
    /**
     * 时间格式错误yyyy-MM-dd hh:mm:ss
     */
    String TIME_RULE_ERROR = "C0006";
    /**
     * 网络缓慢多次提交标识为空的错误
     */
    String SERIAL_NO_IS_NULL_ERROR = "C0007";
    /**
     * 巡检记录id为空
     */
    String RECORD_ID_IS_NULL = "C0012";
    /**
     * 巡检设备id为空
     */
    String EQUIPMENT_ID_IS_NULL = "X0001";
    /**
     * 关键字规则错误
     */
    String KEYWORD_RULE_ERROR = "X0009";
    /**
     * 巡检记录已不存在
     */
    String RECORD_IS_NOT_EXIST = "X0013";

    /**
     * 头像名称过长（120以内）
     */
    String IMAGE_NAME_LENGTH_OUT = "U0010";

    /**
     * 头像格式错误（bmp/jpg/jpeg/png）
     */
    String IMAGE_FORMAT_WRONG="U0011";

    /**
     * 头像大小超过20M
     */
    String IMAGE_OUT_SIZE="U0012";
    /**
     * 巡检人员为空
     */
    String EMPLOYEE_NAME_IS_NULL="X0014";
}
