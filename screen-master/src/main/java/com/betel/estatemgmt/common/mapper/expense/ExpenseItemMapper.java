package com.betel.estatemgmt.common.mapper.expense;

import com.betel.estatemgmt.business.web.charges.model.ChargesItem;
import com.betel.estatemgmt.business.web.charges.model.ChargesItemReq;
import com.betel.estatemgmt.common.model.expense.ExpenseBill;
import com.betel.estatemgmt.common.model.expense.ExpenseItem;
import com.betel.estatemgmt.common.model.house.Building;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface ExpenseItemMapper {
    int deleteByPrimaryKey(Long itemId);

    int insert(ExpenseItem record);

    int insertSelective(ExpenseItem record);

    ExpenseItem selectByPrimaryKey(Long itemId);

    int updateByPrimaryKeySelective(ExpenseItem record);

    int updateByPrimaryKey(ExpenseItem record);
    int updateItemJHtime(ExpenseItem expenseItem);
    /**
     * 查询所有的收费项
     * @return
     */
    List<ExpenseItem> findItems();

    /**
     * 分页收费项目设置
     * @param rowBounds
     * @param chargesItemReq
     * @return
     */
    List<ChargesItem> findAllChargesItemSet(RowBounds rowBounds, ChargesItemReq chargesItemReq);

    /**
     * 通过收费项目id查询关联的楼宇
     * @param itemId
     * @return
     */
    List<Building> findBuildingsByItemId(String itemId);

    /**
     * 新增收费项目
     * @param chargesItemReq
     * @return
     */
     int addChargesItem(ChargesItemReq chargesItemReq);

    /**
     * 查询收费项目详情
     * 判断收费项目是否存在
     * 单表查询
     * @param itemId
     * @return
     */
     ChargesItem findChargesItemDatail(String itemId);

    /**
     *  通过ids集合批量查询收费项目列表
     *  场景:删除收费项目
     * @param itemIds
     * @return
     */
    List<ChargesItem> findBuildingsByItemIds(List<Long> itemIds);

    /**
     * 删除收费项目
     * @param itemIds
     * @return
     */
     int deleteChargesItem(List<Long> itemIds);

    /**
     * 查询收费标准
     * @return
     */
    ChargesItem findChargesItemStandard(String estateId);

    /**
     *更新收费标准
     * @param chargesItemReq
     * @return
     */
     int updateChargesItemStandard(ChargesItemReq chargesItemReq);

    /**
     * 根据id和名称查询：判断项目名称是否重复用
     * @param chargesItemReq
     * @return
     */
    List<ExpenseItem> findItemByNameId(ChargesItemReq chargesItemReq);

    /**
     * 查询收费项目下是否存账单
     * @param itemIds
     * @return
     */
    List<ExpenseBill> findBillsByItemIds(List<Long> itemIds);

    /**
     * 根据收费类型查询收费项
     * @param itemType
     * @return
     */
    List<ExpenseItem> findByItemType(Integer itemType);

    /**
     * 查询物业/公摊水电收费项
     * @return
     */
    List<ExpenseItem> findRemindItems();
}