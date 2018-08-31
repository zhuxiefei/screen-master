package com.betel.estatemgmt.common.mapper.expense;

import com.betel.estatemgmt.business.userapp.pay.model.PayList;
import com.betel.estatemgmt.business.userapp.pay.model.PayPage;
import com.betel.estatemgmt.business.web.expenses.model.*;
import com.betel.estatemgmt.business.web.income.model.ExportReq;
import com.betel.estatemgmt.business.web.income.model.Income;
import com.betel.estatemgmt.business.web.income.model.IncomePageReq;
import com.betel.estatemgmt.business.web.remind.model.OverDue;
import com.betel.estatemgmt.business.web.remind.model.Payment;
import com.betel.estatemgmt.common.model.expense.ExpenseBill;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.Date;
import java.util.List;

public interface ExpenseBillMapper {
    int deleteByPrimaryKey(String billNo);

    int insert(ExpenseBill record);

    int insertSelective(ExpenseBill record);

    ExpenseBill selectByPrimaryKey(String billNo);

    int updateByPrimaryKeySelective(ExpenseBill record);

    int updateByPrimaryKey(ExpenseBill record);

    List<ExpenseBill> findByHouseId(String houseId);

    void deleteBillList(List<String> billNos);

    List<Income> findAllIncome(RowBounds rowBounds, IncomePageReq incomePageReq);

    List<Income> findExportIncomes(ExportReq incomePageReq);

    /**
     * 分页查询费用列表
     *
     * @param rowBounds
     * @param expensesReq
     * @return
     */
    List<Expenses> findAllExpenses(RowBounds rowBounds, ExpensesReq expensesReq);


    /**
     * 分页查询全部的缴费列表
     * Create By ZhouYe ON 2017/9/18 15:33
     */
    List<PayList> findAllPayList(RowBounds rowBounds, PayPage payPage);

    /**
     * 根据房屋id查询全部未缴费的账单
     * Create By ZhouYe ON 2017/9/18 16:35
     */
    List<PayList> findPayListByHouseIds(PayPage payPage);

    /**
     * 根据账单编号查询账单信息
     * Create By ZhouYe ON 2017/9/18 18:30
     */
    com.betel.estatemgmt.business.userapp.pay.model.Payment findPayments(String billNo);

    /**
     * 查询欠费列表
     *
     * @param billNos
     * @return
     */
    List<Expenses> findPayDetail(String[] billNos);

    /**
     * 根据出账时间查询所有未缴费的账单
     *
     * @param time
     * @return
     */
    List<Payment> findByCreateTime(Date time);

    /**
     * 根据到期时间查询所有未缴费的账单
     *
     * @param time
     * @return
     */
    List<OverDue> findByDeadLine(Date time);

    /**
     * 根据 id1,id2 查询多个ExpenseBill
     * Create By ZhouYe ON 2017/9/19 10:14
     */
    List<ExpenseBill> findExpenseBill(String[] billNo);

    /**
     * <p>
     * 根据 id1,id2 设置流水编号
     * </p>
     * Author:ZhouYe<br/>
     * Date: 2017/9/19 11:27
     *
     * @param billNo
     * @param flowNo
     * @return
     * @throws Exception
     */
    void updateFlowNoByBillNo(@Param("billNo") String[] billNo, @Param("flowNo") String flowNo);

    /**
     * <p>
     * 修改订单为支付成功
     * </p>
     * Author:ZhouYe<br/>
     * Date: 2017/9/19 14:38
     *
     * @param flowNo
     * @return
     * @throws Exception
     */
    void surePay(String flowNo);
//    =================================费用设置===========================开始===============

    /**
     * 导出查询费用列表
     *
     * @param expensesReq
     * @return
     */
    List<Expenses> exportBill(ExpensesReq expensesReq);

    /**
     * 根据账单id导出查询费用列表
     *
     * @param billNos
     * @return
     */
    List<Expenses> exportBillById(String[] billNos);

    /**
     * 打印
     *
     * @param billNos
     * @return
     */
    List<PrintNote> printReceipt(String[] billNos);


    /**
     * 通过id查询账单信息
     * 场景：发送催缴短信，线下缴费生成账单
     *
     * @param billNos
     * @return
     */
    List<Expenses> findExpensesByBillNos(String[] billNos);

    /**
     * 通过id查询账单信息:导出账单
     *
     * @param billNos
     * @return
     */
    List<Expenses> findExportExpensesByBillNos(String[] billNos);

    /**
     * <p>
     * 删除收费账单记录
     * </p>
     * Author:ZhouYe<br/>
     * Date: 2017/9/21 14:24
     *
     * @param billNo 账单No
     * @return
     * @throws Exception
     */
    void deletePayment(String billNo);

    /**
     * 查询账单，判断账单流水号是否过期(批量查询)
     *
     * @return
     */
    List<ExpenseBill> findExpenseBillByArraybillNo(String[] billNos);

