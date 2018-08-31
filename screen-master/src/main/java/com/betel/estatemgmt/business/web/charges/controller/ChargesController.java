package com.betel.estatemgmt.business.web.charges.controller;

import com.betel.estatemgmt.business.web.charges.code.ChargesCode;
import com.betel.estatemgmt.business.web.charges.constant.ChargesValidation;
import com.betel.estatemgmt.business.web.charges.model.*;
import com.betel.estatemgmt.business.web.charges.service.ChargesService;
import com.betel.estatemgmt.business.web.expenses.constant.ExpenseStaticStatus;
import com.betel.estatemgmt.common.BaseController;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.model.expense.ExpenseBill;
import com.betel.estatemgmt.common.model.expense.ExpenseItem;
import com.betel.estatemgmt.common.model.expense.ExpenseItemBuildingRela;
import com.betel.estatemgmt.common.model.house.Building;
import com.betel.estatemgmt.common.model.house.House;
import com.betel.estatemgmt.utils.DateUtil;
import com.betel.estatemgmt.utils.StringUtil;
import com.betel.estatemgmt.utils.Tool;
import com.betel.estatemgmt.utils.encrypt.AESUtil;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * <p>
 * 收费项目设置栏目
 * </p>
 * ClassName: ChargesController <br/>
 * Author: zhangjian <br/>
 * Date:  2017/9/19<br/>
 * Version: 1.0 <br/>
 */
