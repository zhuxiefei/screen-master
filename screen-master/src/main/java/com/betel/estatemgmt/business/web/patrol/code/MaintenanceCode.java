package com.betel.estatemgmt.business.web.patrol.code;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: MaintenanceCode <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/1/26 11:19 <br/>
 * Version: 1.0 <br/>
 */
public interface MaintenanceCode {

    /**
     * 设备类型空
     */
    String TYPE_NULL = "X2001";

    /**
     * 时间格式错误
     */
    String TIME_RULE = "X2002";

    /**
     * 开始时间大于结束时间
     */
    String START_LATE_END = "X2003";

    /**
     * 设备编号格式错误
     */
    String EQUIPMENT_NO_RULE = "X2004";

    /**
     * 设备名称格式错误
     */
    String EQUIPMENT_NAME_RULE = "X2005";

    /**
     * 设备位置格式错误
     */
    String EQUIPMENT_LOCATION_RULE = "X2006";

    /**
     * 质保期为空
     */
    String QUALITY_NULL = "X2007";

    /**
     * 质保期格式错误
     */
    String QUALITY_RULE = "X2008";

    /**
     * 维保记录ID为空
     */
    String RECORD_ID_NULL = "X2009";

    /**
     * 维保记录被删除
     */
    String RECORD_IS_DELETE = "X2010";

    /**
     * 设备被删除
     */
    String EQUIPMENT_IS_DELETE = "X2011";

    /**
     * 设备ID和NO不匹配
     */
    String EQUIPMENTID_NO_MATCH_ERROR = "X2012";
}
