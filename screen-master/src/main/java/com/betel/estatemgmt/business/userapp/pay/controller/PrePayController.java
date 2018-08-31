package com.betel.estatemgmt.business.userapp.pay.controller;

import com.betel.estatemgmt.business.web.expenses.code.ExpensesCode;
import com.betel.estatemgmt.business.web.expenses.constant.ExpenseStaticStatus;
import com.betel.estatemgmt.business.web.expenses.model.*;
import com.betel.estatemgmt.business.web.expenses.service.ExpensesService;
import com.betel.estatemgmt.business.web.house.constant.HouseDataValidation;
import com.betel.estatemgmt.business.web.house.service.HouseService;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.model.expense.ExpenseItem;
import com.betel.estatemgmt.common.model.expense.ExpenseItemBuildingRela;
import com.betel.estatemgmt.common.model.house.House;
import com.betel.estatemgmt.utils.DateUtil;
import com.betel.estatemgmt.utils.StringUtil;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 预缴接口
 * </p>
 * ClassName: PrePayController <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/10/18 19:12 <br/>
 * Version: 1.0 <br/>
 */
@RestController
@RequestMapping("userApp/payment")
public class PrePayController {
    private static final Logger LOG = LoggerFactory.getLogger(PrePayController.class);

    @Autowired
    private HouseService houseService;

    @Autowired
    private ExpensesService expensesService;

