package com.betel.estatemgmt.common.mapper.house;

import com.betel.estatemgmt.business.web.building.model.UnitInfo;
import com.betel.estatemgmt.common.model.house.Building;
import com.betel.estatemgmt.common.model.house.BuildingUnit;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuildingUnitMapper {
    int deleteByPrimaryKey(Long unitId);

    int insert(BuildingUnit record);

    int insertSelective(BuildingUnit record);

    BuildingUnit selectByPrimaryKey(Long unitId);

    int updateByPrimaryKeySelective(BuildingUnit record);

    int updateByPrimaryKey(BuildingUnit record);

    List<UnitInfo> findUnitList(Long buildingId);

    void deleteByBuildingId(Long buildingId);

    List<BuildingUnit> selectByUnitNameAndBuildingId(@Param("unitName") String unitName, @Param("buildingId") Long buildingId);

//    BuildingUnit selectByUnitName(String unitName);

    /**
     * 通过楼宇查询单元
     *
     * @param buildId
     * @return
     */
    List<BuildingUnit> selecteUnitByBuildId(Long buildId);

    BuildingUnit selectBuildingIdByUnitId(Long unitId);

    Building selectBuildingByUnitId(Long unitId);
    /**
     * Describer this method...
     *
     * @param unitId
     * @return
     * @throws Exception
     * @author jians.z
     */
    BuildingUnit selectHouseStatusByPrimaryKey(Long unitId);

    /**
     * Describer this method...
     *
     * @param buildId
     * @return
     * @throws Exception
     * @author jians.z
     */
    List<BuildingUnit> selecteHouseStatusUnitByBuildId(Long buildId);
}