@RestController
@RequestMapping("web/charges")
public class ChargesController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(ChargesController.class);
    @Autowired
    private ChargesService chargesService;

    /**
     * <p>
     * 查询收费项目列表（分页）
     * </p>
     * Author: zhangjian <br/>
     * Date: 2017/09/18
     *
     * @return response
     */
    @RequiresPermissions("itemSetting-findAllChargesItemSet")
    @RequestMapping(value = "v1/findAllChargesItemSet", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<Paging<ChargesItem>> findAllChargesItemSet(@RequestBody ChargesItemReq chargesItemReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/charges/v1/findAllChargesItemSet start========ChargesItemReq=" + chargesItemReq);
        }
        Response<Paging<ChargesItem>> response = new Response<Paging<ChargesItem>>();
        Paging<ChargesItem> pager = new Paging<ChargesItem>(chargesItemReq.getCurPage(), chargesItemReq.getPageSize());
        try {
            List<ChargesItem> list = chargesService.findAllChargesItemSet(pager, chargesItemReq);
            pager.result(list);
            response.setData(pager);
        } catch (Exception e) {
            LOG.error("-----------------查询收费项目列表（分页）-----------报错-----------------------------------", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/charges/v1/findAllChargesItemSet end========response" + response);
        }
        return response;
    }


    /**
     * <p>
     * 添加收费项目
     * </p>
     * Author: zhangjian <br/>
     * Date: 2017/09/18
     *
     * @return response
     */
    @RequiresPermissions("itemSetting-addChargesItem")
    @RequestMapping(value = "v1/addChargesItem", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<Object> addChargesItem(@RequestBody ChargesItemReq chargesItemReq,HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/charges/v1/addChargesItem start========ChargesItemReq=" + chargesItemReq);
        }
        Response<Object> response = new Response<Object>();
        try {
            String estateId = AESUtil.decrypt(request.getHeader("estateId"));
            //校验前端参数
            String code = chargesService.checkParamter(chargesItemReq, 1);
            if (!"".equals(code) && null != code) {
                response.setCode(code);
                return response;
            }
            chargesItemReq = paramQKG(chargesItemReq);
            if (LOG.isDebugEnabled()) {
                LOG.debug("------------------------参数chargesItemReq去空格后:" + chargesItemReq);
            }
            code = chargesService.isItemNameReuse(chargesItemReq);
            if (LOG.isDebugEnabled()) {
                LOG.debug("------------------------校验收费项目名称" + chargesItemReq.getItemName() + "是否重复:code=" + code + "");
            }
            if (!"".equals(code) && null != code) {
                response.setCode(code);
                return response;
            }
            Integer itemType = Integer.parseInt(chargesItemReq.getItemType());
            //若添加的是能耗费或公摊水电费，判断是否已经有一条数据
            if (Integer.parseInt(ExpenseStaticStatus.ITEM_TYPE_FIVE) == itemType) {
                List<ExpenseItem> list = chargesService.findByITemtType(itemType);
                if (list != null && list.size() > 0) {
                    response.setCode(ChargesCode.ENERGY_MAX_ONE);
                    return response;
                }
            }
            if (Integer.parseInt(ExpenseStaticStatus.ITEM_TYPE_TWO) == itemType) {
                List<ExpenseItem> list = chargesService.findByITemtType(itemType);
                if (list != null && list.size() > 0) {
                    response.setCode(ChargesCode.SHARE_MAX_ONE);
                    return response;
                }
            }
            chargesService.addChargesItem(chargesItemReq,estateId);
        } catch (Exception e) {
            LOG.error("--------------添加收费项目报错----------------------", e);
            response.setCode(StatusCode.FAILURE);
        }

        if (LOG.isInfoEnabled()) {
            LOG.info("========web/charges/v1/addChargesItem end========response" + response);
        }
        return response;
    }

    /**
     * 参数去空格
     *
     * @param chargesItemReq
     * @return
     */
    private ChargesItemReq paramQKG(ChargesItemReq chargesItemReq) {
        if (!StringUtil.isEmpty(chargesItemReq.getItemName())) {
            chargesItemReq.setItemName(chargesItemReq.getItemName().trim());
        }
        if (!StringUtil.isEmpty(chargesItemReq.getChargeType())) {
            chargesItemReq.setChargeType(chargesItemReq.getChargeType().trim());
        }
        if (!StringUtil.isEmpty(chargesItemReq.getItemPrice())) {
            chargesItemReq.setItemPrice(chargesItemReq.getItemPrice().trim());
        }
        if (!StringUtil.isEmpty(chargesItemReq.getItemCycle())) {
            chargesItemReq.setItemCycle(chargesItemReq.getItemCycle().trim());
        }
        if (!StringUtil.isEmpty(chargesItemReq.getOverdueFine())) {
            chargesItemReq.setOverdueFine(chargesItemReq.getOverdueFine().trim());
        }
        if (!StringUtil.isEmpty(chargesItemReq.getChargeCycle())) {
            chargesItemReq.setChargeCycle(chargesItemReq.getChargeCycle().trim());
        }
        if (!StringUtil.isEmpty(chargesItemReq.getItemType())) {
            chargesItemReq.setItemType(chargesItemReq.getItemType().trim());
        }
        return chargesItemReq;
    }


    /**
     * <p>
     * 查询收费项目（修改收费项目时查询）
     * </p>
     *
     * @param chargesItemReq Author: zhangjian <br/>
     *                       Date: 2017/6/21 13:44
     * @return response
     */
    @RequiresPermissions(value = "itemSetting-findChargesItemDatail")
    @RequestMapping(value = "v1/findChargesItemDatail", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<Object> findChargesItemDatail(@RequestBody ChargesItemReq chargesItemReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======web/charges/v1/findChargesItemDatail start========chargesItemReq:=" + chargesItemReq);
        }
        Response<Object> response = new Response<Object>();
        //判断id是否为空
        String code = "";
        if (StringUtil.isBlank(chargesItemReq.getItemId())) {
            code = ChargesCode.ITEM_ID_IS_NULL;
            response.setCode(code);
        } else {
            chargesItemReq.setItemId(chargesItemReq.getItemId().trim());
        }
        try {
            ChargesItem chargesItem = chargesService.findChargesItemDatail(chargesItemReq);
            if (LOG.isDebugEnabled()) {
                LOG.debug("-------------------------判断是收费项目是否存在：chargesItem------------" + chargesItem);
            }
            if (null == chargesItem) {
                code = ChargesCode.ITEM_IS_NOT_EXIST;
                response.setCode(code);
                return response;
            } else {
                response.setData(chargesItem);
            }

        } catch (Exception e) {
            LOG.error("------------------判断是收费项目是否存在----报错---------", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("======web/charges/v1/findChargesItemDatail end========");
        }
        return response;
    }


    /**
     * <p>
     * 修改收费项目详情
     * </p>
     *
     * @param chargesItemReq 修改对象参数
     *                       Author: zhangjian <br/>
     *                       Date: 2017/09/19 13:44
     * @return response
     */
    @RequiresPermissions(value = "itemSetting-updateChargesItem")
    @RequestMapping(value = "v1/updateChargesItem", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<Object> updateChargesItem(@RequestBody ChargesItemReq chargesItemReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======web/charges/v1/updateChargesItem start========chargesItemReq:=" + chargesItemReq);
        }
        Response<Object> response = new Response<Object>();
        try {
            //校验前端参数
            String code = chargesService.checkParamter(chargesItemReq, 2);
            if (!"".equals(code) && null != code) {
                response.setCode(code);
                return response;
            }
            ChargesItem chargesItem = chargesService.findChargesItemDatail(chargesItemReq);
            if (LOG.isDebugEnabled()) {
                LOG.debug("------------------修改前判断收费项目chargesItem是否存在-------------------------" + chargesItem);
            }
            if (null == chargesItem) {
                code = ChargesCode.ITEM_IS_NOT_EXIST;
                response.setCode(code);
                return response;
            }
            chargesItemReq = paramQKG(chargesItemReq);
            if (LOG.isDebugEnabled()) {
                LOG.debug("------------------------参数chargesItemReq去空格后:" + chargesItemReq);
            }

            int flag = chargesService.updateChargesItem(chargesItemReq);
            if (LOG.isDebugEnabled()) {
                LOG.debug("-----------------------执行修改收费项目，成功的条数：" + flag);
            }
        } catch (Exception e) {
            LOG.error("------------------执行修改收费项目----报错---------", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("=========web/charges/v1/updateChargesItem end!=========response" + response);
        }
        return response;
    }


    /**
     * <p>
     * 删除收费项目（批量）
     * </p>
     * Author: zhangjian <br/>
     * Date: 2017/6/21 13:44
     *
     * @return response
     */
    @RequiresPermissions(value = "itemSetting-deleteChargesItem")
    @RequestMapping(value = "v1/deleteChargesItem", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<Object> deleteChargesItem(@RequestBody ChargesItemReq chargesItemReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======web/charges/v1/deleteChargesItem start========chargesItemReq:=" + chargesItemReq);
        }
        Response<Object> response = new Response<Object>();
        try {
            //判断id是否为空
            String ids = chargesItemReq.getItemIds();
            String code = Tool.checkIdIsNull(ids);
            if (null == code || "".equals(code)) {
                response.setCode(ChargesCode.ITEM_ID_IS_NULL);
                return response;
            }
            List<Long> itemIdLongList = Tool.getIdList(chargesItemReq.getItemIds());//选中的
            if (LOG.isDebugEnabled()) {
                LOG.debug("------------------------------判收费项目下是否有账单存在");
            }
            code = chargesService.isHasItemApplictBill(itemIdLongList);
            if (null != code && !"".equals(code)) {
                response.setCode(code);
                return response;
            }
            //--------------删除的时候判断项目是否存在不需要
            //执行批量删除
            int flag = chargesService.deleteChargesItem(itemIdLongList);
            if (LOG.isDebugEnabled()) {
                LOG.debug("--------------------删除收费项目成功条数------------------------" + flag);
            }
        } catch (Exception e) {
            LOG.error("------------------删除收费项目-------报错", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("=========web/charges/v1/deleteChargesItem end!=========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 查询收费标准
     * </p>
     * Author: zhangjian <br/>
     * Date: 2017/9/19 13:44
     *
     * @return response
     */
    @RequiresPermissions(value = "itemSetting-findChargesItemStandard")
    @RequestMapping(value = "v1/findChargesItemStandard", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<Object> findChargesItemStandard(HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======web/charges/v1/findChargesItemStandard start========");
        }
        Response<Object> response = new Response<Object>();
        try {
            String estateId = AESUtil.decrypt(request.getHeader("estateId"));
            ChargesItem chargesItem = chargesService.findChargesItemStandard(estateId);
            response.setData(chargesItem);
            if (LOG.isDebugEnabled()) {
                LOG.debug("-------------------查询收费标准-------------chargesItem-----------------------------" + chargesItem);
            }
        } catch (Exception e) {
            LOG.error("---------------------查询收费标准--报错----", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("======web/charges/v1/findChargesItemStandard end========response==" + response);
        }
        return response;
    }

    /**
     * <p>
     * 修改收费项目标准
     * </p>
     *
     * @param chargesItemReq 修改对象参数
     *                       Author: zhangjian <br/>
     *                       Date: 2017/09/19 13:44
     * @return response
     */
    @RequiresPermissions(value = "itemSetting-updateChargesItemStandard")
    @RequestMapping(value = "v1/updateChargesItemStandard", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<Object> updateChargesItemStandard(@RequestBody ChargesItemReq chargesItemReq,HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======web/charges/v1/updateChargesItemStandard start========chargesItemReq:=" + chargesItemReq);
        }
        Response<Object> response = new Response<Object>();
        try {
            //校验前端参数
            if (!StringUtil.isBlank(chargesItemReq.getStandard())) {
                if (chargesItemReq.getStandard().length() > ChargesValidation.STANDARD_LENGTH) {
                    response.setCode(ChargesCode.RATE_STANDARD_LENGTH_IS_NOER_THAN_FIVE_QIAN);
                    return response;
                }
            }
            chargesItemReq.setEstateId(AESUtil.decrypt(request.getHeader("estateId")));
            int flag = chargesService.updateChargesItemStandard(chargesItemReq);
            if (LOG.isDebugEnabled()) {
                LOG.debug("-----------------------------------------------收费标准，修改执行成功条数：" + flag + "");
            }
        } catch (Exception e) {
            LOG.error("-----------------------------------------修改收费标准---------报错--------------", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("=========web/charges/v1/updateChargesItem end!=========response" + response);
        }
        return response;
    }


    /**
     * <p>
     * 筛选楼宇列表
     * </p>
     *
     * @param chargesItemReq Author: zhangjian <br/>
     *                       Date: 2017/9/21 13:44
     * @return response
     */
    @RequiresPermissions(value = "itemSetting-filtrateBuilding")
    @RequestMapping(value = "v1/filtrateBuilding", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<Object> filtrateBuilding(@RequestBody ChargesItemReq chargesItemReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======web/charges/v1/filtrateBuilding start========chargesItemReq:=" + chargesItemReq);
        }
        Response<Object> response = new Response<Object>();
        try {
            //判断id是否为空
            String code = "";
            if (StringUtil.isBlank(chargesItemReq.getItemId())) {
                code = ChargesCode.ITEM_ID_IS_NULL;
                response.setCode(code);
            } else {
                chargesItemReq.setItemId(chargesItemReq.getItemId().trim());
            }

            ChargesItem chargesItem = chargesService.findChargesItemDatail(chargesItemReq);
            if (LOG.isDebugEnabled()) {
                LOG.debug("-------------------------激活收费项筛选楼宇时判断项目是否存在：chargesItem：" + chargesItem);
            }
            if (null == chargesItem) {
                code = ChargesCode.ITEM_IS_NOT_EXIST;
                response.setCode(code);
                return response;
            } else {
                //---------------------全部的楼宇和别墅-------------------------------------
                List<Building> buildings = chargesService.findAllHouseBuilding();
                //-----------------------这是屏蔽的楼宇和别墅------------------------------------------
                List<Building> itemBuilds = chargesService.findfiltrateBuilding(chargesItem.getItemId());
                List<Building> filtrateBuildings = new ArrayList<>();
                for (int i = 0; i < itemBuilds.size(); i++) {
                    Building building = new Building();
                    //--------------拼接楼宇名称，id----------------------------------------
                    building.setBuildingId(Long.valueOf(itemBuilds.get(i).getBuildingId()));
                    building.setBuildingName(itemBuilds.get(i).getBuildingName());
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("-----------需屏蔽楼宇id" + Long.valueOf(itemBuilds.get(i).getBuildingId()));
                    }
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("-----------需屏蔽楼宇name" + itemBuilds.get(i).getBuildingName());
                    }
                    filtrateBuildings.add(building);
                }
                HouseBuildings houseBuildings = new HouseBuildings();
                houseBuildings.setBuildingIds(buildings);
                houseBuildings.setFiltrateBuildingIds(filtrateBuildings);
                response.setData(houseBuildings);
            }
        } catch (Exception e) {
            LOG.error("-----------------------------查询所有楼宇筛选屏蔽已经应用的楼宇--------报错--------------", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("======web/charges/v1/filtrateBuilding end========");
        }
        return response;
    }


    /**
     * <p>
     * 激活收费项目应用到所有楼宇
     * </p>
     *
     * @param chargesItemReq Author: zhangjian <br/>
     *                       Date: 2017/9/21 13:44
     * @return response
     */
    @RequiresPermissions(value = "itemSetting-actionChargesItemToAllBuildings")
    @RequestMapping(value = "v1/actionChargesItemToAllBuildings", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<Object> actionChargesItemToAllBuildings(@RequestBody ChargesItemReq chargesItemReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======web/charges/v1/actionChargesItemToAllBuildings start========chargesItemReq:=" + chargesItemReq);
        }
        Response<Object> response = new Response<Object>();
        //判断id是否为空
        String code = "";
        ChargesItem chargesItem = null;
        try {
            code = chargesService.testWebParamterIsLegal(chargesItemReq);
            if (LOG.isDebugEnabled()) {
                LOG.debug("-----------判断计费时间是否合法code--------" + code);
            }
            if (code != null && !"".equals(code)) {
                response.setCode(code);
                return response;
            }
            chargesItem = chargesService.findChargesItemDatail(chargesItemReq);
            if (LOG.isDebugEnabled()) {
                LOG.debug("---------------激活收费项目时查询项目是否存在，取得对象chargesItem------------------" + chargesItem);
            }
            if (StringUtil.isBlank(chargesItemReq.getItemId())) {
                code = ChargesCode.ITEM_ID_IS_NULL;
                response.setCode(code);
            } else {
                chargesItemReq.setItemId(chargesItemReq.getItemId().trim());
            }
        } catch (Exception e) {
            LOG.error("-------------------校验前端参数和判断激活前收费项目是否存在----------报错");
            response.setCode(StatusCode.FAILURE);
            return response;
        }
        if (null == chargesItem) {
            code = ChargesCode.ITEM_IS_NOT_EXIST;
            response.setCode(code);
            return response;
        } else {
            //-------------------------------该选中收费项目已经应用楼宇，再次应用报-------------------------------------
            try {
                List<ExpenseItemBuildingRela> eibra = chargesService.selectItemBuildingRelaByItemId(chargesItemReq.getItemId());
                if (eibra.size() > 0) {
                    code = ChargesCode.ITEM_HAS_APPLIED_TO_THE_BULIDINGS;
                    response.setCode(code);
                    return response;
                }
            } catch (Exception e) {
                LOG.error("---------点击应用全部楼宇的时候后在点击应用所有楼宇报错：该收费项目已经应用了所有的楼宇不能再应用--------查询报错-------------", e);
                response.setCode(StatusCode.FAILURE);
                return response;
            }

            try {
                //--------------该小区的全部楼宇已有部分楼宇被应用该类型的收费标准，不能选择应用全部楼宇--------------------
                try {
                    List<ExpenseItemBuildingRela> itemBuilds = chargesService.findBuildApplicateAssignTypeItemByItemId(chargesItemReq.getItemId());
                    if (itemBuilds.size() > 0) {
                        code = ChargesCode.BUILDING_HAS_APPLIED_STANDARD_OF_SPECIFIED_TYPE;
                        response.setCode(code);
                        return response;
                    }
                } catch (Exception e) {
                    LOG.error("---------该小区的全部楼宇已有部分楼宇被应用该类型的收费标准，不能选择应用全部楼宇--------查询报错-------------", e);
                    response.setCode(StatusCode.FAILURE);
                    return response;
                }
                //----------------------激活--------------开始-------------
                List<Building> buildings = chargesService.findAllHouseBuilding();//查询所有的楼宇和别墅
                //判断楼宇和房屋是否全被删除--xyx
                if (buildings == null || (buildings != null && buildings.size() == 0)) {
                    response.setCode(ChargesCode.ALL_BUILDING_DELETE);
                    return response;
                }
                //---------------------获得所有的楼宇id,组合插入数据库对象------------------------------
                List<ExpenseItemBuildingRela> eibrList = new ArrayList<>();
                for (int i = 0; i < buildings.size(); i++) {
                    ExpenseItemBuildingRela expenseItemBuildingRela = new ExpenseItemBuildingRela();
                    String buildingId = buildings.get(i).getBuildingId().toString();
                    Long buildId = Long.valueOf(buildingId);
                    if ("0".equals(buildingId)) {
                        expenseItemBuildingRela.setBuildingId(null);
                    } else {
                        expenseItemBuildingRela.setBuildingId(buildId);
                    }
                    expenseItemBuildingRela.setItemId(Long.valueOf(chargesItemReq.getItemId()));
                    expenseItemBuildingRela.setCreateTime(new Date());
                    eibrList.add(expenseItemBuildingRela);
                }
                int flag = chargesService.actionChargesItemToAllBuildings(eibrList);
                int flag2 = chargesService.updateItemJHtime(chargesItemReq);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("------------------------激活成功，更新项目激活时间-------------数量---------------" + flag2);
                    LOG.debug("------------------------激活成功，更新项目激活时间为：" + chargesItemReq.getItemActionTime());
                }
                //---------------------------插入操作记录----------------------------
                int flag1 = chargesService.addOperationLogsOfactionItem(chargesItemReq);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("------------------------激活成功----------------------------" + flag);
                    LOG.debug("--------------------------------------插入操作记录成功条数" + flag1);
                }
            } catch (Exception e) {
                LOG.error("---------激活收费项目插入收费项目和楼宇关系表和更新收费项目激活时间-----报错-------------", e);
                response.setCode(StatusCode.FAILURE);
                return response;
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("======web/charges/v1/actionChargesItemToAllBuildings end========");
        }
        return response;
    }

    /**
     * <p>
     * 激活收费项目应用到指定楼宇
     * </p>
     *
     * @param chargesItemReq Author: zhangjian <br/>
     *                       Date: 2017/09/21 13:44
     * @return response
     */
    @RequiresPermissions(value = "itemSetting-actionChargesItemToPartBuildings")
    @RequestMapping(value = "v1/actionChargesItemToPartBuildings", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<Object> actionChargesItemToPartBuildings(@RequestBody ChargesItemReq chargesItemReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======web/charges/v1/actionChargesItemToPartBuildings start========chargesItemReq:=" + chargesItemReq);
        }
        Response<Object> response = new Response<Object>();
        try {
            //==============判断收费项目id是否为空================
            String code = "";
            if (StringUtil.isBlank(chargesItemReq.getItemId())) {
                code = ChargesCode.ITEM_ID_IS_NULL;
                response.setCode(code);
            } else {
                chargesItemReq.setItemId(chargesItemReq.getItemId().trim());
            }
            //============批量判断楼宇id是否为空===================
            String ids = chargesItemReq.getBuildingIds();
            code = Tool.checkIdIsNull(ids);
            if (null == code || "".equals(code)) {
                response.setCode(ChargesCode.BUILDING_ID_IS_NULL);
                return response;
            }

            //=============判断计费时间是否合法=====================
            code = chargesService.testWebParamterIsLegal(chargesItemReq);
            if (code != null && !"".equals(code)) {
                response.setCode(code);
                return response;
            }
            //=========激活操作前判断收费项目是否被删除==============
            ChargesItem chargesItem = chargesService.findChargesItemDatail(chargesItemReq);
            if (null == chargesItem) {
                code = ChargesCode.ITEM_IS_NOT_EXIST;
                response.setCode(code);
                return response;
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("======激活操作前判断收费项目是否被删除，对象chargesItem=====" + chargesItem);
            }
            //=========判断该选中的收费项目是否已经应用了楼宇，如果应用了，不能再次应用且只能一次=======================
            List<ExpenseItemBuildingRela> eibra = chargesService.selectItemBuildingRelaByItemId(chargesItemReq.getItemId());
            if (eibra.size() > 0) {
                code = ChargesCode.ITEM_HAS_APPLIED_TO_THE_BULIDINGS;
                response.setCode(code);
                return response;
            }
            //========================判断选中的楼宇和别墅是否被删除=================================
            List<Long> idsLong = Tool.getIdList(chargesItemReq.getBuildingIds());
            List<Object> buildingList = chargesService.findBatchBuilding(idsLong);
            if (idsLong.size() > 1) {
                if (idsLong.size() > buildingList.size()) {
                    code = ChargesCode.CHOOSE_BUILDING_HAS_PART_OF_DELETE;
                    response.setCode(code);
                    return response;
                }
            } else if (idsLong.size() == 1 && buildingList.size() == 0) {
                code = ChargesCode.BUILDING_IS_NOT_EXIST;
                response.setCode(code);
                return response;
            }
            //===================判断选中的楼宇和别墅是否已经应用该选中的类型的收费项目,再次应用报错（重复）====开始=========================
            ItemBuildReq itemBuildReq = new ItemBuildReq();
            itemBuildReq.setBuildingIds(idsLong);
            itemBuildReq.setItemId(chargesItemReq.getItemId());
            List<ExpenseItemBuildingRela> itemBuilds = chargesService.findExpenseItemnBuildingRelaByBuildingIds(itemBuildReq);
            if (itemBuilds.size() > 0) {
                code = ChargesCode.BUILDING_HAS_APPLIED_STANDARD_OF_SPECIFIED_TYPE;
                response.setCode(code);
                return response;
            }
            List<ExpenseItemBuildingRela> eibrList = new ArrayList<>();
            for (int i = 0; i < idsLong.size(); i++) {
                ExpenseItemBuildingRela expenseItemBuildingRela = new ExpenseItemBuildingRela();
                Long buildingId = idsLong.get(i);
                if (0 == (buildingId)) {
                    expenseItemBuildingRela.setBuildingId(null);
                } else {
                    expenseItemBuildingRela.setBuildingId(buildingId);
                }
                expenseItemBuildingRela.setItemId(Long.valueOf(chargesItemReq.getItemId()));
                expenseItemBuildingRela.setCreateTime(new Date());
                eibrList.add(expenseItemBuildingRela);
            }
            int flag = chargesService.actionChargesItemToAllBuildings(eibrList);
            int flag2 = chargesService.updateItemJHtime(chargesItemReq);
            if (LOG.isDebugEnabled()) {
                LOG.debug("------------------------激活成功，更新项目激活时间-------------数量---------------" + flag2);
                LOG.debug("------------------------激活成功，更新项目激活时间----------------------------" + chargesItemReq.getItemActionTime());
            }
            //======================插入操作记录====================================
            //=================查询收费项目是否存在=====给如参数赋值================
            chargesItemReq.setItemType(chargesItem.getItemType());
            chargesItemReq.setItemName(chargesItem.getItemName());
            int flag1 = chargesService.addOperationLogsOfactionItem(chargesItemReq);
            if (LOG.isDebugEnabled()) {
                LOG.debug("----------------激活成功----------------------------------" + flag);
                LOG.debug("-------------------------------------插入操作记录成功条数" + flag1);
            }
        } catch (Exception e) {
            LOG.error("------------------激活到指定楼宇报错--------------------------", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("======web/charges/v1/actionChargesItemToPartBuildings end========");
        }
        return response;
    }

    /**
     * <p>
     * 查询收费项目操作记录列表（分页）
     * </p>
     * Author: zhangjian <br/>
     * Date: 2017/09/18
     *
     * @return response
     */
    @RequiresPermissions("itemSetting-findAllOperationLogs")
    @RequestMapping(value = "v1/findAllOperationLogs", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<Paging<ExpenseItemHistoryInfo>> findAllOperationLogs(@RequestBody ChargesItemReq chargesItemReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/charges/v1/findAllOperationLogs start=========");
        }
        Response<Paging<ExpenseItemHistoryInfo>> response = new Response<Paging<ExpenseItemHistoryInfo>>();
        Paging<ExpenseItemHistoryInfo> pager = new Paging<ExpenseItemHistoryInfo>(chargesItemReq.getCurPage(), chargesItemReq.getPageSize());
        try {
            List<ExpenseItemHistoryInfo> list = chargesService.findAllOperationLogs(pager, chargesItemReq);
            pager.result(list);
            response.setData(pager);
        } catch (Exception e) {
            LOG.error("----------查询收费项目列表（分页）-----报错----------", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/charges/v1/findAllOperationLogs end========response" + response);
        }
        return response;
    }


    /**
     * <p>
     * 计算出账周期
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/10/19 16:49
     *
     * @param timeReq
     * @return response
     */
    @RequiresPermissions(value = "itemSetting-countBillTime")
    @RequestMapping(value = "v1/countBillTime", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<CountBillTimeResp> countBillTime(@RequestBody CountBillTimeReq timeReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/charges/v1/countBillTime start========CountBillTimeReq=" + timeReq);
        }
        Response<CountBillTimeResp> response = new Response<>();
        //判断编号是否为空
        if (StringUtil.isBlank(timeReq.getItemId())) {
            response.setCode(ChargesCode.ITEM_ID_IS_NULL);
        } else {
            try {
                ExpenseItem item = chargesService.findByItemId(Long.parseLong(timeReq.getItemId()));
                if (item == null) {
                    response.setCode(ChargesCode.ITEM_IS_NOT_EXIST);
                } else {
                    //计算周期
                    response.setData(chargesService.countBillTime(item));
                }
            } catch (Exception e) {
                LOG.error("========web/charges/v1/countBillTime error========error", e);
                response.setCode(StatusCode.FAILURE);
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/charges/v1/countBillTime end========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 出账
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/10/19 17:57
     *
     * @param billReq
     * @return response
     */
    @RequiresPermissions(value = "itemSetting-createBill")
    @RequestMapping(value = "v1/createBill", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> createBill(@RequestBody CreateBillReq billReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/charges/v1/createBill start========billReq=" + billReq);
        }
        Response<String> response = new Response<>();
        //判断编号是否为空
        if (StringUtil.isBlank(billReq.getItemId())) {
            response.setCode(ChargesCode.ITEM_ID_IS_NULL);
        } else if (StringUtil.isBlank(billReq.getStartTime()) || StringUtil.isBlank(billReq.getEndTime())) {
            response.setCode(ChargesCode.TIME_NULL_ERROR);
        } else if (StringUtil.isBlank(billReq.getPrice())) {
            response.setCode(ChargesCode.PRICE_IS_NULL);
        } else if (!billReq.getPrice().trim().matches(ChargesValidation.OTHER_PRICE_RULR)) {
            response.setCode(ChargesCode.ITEM_PRICE_IS_NOT_LEGAL);
        } else {
            try {
                String estateId = AESUtil.decrypt(request.getHeader("estateId"));
                synchronized (this) {
                    Double price = Double.parseDouble(billReq.getPrice());
                    //判断收费项是否存在
                    ExpenseItem item = chargesService.findByItemId(Long.parseLong(billReq.getItemId()));
                    if (item == null) {
                        response.setCode(ChargesCode.ITEM_IS_NOT_EXIST);
                        return response;
                    }
                    //判断房屋是否全被删除
                    List<House> allHouses = chargesService.findAllHouses(estateId);
                    if (allHouses == null || (allHouses != null && allHouses.size() == 0)) {
                        response.setCode(ChargesCode.ALL_HOUSE_DELETE);
                        return response;
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    //判断是否到出账时间
                    if (validateTimeDistance(billReq.getStartTime(), sdf.format(new Date()))
                            && validateTimeDistance(sdf.format(new Date()), billReq.getEndTime())) {
                        response.setCode(ChargesCode.CREATETIME_NOT_ARRIVE);
                        return response;
                    }
                    //判断该时间段的账单是否被生成过
                    List<ExpenseBill> bills = chargesService.findByTimeAndItemId(DateUtil.toDate(billReq.getStartTime(), "yyyy-MM-dd")
                            , DateUtil.toDate(billReq.getEndTime(), "yyyy-MM-dd"), item.getItemId());
                    if (bills != null && bills.size() > 0) {
                        response.setCode(ChargesCode.EXIST_BILL);
                        return response;
                    }
                    //出账
                    chargesService.createBill(
                            price, sdf.parse(billReq.getStartTime()), sdf.parse(billReq.getEndTime()), item,request);
                }
            } catch (Exception e) {
                LOG.error("========web/charges/v1/createBill error========error", e);
                response.setCode(StatusCode.FAILURE);
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/charges/v1/createBill end========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 校验时间差
     * </p>
     * Author: xiayanxin <br/>
     * Date: 2017/8/1 16:29
     *
     * @param startTime,endTime 入参
     * @return flag
     */
    private boolean validateTimeDistance(String startTime, String endTime) {
        boolean flag = true;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            long time1 = format.parse(startTime).getTime();
            long time2 = format.parse(endTime).getTime();
            if (time1 > time2) {
                flag = false;
            }
        } catch (ParseException e) {
        }
        return flag;
    }
}
