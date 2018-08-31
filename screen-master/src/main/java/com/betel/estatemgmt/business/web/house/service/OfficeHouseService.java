package com.betel.estatemgmt.business.web.house.service;

import com.betel.estatemgmt.business.web.house.model.officeHouse.*;
import com.betel.estatemgmt.common.model.house.OfficeHouse;
import com.betel.estatemgmt.utils.pagination.model.Paging;

import java.util.List;

/**
 * Created by Administrator on 2018/5/8/008.
 */
public interface OfficeHouseService {

    String addOfficeHouse(AddOfficeReq addOfficeReq);

    String updateOfficeHouse(UpdateOfficeReq officeReq);

    List<FindAllOfficeResp> findAllOffices(Paging<FindAllOfficeResp> paging, FindAllOfficesReq officesReq);

    void deleteOffices(String houseIds);

    void updateBusinessStatus();

    void updateRentStatus();

    OfficeHouse findByHouseId(String houseId);

    FindOfficeHouseResp findOfficeHouse(String houseId);
}
