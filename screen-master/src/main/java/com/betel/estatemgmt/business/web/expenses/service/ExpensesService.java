package com.betel.estatemgmt.business.web.expenses.service;

import com.betel.estatemgmt.business.web.expenses.model.*;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.model.expense.ExpenseBill;
import com.betel.estatemgmt.common.model.expense.ExpenseFlow;
import com.betel.estatemgmt.common.model.expense.ExpenseItem;
import com.betel.estatemgmt.common.model.expense.ExpenseItemBuildingRela;
import com.betel.estatemgmt.common.model.house.House;
import com.betel.estatemgmt.utils.pagination.model.Paging;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

/**
 * Created by zhangjian on 2017/9/17.
 */
public interface ExpensesService {

    /**
     *分页查询费用列表
     * @param pager
     * @param expensesReq  参数
     * @return
     */
    List<Expenses> findAllExpenses(Paging<Expenses> pager, ExpensesReq expensesReq);
    /**
     * 校验参数合法性
     */
     String checkParamter(ExpensesReq expensesReq);

    /**
     * 查询代缴费账单列表
     * @param expensesReq
     * @return
     */
    public Expenses findPayDetail(ExpensesReq expensesReq) throws ParseException;

    /**
     * 校验前端参数
     * @param expensesReq
     * @return
     */
    public String testFindPayDetailParamter(ExpensesReq expensesReq);

    /**
     * 发送催缴短信
     */
    int sendReminderMessage(ExpensesReq expensesReq, HttpServletRequest request) throws Exception;

    /**
     * 查询账单，判断账单流水号是否过期
     * @return
     */

     String checkExpenseBillisOver(ExpensesReq expensesReq);

//    /**
//     * 缴费：更改账单对应的流水号状态为已支付
//     * @param expensesReq
//     * @return
//     */
//    int updateBill(ExpensesReq expensesReq);

    /**
     * 检查选中的账单是否已经绑定到流水号：
     * 绑定了，不能再绑定
     * @return
     */
     String checkExpenseBillisHasBeenBoundStreamNumber(ExpensesReq expensesReq);

    /**
     * 生成缴费流水号，绑定流水号信息，订单处于待支付状态
     */
    int updateFlowOfBill(PayBillReq payBillReq);

    /**
     * 生成流水号
     */
    int insertFlowInfo(ExpenseFlow expenseFlow);

    /**
     * web端线下支付
     * @return
     */
    Response payWebUnderLine(ExpensesReq expensesReq, Response response);
    /**
     * web端线下支付:1：生成支付订单   2  修改订单
     * @return
     */
    Response payUnderWebLine(ExpensesReq expensesReq, Response response, Integer actionType, HttpServletRequest request) throws Exception;

    /**
     * web端
     * @param billNos
     * @param response
     */
    void   payWebCourse(String billNos, Response response) throws ParseException;

    /**
     * 查询账单是否存在，是否有户主信息
     * 场景：线下缴费
     * @return
     */
    List<Expenses> findExpensesByBillNos(String[] billNos);

    /**
     * 根据收费类型查询收费项列表
     * @param itemType
     * @return
     */
    List<ExpenseItem> findByItemType(Integer itemType);

    /**
     * 根据itemId和buildingId唯一确定关系
     * @param itemId
     * @param buildingId
     * @return
     */
    ExpenseItemBuildingRela findByItemIdAndBuildingId(Long itemId, Long buildingId);

    /**
     * 查询指定收费类型的缴费周期
     * @param items
     * @return
     */
    List<FindItemCycleResp> findItemCycle(House house, List<ExpenseItem> items);

    /**
     * 根据itemId查询收费项
     * @param itemId
     * @return
     */
    ExpenseItem findByItemId(Long itemId);

    /**
     * 计算预缴合计
     * @param floorArea
     * @param itemPrice
     * @param months
     * @param itemType
     * @param buyCounts
     * @param rentCounts
     * @return
     */
    Double countTotalAmount(Double floorArea, BigDecimal itemPrice, Integer months, Integer itemType, Integer buyCounts, Integer rentCounts);

    /**
     * 预缴
     * @param items
     * @param months
     * @param house
     * @param buyCounts
     * @param rentCounts
     * @param flag 1代表app预缴  2代表web预缴
     * @throws Exception
     */
    List<String> prePay(List<ExpenseItem> items, String[] months,
                        House house, Integer buyCounts, Integer rentCounts,
                        Integer flag,HttpServletRequest request) throws Exception;

    /**
     * 根据房屋ID和车位类型查询车位数
     * @param houseId
     * @param spaceType
     * @return
     */
    Integer findSpacesByHouseIdAndType(String houseId, Integer spaceType);

    /**
     * 根据billNos查询
     * @param billNos
     * @return
     */
    List<PrePayPageResp> findByBillNos(String[] billNos, Paging<PrePayPageResp> pager);

    /**
     * 根据收费项ID查询楼宇关联
     * @param itemId
     * @return
     */
    List<ExpenseItemBuildingRela> findRelasByItemId(Long itemId);

    ExpenseBill findByBilNo(String billNo);

    void updateDemandStatus(ExpenseBill expenseBill);
}
