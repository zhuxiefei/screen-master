package com.betel.estatemgmt.business.web.charges.service;

import com.betel.estatemgmt.business.web.charges.model.*;
import com.betel.estatemgmt.common.model.expense.ExpenseBill;
import com.betel.estatemgmt.common.model.expense.ExpenseItem;
import com.betel.estatemgmt.common.model.expense.ExpenseItemBuildingRela;
import com.betel.estatemgmt.common.model.house.Building;
import com.betel.estatemgmt.common.model.house.House;
import com.betel.estatemgmt.utils.pagination.model.Paging;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangjian on 2017/9/19.
 */
public interface ChargesService {

    /**
     * 分页查询费用列表
     *
     * @param pager
     * @param chargesItemReq 参数
     * @return
     */
    List<ChargesItem> findAllChargesItemSet(Paging<ChargesItem> pager, ChargesItemReq chargesItemReq);

    /**
     * 通过收费项目id查询关联的楼宇
     *
     * @param itemId
     * @return
     */
    List<Building> findBuildingsByItemId(String itemId);

    /**
     * 校验参数合法性
     *
     * @param action 1  添加  2修改
     */
    String checkParamter(ChargesItemReq chargesItemReq, int action);

    /**
     * 新增收费项目
     */
    int addChargesItem(ChargesItemReq chargesItemReq,String estateId);

    /**
     * 校验项目名称是否重复
     */
    String isItemNameReuse(ChargesItemReq chargesItemReq);

    /**
     * 查询收费项目详情
     * 场景：过滤应用的楼宇,激活时查询项目是否存在
     *
     * @param chargesItemReq
     * @return
     */
    ChargesItem findChargesItemDatail(ChargesItemReq chargesItemReq);

    /**
     * 更新收费项目
     *
     * @param chargesItemReq
     * @return
     */
    int updateChargesItem(ChargesItemReq chargesItemReq);

    /**
     * 通过ids集合批量查询收费项目列表
     *
     * @param itemIds
     * @return
     */
    List<ChargesItem> findBuildingsByItemIds(List<Long> itemIds);

    /**
     * 删除收费项目
     *
     * @param itemIds
     * @return
     */
    int deleteChargesItem(List<Long> itemIds);

    /**
     * 查询收费标准
     *
     * @return
     */
    ChargesItem findChargesItemStandard(String estateId);

    /**
     * 修改收费标准，并插入操作记录
     *
     * @param chargesItemReq
     */
    int updateChargesItemStandard(ChargesItemReq chargesItemReq);

    /**
     * 通过项目类型查询楼宇和项目关联表信息
     * 这里可以得到所有的已经应用该类型的所有楼宇信息
     *
     * @param itemType
     * @return
     */
    List<Map<String, Object>> findItemAndBuildingsByItemType(String itemType);

    /**
     * 判断该小区的楼宇和别墅是否已经应用了该类型（物业/公摊水电费）的收费项目
     *
     * @param itemId
     * @return
     */
    List<ExpenseItemBuildingRela> findBuildApplicateAssignTypeItemByItemId(String itemId);

    /**
     * 查询所有楼宇和别墅
     *
     * @return
     */
    List<Building> findAllHouseBuilding();

    /**
     * 校验前端参数
     *
     * @param chargesItemReq
     * @return
     */
    String testWebParamterIsLegal(ChargesItemReq chargesItemReq);

    /**
     * 激活应用到指定的所有的
     */
    int actionChargesItemToAllBuildings(List<ExpenseItemBuildingRela> eibrList);

    /**
     * 根据buillid查询该楼宇是否已经应用了改收费项目
     *
     * @param itemBuildReq
     * @return
     */
    List<ExpenseItemBuildingRela> findItemBuildingByBuildingIds(ItemBuildReq itemBuildReq);

    /**
     * 点击应用全部楼宇的时候后在点击应用所有楼宇报错：该收费项目已经应用了所有的楼宇不能再应用
     *
     * @param itemId
     * @return
     */
    List<ExpenseItemBuildingRela> selectItemBuildingRelaByItemId(String itemId);

    /**
     * 批量查询楼宇信息：判断是否存在
     */
    List<Object> findBatchBuilding(List<Long> buildingId);

    /**
     * 查询该选中的楼宇是否已经应用该选中的收费项目类型的项目
     *
     * @return
     */
    List<ExpenseItemBuildingRela> findExpenseItemnBuildingRelaByBuildingIds(ItemBuildReq itemBuildReq);

    /**
     * 删除时插入操作记录
     *
     * @param chargesItemReq
     * @return
     */
    int addOperationLogsOfdelete(ChargesItemReq chargesItemReq);

    /**
     * 新增激活操作记录
     *
     * @param chargesItemReq
     * @return
     */
    int addOperationLogsOfactionItem(ChargesItemReq chargesItemReq);

    /**
     * 分页查询操作记录日志
     *
     * @param chargesItemReq
     * @return
     */
    List<ExpenseItemHistoryInfo> findAllOperationLogs(Paging<ExpenseItemHistoryInfo> pager, ChargesItemReq chargesItemReq);

    /**
     * 查询已经应用的收费项目的楼宇（需要屏蔽的）
     *
     * @return
     */
    List<Building> findfiltrateBuilding(String itemId);

    /**
     * 激活，插入激活时间
     */
    int updateItemJHtime(ChargesItemReq chargesItemReq);

    /**
     * 判断该收费项目下是否存在账单
     *
     * @return
     */
    String isHasItemApplictBill(List<Long> itemIds);

    /**
     * 根据收费项类型查询
     *
     * @param itemType
     * @return
     */
    List<ExpenseItem> findByITemtType(Integer itemType);

    /**
     * 根据收费项ID查询
     *
     * @param itemId
     * @return
     */
    ExpenseItem findByItemId(Long itemId);

    /**
     * 根据收费项ID查询最新的一条账单
     *
     * @param itemId
     * @return
     */
    ExpenseBill findNewestByItemId(Long itemId);

    /**
     * 计算出账周期
     *
     * @param item
     * @return
     */
    CountBillTimeResp countBillTime(ExpenseItem item) throws Exception;

    /**
     * 出账
     *
     * @param price
     * @param startTime
     * @param endTime
     * @param item
     */
    void createBill(Double price, Date startTime, Date endTime, ExpenseItem item, HttpServletRequest request) throws Exception;

    /**
     * 查询所有房屋
     *
     * @return
     */
    List<House> findAllHouses(String estateId);

    /**
     * 根据条件查询账单
     *
     * @param startTime
     * @param endTime
     * @param itemId
     * @return
     */
    List<ExpenseBill> findByTimeAndItemId(Date startTime, Date endTime, Long itemId);
}
