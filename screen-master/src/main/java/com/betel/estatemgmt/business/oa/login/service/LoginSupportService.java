package com.betel.estatemgmt.business.oa.login.service;

import com.betel.estatemgmt.common.model.estate.Estate;
import com.betel.estatemgmt.common.model.estate.EstateCity;

import java.util.List;

/**
 * Created by Administrator on 2018/5/7/007.
 */
public interface LoginSupportService {

    List<EstateCity> findByEstateIds(String estateIds,String cityName);

    List<Estate> findEstateByEstateIds(String estateIds,String cityId) throws Exception;
}
