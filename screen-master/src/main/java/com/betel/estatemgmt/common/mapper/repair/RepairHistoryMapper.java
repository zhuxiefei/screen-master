package com.betel.estatemgmt.common.mapper.repair;

import com.betel.estatemgmt.business.web.repair.model.FindAllRecordReq;
import com.betel.estatemgmt.common.model.repair.RepairHistory;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface RepairHistoryMapper {
    int deleteByPrimaryKey(Long historyId);

    int insert(RepairHistory record);

    int insertSelective(RepairHistory record);

    RepairHistory selectByPrimaryKey(Long historyId);

    int updateByPrimaryKeySelective(RepairHistory record);

    int updateByPrimaryKey(RepairHistory record);

    RepairHistory findOperatorInfo(String orderNo);

    List<RepairHistory> findRecordsByOrderNo(String orderNo);

    List<RepairHistory> findAllRecords(RowBounds rowBounds, FindAllRecordReq recordReq);
}