package com.betel.estatemgmt.business.web.building.code;

/**
 * <p>
 * 楼宇和单元返回码
 * </p>
 * ClassName: BuildingCode <br/>
 * Author: zhangli <br/>
 * Date: 2017/6/20 10.18 <br/>
 * Version: 1.0 <br/>
 */
public interface BuildingCode {
    /**
     * 楼宇id为空
     */
    String BUILDING_ID_NULL = "B0001";

    /**
     * 楼宇名称为空
     */
    String BUILDING_NAME_NULL = "B0002";

    /**
     * 楼宇名称格式错误
     */
    String BUILDING_FORMAT_ERROR = "B0003";

    /**
     * 楼宇描述格式错误
     */
    String BUILDINGDESC_FORMAT_ERROR = "B0004";

    /**
     * 楼宇名称已存在
     */
    String BUILDING_NAME_EXIST = "B0005";

    /**
     * 单元id为空
     */
    String UNIT_ID_NULL = "B0006";

    /**
     * 单元名称为空
     */
    String UNIT_NAME_NULL = "B0007";

    /**
     * 单元名称格式错误
     */
    String UNIT_FORMAT_ERROR = "B0008";

    /**
     * 单元名称已存在
     */
    String UNIT_NAME_EXIST = "B0009";
    /**
     * 当前楼宇下存在房屋
     */
    String BUILDING_HOUSE_EXIST = "B0010";
    /**
     * 当前楼宇已被删除
     */
    String BUILDING_DELETED = "B0011";
    /**
     * 当前楼宇下单元已被删除
     */
    String UNIT_DELETED = "B0012";

    /**
     * 该单元下存在房屋
     */
    String UNIT_HOUSE_EXIST = "B0013";

    /**
     * 排序规则错误
     */
    String DISPLAY_WRONG = "B0014";
}