    /**
     * <p>
     * 查询物业费/停车费的计费周期
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/10/13 17:20
     * @param findItemCycleReq
     * @return response
     *
     * */
    @RequestMapping(value = "v1/findItemCycle", method = RequestMethod.GET)
    public Response<List<FindItemCycleResp>> findItemCycle(FindItemCycleReq findItemCycleReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========app/paymentManage/v1/findItemCycle start========findItemCycleReq" + findItemCycleReq);
        }
        Response<List<FindItemCycleResp>> response = new Response<>();
        //校验
        if (StringUtil.isBlank(findItemCycleReq.getHouseId())){
            response.setCode(ExpensesCode.HOUSE_ID_NULL);
        } else if (StringUtil.isBlank(findItemCycleReq.getItemType())){
            response.setCode(ExpensesCode.ITEM_TYPE_NULL);
        } else {
            try {
                String houseId = findItemCycleReq.getHouseId();
                String[] itemTypes = findItemCycleReq.getItemType().split(",");
                House house = houseService.findByHouseId(houseId);
                //判断房屋是否存在
                if (house==null){
                    response.setCode(ExpensesCode.HOUSE_IS_DELETE);
                    return response;
                }
                List<ExpenseItem> itemList = new ArrayList<>();
                for (String s:
                        itemTypes) {
                    //获取收费项类型
                    Integer itemType = Integer.parseInt(s);
                    //判断该类型的收费项是否存在
                    List<ExpenseItem> items = expensesService.findByItemType(itemType);
                    if (items != null && items.size() > 0){
                        for (ExpenseItem i :
                                items) {
                            ExpenseItemBuildingRela rela = expensesService.findByItemIdAndBuildingId(i.getItemId(), house.getBuildingId());
                            //判断该收费项是否与该房屋绑定
                            if (rela != null) {
                                itemList.add(i);
                            }
                        }
                    }
                }
                //判断该房屋是否存在收费项
                if (itemList.size()==0){
                    response.setCode(ExpensesCode.HOUSE_NOTRELA_ITEM);
                    return response;
                }
                //查询周期
                List<FindItemCycleResp> resp = expensesService.findItemCycle(house,itemList);
                response.setData(resp);
            } catch (Exception e) {
                LOG.error("========app/paymentManage/v1/findItemCycle error========", e);
                response.setCode(StatusCode.FAILURE);
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========app/paymentManage/v1/findItemCycle end========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 计算预缴合计
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/10/16 16:19
     * @param findCountReq
     * @return response
     *
     * */
    @RequestMapping(value = "v1/findTotalAmount", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<FindCountResp> findTotalAmount(@RequestBody FindCountReq findCountReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========app/paymentManage/v1/findTotalAmount start========findCountReq" + findCountReq);
        }
        Response<FindCountResp> response = new Response<>();
        //校验
        if (StringUtil.isBlank(findCountReq.getItemId())){
            response.setCode(ExpensesCode.EXPENSE_ID_NULL);
        } else if (StringUtil.isBlank(findCountReq.getMonths())){
            response.setCode(ExpensesCode.MONTHS_NULL);
        } else if (StringUtil.isBlank(findCountReq.getHouseId())){
            response.setCode(ExpensesCode.HOUSE_ID_NULL);
        } else {
            try {
                //计算合计
                Double total = 0.00;
                String houseId = findCountReq.getHouseId();
                House house = houseService.findByHouseId(houseId);
                //判断房屋是否存在
                if (house==null){
                    response.setCode(ExpensesCode.HOUSE_IS_DELETE);
                    return response;
                }
                String[] itemIds = findCountReq.getItemId().split(",");
                String[] months = findCountReq.getMonths().split(",");
                for (int i = 0; i < itemIds.length; i++) {
                    Long itemId = Long.parseLong(itemIds[i]);
                    Integer month = Integer.parseInt(months[i]);
                    ExpenseItem item = expensesService.findByItemId(itemId);
                    //判断收费项是否存在
                    if (item == null){
                        response.setCode(ExpensesCode.EXPENSE_ITEM_DELETE);
                        return response;
                    }
                    //计算停车位
                    Integer buyCounts = expensesService.findSpacesByHouseIdAndType(houseId, HouseDataValidation.BUY_PARKING_SPACE);
                    Integer rentCounts = expensesService.findSpacesByHouseIdAndType(houseId, HouseDataValidation.RENT_PARKING_SPACE);
                    //计算合计
                    Double totalAmount = expensesService.countTotalAmount(house.getFloorArea(),item.getItemPrice()
                            ,month,item.getItemType(),buyCounts,rentCounts);
                    total += totalAmount;

                }
                //四舍五入，普通计数法
                String billAmount = String.format("%.2f",total);
                BigDecimal a = new BigDecimal(billAmount);
                billAmount = a.toPlainString();
                FindCountResp count = new FindCountResp();
                count.setTotalAmount(billAmount.toString());
                response.setData(count);
            } catch (Exception e) {
                LOG.error("========app/paymentManage/v1/findItemCycle error========", e);
                response.setCode(StatusCode.FAILURE);
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========app/paymentManage/v1/findItemCycle end========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 预缴
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/10/17 10:06
     * @param findCountReq
     * @return response
     *
     * */
    @RequestMapping(value = "v1/prepay", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<List<String>> prepay(@RequestBody FindCountReq findCountReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========app/paymentManage/v1/prepay start========findCountReq" + findCountReq);
        }
        Response<List<String>> response = new Response<>();
        //校验
        if (StringUtil.isBlank(findCountReq.getItemId())){
            response.setCode(ExpensesCode.EXPENSE_ID_NULL);
        } else if (StringUtil.isBlank(findCountReq.getMonths())){
            response.setCode(ExpensesCode.MONTHS_NULL);
        } else if (StringUtil.isBlank(findCountReq.getHouseId())){
            response.setCode(ExpensesCode.HOUSE_ID_NULL);
        } else {
            try {
                String houseId = findCountReq.getHouseId();
                House house = houseService.findByHouseId(houseId);
                //判断房屋是否存在
                if (house==null){
                    response.setCode(ExpensesCode.HOUSE_IS_DELETE);
                    return response;
                }
                String buildingId = findCountReq.getBuildingId();
                //判断该房屋的楼宇是否变换过
                if (!StringUtil.isBlank(buildingId)){
                    if (house.getBuildingId()!=null){
                        if (Long.parseLong(buildingId)!=house.getBuildingId()){
                            response.setCode(ExpensesCode.HOUSE_IS_CHANGE);
                            return response;
                        }
                    }else {
                        response.setCode(ExpensesCode.HOUSE_IS_CHANGE);
                        return response;
                    }
                }else {
                    if (house.getBuildingId()!=null){
                        response.setCode(ExpensesCode.HOUSE_IS_CHANGE);
                        return response;
                    }
                }
                String[] itemIds = findCountReq.getItemId().split(",");
                String[] months = findCountReq.getMonths().split(",");
                Integer buyCounts = 0;
                Integer rentCounts = 0;
                List<ExpenseItem> items = new ArrayList<>();
                for (String i:
                        itemIds) {
                    Long itemId = Long.parseLong(i);
                    ExpenseItem item = expensesService.findByItemId(itemId);
                    //判断收费项是否存在
                    if (item == null){
                        response.setCode(ExpensesCode.EXPENSE_ITEM_DELETE);
                        return response;
                    }else {
                        //当选择收费类型为停车费（3，4）时，判断该房屋有无对应车位
                        if (Integer.parseInt(ExpenseStaticStatus.ITEM_TYPE_THREE)==item.getItemType()){
                            buyCounts = expensesService.findSpacesByHouseIdAndType(houseId, HouseDataValidation.BUY_PARKING_SPACE);
                            if (buyCounts == 0){
                                response.setCode(ExpensesCode.HOUSE_BUYSPACE_NULL);
                                return response;
                            }
                        }
                        if (Integer.parseInt(ExpenseStaticStatus.ITEM_TYPE_FOUR)==item.getItemType()){
                            rentCounts = expensesService.findSpacesByHouseIdAndType(houseId, HouseDataValidation.RENT_PARKING_SPACE);
                            if (rentCounts == 0){
                                response.setCode(ExpensesCode.HOUSE_RENTSPACE_NULL);
                                return response;
                            }
                        }
                        //判断房屋所属楼宇是否与收费项绑定
                        List<ExpenseItemBuildingRela> relas = expensesService.findRelasByItemId(itemId);
                        if (relas!=null && relas.size()>0){
                            boolean flag = false;
                            for (ExpenseItemBuildingRela rela:
                                    relas) {
                                if (rela.getBuildingId() == house.getBuildingId()){
                                    flag = true;
                                }
                            }
                            if (!flag){
                                response.setCode(ExpensesCode.HOUSE_NOTRELA_ITEM);
                                return response;
                            }
                        }
                        items.add(item);
                    }
                }
                //生成账单
                response.setData(expensesService.prePay(items,months,house,buyCounts,rentCounts,1,request));
            } catch (Exception e) {
                LOG.error("========app/paymentManage/v1/prepay error========", e);
                response.setCode(StatusCode.FAILURE);
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========app/paymentManage/v1/prepay end========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 分页查询所有预交费账单
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/10/19 12:56
     * @param payPageReq
     * @return response
     *
     * */
    @RequestMapping(value = "v1/findAllPrePay", method = RequestMethod.GET)
    public Response<Paging<PrePayPageResp>> findAllPrePay(PrePayPageReq payPageReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========app/paymentManage/v1/findAllPrePay start========payPageReq=" + payPageReq);
        }
        Response<Paging<PrePayPageResp>> response = new Response<>();
        Paging<PrePayPageResp> pager = new Paging<>(payPageReq.getCurPage(), payPageReq.getPageSize());
        try {
            String billNos = payPageReq.getBillNos();
            if (!StringUtil.isBlank(billNos)){
                String[] aa = billNos.split(",");
                //查询
                List<PrePayPageResp> list = expensesService.findByBillNos(aa,pager);
                if (list!=null && list.size()>0){
                    for (PrePayPageResp resp:
                         list) {
                        if (!StringUtil.isBlank(resp.getBillingStartTime()) && !StringUtil.isBlank(resp.getBillingEndTime())){
                            resp.setBillingStartTime(DateUtil.toString(DateUtil.toDate(resp.getBillingStartTime(),"yyyy-MM-dd"),"yyyy年MM月"));
                            resp.setBillingEndTime(DateUtil.toString(DateUtil.toDate(resp.getBillingEndTime(),"yyyy-MM-dd"),"yyyy年MM月"));
                        }
                    }
                }
                pager.result(list);
                response.setData(pager);
            }
        } catch (Exception e) {
            LOG.error("========app/paymentManage/v1/findAllPrePay error!=========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========app/paymentManage/v1/findAllPrePay end========response" + response);
        }
        return response;
    }
}
