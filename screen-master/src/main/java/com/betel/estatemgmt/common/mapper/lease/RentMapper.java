package com.betel.estatemgmt.common.mapper.lease;

import com.betel.estatemgmt.business.propertyapp.lease.model.FindAllLeaseReq;
import com.betel.estatemgmt.business.propertyapp.lease.model.FindAllLeaseResp;
import com.betel.estatemgmt.business.propertyapp.lease.model.LeaseInfoResp;
import com.betel.estatemgmt.business.web.lease.model.TimeConflict;
import com.betel.estatemgmt.business.web.lease.model.WebFindAllLeaseReq;
import com.betel.estatemgmt.business.web.lease.model.WebLeaseInfo;
import com.betel.estatemgmt.business.web.lease.model.WebLeaseList;
import com.betel.estatemgmt.common.model.lease.Rent;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentMapper {
    int deleteByPrimaryKey(String rentId);

    int insert(Rent record);

    int insertSelective(Rent record);

    Rent selectByPrimaryKey(String rentId);

    int updateByPrimaryKeySelective(Rent record);

    int updateByPrimaryKey(Rent record);

    List<FindAllLeaseResp> findAllLease(RowBounds rowBounds, FindAllLeaseReq req);

    LeaseInfoResp findLeaseInfoFree(String houseId);

    LeaseInfoResp findLeaseInfo(String houseId);

    List<Rent> isLeaseTimeConflict(TimeConflict timeConflict);

    List<WebLeaseList> findAllWebList(RowBounds rowBounds, WebFindAllLeaseReq req);

    WebLeaseInfo findWebLeaseInfo(String rentId);

    Rent findNowRent(String houseId);

    Rent findOutRent(String houseId);

    void deleteByHouseIds(String[] houseIds);
}