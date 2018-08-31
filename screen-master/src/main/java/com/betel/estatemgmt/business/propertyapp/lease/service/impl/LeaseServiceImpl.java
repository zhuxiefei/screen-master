package com.betel.estatemgmt.business.propertyapp.lease.service.impl;

import com.betel.estatemgmt.business.propertyapp.lease.model.*;
import com.betel.estatemgmt.business.propertyapp.lease.service.LeaseService;
import com.betel.estatemgmt.common.mapper.house.OfficeHouseMapper;
import com.betel.estatemgmt.common.mapper.lease.RentMapper;
import com.betel.estatemgmt.common.model.house.OfficeHouse;
import com.betel.estatemgmt.common.model.lease.Rent;
import com.betel.estatemgmt.utils.RandomId;
import com.betel.estatemgmt.utils.UuidUtil;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * 文件说明
 * </p>
 * Author: geyf  <br/>
 * Date: 2018/5/8 11:09 <br/>
 * Version: 1.0 <br/>
 */
@Service
@Transactional
public class LeaseServiceImpl implements LeaseService {

    private static final Logger LOG = LoggerFactory.getLogger(LeaseServiceImpl.class);

    @Autowired
    private RentMapper rentMapper;

    @Autowired
    private OfficeHouseMapper officeHouseMapper;


    @Override
    public OfficeHouse findAreaByHouseId(String houseId) {
        return officeHouseMapper.selectByPrimaryKey(houseId);
    }

    @Override
    public LeaseInfoResp findLeaseInfo(String rentId) {
        LeaseInfoResp leaseInfo = new LeaseInfoResp();
        Rent rent = rentMapper.selectByPrimaryKey(rentId);
        if(rent != null){
            OfficeHouse officeHouse = officeHouseMapper.selectByPrimaryKey(rent.getHouseId());
            if(officeHouse.getHouseStatus() == 3){
                leaseInfo = rentMapper.findLeaseInfo(rentId);
            }else {
                leaseInfo = rentMapper.findLeaseInfoFree(rentId);
            }
        }
        return leaseInfo;
    }

    @Override
    public List<FindAllLeaseResp> findAllLease(Paging<FindAllLeaseResp> pager, FindAllLeaseReq req) {

        return rentMapper.findAllLease(pager.getRowBounds(), req);
    }

    @Override
    public HouseNumResp findHouseNumber() {
        HouseNumResp resp = new HouseNumResp();
        resp.setAttractNumber(officeHouseMapper.selectNumberByHouseStatus(1));
        resp.setFreeNumber(officeHouseMapper.selectNumberByHouseStatus(2));
        resp.setLeaseNumber(officeHouseMapper.selectNumberByHouseStatus(3));
        return resp;
    }

    @Override
    public void saveRentInfo(SaveLeaseReq req) {
        Rent rent = new Rent();
        BeanUtils.copyProperties(req, rent);
        rent.setHouseId(req.getAddress());
        rent.setIsUpload(0);
        rent.setCreateTime(new Date());
        rent.setRentId(UuidUtil.create());
        rent.setRentNo(RandomId.getLocalTrmSeqNum("FW"));
        rentMapper.insertSelective(rent);
        if(req.getStartTime().getTime() <= System.currentTimeMillis()){
            // 计租时间小于等于当前时间，即刻修改房屋状态为出租中
            OfficeHouse officeHouse = new OfficeHouse();
            officeHouse.setHouseId(rent.getHouseId());
            officeHouse.setHouseStatus(3);
            officeHouseMapper.updateByPrimaryKeySelective(officeHouse);
        }
    }
}