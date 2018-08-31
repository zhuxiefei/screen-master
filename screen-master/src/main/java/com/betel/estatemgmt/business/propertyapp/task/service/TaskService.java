package com.betel.estatemgmt.business.propertyapp.task.service;

import com.betel.estatemgmt.business.propertyapp.task.model.*;
import com.betel.estatemgmt.business.web.task.model.FindTaskReq;
import com.betel.estatemgmt.utils.pagination.model.Paging;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 任务实现接口
 * </p>
 * ClassName: TaskService <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/12/21 17:25 <br/>
 * Version: 1.0 <br/>
 */
public interface TaskService {

    List<FindAllTasksResp> findAllTasks(FindAllTasksReq req, Paging<FindAllTasksResp> paging);

    FindTaskResp findTask(FindTaskReq req, HttpServletRequest request) throws Exception;

    String doTask(DoTaskReq taskReq,HttpServletRequest request) throws Exception;

    String deleteTask(DeleteRecordReq recordReq);
}
