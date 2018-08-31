package com.betel.estatemgmt.business.oa.task.service;

import com.betel.estatemgmt.business.oa.task.model.HomeReq;
import com.betel.estatemgmt.business.propertyapp.task.model.FindAllTasksResp;

import java.util.List;

/**
 * <p>
 * 任务实现接口
 * </p>
 * ClassName: PropertyTaskService <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/12/21 17:25 <br/>
 * Version: 1.0 <br/>
 */
public interface PropertyTaskService {
    String findWaitTaskByEmpId(String empId,String estateId);

    String queryAllAppTasks(HomeReq homeReq);

    String queryAppTasksToMe(HomeReq homeReq);

    String hasFinishInAcceptToMe(HomeReq homeReq);
}
