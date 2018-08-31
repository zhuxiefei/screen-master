package com.betel.estatemgmt.common.mapper.work;

import com.betel.estatemgmt.business.web.task.model.FindAllTasksReq;
import com.betel.estatemgmt.business.web.task.model.FindAllTasksResp;
import com.betel.estatemgmt.common.model.work.Task;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskMapper {
    int deleteByPrimaryKey(String taskId);

    int insert(Task record);

    int insertSelective(Task record);

    Task selectByPrimaryKey(String taskId);

    int updateByPrimaryKeySelective(Task record);

    int updateByPrimaryKeyWithBLOBs(Task record);

    int updateByPrimaryKey(Task record);

    List<FindAllTasksResp> findAllTasks(FindAllTasksReq tasksReq, RowBounds rowBounds);
}