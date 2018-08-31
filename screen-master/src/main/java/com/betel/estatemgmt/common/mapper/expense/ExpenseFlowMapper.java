package com.betel.estatemgmt.common.mapper.expense;

import com.betel.estatemgmt.common.model.expense.ExpenseFlow;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ExpenseFlowMapper {
    int deleteByPrimaryKey(String flowNo);

    int insert(ExpenseFlow record);

    int insertSelective(ExpenseFlow record);

    ExpenseFlow selectByPrimaryKey(String flowNo);

    int updateByPrimaryKeySelective(ExpenseFlow record);

    int updateByPrimaryKey(ExpenseFlow record);

    /**
     * <p>
     * 修改状态为已支付
     * </p>
     * Author:ZhouYe<br/>
     * Date: 2017/9/19 14:44
     *
     * @param flowNo
     * @param payTime
     * @return
     * @throws Exception
     */
    void surePay(@Param("flowNo") String flowNo, @Param("payTime") Date payTime);
}