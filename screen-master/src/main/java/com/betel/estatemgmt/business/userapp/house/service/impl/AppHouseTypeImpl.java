package com.betel.estatemgmt.business.userapp.house.service.impl;

import com.betel.estatemgmt.business.userapp.house.service.AppHouseTypeService;
import com.betel.estatemgmt.common.mapper.house.HouseTypeFunctionMapper;
import com.betel.estatemgmt.common.model.house.HouseTypeFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: AppHouseTypeImp <br/>
 * Author: zhouye  <br/>
 * Date: 2017/6/23 13:30 <br/>
 * Version: 1.0 <br/>
 */
@Service
@Transactional
public class AppHouseTypeImpl implements AppHouseTypeService {
    @Autowired
    HouseTypeFunctionMapper houseTypeFunctionMapper;

    @Override
    public List<HouseTypeFunction> findHouseTypeFunctionByHouseId(String houseId) {
        return houseTypeFunctionMapper.findHouseTypeFunctionByHouseId(houseId);
    }
}
