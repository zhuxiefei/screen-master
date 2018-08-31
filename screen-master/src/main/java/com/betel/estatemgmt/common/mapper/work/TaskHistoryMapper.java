package com.betel.estatemgmt.common.mapper.work;

import com.betel.estatemgmt.business.web.task.model.FindAllRecordReq;
import com.betel.estatemgmt.common.model.work.TaskHistory;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskHistoryMapper {
    int deleteByPrimaryKey(Long historyId);

    int insert(TaskHistory record);

    int insertSelective(TaskHistory record);

    TaskHistory selectByPrimaryKey(Long historyId);

    int updateByPrimaryKeySelective(TaskHistory record);

    int updateByPrimaryKey(TaskHistory record);

    List<TaskHistory> findAllRecords(RowBounds rowBounds, FindAllRecordReq recordReq);
}