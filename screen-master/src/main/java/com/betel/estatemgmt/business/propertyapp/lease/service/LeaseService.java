package com.betel.estatemgmt.business.propertyapp.lease.service;

import com.betel.estatemgmt.business.propertyapp.lease.model.*;
import com.betel.estatemgmt.common.model.house.OfficeHouse;
import com.betel.estatemgmt.utils.pagination.model.Paging;

import java.util.List;

/**
 * <p>
 * 文件说明
 * </p>
 * Author: geyf  <br/>
 * Date: 2018/5/8 11:08 <br/>
 * Version: 1.0 <br/>
 */
public interface LeaseService {

    OfficeHouse findAreaByHouseId(String houseId);

    LeaseInfoResp findLeaseInfo(String rentId);

    List<FindAllLeaseResp> findAllLease(Paging<FindAllLeaseResp> pager, FindAllLeaseReq req);

    HouseNumResp findHouseNumber();

    void saveRentInfo(SaveLeaseReq req);
}