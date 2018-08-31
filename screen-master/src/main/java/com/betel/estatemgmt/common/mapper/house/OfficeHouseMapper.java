package com.betel.estatemgmt.common.mapper.house;

import com.betel.estatemgmt.business.web.house.model.officeHouse.*;
import com.betel.estatemgmt.business.web.repair.model.HouseInfo;
import com.betel.estatemgmt.common.model.house.House;
import com.betel.estatemgmt.common.model.house.OfficeHouse;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface OfficeHouseMapper {
    int deleteByPrimaryKey(String houseId);

    int insert(OfficeHouse record);

    int insertSelective(OfficeHouse record);

    OfficeHouse selectByPrimaryKey(String houseId);

    int updateByPrimaryKeySelective(OfficeHouse record);

    int updateByPrimaryKey(OfficeHouse record);

    List<HouseInfo> selectByIds(@Param("buildingId") String buildingId, @Param("unitId") String unitId);

    Integer selectNumberByHouseStatus(Integer status);

    OfficeHouse selectByHouseReq(AddOfficeReq houseReq);

    OfficeHouse selectByUpdateHouseReq(UpdateOfficeReq houseReq);

    List<FindAllOfficeResp> findAllOffices(RowBounds rowBounds, FindAllOfficesReq officesReq);

    void deleteOffices(String[] houseIds);

    List<OfficeHouse> findHouses();

    FindOfficeHouseResp findOfficeHouse(String houseId);

    List<House> selectByUnitId(Long unitId);

    List<House> selectHouseByBuildIdUnitNull(Long buildId);

    List<House> selecteHouseVillas(String estateId);
}