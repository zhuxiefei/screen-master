package com.betel.estatemgmt.business.oa.task.service.impl;

import com.betel.estatemgmt.business.oa.task.model.HomeReq;
import com.betel.estatemgmt.business.oa.task.service.PropertyTaskService;
import com.betel.estatemgmt.business.propertyapp.task.model.FindAllTasksResp;
import com.betel.estatemgmt.common.mapper.patrol.PatrolEquipmentMapper;
import com.betel.estatemgmt.common.mapper.work.TaskRecordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: PropertyTaskServiceImpl <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/12/21 17:25 <br/>
 * Version: 1.0 <br/>
 */
@Service("PropertyTaskService")
@Transactional(rollbackFor = Exception.class)
public class PropertyTaskServiceImpl implements PropertyTaskService {

    private static final Logger LOG = LoggerFactory.getLogger(PropertyTaskServiceImpl.class);

    @Autowired
    TaskRecordMapper recordMapper;

    @Autowired
    PatrolEquipmentMapper equipmentMapper;

    @Override
    public String findWaitTaskByEmpId(String empId,String estateId) {
        Integer num = recordMapper.countUnreceived(empId,estateId);
        if (null != num){
            return num.toString();
        }
        return null;
    }

    @Override
    public String queryAllAppTasks(HomeReq homeReq) {
        List<FindAllTasksResp> list = recordMapper.queryAllAppTasks(homeReq);
        if (list != null){
            Integer a = list.size();
            return a.toString();
        }
        return null;
    }

    @Override
    public String queryAppTasksToMe(HomeReq homeReq) {
        List<FindAllTasksResp> list = recordMapper.queryAppTasksToMe(homeReq);
        if (list != null){
            Integer a = list.size();
            return a.toString();
        }
        return null;
    }

    @Override
    public String hasFinishInAcceptToMe(HomeReq homeReq) {
        List<FindAllTasksResp> list = recordMapper.hasFinishInAcceptToMe(homeReq);
        if (list != null){
            Integer a = list.size();
            return a.toString();
        }
        return null;
    }
}
