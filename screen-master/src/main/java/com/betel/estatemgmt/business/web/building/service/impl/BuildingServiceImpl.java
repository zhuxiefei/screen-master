package com.betel.estatemgmt.business.web.building.service.impl;

import com.betel.estatemgmt.business.web.building.model.BuildingInfo;
import com.betel.estatemgmt.business.web.building.model.UnitInfo;
import com.betel.estatemgmt.business.web.building.service.BuildingService;
import com.betel.estatemgmt.business.web.expenses.constant.ExpenseStaticStatus;
import com.betel.estatemgmt.common.mapper.expense.ExpenseItemBuildingRelaMapper;
import com.betel.estatemgmt.common.mapper.expense.ExpenseItemMapper;
import com.betel.estatemgmt.common.mapper.house.BuildingMapper;
import com.betel.estatemgmt.common.mapper.house.BuildingUnitMapper;
import com.betel.estatemgmt.common.mapper.house.HouseMapper;
import com.betel.estatemgmt.common.model.expense.ExpenseItem;
import com.betel.estatemgmt.common.model.expense.ExpenseItemBuildingRela;
import com.betel.estatemgmt.common.model.house.Building;
import com.betel.estatemgmt.common.model.house.BuildingUnit;
import com.betel.estatemgmt.common.model.house.House;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 楼宇相关接口实现
 * </p>
 * ClassName: BuildingServiceImpl <br/>
 * Author: Zhang Li  <br/>
 * Date: 2017/6/20 10:28 <br/>
 * Version: 1.0 <br/>
 */
@Service("BuildingService")
@Transactional
public class BuildingServiceImpl implements BuildingService{

    @Autowired
    private BuildingMapper buildingMapper;

    @Autowired
    private HouseMapper houseMapper;

    @Autowired
    private BuildingUnitMapper buildingUnitMapper;

    @Autowired
    private ExpenseItemMapper itemMapper;

    @Autowired
    private ExpenseItemBuildingRelaMapper relaMapper;

    @Override
    public int addBuilding(Building building) {
        buildingMapper.insertSelective(building);
        //判断收费项是否存在能耗费和公摊水电费
        List<Long> itemIds = new ArrayList<>();
        List<ExpenseItem> publicItems = itemMapper.findByItemType(Integer.parseInt(ExpenseStaticStatus.ITEM_TYPE_TWO));
        if (publicItems!=null && publicItems.size()>0){
            itemIds.add(publicItems.get(0).getItemId());
        }
        List<ExpenseItem> energyItems = itemMapper.findByItemType(Integer.parseInt(ExpenseStaticStatus.ITEM_TYPE_FIVE));
        if (energyItems!=null && energyItems.size()>0){
            itemIds.add(energyItems.get(0).getItemId());
        }
        if (itemIds.size()>0){
            for (Long itemId:
                 itemIds) {
                ExpenseItemBuildingRela rela = new ExpenseItemBuildingRela();
                rela.setCreateTime(new Date(System.currentTimeMillis()));
                rela.setItemId(itemId);
                rela.setBuildingId(building.getBuildingId());
                //插入关系表
                relaMapper.insertSelective(rela);
            }
        }
        return 0;
    }

    @Override
    public List<BuildingInfo> findBuildingList(String estateId) {
        return buildingMapper.findBuildingList(estateId);
    }

    @Override
    public List<UnitInfo> findUnitList(Long buildingId) {
        return buildingUnitMapper.findUnitList(buildingId);
    }

    @Override
    public Building selectByPrimaryKey(Long buildingId) {
        return buildingMapper.selectByPrimaryKey(buildingId);
    }

    @Override
    public void deleteBuilding(Long buildingId) {
        buildingMapper.deleteByPrimaryKey(buildingId);
        buildingUnitMapper.deleteByBuildingId(buildingId);
        //删除楼宇收费项关系表
        relaMapper.deleteByBuildingId(buildingId);
    }
    @Override
    public List<House> selectByBuildingId(Long buildingId) {
        return houseMapper.selectByBuildingId(buildingId);
    }

    @Override
    public void deleteUnit(Long unitId) {
        buildingUnitMapper.deleteByPrimaryKey(unitId);
    }

    @Override
    public int updateUnitId(House house){
       return houseMapper.updateUnitId(house.getHouseId());
    }

    @Override
    public BuildingUnit selectBuildingIdByUnitId(Long unitId) {
        return buildingUnitMapper.selectBuildingIdByUnitId(unitId);
    }

    @Override
    public BuildingUnit selectBuildingUnitByUnitId(Long unitId) {
        return buildingUnitMapper.selectByPrimaryKey(unitId);
    }

    @Override
    public Building selectBuildingByBuildingId(Long buildingId) {
        return buildingMapper.selectByPrimaryKey(buildingId);
    }


    @Override
    public Building selectByBuildingName(String buildingName,String estateId) {
        return buildingMapper.selectByBuildingName(buildingName,estateId);
    }

    @Override
    public int updateBuilding(Building building) {
       return buildingMapper.updateByPrimaryKeySelective(building);
    }

    @Override
    public List<BuildingUnit> selectByUnitNameAndBuildingId(String unitName, Long buildingId) {
        return buildingUnitMapper.selectByUnitNameAndBuildingId(unitName,buildingId);
    }

    @Override
    public int updateUnit(BuildingUnit buildingUnit) {
        return buildingUnitMapper.updateByPrimaryKeySelective(buildingUnit);
    }

    @Override
    public int addUnit(BuildingUnit buildingUnit) {
        return buildingUnitMapper.insertSelective(buildingUnit);
    }

    @Override
    public List<House> selectByUnitId(Long unitId) {
        return houseMapper.selectByUnitId(unitId);
    }
}
