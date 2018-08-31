package com.betel.estatemgmt.business.web.screen.constant;

import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.common.ConfigManager;
import com.betel.estatemgmt.common.RedisManager;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: ScreenConstant <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/1/10 9:07 <br/>
 * Version: 1.0 <br/>
 */
public interface ScreenConstant {

    Integer PROPERTY_TYPE = 1;

    Integer PUBLIC_TYPE = 2;

    Integer BUY_SPACE_TYPE = 3;

    Integer RENT_SPACE_TYPE = 4;

    Integer ENERGY_TYPE = 5;

    /**
     * 系统交付时间
     */
    String SYSTEM_DELIVER_TIME = ConfigManager.read(ConfigName.SYSTEM_DELIVER_TIME);

    Integer NOT_PAY_STATUS = 1;

    Integer PAY_STATUS = 2;

    Integer ONLINE_PAY = 0;
}