    /**
     * 支付修改账单的流水号状态：已支付
     *
     * @param payBillReq
     * @return
     */
    int updateBillFlow(PayBillReq payBillReq);

    /**
     * 通过账单id更新账单状态为已支付
     *
     * @param payBillReq
     * @return
     */
    int updateBillStatus(PayBillReq payBillReq);

    /**
     * 查询账单是否已经绑定了流水表
     */
    List<ExpenseBill> findExpenseBillAndFlowNoIsExist(PayBillReq payBillReq);

    /**
     * 选中欠缴费账单生成缴费明细时账单绑定流水号
     *
     * @param payBillReq
     * @return
     */
    int insertBillFlowNoByBillNos(PayBillReq payBillReq);

    /**
     * 更新账单设置滞纳金
     *
     * @return
     */
    int setOverdueFineOfupdateBill(ExpenseBill record);
    //=================================费用设置===========================结束===============

    /**
     * <p>
     * 取消支付账单
     * </p>
     * Author:ZhouYe<br/>
     * Date: 2017/9/21 14:27
     *
     * @return
     * @throws Exception
     */
    void cancelPay(String flowNo);

    /**
     * <p>
     * 保存滞纳金
     * </p>
     * Author:ZhouYe<br/>
     * Date: 2017/9/25 10:01
     *
     * @param expenseBill
     * @return
     * @throws Exception
     */
    void saveOverdueFine(ExpenseBill expenseBill);

    /**
     * 根据房屋和收费项查询最新的账单
     *
     * @param houseId
     * @param itemId
     * @return
     */
    ExpenseBill findNewestByHouseIdAndItemId(@Param("houseId") String houseId, @Param("itemId") Long itemId);

    /**
     * 批量添加账单
     *
     * @param list
     */
    void insertList(List<ExpenseBill> list);

    /**
     * 根据房屋ID，收费项ID，计费开始时间和结束时间查询
     *
     * @param expenseBill
     * @return
     */
    List<ExpenseBill> findByExpenseBill(ExpenseBill expenseBill);

    /**
     * 根据billNos分页查询
     *
     * @param billNos
     * @param rowBounds
     * @return
     */
    List<PrePayPageResp> findAllByBillNos(String[] billNos, RowBounds rowBounds);

    /**
     * 根据收费项类型查询最新的一条账单
     *
     * @param itemType
     * @return
     */
    ExpenseBill findNewestByItemType(@Param("itemType") Integer itemType);

    /**
     * 根据flowNo查询
     *
     * @param flowNo
     * @return
     */
    List<Expenses> findByFlowNo(@Param("flowNo") String flowNo);

    /**
     * 根据收费项ID，开始收费时间，结束收费时间查询
     *
     * @param startTime
     * @param endTime
     * @param itemId
     * @return
     */
    List<ExpenseBill> findByTimeAndItemId(Date startTime, Date endTime, Long itemId);

    /**
     * 查询送收据
     *
     * @param demandBillStatus
     * @return
     */
    List<ExpenseBill> findSendReceipt(String demandBillStatus);

    /**
     * 查询账单总金额
     *
     * @param startTime 账单起始月
     * @param endTime   账单结束月
     * @param itemId    收费项ID
     * @param isPay     是否缴费 1为欠缴费（未缴费），2为已缴费
     * @return
     */
    Double countAmountByParams(@Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("itemId") Long itemId, @Param("isPay") Integer isPay);

    /**
     * 查询账单总滞纳金额
     *
     * @param startTime 账单起始月
     * @param endTime   账单结束月
     * @param itemId    收费项ID
     * @param isPay     是否缴费 1为欠缴费（未缴费），2为已缴费
     * @return
     */
    Double countOverFineByParams(@Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("itemId") Long itemId, @Param("isPay") Integer isPay);

    /**
     * 查询用户数
     *
     * @param startTime 账单起始月
     * @param endTime   账单结束月
     * @param itemId    收费项ID
     * @param isPay     是否缴费 1为欠缴费（未缴费），2为已缴费
     * @return
     */
    Integer countHouseByParams(@Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("itemId") Long itemId, @Param("isPay") Integer isPay);

    /**
     * 查询周期内的账单
     *
     * @param startTime 账单起始月
     * @param endTime   账单结束月
     * @param itemId    收费项ID
     * @param isPay     是否缴费 1为欠缴费（未缴费），2为已缴费
     * @return
     */
    List<ExpenseBill> findByTimeBetween(@Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("itemId") Long itemId, @Param("isPay") Integer isPay);

    /**
     * 查询周期内的所有逾期用户数
     *
     * @param startTime 账单起始月
     * @param endTime   账单结束月
     * @param itemId    收费项ID
     * @return
     */
    List<String> findOverHousesByParams(@Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("itemId") Long itemId);

    List<ExpenseBill> findList();
}