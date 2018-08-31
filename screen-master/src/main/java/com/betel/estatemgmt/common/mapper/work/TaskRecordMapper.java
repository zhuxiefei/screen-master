package com.betel.estatemgmt.common.mapper.work;

import com.betel.estatemgmt.business.oa.task.model.HomeReq;
import com.betel.estatemgmt.business.propertyapp.task.model.FindAllTasksReq;
import com.betel.estatemgmt.business.propertyapp.task.model.FindAllTasksResp;
import com.betel.estatemgmt.business.web.screen.model.DepTaskData;
import com.betel.estatemgmt.business.web.screen.model.TodayTaskData;
import com.betel.estatemgmt.business.web.task.model.FindTaskResp;
import com.betel.estatemgmt.common.model.work.TaskRecord;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRecordMapper {
    int deleteByPrimaryKey(String recordId);

    int insert(TaskRecord record);

    int insertSelective(TaskRecord record);

    TaskRecord selectByPrimaryKey(String recordId);

    int updateByPrimaryKeySelective(TaskRecord record);

    int updateByPrimaryKey(TaskRecord record);

    FindTaskResp findTaskByRecordId(String recordId);

    List<TaskRecord> findByTaskId(String taskId);

    List<FindAllTasksResp> findAllAppTasks(FindAllTasksReq req, RowBounds rowBounds);

    com.betel.estatemgmt.business.propertyapp.task.model.FindTaskResp findAppByRecordId(@Param("recordId") String recordId);


    /**
     * 未接受的任务数
     *
     * @return
     */
    Integer countUnreceived(@Param("userId") String userId,
                            @Param("estateId") String estateId);

    void deleteAppRecord(TaskRecord record);

    /**
     * 根据时间段查询
     * @param startTime
     * @param endTime
     * @return
     */
    TodayTaskData findTodayTaskData(@Param("startTime") String startTime, @Param("endTime") String endTime);

    DepTaskData findDepTaskData(String depId);

    /**
     * 查询我收到的任务列表和我发出的任务列表
     *
     * @param homeReq
     * @return
     */
    List<FindAllTasksResp> queryAllAppTasks(HomeReq homeReq);

    /**
     * 我接受的进行中，已完成，已拒绝的任务
     *
     * @param homeReq
     * @return
     */
    List<FindAllTasksResp> queryAppTasksToMe(HomeReq homeReq);

    /**
     * 今日已接受任务数中已完成的
     *
     * @param homeReq
     * @return
     */
    List<FindAllTasksResp> hasFinishInAcceptToMe(HomeReq homeReq);
}