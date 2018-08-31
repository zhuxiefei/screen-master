package com.betel.estatemgmt.business.web.task.service;

import com.betel.estatemgmt.business.propertyapp.task.model.AppTaskCount;
import com.betel.estatemgmt.business.web.task.model.*;
import com.betel.estatemgmt.common.model.work.TaskType;
import com.betel.estatemgmt.utils.pagination.model.Paging;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 业务实现接口
 * </p>
 * ClassName: TaskService <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/12/21 8:56 <br/>
 * Version: 1.0 <br/>
 */
public interface TaskService {

    String addTaskType(AddTaskTypeReq typeReq);

    String updateTaskType(UpdateTaskTypeReq typeReq);

    void deleteTaskType(DeleteTaskTypeReq typeReq);

    List<TaskType> findAllTaskTypes(Paging<TaskType> paging,String estateId);

    TaskType findTaskType(FindTaskTypeReq typeReq);

    /**
     * 添加任务
     * @param taskReq
     * @param userId
     * @param type 1为web添加  2为app添加
     * @return
     */
    String addTask(AddTaskReq taskReq, String userId, Integer type, HttpServletRequest request) throws Exception;

    List<FindAllTasksResp> findAllTasks(FindAllTasksReq tasksReq
                                        , Paging<FindAllTasksResp> paging
                                        ,HttpServletRequest request) throws Exception;

    FindTaskResp findTask(FindTaskReq taskReq);

    /**
     *
     * @param taskReq
     * @param userId
     * @param type 1为web添加  2为app添加
     * @return
     */
    String updateTask(UpdateTaskReq taskReq, String userId, Integer type,HttpServletRequest request) throws Exception;

    void deleteTask(DeleteTaskReq deleteTaskReq, String userId) throws Exception;

    List<TaskType> findTaskTyps(String estateId);

    List<FindAllRecordResp> findAllRecords(Paging<FindAllRecordResp> paging, FindAllRecordReq recordReq,HttpServletRequest request) throws Exception;

    TaskCountResp findTaskCount(TaskCountReq taskCountReq) throws Exception;

    AppTaskCount appFindTaskCount(TaskCountReq taskCountReq) throws Exception;
}
