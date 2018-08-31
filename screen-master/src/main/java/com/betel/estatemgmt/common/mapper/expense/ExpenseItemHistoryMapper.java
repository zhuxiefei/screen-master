package com.betel.estatemgmt.common.mapper.expense;

import com.betel.estatemgmt.business.web.charges.model.ChargesItemReq;
import com.betel.estatemgmt.business.web.charges.model.ExpenseItemHistoryInfo;
import com.betel.estatemgmt.common.model.expense.ExpenseItemHistory;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface ExpenseItemHistoryMapper {
    int deleteByPrimaryKey(Long historyId);

    int insert(ExpenseItemHistory record);

    int insertSelective(ExpenseItemHistory record);

    ExpenseItemHistory selectByPrimaryKey(Long historyId);

    int updateByPrimaryKeySelective(ExpenseItemHistory record);

    int updateByPrimaryKey(ExpenseItemHistory record);

    /**
     * 分页查询操作记录日志
     * @param rowBounds
     * @param chargesItemReq
     * @return
     */
    List<ExpenseItemHistoryInfo> findAllOperationLogs(RowBounds rowBounds, ChargesItemReq chargesItemReq);
    /**
     * 批量查询项目名称
     * @return
     */
    int insertBatchSelective(List<ExpenseItemHistory> eih);
}