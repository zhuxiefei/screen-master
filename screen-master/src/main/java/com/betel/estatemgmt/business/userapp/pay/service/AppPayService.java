package com.betel.estatemgmt.business.userapp.pay.service;

import com.betel.estatemgmt.business.userapp.pay.model.*;
import com.betel.estatemgmt.business.web.expenses.model.Expenses;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.model.expense.ExpenseBill;
import com.betel.estatemgmt.common.model.expense.ExpenseFlow;
import com.betel.estatemgmt.common.model.system.Notice;
import com.betel.estatemgmt.common.model.user.UserAccount;
import org.jdom.JDOMException;
import org.quartz.SchedulerException;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * <p>
 * app端支付service层
 * </p>
 * ClassName: AppPayService <br/>
 * Author: zhouye  <br/>
 * Date: 2017/9/18 14:17 <br/>
 * Version: 1.0 <br/>
 */
public interface AppPayService {

    AllPayList findAllPayList(PayPage payPage, String ids) throws ParseException;

    Payment findPayments(String billNo) throws ParseException;

    ExpenseBill findExpenses(String billNo);

    List<ExpenseBill> findExpenseBillByBillNos(String billNo);

    String findFeeScale(String estateId);

    void pay(PayModel payModel, Response response) throws ParseException, IOException, JDOMException, SchedulerException;

    void surePay(String flowNo);

    void deletePayment(DeleteBillReq req);

    ExpenseFlow findUserPhoneByFlowNo(String flowNo) throws Exception;

    void cancelPay(String flowNo);

    ExpenseFlow findExpenseFlowByFlowNo(String flow);

    List<Expenses> findByFlowNo(String flowNo);

    void demandBill(String billNo);

    UserAccount findByAcctName(String acctName) throws Exception;

    Long insertNotice(Notice notice);

    /**
     * 确认收到收据
     *
     * @param billNo
     * @return
     */
    String updateDemandStatus(String billNo);

    ExpenseBill findByBilNo(String billNo);

    /**
     * 确认收到收据
     *
     * @return
     */
    String updateDemandStatusNew() throws ParseException;
}
