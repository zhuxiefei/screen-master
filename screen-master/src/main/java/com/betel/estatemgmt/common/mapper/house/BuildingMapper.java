package com.betel.estatemgmt.common.mapper.house;

import com.betel.estatemgmt.business.smarthome.model.BuildingResp;
import com.betel.estatemgmt.business.web.building.model.BuildingInfo;
import com.betel.estatemgmt.common.model.house.Building;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuildingMapper {
    int deleteByPrimaryKey(Long buildingId);

    int insert(Building record);

    int insertSelective(Building record);

    Building selectByPrimaryKey(Long buildingId);

    int updateByPrimaryKeySelective(Building record);

    int updateByPrimaryKey(Building record);

    List<BuildingInfo> findBuildingList(String estateId);

    Building selectByBuildingName(@Param("buildingName") String buildingName,@Param("estateId") String estateId);

    List<Building> findAllBuildings(String estateId);

    List<Building> findFirtBuilding(String estateId);

    List<BuildingResp> findBuildings(String estateId);
}