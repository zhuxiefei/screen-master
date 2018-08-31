package com.betel.estatemgmt.business.web.charges.service.impl;


import com.betel.estatemgmt.business.smartre.user.service.UserService;
import com.betel.estatemgmt.business.web.charges.code.ChargesCode;
import com.betel.estatemgmt.business.web.charges.constant.ChargesValidation;
import com.betel.estatemgmt.business.web.charges.model.*;
import com.betel.estatemgmt.business.web.charges.service.ChargesService;
import com.betel.estatemgmt.business.web.expenses.constant.ExpenseStaticStatus;
import com.betel.estatemgmt.business.web.remind.constant.RemindDataValidation;
import com.betel.estatemgmt.common.SendMessage;
import com.betel.estatemgmt.common.mapper.expense.ExpenseBillMapper;
import com.betel.estatemgmt.common.mapper.expense.ExpenseItemBuildingRelaMapper;
import com.betel.estatemgmt.common.mapper.expense.ExpenseItemHistoryMapper;
import com.betel.estatemgmt.common.mapper.expense.ExpenseItemMapper;
import com.betel.estatemgmt.common.mapper.house.BuildingMapper;
import com.betel.estatemgmt.common.mapper.house.BuildingUnitMapper;
import com.betel.estatemgmt.common.mapper.house.HouseMapper;
import com.betel.estatemgmt.common.mapper.house.HouseOwnerMapper;
import com.betel.estatemgmt.common.mapper.system.NoticeMapper;
import com.betel.estatemgmt.common.model.expense.ExpenseBill;
import com.betel.estatemgmt.common.model.expense.ExpenseItem;
import com.betel.estatemgmt.common.model.expense.ExpenseItemBuildingRela;
import com.betel.estatemgmt.common.model.expense.ExpenseItemHistory;
import com.betel.estatemgmt.common.model.house.Building;
import com.betel.estatemgmt.common.model.house.BuildingUnit;
import com.betel.estatemgmt.common.model.house.House;
import com.betel.estatemgmt.common.model.house.HouseOwner;
import com.betel.estatemgmt.common.model.system.Notice;
import com.betel.estatemgmt.common.model.user.UserAccount;
import com.betel.estatemgmt.common.msgpush.MsgPushUtils;
import com.betel.estatemgmt.shiro.ActiveUser;
import com.betel.estatemgmt.utils.*;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 费用查询接口实现类
 * </p>
 * ClassName: ChargesServiceImpl <br/>
 * Author:zhangjain <br/>
 * Date: 2017/9/19 <br/>
 * Version: 1.0 <br/>
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ChargesServiceImpl implements ChargesService {
    private static final Logger LOG = LoggerFactory.getLogger(ChargesServiceImpl.class);
    @Autowired
    private ExpenseItemMapper expenseItemMapper;
    @Autowired
    private ExpenseItemBuildingRelaMapper expenseItemBuildingRelaMapper;
    @Autowired
    private ExpenseItemHistoryMapper expenseItemHistoryMapper;
    @Autowired
    private BuildingMapper buildingMapper;
    @Autowired
    private ExpenseBillMapper billMapper;
    @Autowired
    private HouseMapper houseMapper;
    @Autowired
    private BuildingUnitMapper unitMapper;
    @Autowired
    private NoticeMapper noticeMapper;
    @Autowired
    private HouseOwnerMapper houseOwnerMapper;
    @Autowired
    private UserService userService;

    @Override
    public List<ChargesItem> findAllChargesItemSet(Paging<ChargesItem> pager, ChargesItemReq chargesItemReq) {
        List<ChargesItem> chargesItems = expenseItemMapper.findAllChargesItemSet(pager.getRowBounds(), chargesItemReq);
        String buildings = "";
        for (int i = 0; i < chargesItems.size(); i++) {
            String itemId = chargesItems.get(i).getItemId();
            if (LOG.isDebugEnabled()) {
                LOG.debug("------------------------------------查询第" + (i + 0) + "出项目id=" + itemId);
            }
            List<Building> buildingList = expenseItemMapper.findBuildingsByItemId(itemId);
            for (int j = 0; j < buildingList.size(); j++) {
                String buildingName = buildingList.get(j).getBuildingName();
                if (j == buildingList.size() - 1) {
                    buildings = buildings + buildingName;
                } else {
                    buildings = buildings + buildingName + ",";
                }
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("--------------查询第" + (i + 0) + "出项目id=" + itemId + "下的楼宇有：" + buildings);
            }
            chargesItems.get(i).setBuildings(buildings);
            buildings = "";
        }
        return chargesItems;
    }

    @Override
    public List<Building> findBuildingsByItemId(String itemId) {
        return null;
    }

    @Override
    public String checkParamter(ChargesItemReq chargesItemReq, int action) {
        String code = "";
        //chargeType 计费方式
        if (1 == action) {
            if (StringUtil.isBlank(chargesItemReq.getChargeType())) {
                code = ChargesCode.CHARGE_TYPE_IS_NULL;
                return code;
            } else {
                if (!ChargesValidation.CHARGETYPE_ONE.equals(chargesItemReq.getChargeType().trim())) {
                    code = ChargesCode.CHARGE_TYPE_IS_NOT_LEGAL;
                    return code;
                }
            }
            //itemName项目名称
            if (StringUtil.isBlank(chargesItemReq.getItemName())) {
                code = ChargesCode.ITEM_NAME_IS_NULL;
                return code;
            } else {
                if (!chargesItemReq.getItemName().trim().matches(ChargesValidation.ITEM_NAME_RULE)) {
                    code = ChargesCode.ITEM_NAME_IS_NOT_LEGAL;
                    return code;
                }
            }
            //itemCycle 计费周期:0<x<=24
            if (StringUtil.isBlank(chargesItemReq.getItemCycle())) {
                code = ChargesCode.ITEM_CYCLE_IS_NULL;
                return code;
            } else {
                if (!chargesItemReq.getItemCycle().trim().matches(ChargesValidation.ITEM_CYCLE)) {
                    code = ChargesCode.ITEM_CYCLE_IS_NOT_LEGAL;
                    return code;
                }
            }
            //itemType 收费项类型
            if (StringUtil.isBlank(chargesItemReq.getItemType())) {
                code = ChargesCode.ITEM_TYPE_IS_NULL;
                return code;
            } else {
                boolean flag1 = "1".equals(chargesItemReq.getItemType().trim());
                boolean flag2 = "2".equals(chargesItemReq.getItemType().trim());
                boolean flag3 = "3".equals(chargesItemReq.getItemType().trim());
                boolean flag4 = "4".equals(chargesItemReq.getItemType().trim());
                boolean flag5 = "5".equals(chargesItemReq.getItemType().trim());
                if (flag1 == false && flag2 == false && flag3 == false && flag4 == false && flag5 == false) {
                    code = ChargesCode.ITEM_TYPE_IS_NOT_EXIST;
                    return code;
                }
            }
        }
        //itemPrice 价格0<x<=50000
        if (!StringUtil.isBlank(chargesItemReq.getItemPrice()) && !chargesItemReq.getItemPrice().trim().matches(ChargesValidation.PRICE_RULE)) {
            code = ChargesCode.ITEM_PRICE_IS_NOT_LEGAL;
            return code;
        }


        //overdueFine 滞纳金率
        if (StringUtil.isBlank(chargesItemReq.getOverdueFine())) {
            code = ChargesCode.OVERDUE_FINE_IS_NULL;
            return code;
        } else {
            if (!chargesItemReq.getOverdueFine().trim().matches(ChargesValidation.OVER_FINE_PRICE)) {
                code = ChargesCode.OVERDUE_FINE_PRICE_IS_NOT_LEGAL;
                return code;
            }
        }

        //chargeCycle  缴费周期:9<x<100
        if (StringUtil.isBlank(chargesItemReq.getChargeCycle())) {
            code = ChargesCode.CHARGECYCLE_IS_NULL;
            return code;
        } else {
            if (!chargesItemReq.getChargeCycle().trim().matches(ChargesValidation.CHARGE_CYCLE)) {
                code = ChargesCode.CHARGECYCLE_IS_NOT_LEGAL;
                return code;
            }
        }
        return code;
    }

    @Override
    public int addChargesItem(ChargesItemReq chargesItemReq,String estateId) {
        ExpenseItem expenseItem = new ExpenseItem();
        expenseItem.setChargeType(Integer.valueOf(chargesItemReq.getChargeType()));
        expenseItem.setItemName(chargesItemReq.getItemName());
        if (!StringUtil.isBlank(chargesItemReq.getItemPrice())) {
            expenseItem.setItemPrice(BigDecimal.valueOf(Double.valueOf(chargesItemReq.getItemPrice())));
        }
        expenseItem.setItemCycle((Integer.valueOf(chargesItemReq.getItemCycle())));
        expenseItem.setOverdueFine(Double.valueOf(chargesItemReq.getOverdueFine()));
        String itemType = chargesItemReq.getItemType();
        String typeName = "";
        //1提前收费 2使用后收费
        Integer billType = 1;
        if (ChargesValidation.ITEM_TYPE_ONE.equals(itemType)) {
            typeName = "物业费";
        }
        if (ChargesValidation.ITEM_TYPE_TWO.equals(itemType)) {
            typeName = "公摊水电费";
            billType = 2;
        }
        if (ChargesValidation.ITEM_TYPE_THREE.equals(itemType)) {
            typeName = "购买停车费";
        }
        if (ChargesValidation.ITEM_TYPE_FOUR.equals(itemType)) {
            typeName = "租赁停车费";
        }
        if (ChargesValidation.ITEM_TYPE_FIVE.equals(itemType)) {
            typeName = "能耗费";
            billType = 2;
        }
        expenseItem.setItemType(Integer.valueOf(itemType));
        expenseItem.setChargeCycle(Integer.valueOf(chargesItemReq.getChargeCycle()));
        expenseItem.setCreateTime(new Date());
        expenseItem.setUpdateTime(expenseItem.getCreateTime());
        expenseItem.setBillType(billType);
        if (ChargesValidation.ITEM_TYPE_TWO.equals(itemType) || ChargesValidation.ITEM_TYPE_FIVE.equals(itemType)) {
            expenseItem.setBillType(2);
        } else if (ChargesValidation.ITEM_TYPE_ONE.equals(itemType) || ChargesValidation.ITEM_TYPE_THREE.equals(itemType) || ChargesValidation.ITEM_TYPE_FOUR.equals(itemType)) {
            expenseItem.setBillType(1);
        }
        //===============新增收费项目===========================
        int flag = expenseItemMapper.insertSelective(expenseItem);
        //若新增收费项目为能耗费或公摊水电费，则绑定所有楼宇和别墅
        if (Integer.parseInt(ExpenseStaticStatus.ITEM_TYPE_TWO) == expenseItem.getItemType()
                || Integer.parseInt(ExpenseStaticStatus.ITEM_TYPE_FIVE) == expenseItem.getItemType()) {
            //获取主键
            Long itemId = expenseItem.getItemId();
            List<Building> list = buildingMapper.findAllBuildings(estateId);
            List<ExpenseItemBuildingRela> relas = new ArrayList<>();
            if (list != null || list.size() > 0) {
                for (Building build :
                        list) {
                    ExpenseItemBuildingRela rela = new ExpenseItemBuildingRela();
                    rela.setBuildingId(build.getBuildingId());
                    rela.setCreateTime(new Date(System.currentTimeMillis()));
                    rela.setItemId(itemId);
                    relas.add(rela);
                }
            }
            //将别墅添加进去
            ExpenseItemBuildingRela rr = new ExpenseItemBuildingRela();
            rr.setBuildingId(null);
            rr.setCreateTime(new Date(System.currentTimeMillis()));
            rr.setItemId(itemId);
            relas.add(rr);
            //插入关系表
            expenseItemBuildingRelaMapper.actionChargesItemToAllBuildings(relas);
        }
        //===============新增操作记录===========================
        Subject subject = SecurityUtils.getSubject();
        ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
        if (null == activeUser) {
            activeUser = new ActiveUser();
            activeUser.setUserId("1");
        }
        ExpenseItemHistory eih = new ExpenseItemHistory();
        eih.setItemId(expenseItem.getItemId());//收费项目id
        eih.setAdminName(activeUser.getAdminName());//修改的管理员名称
        eih.setCreateTime(new Date());
        eih.setHistoryDesc("添加新的" + typeName + "收费项，项目名称为" + expenseItem.getItemName());
        int flag11 = expenseItemHistoryMapper.insert(eih);
        if (LOG.isDebugEnabled()) {
            LOG.debug("----------- 新增收费项目成功，插入操作记录成功-----------------" + flag11);
        }
        return flag;
    }

    @Override
    public String isItemNameReuse(ChargesItemReq chargesItemReq) {
        String code = "";
        List<ExpenseItem> expenseItems = expenseItemMapper.findItemByNameId(chargesItemReq);
        if (expenseItems.size() > 0) {
            code = ChargesCode.ITEM_NAME_IS_REPETION;
        }
        return code;
    }

    @Override
    public ChargesItem findChargesItemDatail(ChargesItemReq chargesItemReq) {
        return expenseItemMapper.findChargesItemDatail(chargesItemReq.getItemId());
    }

    @Override
    public int updateChargesItem(ChargesItemReq chargesItemReq) {
        ChargesItem chargesItem = expenseItemMapper.findChargesItemDatail(chargesItemReq.getItemId());
        ExpenseItem expenseItem = new ExpenseItem();
        try {
            expenseItem.setItemId(Long.valueOf(chargesItemReq.getItemId()));
        } catch (Exception e) {
            expenseItem.setItemId(0L);
        }
        if (!StringUtil.isBlank(chargesItemReq.getItemPrice())) {
            expenseItem.setItemPrice(BigDecimal.valueOf(Double.valueOf(chargesItemReq.getItemPrice())));
        }
        if (!StringUtil.isBlank(chargesItemReq.getOverdueFine())) {
            expenseItem.setOverdueFine(Double.valueOf(chargesItemReq.getOverdueFine()));
        }
        if (!StringUtil.isBlank(chargesItemReq.getChargeCycle())) {
            expenseItem.setChargeCycle(Integer.valueOf(chargesItemReq.getChargeCycle()));
        }
        expenseItem.setUpdateTime(new Date());
        int flag = expenseItemMapper.updateByPrimaryKeySelective(expenseItem);
        if (LOG.isDebugEnabled()) {
            LOG.debug("----------------修改收费项目成功条数" + flag);
        }
        //=====================插入操作记录================================
        Subject subject = SecurityUtils.getSubject();
        ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
        if (null == activeUser) {
            activeUser = new ActiveUser();
            activeUser.setUserId("1");
        }
        ExpenseItemHistory eih = new ExpenseItemHistory();
        eih.setItemId(expenseItem.getItemId());//收费项目id
        eih.setAdminName(activeUser.getAdminName());//修改的管理员名称
        eih.setCreateTime(new Date());
        String itemType = chargesItem.getItemType();
        String typeName = "";
        if (ChargesValidation.ITEM_TYPE_ONE.equals(itemType)) {
            typeName = "物业费";
        }
        if (ChargesValidation.ITEM_TYPE_TWO.equals(itemType)) {
            typeName = "公摊水电费";
        }
        if (ChargesValidation.ITEM_TYPE_FIVE.equals(itemType) || ChargesValidation.ITEM_TYPE_TWO.equals(itemType)) {
            eih.setHistoryDesc("修改" + typeName
                    + "收费项，项目名称为" + chargesItem.getItemName()
                    + "，滞纳金率更改为（" + chargesItemReq.getOverdueFine() + "%）、"
                    + "缴费周期更改为（" + chargesItemReq.getChargeCycle() + "）天)");
        } else {
            eih.setHistoryDesc("修改" + typeName
                    + "收费项，项目名称为" + chargesItem.getItemName() + "，价格更改为（" +
                    chargesItemReq.getItemPrice() + "）元、"
                    + "滞纳金率更改为（" + chargesItemReq.getOverdueFine() + "%）、"
                    + "缴费周期更改为（" + chargesItemReq.getChargeCycle() + "）天)");
        }
        expenseItemHistoryMapper.insertSelective(eih);
        return flag;
    }

    @Override
    public List<ChargesItem> findBuildingsByItemIds(List<Long> itemIds) {
        return expenseItemMapper.findBuildingsByItemIds(itemIds);
    }

    @Override
    public int deleteChargesItem(List<Long> itemIds) {
        int flag = addOperationLogsOfdelete(itemIds);
        if (LOG.isDebugEnabled()) {
            LOG.debug("--------------------删除操作记录成功条数------------------------" + flag);
        }
        return expenseItemMapper.deleteChargesItem(itemIds);
    }


    @Override
    public ChargesItem findChargesItemStandard(String estateId) {
        return expenseItemMapper.findChargesItemStandard(estateId);
    }

    @Override
    public int updateChargesItemStandard(ChargesItemReq chargesItemReq) {
        int flag = expenseItemMapper.updateChargesItemStandard(chargesItemReq);
        if (LOG.isDebugEnabled()) {
            LOG.debug("----------------修改收费标准成功条数" + flag);
        }
        Subject subject = SecurityUtils.getSubject();
        ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
        if (null == activeUser) {
            activeUser = new ActiveUser();
            activeUser.setUserId("1");
        }
        ExpenseItemHistory eih = new ExpenseItemHistory();
        eih.setItemId(0L);//收费项目id
        eih.setAdminName(activeUser.getAdminName());//修改的管理员名称
        eih.setCreateTime(new Date());
        eih.setHistoryDesc("修改收费标准");
        int flag1 = expenseItemHistoryMapper.insertSelective(eih);//插入操作记录
        if (LOG.isDebugEnabled()) {
            LOG.debug("---------- 更新操作收费标准时插入操作记录-----------记录成功条数----" + flag1);
        }
        return flag;
    }

    @Override
    public List<Map<String, Object>> findItemAndBuildingsByItemType(String itemType) {
        return expenseItemBuildingRelaMapper.findItemAndBuildingsByItemType(itemType);
    }

    @Override
    public List<ExpenseItemBuildingRela> findBuildApplicateAssignTypeItemByItemId(String itemId) {
        return expenseItemBuildingRelaMapper.findBuildApplicateAssignTypeItemByItemId(itemId);
    }

    @Override
    public List<Building> findAllHouseBuilding() {
        List<House> houseList = expenseItemBuildingRelaMapper.findVilla();
        if (LOG.isDebugEnabled()) {
            LOG.debug("--查询所有的别墅sql:SELECT * FROM  house_house hh WHERE  hh.buildingId is NULL and unitId is NULL----别墅个数：" + houseList.size());
        }
        //查询普通房屋得到楼宇信息
        List<Building> buildingList = expenseItemBuildingRelaMapper.findHouseBuildings();
        if (LOG.isDebugEnabled()) {
            LOG.debug("--查询所有的楼宇sql:SELECT hb.buildingId,hb.buildingName FROM  house_building hb-----楼宇的个数：" + buildingList.size());
        }
        if (houseList.size() > 0) {
            Building building = new Building();
            building.setBuildingId(0L);
            building.setBuildingName("其他");
            buildingList.add(building);
        }
        return buildingList;
    }

    @Override
    public String testWebParamterIsLegal(ChargesItemReq chargesItemReq) {
        String code = "";
        //校验时间
        if (!StringUtil.isBlank(chargesItemReq.getItemActionTime())) {
            String activityTime = chargesItemReq.getItemActionTime().trim();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            try {
                Date dateParm = sdf.parse(chargesItemReq.getItemActionTime());
                if (LOG.isDebugEnabled()) {
                    LOG.debug("--------激活时客户选择的年月:-------" + chargesItemReq.getItemActionTime());
                }
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.MONTH, 1);
                Date curDate = calendar.getTime();
                String time01 = sdf.format(curDate);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("--------激活时将calendar装换为Date类型且获取当前年月的下一个月：-------" + time01);
                }
                Date dateCur = sdf.parse(time01);
                if (dateParm.getTime() < dateCur.getTime()) {
                    return code = ChargesCode.CHOOS_ITEM_HAS_NOT_EXIST_PART_OF_IETEMS;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (!activityTime.matches(RegexRule.START_END_TIME_RULE)) {
                return code = ChargesCode.BILLING_MONTHLY_IS_NOT_LEGAL;
            }
        } else {
            return code = ChargesCode.ITEM_HAS_APPLIED_TO_THE_BULIDINGS_CHARGES_TIME_IS_NULL;
        }
        return code;
    }

    @Override
    public int actionChargesItemToAllBuildings(List<ExpenseItemBuildingRela> eibrList) {
        return expenseItemBuildingRelaMapper.actionChargesItemToAllBuildings(eibrList);
    }

    @Override
    public List<ExpenseItemBuildingRela> findItemBuildingByBuildingIds(ItemBuildReq itemBuildReq) {
        return expenseItemBuildingRelaMapper.findItemBuildingByBuildingIds(itemBuildReq);
    }

    @Override
    public List<ExpenseItemBuildingRela> selectItemBuildingRelaByItemId(String itemId) {
        return expenseItemBuildingRelaMapper.selectItemBuildingRelaByItemId(itemId);
    }

    @Override
    public List<Object> findBatchBuilding(List<Long> buildingIds) {
        List<Object> objects = new ArrayList<>();
        for (int i = 0; i < buildingIds.size(); i++) {
            Long buildId = buildingIds.get(i);
            if (0 == buildId) {
                List<House> houseList = expenseItemBuildingRelaMapper.findVilla();
                objects.add(houseList);
            } else {
                List<Building> buildingList = expenseItemBuildingRelaMapper.findBatchOfJHBuilding(buildingIds);
                if (buildingList.size() != 0) {
                    objects.add(buildingList);
                }
            }
        }
        return objects;
    }

    @Override
    public List<ExpenseItemBuildingRela> findExpenseItemnBuildingRelaByBuildingIds(ItemBuildReq itemBuildReq) {
        return expenseItemBuildingRelaMapper.findExpenseItemnBuildingRelaByBuildingIds(itemBuildReq);
    }

    @Override
    public int addOperationLogsOfdelete(ChargesItemReq chargesItemReq) {
        Subject subject = SecurityUtils.getSubject();
        ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
        if (null == activeUser) {
            activeUser = new ActiveUser();
            activeUser.setUserId("1");
        }
        List<ExpenseItemHistory> eih = new ArrayList<>();
        List<Long> itemIdLongList = Tool.getIdList(chargesItemReq.getItemIds());//选中的
        List<ChargesItem> chargesItems = expenseItemMapper.findBuildingsByItemIds(itemIdLongList);
        for (int i = 0; i < chargesItems.size(); i++) {
            ExpenseItemHistory itemHistory = new ExpenseItemHistory();
            itemHistory.setItemId(Long.valueOf(chargesItems.get(i).getItemId()));
            itemHistory.setAdminName(activeUser.getAdminName());
            itemHistory.setCreateTime(new Date());
            String itemType = chargesItems.get(i).getItemType();
            if ("1".equals(itemType)) {
                String historyDec = "删除物业费收费项，项目名称为" + chargesItems.get(i).getItemName();
                itemHistory.setHistoryDesc(historyDec);
            } else if ("2".equals(itemType)) {
                String historyDec = "删除公摊水电费收费项，项目名称为" + chargesItems.get(i).getItemName();
                itemHistory.setHistoryDesc(historyDec);
            } else {
                String historyDec = "删除收费项，项目名称为" + chargesItems.get(i).getItemName();
                itemHistory.setHistoryDesc(historyDec);
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("-----------------------删除时插入操作历史记录" + itemHistory);
            }
            eih.add(itemHistory);
        }
        return expenseItemHistoryMapper.insertBatchSelective(eih);//插入操作记录;
    }

    /**
     * 删除收费项目的时候插入操作记录
     *
     * @param itemIdLongList
     * @return
     */
    private int addOperationLogsOfdelete(List<Long> itemIdLongList) {
        Subject subject = SecurityUtils.getSubject();
        ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
        if (null == activeUser) {
            activeUser = new ActiveUser();
            activeUser.setUserId("1");
        }
        List<ExpenseItemHistory> eih = new ArrayList<>();
//        List<Long> itemIdLongList= Tool.getIdList(chargesItemReq.getItemIds());//选中的
        List<ChargesItem> chargesItems = expenseItemMapper.findBuildingsByItemIds(itemIdLongList);
        for (int i = 0; i < chargesItems.size(); i++) {
            ExpenseItemHistory itemHistory = new ExpenseItemHistory();
            itemHistory.setItemId(Long.valueOf(chargesItems.get(i).getItemId()));
            itemHistory.setAdminName(activeUser.getAdminName());
            itemHistory.setCreateTime(new Date());
            String itemType = chargesItems.get(i).getItemType();
            if ("1".equals(itemType)) {
                String historyDec = "删除物业费收费项，项目名称为" + chargesItems.get(i).getItemName();
                itemHistory.setHistoryDesc(historyDec);
            } else if ("2".equals(itemType)) {
                String historyDec = "删除公摊水电费收费项，项目名称为" + chargesItems.get(i).getItemName();
                itemHistory.setHistoryDesc(historyDec);
            } else if ("3".equals(itemType)) {
                String historyDec = "删除购买停车费收费项，项目名称为" + chargesItems.get(i).getItemName();
                itemHistory.setHistoryDesc(historyDec);
            } else if ("4".equals(itemType)) {
                String historyDec = "删除租赁停车费收费项，项目名称为" + chargesItems.get(i).getItemName();
                itemHistory.setHistoryDesc(historyDec);
            } else if ("5".equals(itemType)) {
                String historyDec = "删除能耗费收费项，项目名称为" + chargesItems.get(i).getItemName();
                itemHistory.setHistoryDesc(historyDec);
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("-----------------------删除时插入操作历史记录" + itemHistory);
            }
            eih.add(itemHistory);
        }
        return expenseItemHistoryMapper.insertBatchSelective(eih);//插入操作记录;
    }

    @Override
    public int addOperationLogsOfactionItem(ChargesItemReq chargesItemReq) {
        Subject subject = SecurityUtils.getSubject();
        ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
        if (null == activeUser) {
            activeUser = new ActiveUser();
            activeUser.setUserId("1");
        }
        ExpenseItemHistory eih = new ExpenseItemHistory();
        eih.setItemId(Long.valueOf(chargesItemReq.getItemId()));//收费项目id
        eih.setAdminName(activeUser.getAdminName());//修改的管理员ID
        String itemName = "";
        if (ChargesValidation.ITEM_TYPE_ONE.equals(chargesItemReq.getItemType())) {
            itemName = "物业费";
        } else if (ChargesValidation.ITEM_TYPE_TWO.equals(chargesItemReq.getItemType())) {
            itemName = "公摊水电费";
        }
        String historyDec = "激活" + itemName + "收费项，项目名称为" + chargesItemReq.getItemName();
        eih.setCreateTime(new Date());
        eih.setHistoryDesc(historyDec);
        return expenseItemHistoryMapper.insertSelective(eih);//插入操作记录;
    }


    @Override
    public List<ExpenseItemHistoryInfo> findAllOperationLogs(Paging<ExpenseItemHistoryInfo> pager, ChargesItemReq chargesItemReq) {
        return expenseItemHistoryMapper.findAllOperationLogs(pager.getRowBounds(), chargesItemReq);
    }

    @Override
    public List<Building> findfiltrateBuilding(String itemId) {
        return expenseItemBuildingRelaMapper.findfiltrateBuilding(itemId);
    }

    @Override
    public int updateItemJHtime(ChargesItemReq chargesItemReq) {
        ExpenseItem expenseItem = new ExpenseItem();
        expenseItem.setItemId(Long.valueOf(chargesItemReq.getItemId()));
        //激活时间
        String jhTime = chargesItemReq.getItemActionTime();
        String jhTempTime = jhTime + "-01 00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            Date date = sdf.parse(jhTempTime);
            expenseItem.setStartTime(date);
        } catch (ParseException e) {
            LOG.error("时间转化出错");
        }
        return expenseItemMapper.updateItemJHtime(expenseItem);
    }

    @Override
    public String isHasItemApplictBill(List<Long> itemIds) {
        List<ExpenseBill> expenseBillList = expenseItemMapper.findBillsByItemIds(itemIds);
        if (itemIds.size() > 1) {
            if (expenseBillList.size() > 0) {
                return ChargesCode.ITEM_PART_HAS_BILL_NOT_DELETE;
            }
        } else {
            if (expenseBillList.size() > 0) {
                return ChargesCode.ITEM_HAS_BILL_NOT_DELETE;
            }
        }
        return "";
    }

    @Override
    public List<ExpenseItem> findByITemtType(Integer itemType) {
        return expenseItemMapper.findByItemType(itemType);
    }

    @Override
    public ExpenseItem findByItemId(Long itemId) {
        return expenseItemMapper.selectByPrimaryKey(itemId);
    }

    @Override
    public ExpenseBill findNewestByItemId(Long itemId) {
        return billMapper.findNewestByHouseIdAndItemId(null, itemId);
    }

    @Override
    public CountBillTimeResp countBillTime(ExpenseItem item) throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("===========countBillTime start=============ExpenseItem=" + item);
        }
        CountBillTimeResp resp = new CountBillTimeResp();
        ExpenseBill bill = billMapper.findNewestByItemType(item.getItemType());
        if (bill == null) {
            //第一次计算周期
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date startTime = sdf.parse(ChargesValidation.SYSTEM_DELIVER_TIME);
            resp.setStartTime(startTime);
            resp.setEndTime(countEndTime(startTime, item.getItemCycle()));
        } else {
            //已经存在账单，就根据账单的截止时间计算往后计算一个周期
            Date endTime = bill.getEndTime();
            resp.setStartTime(getAfterDay(endTime));
            resp.setEndTime(countEndTime(getAfterDay(endTime), item.getItemCycle()));
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("===========countBillTime end=============resp=" + resp);
        }
        return resp;
    }

    @Override
    public void createBill(Double price, Date startTime, Date endTime, ExpenseItem item, HttpServletRequest request) throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("============createBill start==============price" + price + ",startTime=" + startTime
                    + ",endTime=" + endTime + ",item=" + item);
        }
        List<House> houses = houseMapper.selectByItemId(item.getItemId());
        if (LOG.isDebugEnabled()) {
            LOG.debug("========everyItem houses========" + houses);
        }
        //创建账单列表
        List<ExpenseBill> list = new ArrayList<>();
        //判断是否存在别墅
        List<Long> buildingIds = expenseItemBuildingRelaMapper.selectBuildingIdByItemId(item.getItemId());
        if (LOG.isDebugEnabled()) {
            LOG.debug("========everyItem buildingIds========" + buildingIds);
        }
        if (buildingIds.contains(null)) {
            List<House> villa = expenseItemBuildingRelaMapper.findVilla();
            if (LOG.isDebugEnabled()) {
                LOG.debug("========everyItem villa========" + villa);
            }
            if (villa != null && villa.size() > 0) {
                houses.addAll(villa);
            }
        }
        if (houses != null && houses.size() > 0) {
            //计算总面积
            Double allFloorArea = 0.0;
            Double allInterArea = 0.0;
            for (House house :
                    houses) {
                allFloorArea += house.getFloorArea();
                allInterArea += house.getInterFloorArea();
            }
            for (House house :
                    houses) {
                //计算金额
                //5	能耗费——产生的能耗费总金额（6/月） ÷ 小区所有房屋的套内面积 x 每户套内面积 + 滞纳金
                //2	公摊水电费——产生的公摊水电费总金额（6/月） ÷ 小区所有房屋的建筑面积 x 每户建筑面积 + 滞纳金
                BigDecimal billAmount = null;
                if (item.getItemType() == Integer.parseInt(ExpenseStaticStatus.ITEM_TYPE_TWO)) {
                    billAmount = BillAmountUtil.countPublicAmount(price, allFloorArea, house.getFloorArea());
                } else if (item.getItemType() == Integer.parseInt(ExpenseStaticStatus.ITEM_TYPE_FIVE)) {
                    billAmount = BillAmountUtil.countEnergyAmount(price, allInterArea, house.getInterFloorArea());
                }
                //创建账单对象
                ExpenseBill bill = new ExpenseBill();
                bill.setBillNo(getLocalTrmSeqNum("ZD"));
                bill.setItemType(item.getItemType());
                bill.setHouseId(house.getHouseId());
                bill.setCreateTime(new Date(System.currentTimeMillis()));
                bill.setItemId(item.getItemId());
                bill.setBillStatus(RemindDataValidation.BILL_NOTPAY_STATUS);
                bill.setIsPrint(0);
                bill.setIsDelete(1);
                bill.setUrgeCount(0);
                bill.setDemandBillStatus(1);
                HouseOwner houseOwner = houseOwnerMapper.selectByHouseId(house.getHouseId());
                if (houseOwner != null) {
                    if (!StringUtil.isBlank(houseOwner.getUserId())) {
                        //jianz.z 2018-1-21
                        bill.setHouseOwnerPhone(userService.findByUserId(houseOwner.getUserId(), request).getAcctName());
                    } else {
                        bill.setHouseOwnerPhone(houseOwner.getPhoneNum());
                    }
                    bill.setHouseOwnerName(houseOwner.getRealName());
                }
                bill.setBillAmount(billAmount);
                bill.setStartTime(DateUtil.toDate(DateUtil.toString(startTime, "yyyy-MM-dd"), "yyyy-MM-dd"));
                bill.setEndTime(DateUtil.toDate(DateUtil.toString(endTime, "yyyy-MM-dd"), "yyyy-MM-dd"));
                bill.setDeadLine(DateUtil.toDate(DateUtil.toString(countDeadLine(new Date(), item.getChargeCycle()), "yyyy-MM-dd"), "yyyy-MM-dd"));
                if (bill.getBillAmount().doubleValue() != 0) {
                    list.add(bill);
                }
                if (billAmount != null && billAmount.doubleValue() != 0) {
                    String content = null;
                    String[] params = null;
                    //计算计费周期
                    String billCycle = DateUtil.toString(bill.getStartTime(), "yyyy-MM-dd") + "~" + DateUtil.toString(bill.getEndTime(), "yyyy-MM-dd");
                    //将费用转换为普通计数法
                    String billAm = billAmount.toPlainString();
                    if (item.getItemType() == Integer.parseInt(ExpenseStaticStatus.ITEM_TYPE_TWO)) {
                        content = "尊敬的" + makeHouseNo(house.getHouseId()) + "业主" + bill.getHouseOwnerName()
                                + "\n您" + billCycle + "的公摊水电费共计人民币" + billAm + "元，请尽快缴费";
                        params = new String[]{makeHouseNo(house.getHouseId()),bill.getHouseOwnerName(),
                                billCycle,"公摊水电费",billAm};
                    } else {
                        content = "尊敬的" + makeHouseNo(house.getHouseId()) + "业主" + bill.getHouseOwnerName()
                                + "\n您" + billCycle + "的能耗费共计人民币" + billAm + "元，请尽快缴费";
                        params = new String[]{makeHouseNo(house.getHouseId()),bill.getHouseOwnerName(),
                                billCycle,"能耗费",billAm};
                    }
                    //发短信和通知
                    //判断是否存在户主账号
                    if (houseOwner != null && !StringUtil.isBlank(houseOwner.getUserId())) {
                        //发通知
                        //创建系统通知对象，将通知存到数据库
                        Notice notice = new Notice();
                        notice.setNoticeStatus(1);
                        notice.setNoticeType(Integer.parseInt(RemindDataValidation.PAYMENT_NOTICE_SENDNO));
                        notice.setCreateTime(new Date(System.currentTimeMillis()));
                        notice.setNoticeUserId(houseOwner.getUserId());
                        notice.setNoticeContent(content);
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("========before addNotice========notice=" + notice);
                        }
                        noticeMapper.insertSelective(notice);
                        //返回主键
                        Long noticeId = notice.getNoticeId();
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("========after addNotice========notice=" + notice);
                        }
                        SendMessage send = new SendMessage();
                        send.setSendId(noticeId.toString());
                        send.setSendTitle("缴费通知");
                        send.setSendNo(RemindDataValidation.PAYMENT_NOTICE_SENDNO);
                        send.setSendType(RemindDataValidation.SENDTYPE);
                        send.setSendContent(content);
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("========SendMessage========" + send);
                        }
                        //转json
                        String pushInfo = GsonUtil.object2Gson(send);
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("========SendMessage to json========" + pushInfo);
                        }
                        //查询用户账号
