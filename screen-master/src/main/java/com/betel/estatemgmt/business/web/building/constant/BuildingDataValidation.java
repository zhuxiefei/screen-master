package com.betel.estatemgmt.business.web.building.constant;

/**
 * <p>
 * 楼宇和单元数据校验
 * </p>
 * ClassName: BuildingDataValidation <br/>
 * Author: zhangli <br/>
 * Date: 2017/6/20 11:21 <br/>
 * Version: 1.0 <br/>
 */
public interface BuildingDataValidation {
    /**
     * 楼宇名称格式校验 不超过20个字符 不能包含特殊字符（英文\<>'"%）
     */
    String BUILDING_NAME_FORMAT = "[^\\\\<>%'\"]{1,20}";

   /**
     * 楼宇描述内容长度校验 长度为512的所有字符
     */
    Integer BUILDING_DESC_SIZE = 512;

    /**
     * 单元名称格式 不超过20个字符 不能包含特殊字符（英文\<>'"%）
     */
    String UNIT_NAME_FORMAT = "[^\\\\<>%'\"]{1,20}";

    String DISPLAY_RULE = "^(([1-9]\\d{0,3})|10000)$";
}
