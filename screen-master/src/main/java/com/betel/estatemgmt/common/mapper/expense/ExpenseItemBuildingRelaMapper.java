package com.betel.estatemgmt.common.mapper.expense;

import com.betel.estatemgmt.business.web.charges.model.ItemBuildReq;
import com.betel.estatemgmt.common.model.expense.ExpenseItemBuildingRela;
import com.betel.estatemgmt.common.model.house.Building;
import com.betel.estatemgmt.common.model.house.House;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ExpenseItemBuildingRelaMapper {
    int deleteByPrimaryKey(Long ibId);

    int insert(ExpenseItemBuildingRela record);

    int insertSelective(ExpenseItemBuildingRela record);

    ExpenseItemBuildingRela selectByPrimaryKey(Long ibId);

    int updateByPrimaryKeySelective(ExpenseItemBuildingRela record);

    int updateByPrimaryKey(ExpenseItemBuildingRela record);


    /**
     * 通过项目类型查询楼宇和项目关联表信息
     * @param itemType
     * @return
     */
    List<Map<String,Object>> findItemAndBuildingsByItemType(String itemType);

    /**
     *  查询已经应用的收费项目的楼宇（需要屏蔽的）
     * @return
     */
    List<Building> findfiltrateBuilding(String itemId);

    /**
     *判断该小区的楼宇和别墅是否已经应用了该类型（物业/公摊水电费）的收费项目
     * @param itemId
     * @return
     */
    List<ExpenseItemBuildingRela> findBuildApplicateAssignTypeItemByItemId(String itemId);

    /**
     * 查询所有别墅
     * @return
     */
    List<House> findVilla();

    /**
     * 查询普通房屋取得楼宇信息
     * @return
     */
    List<Building> findHouseBuildings();
    /**
     * 激活指定楼宇时查询普通楼宇是否存在
     * @return
     */
    List<Building> findBatchOfJHBuilding(List<Long> buildingIds);

    List<Long> selectBuildingIdByItemId(Long itemId);

    /**
     * 激活收费项目到所有楼宇和别墅
     * @param eibrList
     * @return
     */
    public int actionChargesItemToAllBuildings(List<ExpenseItemBuildingRela> eibrList);

    /**
     * 应用全部楼宇的时候点击一次再点击报错：改收费项目已经应用了全部楼宇，不能再应用所有楼宇
     * @param itemId
     * @return
     */
    public  List<ExpenseItemBuildingRela> selectItemBuildingRelaByItemId(String itemId);

    /**
     * 点击应用到指定楼宇后再点击应有到指定楼宇报错，改收费项目应该应用了楼宇，且只能一次应用
     * @param itemBuildReq
     * @return
     */
    List<ExpenseItemBuildingRela> findItemBuildingByBuildingIds(ItemBuildReq itemBuildReq);
    /**
     * 批量查询楼宇信息：判断是否存在
     */
    List<Building> findBatchBuilding(List<Long> buildingId);

    /**
     * 判断该选中的楼宇是否已经应用该选中类型的的收费项目
     * @return
     */
    List<ExpenseItemBuildingRela> findExpenseItemnBuildingRelaByBuildingIds(ItemBuildReq itemBuildReq);

    /**
     * 根据收费项ID和楼宇ID查询关系
     * @param itemId
     * @return
     */
    ExpenseItemBuildingRela findByItemIdAndBuildingId(@Param("itemId") Long itemId, @Param("buildingId") Long buildingId);

    /**
     * 根据楼宇ID删除
     * @param buildingId
     */
    void deleteByBuildingId(@Param("buildingId") Long buildingId);

    /**
     * 根据收费项Id查询
     * @param itemId
     * @return
     */
    List<ExpenseItemBuildingRela> findByItemId(Long itemId);
}