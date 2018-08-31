package com.betel.estatemgmt.business.userapp.house.service;

import com.betel.estatemgmt.common.model.house.HouseTypeFunction;

import java.util.List;

/**
 * <p>
 * app户型模块的service
 * </p>
 * ClassName: AppHouseType <br/>
 * Author: zhouye  <br/>
 * Date: 2017/6/23 13:27 <br/>
 * Version: 1.0 <br/>
 */
public interface AppHouseTypeService {
    /**
     * <p>
     * 查询户型的功能区域
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/6/23 13:28
     *return response
     */
    List<HouseTypeFunction> findHouseTypeFunctionByHouseId(String houseId);
}
