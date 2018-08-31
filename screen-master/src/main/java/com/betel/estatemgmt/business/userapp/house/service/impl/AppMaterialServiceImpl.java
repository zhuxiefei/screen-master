package com.betel.estatemgmt.business.userapp.house.service.impl;

import com.betel.estatemgmt.business.userapp.house.model.Materials;
import com.betel.estatemgmt.business.userapp.house.model.Page;
import com.betel.estatemgmt.business.userapp.house.service.AppMaterialService;
import com.betel.estatemgmt.common.mapper.house.HouseMaterialMapper;
import com.betel.estatemgmt.common.mapper.house.HouseMaterialParmMapper;
import com.betel.estatemgmt.common.model.house.HouseMaterialParm;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: AppMaterialServiceImp <br/>
 * Author: zhouye  <br/>
 * Date: 2017/6/22 19:57 <br/>
 * Version: 1.0 <br/>
 */
@Service
@Transactional
public class AppMaterialServiceImpl implements AppMaterialService {

    @Autowired
    HouseMaterialMapper houseMaterialMapper;

    @Autowired
    HouseMaterialParmMapper houseMaterialParmMapper;

    @Override
    public List<Materials> findAllMaterialByHouseId(Paging paging, Page page) {
        return houseMaterialMapper.findAllMaterialByHouseId(paging.getRowBounds(),page);
    }

    @Override
    public Materials findMaterialBymaterialId(Long materialId) {
        return houseMaterialMapper.findMaterialBymaterialId(materialId);
    }

    @Override
    public List<HouseMaterialParm> finAllmaterialParmsBymaterialsId(Long materialId) {
        return houseMaterialParmMapper.finAllmaterialParmsBymaterialsId(materialId);
    }


}
