package com.betel.estatemgmt.business.web.income.service;

import com.betel.estatemgmt.business.web.income.model.ExportReq;
import com.betel.estatemgmt.business.web.income.model.Income;
import com.betel.estatemgmt.business.web.income.model.IncomePageReq;
import com.betel.estatemgmt.utils.pagination.model.Paging;

import java.util.List;

/**
 * <p>
 * 收入明细接口
 * </p>
 * ClassName: IncomeService <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/9/13 11:49 <br/>
 * Version: 1.0 <br/>
 */
public interface IncomeService {
    List<Income> findAllIncome(Paging<Income> pager, IncomePageReq incomePageReq);

    List<Income> findExportIncomes(ExportReq incomePageReq);
}
