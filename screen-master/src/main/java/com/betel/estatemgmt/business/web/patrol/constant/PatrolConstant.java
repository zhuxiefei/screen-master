package com.betel.estatemgmt.business.web.patrol.constant;

import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.common.ConfigManager;
import com.betel.estatemgmt.common.RedisManager;

/**
 * <p>
 * Describe this class...
 * </p>
 *
 * @ClassName: PatrolConstant <br/>
 * @author: jian.z  <br/>
 * @date: 2017/12/4 17:26 <br/>
 * @Version: 1.0
 */
public interface PatrolConstant {
    /**
     * 巡检设备名称格式校验
     */
    String EQUIPMENT_NAME_RULE = "[^\\\\<>%'\"]{1,50}";
    /**
     * 巡检设备的位置校验
     */
    String EQUIPMENT_LOCATION_RULE = "[^\\\\<>%'\"]{1,50}";
    /**
     * 常量：长度30位
     */
    int QUANTITY_THIRTY=30;

    String PRIVACY_FILE_PATH = ConfigManager.read(ConfigName.PRIVACY_FILE_DIR);

    Integer EQUIP_NO_MAX_LENGTH = 15;

    Integer EQUIP_NAME_MAX_LENGTH = 15;

    Integer EQUIP_LOCATION_MAX_LENGTH = 15;

    String QUALITY_RULE = "^[1-9]\\d{0,3}|10000$";

    String PRICE_RULR = "^((([1-9]\\d{0,5})(\\.\\d{1,2})?)|1000000|1000000.0|1000000.00)$";

    String IS_PATROL = "2";
}
