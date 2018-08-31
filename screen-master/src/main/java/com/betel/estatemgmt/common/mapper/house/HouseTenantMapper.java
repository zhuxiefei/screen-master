package com.betel.estatemgmt.common.mapper.house;

import com.betel.estatemgmt.business.web.house.model.MemberInfo;
import com.betel.estatemgmt.business.web.housestatus.model.HouseStatusReq;
import com.betel.estatemgmt.business.web.housestatus.model.HouseStatusTenant;
import com.betel.estatemgmt.common.model.house.HouseTenant;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HouseTenantMapper {
    int deleteByPrimaryKey(String tenantId);

    int insert(HouseTenant record);

    int insertSelective(HouseTenant record);

    HouseTenant selectByPrimaryKey(String tenantId);

    int updateByPrimaryKeySelective(HouseTenant record);

    int updateByPrimaryKey(HouseTenant record);

    List<HouseStatusTenant> findHouseStatusTenant(@Param("houseId") String hoouseId);
    HouseTenant isTenantExist(HouseStatusReq houseStatusReq);

    List<MemberInfo> findInfoByHouseId(String houseId);
}