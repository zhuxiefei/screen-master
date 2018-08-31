package com.betel.estatemgmt.business.web.expenses.service;

import com.betel.estatemgmt.business.web.expenses.model.Expenses;
import com.betel.estatemgmt.business.web.expenses.model.ExpensesReq;
import com.betel.estatemgmt.business.web.expenses.model.PrintNote;

import java.util.List;

/**
 * <p>
 *  导出打印查询接口
 * </p>
 * ClassName: ExportPrintService <br/>
 * Author:zhangjain <br/>
 * Date: 2017/9/18  <br/>
 * Version: 1.0 <br/>
 */
public interface ExportPrintService {
    /**
     * 导出查询账单列表
     * @param expensesReq
     * @param flag    true   全部导出，false 是根据id导出
     * @return
     */
    List<Expenses> exportBill(ExpensesReq expensesReq, boolean flag);
    /**
     * 打印查询已缴缴费账单列表
     * @param expensesReq
     * @return
     */
     PrintNote printReceipt(ExpensesReq expensesReq);

    /**
     * 查询账单是否存在，是否有户主信息
     * @return
     */
    List<Expenses> findExportExpensesByBillNos(String[] billNos);


}
