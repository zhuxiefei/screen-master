package com.betel.estatemgmt.business.web.building.service;

import com.betel.estatemgmt.business.web.building.model.BuildingInfo;
import com.betel.estatemgmt.business.web.building.model.UnitInfo;
import com.betel.estatemgmt.common.model.house.Building;
import com.betel.estatemgmt.common.model.house.BuildingUnit;
import com.betel.estatemgmt.common.model.house.House;

import java.util.List;

/**
 * <p>
 * 关于楼宇相关的操作接口
 * </p>
 * ClassName: BuildingService <br/>
 * Author: zhangli <br/>
 * Date: 2017/6/20 14:21 <br/>
 * Version: 1.0 <br/>
 */
public interface BuildingService {

    /**
     * <p>
     * 新增楼宇
     * </p>
     * Author:zhangli <br/>
     * Date: 2017/6/20 15:08
     *
     * @param building 楼宇
     * @return 新增结果
     */
    int addBuilding(Building building);

    /**
     * <p>
     * 查询所有楼宇
     * </p>
     * Author:zhangli <br/>
     * Date: 2017/6/20 15:08
     *
     * @param
     * @return response
     */
    List<BuildingInfo> findBuildingList(String estateId);

    /**
     * <p>
     * 查询所有单元
     * </p>
     * Author:zhangli <br/>
     * Date: 2017/6/20 15:08
     *
     * @param buildingId
     * @return response
     */
    List<UnitInfo> findUnitList(Long buildingId);

    /**
     * <p>
     * <p>
     * </p>
     * Author:zhangli <br/>
     * Date: 2017/6/20 15:08
     *
     * @param buildingId
     * @return response
     */
    Building selectByPrimaryKey(Long buildingId);


    /**
     * <p>
     * 删除楼宇
     * </p>
     * Author:zhangli <br/>
     * Date: 2017/6/20 15:08
     *
     * @param buildingId
     * @return
     */
    void deleteBuilding(Long buildingId);

    /**
     * <p>
     * 查楼宇下所有房屋
     * </p>
     * Author:zhangli <br/>
     * Date: 2017/6/20 15:08
     *
     * @param buildingId
     * @return
     */
    List<House> selectByBuildingId(Long buildingId);

    void deleteUnit(Long unitId);

    Building selectByBuildingName(String buildingName,String estateId);

    int updateBuilding(Building building);

    List<BuildingUnit> selectByUnitNameAndBuildingId(String unitName, Long buildingId);

    int updateUnit(BuildingUnit buildingUnit);

    int addUnit(BuildingUnit buildingUnit);

    List<House> selectByUnitId(Long unitId);

    int updateUnitId(House house);

    BuildingUnit selectBuildingIdByUnitId(Long unitId);

    BuildingUnit selectBuildingUnitByUnitId(Long unitId);

    Building selectBuildingByBuildingId(Long buildingId);
}