//                        UserAccount account = userAccountMapper.selectUserPhoneByUserId(houseOwner.getUserId());
                        //jianz.z 2018-1-21
                        UserAccount account = userService.findByUserId(houseOwner.getUserId(), request);
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("========findAccountByUserId========" + account);
                        }
                        if (account != null) {
                            //推送
                            MsgPushUtils.push(account.getAcctName(), pushInfo);
                            //发短信
                            String[] phones = new String[]{account.getAcctName()};
                            BaiduSmsUtil.sendSms(BaiduSmsCode.PAY_REMIND_TEMPLATE,phones,params);
                        }
                    }
                    //若户主账号不存在，则给户主预留手机号发短信
                    if (houseOwner != null && StringUtil.isBlank(houseOwner.getUserId())) {
                        //发短信
                        String[] phones = new String[]{houseOwner.getPhoneNum()};
                        BaiduSmsUtil.sendSms(BaiduSmsCode.PAY_REMIND_TEMPLATE,phones,params);
                    }
                }
            }
            if (list.size() > 0) {
                billMapper.insertList(list);
            }
            //将价格写入收费项
            ExpenseItem exItem = new ExpenseItem();
            exItem.setItemId(item.getItemId());
            exItem.setItemPrice(new BigDecimal(price));
            expenseItemMapper.updateByPrimaryKeySelective(exItem);
            //插入操作记录
            Subject subject = SecurityUtils.getSubject();
            ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
            if (null == activeUser) {
                activeUser = new ActiveUser();
                activeUser.setUserId("1");
            }
            ExpenseItemHistory eih = new ExpenseItemHistory();
            eih.setItemId(item.getItemId());//收费项目id
            eih.setAdminName(activeUser.getAdminName());//修改的管理员名称
            eih.setCreateTime(new Date());
            Integer itemType = item.getItemType();
            String typeName = "";
            if (Integer.parseInt(ExpenseStaticStatus.ITEM_TYPE_TWO) == itemType) {
                typeName = "公摊水电费";
            }
            if (Integer.parseInt(ExpenseStaticStatus.ITEM_TYPE_FIVE) == itemType) {
                typeName = "能耗费";
            }
            //判断周期是否是一个月
            if (isSameMonth(startTime, endTime)) {
                eih.setHistoryDesc("生成了" + DateUtil.toString(startTime, "yyyy年MM月")
                        + "的总金额为" + price + "元的" + typeName + "账单");
            } else {
                eih.setHistoryDesc("生成了" + DateUtil.toString(startTime, "yyyy年MM月") + "-" + DateUtil.toString(endTime, "yyyy年MM月")
                        + "总金额为" + price + "元的" + typeName + "账单");
            }
            expenseItemHistoryMapper.insertSelective(eih);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("============createBill end==============");
        }
    }

    @Override
    public List<House> findAllHouses(String estateId) {
        return houseMapper.selectAllHouse(estateId);
    }

    @Override
    public List<ExpenseBill> findByTimeAndItemId(Date startTime, Date endTime, Long itemId) {
        return billMapper.findByTimeAndItemId(startTime, endTime, itemId);
    }

    /**
     * 比较两个日期是否在同一个月
     *
     * @param time1
     * @param time2
     * @return
     */
    public boolean isSameMonth(Date time1, Date time2) {
        boolean flag = false;
        Calendar t1 = Calendar.getInstance();
        Calendar t2 = Calendar.getInstance();
        t1.setTime(time1);
        t2.setTime(time2);
        if (t1.get(Calendar.MONTH) == t2.get(Calendar.MONTH)) {
            flag = true;
        }
        return flag;
    }

    //生成房屋信息
    public String makeHouseNo(String houseId) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("=========RemindServiceImpl makeHouseNo start=========houseId=" + houseId);
        }
        House house = houseMapper.selectByPrimaryKey(houseId);
        if (LOG.isDebugEnabled()) {
            LOG.debug("=========makeHouseNo findHouseById=========house=" + house);
        }
        StringBuffer houseNo = new StringBuffer();
        if (house.getBuildingId() != null) {
            Building building = buildingMapper.selectByPrimaryKey(house.getBuildingId());
            if (building != null) {
                houseNo.append(building.getBuildingName());
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("=========makeHouseNo houseNoAppendBuildingId=========houseNo=" + houseNo);
        }
        if (house.getUnitId() != null) {
            BuildingUnit unit = unitMapper.selectByPrimaryKey(house.getUnitId());
            if (unit != null) {
                houseNo.append(unit.getUnitName());
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("=========makeHouseNo houseNoAppendUnitId=========houseNo=" + houseNo);
        }
        houseNo.append(house.getHouseNum());
        if (LOG.isDebugEnabled()) {
            LOG.debug("=========RemindServiceImpl makeHouseNo end=========houseNo" + houseNo);
        }
        return houseNo.toString();
    }

    /**
     * 计算生成账单的到期日
     *
     * @param currentCycleStartTime 当前账单周期的起始时间
     * @param chargeCycle           缴费周期
     * @return
     */
    private Date countDeadLine(Date currentCycleStartTime, Integer chargeCycle) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentCycleStartTime);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + chargeCycle - 1);
        return calendar.getTime();
    }

    /**
     * <p>
     * 标识+YYYYMMDDHHMMSS+6位自增长码(20位) 
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/9/11 9:37
     *
     * @param flag 标识
     * @return String 自增序列号
     */
    private Integer sequence = RemindDataValidation.ADDNO_SEQUENCE;
    private Integer length = RemindDataValidation.ADDNO_LENGTH;

    private synchronized String getLocalTrmSeqNum(String flag) {
        sequence = sequence >= 999999 ? 1 : sequence + 1;
        String dateTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String s = Integer.toString(sequence);
        return flag + dateTime + IdUtil.addLeftZero(s, length);
    }

    /**
     * 获取后一天的日期
     *
     * @param date
     * @return
     */
    public static Date getAfterDay(Date date) {
        //使用set方法直接进行设置
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date);
        int day = c1.get(Calendar.DATE);
        c1.set(Calendar.DATE, day + 1);
        return c1.getTime();
    }

    /**
     * 获取周期结束时间
     *
     * @param startTime 周期开始时间
     * @param itemCycle 周期月
     * @return
     */
    private Date countEndTime(Date startTime, Integer itemCycle) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("=======currentCycleStartTime start========itemCycle=" + itemCycle + ",startTime=" + startTime);
        }
        //获取下个周期的第一天
        Calendar cl = Calendar.getInstance();
        cl.setTime(startTime);
        cl.add(Calendar.MONTH, itemCycle);
        //获取当前周期结束时间
        Calendar firstDay = Calendar.getInstance();
        firstDay.setTime(cl.getTime());
        firstDay.set(Calendar.DAY_OF_MONTH, 1);
        firstDay.set(Calendar.HOUR_OF_DAY, 23);//时
        firstDay.set(Calendar.MINUTE, 59);//分
        firstDay.set(Calendar.SECOND, 59);//秒
        firstDay.set(Calendar.MILLISECOND, 0);//毫秒
        firstDay.add(Calendar.DATE, -1);
        if (LOG.isDebugEnabled()) {
            LOG.debug("=======currentCycleStartTime end========date=" + cl.getTime());
        }
        return firstDay.getTime();
    }

    private ExpenseItemHistory addOperationLogs(ExpenseItem expenseItem, String historyDec) {
        Subject subject = SecurityUtils.getSubject();
        ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
        if (null == activeUser) {
            activeUser = new ActiveUser();
            activeUser.setUserId("1");
        }
        ExpenseItemHistory eih = new ExpenseItemHistory();
        eih.setItemId(expenseItem.getItemId());//收费项目id
        eih.setAdminName(activeUser.getAdminName());//修改的管理员ID
        eih.setHistoryDesc(historyDec);
        return eih;
    }


}
