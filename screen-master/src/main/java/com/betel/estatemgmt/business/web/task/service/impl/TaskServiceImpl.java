package com.betel.estatemgmt.business.web.task.service.impl;

import com.betel.estatemgmt.business.propertyapp.task.model.AppTaskCount;
import com.betel.estatemgmt.business.propertyapp.task.model.CleanResp;
import com.betel.estatemgmt.business.userapp.repair.constant.RepairConstant;
import com.betel.estatemgmt.business.web.cleaning.constant.CleaningConstant;
import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.business.web.patrol.constant.PatrolConstant;
import com.betel.estatemgmt.business.web.security.constant.SecurityConstant;
import com.betel.estatemgmt.business.web.task.code.TaskCode;
import com.betel.estatemgmt.business.web.task.constant.TaskConstant;
import com.betel.estatemgmt.business.web.task.model.*;
import com.betel.estatemgmt.business.web.task.service.TaskService;
import com.betel.estatemgmt.business.web.task.util.GetDateUtil;
import com.betel.estatemgmt.business.web.task.util.IdUtil;
import com.betel.estatemgmt.common.*;
import com.betel.estatemgmt.common.mapper.cleaning.CleaningAreaMapper;
import com.betel.estatemgmt.common.mapper.cleaning.CleaningRecordMapper;
import com.betel.estatemgmt.common.mapper.patrol.PatrolRecordMapper;
import com.betel.estatemgmt.common.mapper.repair.RepairOrderMapper;
import com.betel.estatemgmt.common.mapper.security.SecurityRecordMapper;
import com.betel.estatemgmt.common.mapper.system.NoticeMapper;
import com.betel.estatemgmt.common.mapper.system.SystemPictureMapper;
import com.betel.estatemgmt.common.mapper.work.*;
import com.betel.estatemgmt.common.model.cleaning.CleaningArea;
import com.betel.estatemgmt.common.model.cleaning.CleaningRecord;
import com.betel.estatemgmt.common.model.patrol.PatrolRecord;
import com.betel.estatemgmt.common.model.repair.RepairOrder;
import com.betel.estatemgmt.common.model.security.SecurityContent;
import com.betel.estatemgmt.common.model.security.SecurityRecord;
import com.betel.estatemgmt.common.model.system.SystemPicture;
import com.betel.estatemgmt.common.model.work.Task;
import com.betel.estatemgmt.common.model.work.TaskHistory;
import com.betel.estatemgmt.common.model.work.TaskRecord;
import com.betel.estatemgmt.common.model.work.TaskType;
import com.betel.estatemgmt.common.msgpush.PropertyPushUtil;
import com.betel.estatemgmt.utils.*;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 业务实现类
 * </p>
 * ClassName: TaskServiceImpl <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/12/21 8:57 <br/>
 * Version: 1.0 <br/>
 */
@Service("TaskService")
@Transactional(rollbackFor = Exception.class)
public class TaskServiceImpl implements TaskService {

    private static Logger LOG = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Autowired
    TaskMapper taskMapper;

    @Autowired
    TaskTypeMapper typeMapper;

    @Autowired
    TaskRecordMapper recordMapper;

    @Autowired
    TaskRecordPicMapper recordPicMapper;

    @Autowired
    SystemPictureMapper pictureMapper;

    @Autowired
    TaskHistoryMapper historyMapper;

    @Autowired
    RepairOrderMapper repairOrderMapper;

    @Autowired
    PatrolRecordMapper patrolRecordMapper;

    @Autowired
    CleaningRecordMapper cleaningRecordMapper;

    @Autowired
    SecurityRecordMapper securityRecordMapper;

    @Autowired
    CleaningAreaMapper areaMapper;

    @Override
    public String addTaskType(AddTaskTypeReq typeReq) {
        if (null != typeMapper.findByTypeName(typeReq.getTypeName(), typeReq.getEstateId())) {
            return TaskCode.TASK_TYPE_EXIST;
        }
        TaskType taskType = new TaskType();
        taskType.setTypeName(typeReq.getTypeName());
        taskType.setCreateTime(new Date(System.currentTimeMillis()));
        taskType.setUpdateTime(new Date(System.currentTimeMillis()));
        taskType.setEstateId(typeReq.getEstateId());
        typeMapper.insertSelective(taskType);
        return StatusCode.SUCCESS;
    }

    @Override
    public String updateTaskType(UpdateTaskTypeReq typeReq) {
        TaskType type = typeMapper.selectByPrimaryKey(typeReq.getTypeId());
        if (null == type) {
            return TaskCode.TASK_TYPE_DELETE;
        }
        TaskType type1 = typeMapper.findByTypeName(typeReq.getTypeName(), typeReq.getEstateId());
        if (null != type1 && !type1.getTypeId().equals(type.getTypeId())) {
            return TaskCode.TASK_TYPE_EXIST;
        }
        TaskType taskType = new TaskType();
        taskType.setTypeId(typeReq.getTypeId());
        taskType.setTypeName(typeReq.getTypeName());
        taskType.setUpdateTime(new Date(System.currentTimeMillis()));
        typeMapper.updateByPrimaryKeySelective(taskType);
        return null;
    }

    @Override
    public void deleteTaskType(DeleteTaskTypeReq typeReq) {
        if (!StringUtil.isBlank(typeReq.getTypeIds())) {
            String[] typeIdArray = typeReq.getTypeIds().split(",");
            typeMapper.deleteByTypeIdArray(typeIdArray);
        }
    }

    @Override
    public List<TaskType> findAllTaskTypes(Paging<TaskType> paging, String estateId) {
        return typeMapper.findAllTaskTypes(paging.getRowBounds(), estateId);
    }

    @Override
    public TaskType findTaskType(FindTaskTypeReq typeReq) {
        if (!StringUtil.isBlank(typeReq.getTypeId())) {
            TaskType taskType = typeMapper.selectByPrimaryKey(typeReq.getTypeId());
            if (null != taskType) {
                return taskType;
            }
        }
        return null;
    }

    @Override
    public String addTask(AddTaskReq taskReq, String userId, Integer type, HttpServletRequest request) throws Exception {
        if (StringUtil.isBlank(taskReq.getCloseTime())) {
            return TaskCode.CLOSE_TIME_IS_NULL;
        }
        if (StringUtil.isBlank(taskReq.getTypeId())) {
            return TaskCode.TASK_TYPE_NULL;
        }
        TaskType taskType = typeMapper.selectByPrimaryKey(taskReq.getTypeId());
        if (null == taskType) {
            return TaskCode.TASK_TYPE_DELETE;
        }
        if (StringUtil.isBlank(taskReq.getTaskDesc())) {
            return TaskCode.TASK_DESC_NULL;
        }
        taskReq.setTaskDesc(taskReq.getTaskDesc().trim());
        if (taskReq.getTaskDesc().length() > TaskConstant.TASK_DESC_MAX_LENGTH) {
            return TaskCode.TASK_DESC_RULE;
        }
        if (StringUtil.isBlank(taskReq.getExecutors()) && StringUtil.isBlank(taskReq.getExecutorNames())
                && StringUtil.isBlank(taskReq.getDepartmentIds())) {
            return TaskCode.EXECUTOR_NULL;
        }
        if (StringUtil.isBlank(taskReq.getCloseTime())) {
            return TaskCode.CLOSE_TIME_IS_NULL;
        }
        //定义执行人ID和执行人名称
        List<String> executorList = new ArrayList<>();
        List<String> executorNameList = new ArrayList<>();
        //查询入参员工，插入executorList
        if (!StringUtil.isBlank(taskReq.getExecutors()) && !StringUtil.isBlank(taskReq.getExecutorNames())) {
            String[] executorArray = taskReq.getExecutors().split(",");
            String[] executorNameArray = taskReq.getExecutorNames().split(",");
            for (String e :
                    executorArray) {
                executorList.add(e);
            }
            for (String e :
                    executorNameArray) {
                executorNameList.add(e);
            }
        }
        //判断员工是否被删除
        for (String executor :
                executorList) {
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("empId", executor);
            String url = ConfigManager.read(ConfigName.OA_PROJECT_URL) + "httpclientOA/task/v1/findEmpByEmpId";
            Response response = HttpClientUtil.post(url, jsonParam, request);
            if (null != response && null == response.getData()) {
                return TaskCode.EXECUTOR_DELETE;
            }
        }
        //添加任务
        Task task = new Task();
        task.setTaskNo(IdUtil.getFlowNo());
        task.setTaskDesc(taskReq.getTaskDesc());
        task.setCreateTime(new Date(System.currentTimeMillis()));
        task.setCreateUser(userId);
        task.setEstateId(taskReq.getEstateId());
        String name = null;
        if (type.equals(1)) {
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("adminId", userId);
            String url = ConfigManager.read(ConfigName.OA_PROJECT_URL) + "httpclientOA/task/v1/findAdminByAdminId";
            Response response = HttpClientUtil.post(url, jsonParam, request);
            JSONObject admin = null;
            if (null != response && null != response.getData()) {
                admin = JSONObject.fromObject(response.getData());
                name = admin.get("adminName").toString();
            }
        } else if (type.equals(2)) {
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("empId", userId);
            String url = ConfigManager.read(ConfigName.OA_PROJECT_URL) + "httpclientOA/task/v1/findFullNameByEmpId";
            Response response = HttpClientUtil.post(url, jsonParam, request);
            if (null != response && null != response.getData()) {
                name = response.getData().toString();
            }

            JSONObject jsonParam1 = new JSONObject();
            jsonParam1.put("empId", userId);
            String url1 = ConfigManager.read(ConfigName.OA_PROJECT_URL) + "httpclientOA/task/v1/findEmpByEmpId";
            Response response1 = HttpClientUtil.post(url1, jsonParam1, request);
            if (null != response1 && null != response1.getData()) {
                JSONObject employee = JSONObject.fromObject(response1.getData());
                task.setCreateUserPhone(employee.get("phoneNum").toString());
                task.setLastModifyPhone(employee.get("phoneNum").toString());
            }
        }

        task.setCreateUserName(name);
        task.setLastModifyId(userId);
        task.setLastModifyName(name);
        task.setTaskType(taskReq.getTypeId());
        task.setUpdateTime(new Date(System.currentTimeMillis()));
        task.setIsDelete(TaskConstant.NO_DELETE);
        taskMapper.insertSelective(task);
        //操作记录（新增）
        TaskHistory history = new TaskHistory();
        history.setCreateTime(new Date(System.currentTimeMillis()));
        history.setHistoryType(1);
        history.setOperatorId(userId);
        if (type.equals(1)) {
            history.setOperatorType(1);
        } else if (type.equals(2)) {
            history.setOperatorType(2);
        }
        history.setTaskId(task.getTaskId());
        StringBuilder desc = new StringBuilder();
        desc.append("新增任务，任务类型：" + taskType.getTypeName() +
                    "；任务内容：" + taskReq.getTaskDesc() +
                    "；任务截止时间：" + taskReq.getCloseTime());
        history.setHistoryDesc(desc.toString());
        historyMapper.insertSelective(history);
        //操作记录（指派）
        history.setHistoryType(7);
        StringBuilder desc1 = new StringBuilder();
        desc1.append("指派给：");
        for (String empId :
                executorList) {
            String name1 = null;
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("empId", empId);
            String url = ConfigManager.read(ConfigName.OA_PROJECT_URL) + "httpclientOA/task/v1/findFullNameByEmpId";
            Response response = HttpClientUtil.post(url, jsonParam, request);
            if (null != response && null != response.getData()) {
                name1 = response.getData().toString();
                if (null != name1) {
                    desc1.append(name1 + ";");
                }
            }
        }
        history.setHistoryDesc(desc1.toString());
        historyMapper.insertSelective(history);
        //添加任务记录
        List<TaskRecord> list = new ArrayList<>();
        for (int i = 0; i < executorList.size(); i++) {
            TaskRecord record = new TaskRecord();
            record.setCreateTime(new Date(System.currentTimeMillis()));
            record.setUpdateTime(new Date(System.currentTimeMillis()));
            record.setExecutor(executorList.get(i));
            record.setExecutorName(executorNameList.get(i));
            record.setTaskId(task.getTaskId());
            record.setTaskStatus(TaskConstant.TASK_WAIT_ACCEPT);
            record.setCloseTime(DateUtil.toDate(taskReq.getCloseTime(), "yyyy-MM-dd HH:mm:ss"));
            //查询部门信息
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("empId", executorList.get(i));
            String url = ConfigManager.read(ConfigName.OA_PROJECT_URL) + "httpclientOA/task/v1/findDepByEmpId";
            Response response = HttpClientUtil.post(url, jsonParam, request);
            if (null != response && null != response.getData()) {
                JSONObject department = JSONObject.fromObject(response.getData());
                record.setDepartmentId(department.get("departmentId").toString());
                record.setDepId(department.get("depId").toString());
            }
            //查询手机号
            JSONObject jsonParam1 = new JSONObject();
            jsonParam1.put("empId", executorList.get(i));
            String url1 = ConfigManager.read(ConfigName.OA_PROJECT_URL) + "httpclientOA/task/v1/findEmpByEmpId";
            Response response1 = HttpClientUtil.post(url1, jsonParam1, request);
            String phone = null;
            if (null != response1 && null != response1.getData()) {
                JSONObject employee = JSONObject.fromObject(response1.getData());
                phone = employee.get("phoneNum").toString();
            }
            record.setExecutorPhone(phone);
            list.add(record);
        }
        if (list.size() > 0) {
            for (TaskRecord record :
                    list) {
                //任务记录入库
                recordMapper.insertSelective(record);
                //推送通知给被指派人员,通知不入库
                //创建推送模型
                SendMessage send = new SendMessage();
                send.setSendId(record.getRecordId());
                if (type.equals(1)) {
                    send.setSendTitle(TaskConstant.ADD_TASK_SEND_TITLE);
                } else if (type.equals(2)) {
                    send.setSendTitle(TaskConstant.APP_ADD_TASK_SEND_TITLE);
                }
                send.setSendNo(TaskConstant.ADD_DAILY_SENDNO.toString());
                send.setSendType(TaskConstant.PUSH_SENDTYPE);
                send.setSendContent(TaskConstant.ADD_DAILY_SEND_CONTENT);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("========SendMessage========" + send);
                }
                //转json
                String pushInfo = GsonUtil.object2Gson(send);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("========SendMessage to json========" + pushInfo);
                }
                //推送
                if (!StringUtil.isBlank(record.getExecutorPhone())) {
                    PropertyPushUtil.push(record.getExecutorPhone(), pushInfo);
                }
            }
        }
        return null;
    }

    @Override
    public List<FindAllTasksResp> findAllTasks(FindAllTasksReq tasksReq
            , Paging<FindAllTasksResp> paging
            , HttpServletRequest request) throws Exception {
        if (!StringUtil.isBlank(tasksReq.getDepartmentId())) {
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("departmentId", tasksReq.getDepartmentId());
            String url = ConfigManager.read(ConfigName.OA_PROJECT_URL) + "httpclientOA/task/v1/findDepByDepartmentId";
            Response response = HttpClientUtil.post(url, jsonParam, request);
            if (null != response && null != response.getData()) {
                JSONObject department = JSONObject.fromObject(response.getData());
                String depId = null;
                String depId1 = department.get("depId").toString();
                Integer depth = Integer.valueOf(department.get("depth").toString());
                if (depth == 1) {
                    depId = depId1.substring(0, 4);
                } else if (depth == 2) {
                    depId = depId1.substring(0, 8);
                } else if (depth == 3) {
                    depId = depId1.substring(0, 12);
                } else if (depth == 4) {
                    depId = depId1.substring(0, 16);
                } else if (depth == 5) {
                    depId = depId1.substring(0, 20);
                }
                tasksReq.setDepId(depId);
                tasksReq.setDepth(depth);
            }
        }
        return taskMapper.findAllTasks(tasksReq, paging.getRowBounds());
    }

    @Override
    public FindTaskResp findTask(FindTaskReq taskReq) {
        FindTaskResp taskResp = recordMapper.findTaskByRecordId(taskReq.getRecordId());
        List<String> pictures = recordPicMapper.findPicUrlByRecordId(taskReq.getRecordId());
        if (null != taskReq && pictures != null && pictures.size() > 0) {
            List<String> pics = new ArrayList<>();
            for (String s :
                    pictures) {
                pics.add(TaskConstant.FILE_SERVER + s);
            }
            taskResp.setPictures(pics);
        }
        return taskResp;
    }

    @Override
    public String updateTask(UpdateTaskReq taskReq, String userId, Integer type, HttpServletRequest request) throws Exception {
        String code = validateParams(taskReq, request);
        if (!StringUtil.isBlank(code)) {
            return code;
        }
        TaskRecord record1 = recordMapper.selectByPrimaryKey(taskReq.getRecordId());
        //操作记录（编辑）
        TaskHistory history = new TaskHistory();
        history.setCreateTime(new Date(System.currentTimeMillis()));
        history.setHistoryType(3);
        history.setOperatorId(userId);
        if (type.equals(1)) {
            history.setOperatorType(1);
        } else if (type.equals(2)) {
            history.setOperatorType(2);
        }
        history.setTaskId(taskReq.getTaskId());
        Task task1 = taskMapper.selectByPrimaryKey(taskReq.getTaskId());
        StringBuilder desc = new StringBuilder();
        desc.append("编辑任务");
        if (null != task1) {
            TaskType type1 = typeMapper.selectByPrimaryKey(taskReq.getTypeId());
            //判断是否修改了任务类型
            if (null != type1 && !taskReq.getTypeId().equals(task1.getTaskType())) {
                desc.append(",修改任务类型为：" + type1.getTypeName());
            }
            //判断是否修改了任务内容
            if (!task1.getTaskDesc().equals(taskReq.getTaskDesc())) {
                desc.append(",修改任务内容为：" + taskReq.getTaskDesc());
            }
            //判断是否修改了任务截止时间
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (0 != format.format(record1.getCloseTime()).compareTo(taskReq.getCloseTime())) {
                desc.append(",修改任务截止时间为：" + taskReq.getCloseTime());
            }
        }
        history.setHistoryDesc(desc.toString());
        if (!desc.toString().equals("编辑任务")) {
            historyMapper.insertSelective(history);
        }
        //操作记录（指派）
        history.setHistoryType(7);
        JSONObject jsonParam1 = new JSONObject();
        jsonParam1.put("empId", taskReq.getExecutor());
        String url1 = ConfigManager.read(ConfigName.OA_PROJECT_URL) + "httpclientOA/task/v1/findFullNameByEmpId";
        Response response = HttpClientUtil.post(url1, jsonParam1, request);
        if (null != response && null != response.getData()) {
            String name1 = response.getData().toString();
            String desc1 = null;
            //判断是否再次指派
            if (!StringUtil.isBlank(name1) && !taskReq.getExecutor().equals(record1.getExecutor())) {
                desc1 = "指派给：" + name1;
            }
            history.setHistoryDesc(desc1);
            if (!StringUtil.isBlank(desc1)) {
                historyMapper.insertSelective(history);
            }
        }
        //修改task
        Task task = new Task();
        task.setLastModifyId(userId);
        String name = null;
        if (type.equals(1)) {
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("adminId", userId);
            String url = ConfigManager.read(ConfigName.OA_PROJECT_URL) + "httpclientOA/task/v1/findAdminByAdminId";
            Response resp = HttpClientUtil.post(url, jsonParam, request);
            JSONObject admin = null;
            if (null != resp && null != resp.getData()) {
                admin = JSONObject.fromObject(resp.getData());
                if (admin != null) {
                    name = admin.get("adminName").toString();
                }
            }
        } else if (type.equals(2)) {
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("empId", userId);
            String url = ConfigManager.read(ConfigName.OA_PROJECT_URL) + "httpclientOA/task/v1/findFullNameByEmpId";
            Response resp = HttpClientUtil.post(url, jsonParam, request);
            if (null != resp && null != resp.getData()) {
                name = resp.getData().toString();
            }

            JSONObject jsonParam2 = new JSONObject();
            jsonParam2.put("empId", userId);
            String url2 = ConfigManager.read(ConfigName.OA_PROJECT_URL) + "httpclientOA/task/v1/findEmpByEmpId";
            Response response2 = HttpClientUtil.post(url2, jsonParam2, request);
            if (null != response2 && null != response2.getData()) {
                JSONObject employee = JSONObject.fromObject(response2.getData());
                task.setLastModifyPhone(employee.get("phoneNum").toString());
            }
        }
        task.setLastModifyName(name);
        task.setUpdateTime(new Date(System.currentTimeMillis()));
        task.setTaskType(taskReq.getTypeId());
        task.setTaskDesc(taskReq.getTaskDesc());
        task.setTaskId(taskReq.getTaskId());
        taskMapper.updateByPrimaryKeySelective(task);
        //修改record
        TaskRecord record = new TaskRecord();
        record.setRecordId(taskReq.getRecordId());
        record.setExecutor(taskReq.getExecutor());
        record.setExecutorName(taskReq.getExecutorName());
        record.setUpdateTime(new Date(System.currentTimeMillis()));
        record.setCloseTime(DateUtil.toDate(taskReq.getCloseTime(), "yyyy-MM-dd HH:mm:ss"));
        //查询部门信息
        JSONObject jsonParam2 = new JSONObject();
        jsonParam2.put("empId", taskReq.getExecutor());
        String url2 = ConfigManager.read(ConfigName.OA_PROJECT_URL) + "httpclientOA/task/v1/findDepByEmpId";
        Response response1 = HttpClientUtil.post(url2, jsonParam2, request);
        if (null != response1 && null != response1.getData()) {
            JSONObject department = JSONObject.fromObject(response1.getData());
            record.setDepartmentId(department.get("departmentId").toString());
            record.setDepId(department.get("depId").toString());
        }
        //执行人电话
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("empId", taskReq.getExecutor());
        String url = ConfigManager.read(ConfigName.OA_PROJECT_URL) + "httpclientOA/task/v1/findEmpByEmpId";
        Response resp = HttpClientUtil.post(url, jsonParam, request);
        String phone = null;
        if (null != resp && null != resp.getData()) {
            JSONObject employee = JSONObject.fromObject(resp.getData());
            phone = employee.get("phoneNum").toString();
        }
        record.setExecutorPhone(phone);
        recordMapper.updateByPrimaryKeySelective(record);
        //推送通知给被指派人员,通知不入库
        if (record1.getTaskStatus().equals(TaskConstant.TASK_WAIT_ACCEPT) && record1.getExecutor().equals(taskReq.getExecutor())) {
            return null;
        } else {
            //创建推送模型
            SendMessage send = new SendMessage();
            send.setSendId(record.getRecordId());
            if (type.equals(1)) {
                send.setSendTitle(TaskConstant.ASSIGN_TASK_SEND_TITLE);
            } else if (type.equals(2)) {
                send.setSendTitle(TaskConstant.APP_ASSIGN_TASK_SEND_TITLE);
            }
            send.setSendNo(TaskConstant.ADD_DAILY_SENDNO.toString());
            send.setSendType(TaskConstant.PUSH_SENDTYPE);
            send.setSendContent(TaskConstant.ADD_DAILY_SEND_CONTENT);
            if (LOG.isDebugEnabled()) {
                LOG.debug("========SendMessage========" + send);
            }
            //转json
            String pushInfo = GsonUtil.object2Gson(send);
            if (LOG.isDebugEnabled()) {
                LOG.debug("========SendMessage to json========" + pushInfo);
            }
            if (!StringUtil.isBlank(record.getExecutorPhone())) {
                //推送
                PropertyPushUtil.push(record.getExecutorPhone(), pushInfo);
            }
        }
        return null;
    }

    @Override
    public void deleteTask(DeleteTaskReq deleteTaskReq, String userId) throws Exception {
        String[] recordIdArray = deleteTaskReq.getRecordIds().split(",");
        for (String recordId :
                recordIdArray) {
            TaskRecord record = recordMapper.selectByPrimaryKey(recordId);
            if (null != record) {
                List<TaskRecord> rList = recordMapper.findByTaskId(record.getTaskId());
                //判断是否删除任务
                if (null != rList && rList.size() == 1) {
                    Task task = new Task();
                    task.setTaskId(record.getTaskId());
                    task.setIsDelete(TaskConstant.IS_DELETE);
                    taskMapper.updateByPrimaryKeySelective(task);
                    //操作记录
                    TaskHistory history = new TaskHistory();
                    history.setCreateTime(new Date(System.currentTimeMillis()));
                    history.setHistoryType(2);
                    history.setOperatorId(userId);
                    history.setOperatorType(1);
                    history.setTaskId(record.getTaskId());
                    history.setHistoryDesc("删除任务");
                    historyMapper.insertSelective(history);
                }
                //判断是否有记录图片
                List<String> picIds = recordPicMapper.findPicIdByRecordId(recordId);
                if (null != picIds && picIds.size() > 0) {
                    for (String picId :
                            picIds) {
                        SystemPicture pic = pictureMapper.selectByPrimaryKey(picId);
                        if (null != pic) {
                            //删除物理路径
                            FileUtil.deletefile(TaskConstant.FILE_PATH + pic.getPictureUrl());
                        }
                    }
                    //删除图片表
                    pictureMapper.deleteSysPicByIds(picIds);
                }
                //删除记录图片关系表
                recordPicMapper.deleteByRecordId(recordId);
                //删除记录
                recordMapper.deleteByPrimaryKey(recordId);
                //若删除的是物管人员发布的任务，则发出通知
//                Task task = taskMapper.selectByPrimaryKey(record.getTaskId());
//                String empId = task.getCreateUser();
//                JSONObject jsonParam = new JSONObject();
//                jsonParam.put("empId", empId);
//                String url = ConfigManager.read(ConfigName.OA_PROJECT_URL) + "oa/task/v1/findEmpByEmpId";
//                Response resp = HttpClientUtil.post(url, jsonParam, "smartre",null);
//                if (null != resp.getData()){
//                    String taskDesc = task.getTaskDesc();
//                    if (!StringUtil.isBlank(taskDesc) && taskDesc.length()>TaskConstant.TASK_DESC_NOTICE_LENGTH){
//                        taskDesc = task.getTaskDesc().substring(0,200);
//                    }
//                    String content = TaskConstant.DELETE_TASK_SEND_CONTENT+"任务概述："+taskDesc;
//                    push(empId,content,TaskConstant.DELETE_TASK_SENDNO,TaskConstant.DELETE_TASK_SEND_TITLE);
//                }
            }
        }
    }

    @Override
    public List<TaskType> findTaskTyps(String estateId) {
        return typeMapper.findTaskTypes(estateId);
    }

    @Override
    public List<FindAllRecordResp> findAllRecords(Paging<FindAllRecordResp> paging
            , FindAllRecordReq recordReq
            , HttpServletRequest request) throws Exception {
        List<TaskHistory> list = historyMapper.findAllRecords(paging.getRowBounds(), recordReq);
        List<FindAllRecordResp> recordList = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (TaskHistory history :
                    list) {
                FindAllRecordResp resp = new FindAllRecordResp();
                Task task = taskMapper.selectByPrimaryKey(history.getTaskId());
                if (null != task) {
                    resp.setTaskNo(task.getTaskNo());
                }
                resp.setCreateTime(history.getCreateTime());
                resp.setDescription(history.getHistoryDesc());
                //操作人员
                if (history.getOperatorType() == 1) {
                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("adminId", history.getOperatorId());
                    String url = ConfigManager.read(ConfigName.OA_PROJECT_URL) + "httpclientOA/task/v1/findAdminByAdminId";
                    Response response = HttpClientUtil.post(url, jsonParam, request);
                    JSONObject admin = null;
                    if (null != response && null != response.getData()) {
                        admin = JSONObject.fromObject(response.getData());
                        if (admin != null) {
                            resp.setOperatorName(admin.get("adminName").toString());
                        }
                    }
                } else if (history.getOperatorType() == 2) {
                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("empId", history.getOperatorId());
                    String url = ConfigManager.read(ConfigName.OA_PROJECT_URL) + "httpclientOA/task/v1/findFullNameByEmpId";
                    Response response = HttpClientUtil.post(url, jsonParam, request);
                    if (null != response && null != response.getData()) {
                        String name = response.getData().toString();
                        if (!StringUtil.isBlank(name)) {
                            resp.setOperatorName(name);
                        }
                    }
                }
                recordList.add(resp);
            }
        }
        return recordList;
    }

    @Override
    public TaskCountResp findTaskCount(TaskCountReq taskCountReq) throws Exception {
        TaskCountResp resp = new TaskCountResp();
        //判断日期筛选条件
        if (!StringUtil.isBlank(taskCountReq.getFlag())) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (taskCountReq.getFlag().equals("1")) {
                //本月
                taskCountReq.setStartTime(format.format(GetDateUtil.getCurrentMonthStartTime()));
                taskCountReq.setEndTime(format.format(GetDateUtil.getCurrentMonthEndTime()));
            } else if (taskCountReq.getFlag().equals("2")) {
                //本季
                taskCountReq.setStartTime(format.format(GetDateUtil.getCurrentQuarterStartTime()));
                taskCountReq.setEndTime(format.format(GetDateUtil.getCurrentQuarterEndTime()));
            } else if (taskCountReq.getFlag().equals("3")) {
                //本年
                taskCountReq.setStartTime(format.format(GetDateUtil.getCurrentYearStartTime()));
                taskCountReq.setEndTime(format.format(GetDateUtil.getCurrentYearEndTime()));
            }
        }
        //查询报修统计数据
        List<RepairOrder> repairOrders = repairOrderMapper.findRepairs(taskCountReq);
        if (null != repairOrders && repairOrders.size() > 0) {
            resp.setAllRepairs(repairOrders.size());
            Integer wait = 0;
            Integer accept = 0;
            Integer finish = 0;
            Integer cancel = 0;
            for (RepairOrder r :
                    repairOrders) {
                if (RepairConstant.ORDER_STATUS_START.equals(r.getOrderStatus())) {
                    wait++;
                } else if (RepairConstant.ORDER_STATUS_DOING.equals(r.getOrderStatus())) {
                    accept++;
                } else if (RepairConstant.ORDER_STATUS_END.equals(r.getOrderStatus())) {
                    finish++;
                } else if (RepairConstant.ORDER_STATUS_CANCEL.equals(r.getOrderStatus())) {
                    cancel++;
                }
            }
            resp.setAcceptRepairs(accept);
            resp.setWaitRepairs(wait);
            resp.setFinishRepairs(finish);
            resp.setCancelRepairs(cancel);
        }
        //查询保洁数据
        List<CleaningRecord> cleaningRecords = cleaningRecordMapper.findRecords(taskCountReq);
        if (null != cleaningRecords && cleaningRecords.size() > 0) {
            resp.setAllCleanings(cleaningRecords.size());
            Integer isStandard = 0;
            Integer notStandard = 0;
            for (CleaningRecord r :
                    cleaningRecords) {
                if (CleaningConstant.IS_STANDARD.equals(r.getIsStandard())) {
                    isStandard++;
                } else {
                    notStandard++;
                }
            }
            resp.setFinishCleanings(isStandard);
            resp.setUnFinishCleanings(notStandard);
        }
        //查询巡检数据
        List<PatrolRecord> patrolRecords = patrolRecordMapper.findRecords(taskCountReq);
        if (null != patrolRecords && patrolRecords.size() > 0){
            resp.setAllPatrols(patrolRecords.size());
            Integer isPatrol = 0;
            Integer notPatrol = 0;
            Integer overTime = 0;
            for (PatrolRecord r:
                 patrolRecords) {
                if (PatrolConstant.IS_PATROL.equals(r.getIsPatrol())){
                    isPatrol++;
                }
            }
            //查询逾期未巡检任务
            List<PatrolRecord> overList = patrolRecordMapper.findOverRecords(taskCountReq);
            if (null != overList && overList.size() > 0){
                overTime = overList.size();
                notPatrol = patrolRecords.size()-overTime-isPatrol;
            } else {
                notPatrol = patrolRecords.size()-isPatrol;
            }
            resp.setFinishPatrols(isPatrol);
            resp.setUnFinishPatrols(notPatrol);
            resp.setOverTimePatrols(overTime);
        }
        //查询巡逻数据
        List<SecurityRecord> securityRecords = securityRecordMapper.findRecords(taskCountReq);
        if (null != securityRecords && securityRecords.size() > 0){
            resp.setAllSecurity(securityRecords.size());
            Integer isPatrol = 0;
            Integer notPatrol = 0;
            Integer overTime = 0;
            for (SecurityRecord r:
                    securityRecords) {
                if (SecurityConstant.IS_PATROL_TWO.equals(r.getIsPatrol())){
                    isPatrol++;
                }else if (SecurityConstant.IS_PATROL_ONE.equals(r.getIsPatrol())){
                    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                    Date bt=sdf.parse(sdf.format(new Date()));
                    Date et=sdf.parse(sdf.format(r.getCreateTime()));
                    if (bt.after(et)){
                        overTime++;
                    }else{
                        notPatrol++;
                    }
                }
            }
            resp.setOverTimeSecurity(overTime);
            resp.setUnFinishSecurity(notPatrol);
            resp.setFinishSecurity(isPatrol);
        }
        return resp;
    }

    @Override
    public AppTaskCount appFindTaskCount(TaskCountReq taskCountReq) throws Exception {

        List<CleaningArea> areasList = areaMapper.findAreas(taskCountReq.getEstateId());
        StringBuilder sb = new StringBuilder();
        if(areasList != null && areasList.size() > 0){
            for (CleaningArea ca : areasList){
                sb.append(ca.getAreaId()).append(",");
            }
        }
       taskCountReq.setAreaId(sb.toString());
        AppTaskCount resp = new AppTaskCount();
        //判断日期筛选条件
        if (!StringUtil.isBlank(taskCountReq.getFlag())) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (taskCountReq.getFlag().equals("1")) {
                //本月
                taskCountReq.setStartTime(format.format(GetDateUtil.getCurrentMonthStartTime()));
                taskCountReq.setEndTime(format.format(GetDateUtil.getCurrentMonthEndTime()));
            } else if (taskCountReq.getFlag().equals("2")) {
                //本季
                taskCountReq.setStartTime(format.format(GetDateUtil.getCurrentQuarterStartTime()));
                taskCountReq.setEndTime(format.format(GetDateUtil.getCurrentQuarterEndTime()));
            } else if (taskCountReq.getFlag().equals("3")) {
                //本年
                taskCountReq.setStartTime(format.format(GetDateUtil.getCurrentYearStartTime()));
                taskCountReq.setEndTime(format.format(GetDateUtil.getCurrentYearEndTime()));
            }
        }
        //查询报修统计数据
        List<RepairOrder> repairOrders = repairOrderMapper.findRepairs(taskCountReq);
        if (null != repairOrders && repairOrders.size() > 0) {
            resp.setAllRepairs(repairOrders.size());
            Integer wait = 0;
            Integer accept = 0;
            Integer finish = 0;
            Integer cancel = 0;
            for (RepairOrder r :
                    repairOrders) {
                if (RepairConstant.ORDER_STATUS_START.equals(r.getOrderStatus())) {
                    wait++;
                } else if (RepairConstant.ORDER_STATUS_DOING.equals(r.getOrderStatus())) {
                    accept++;
                } else if (RepairConstant.ORDER_STATUS_END.equals(r.getOrderStatus())) {
                    finish++;
                } else if (RepairConstant.ORDER_STATUS_CANCEL.equals(r.getOrderStatus())) {
                    cancel++;
                }
            }
            resp.setAcceptRepairs(accept);
            resp.setWaitRepairs(wait);
            resp.setFinishRepairs(finish);
            resp.setCancelRepairs(cancel);
        }


        //查询所有保洁数据
        if(!StringUtils.isEmpty(taskCountReq.getAreaId())){
            String[] areas = taskCountReq.getAreaId().split(",");
            if(areas.length > 0){
                List<CleanResp> cleans = new ArrayList<>();
                for(String id : areas){
                    taskCountReq.setAreaId(id);
                    List<CleaningRecord> cleaningRecords = cleaningRecordMapper.findRecords(taskCountReq);
                    if (null != cleaningRecords && cleaningRecords.size() > 0) {
                        CleanResp cr = new CleanResp();
                        cr.setAreaId(id);
                        cr.setAllCleanings(cleaningRecords.size());
                        Integer isStandard = 0;
                        Integer notStandard = 0;
                        for (CleaningRecord r :
                                cleaningRecords) {
                            if (CleaningConstant.IS_STANDARD.equals(r.getIsStandard())) {
                                isStandard++;
                            } else {
                                notStandard++;
                            }
                        }
                        cr.setFinishCleanings(isStandard);
                        cr.setUnFinishCleanings(notStandard);
                        for (CleaningArea ca : areasList){
                            if(ca.getAreaId().equals(id)){
                                // 设置区域名称
                                cr.setAreaName(ca.getAreaName());
                            }
                        }
                        cleans.add(cr);
                    }else {
                        // 保洁区域没有任务，设置默认值
                        CleanResp cr = new CleanResp();
                        for (CleaningArea ca : areasList){
                            if(ca.getAreaId().equals(id)){
                                // 设置区域名称
                                cr.setAreaName(ca.getAreaName());
                            }
                        }
                        cleans.add(cr);
                    }
                }
                resp.setCleanResps(cleans);
            }
            taskCountReq.setAreaId(null);
            List<CleaningRecord> cleaningRecords = cleaningRecordMapper.findRecords(taskCountReq);
            if (null != cleaningRecords && cleaningRecords.size() > 0) {
                resp.setAllCleanings(cleaningRecords.size());
                Integer isStandard = 0;
                Integer notStandard = 0;
                for (CleaningRecord r :
                        cleaningRecords) {
                    if (CleaningConstant.IS_STANDARD.equals(r.getIsStandard())) {
                        isStandard++;
                    } else {
                        notStandard++;
                    }
                }
                resp.setFinishCleanings(isStandard);
                resp.setUnFinishCleanings(notStandard);
            }
        }
        //查询巡检数据
        List<PatrolRecord> patrolRecords = patrolRecordMapper.findRecords(taskCountReq);
        if (null != patrolRecords && patrolRecords.size() > 0){
            resp.setAllPatrols(patrolRecords.size());
            Integer isPatrol = 0;
            Integer notPatrol = 0;
            Integer overTime = 0;
            for (PatrolRecord r:
                    patrolRecords) {
                if (PatrolConstant.IS_PATROL.equals(r.getIsPatrol())){
                    isPatrol++;
                }
            }
            //查询逾期未巡检任务
            List<PatrolRecord> overList = patrolRecordMapper.findOverRecords(taskCountReq);
            if (null != overList && overList.size() > 0){
                overTime = overList.size();
                notPatrol = patrolRecords.size()-overTime-isPatrol;
            } else {
                notPatrol = patrolRecords.size()-isPatrol;
            }
            resp.setFinishPatrols(isPatrol);
            resp.setUnFinishPatrols(notPatrol);
            resp.setOverTimePatrols(overTime);
        }
        //查询巡逻数据
        List<SecurityRecord> securityRecords = securityRecordMapper.findRecords(taskCountReq);
        if (null != securityRecords && securityRecords.size() > 0){
            resp.setAllSecurity(securityRecords.size());
            Integer isPatrol = 0;
            Integer notPatrol = 0;
            Integer overTime = 0;
            for (SecurityRecord r:
                    securityRecords) {
                if (SecurityConstant.IS_PATROL_TWO.equals(r.getIsPatrol())){
                    isPatrol++;
                }else if (SecurityConstant.IS_PATROL_ONE.equals(r.getIsPatrol())){
                    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                    Date bt=sdf.parse(sdf.format(new Date()));
                    Date et=sdf.parse(sdf.format(r.getCreateTime()));
                    if (bt.after(et)){
                        overTime++;
                    }else{
                        notPatrol++;
                    }
                }
            }
            resp.setOverTimeSecurity(overTime);
            resp.setUnFinishSecurity(notPatrol);
            resp.setFinishSecurity(isPatrol);
        }
        return resp;
    }


    /**
     * 校验编辑入参
     *
     * @param taskReq
     * @return
     */
    private String validateParams(UpdateTaskReq taskReq, HttpServletRequest request) throws Exception {
        if (StringUtil.isBlank(taskReq.getCloseTime())) {
            return TaskCode.CLOSE_TIME_IS_NULL;
        }
        if (StringUtil.isBlank(taskReq.getTypeId())) {
            return TaskCode.TASK_TYPE_NULL;
        }
        if (null == typeMapper.selectByPrimaryKey(taskReq.getTypeId())) {
            return TaskCode.TASK_TYPE_DELETE;
        }
        if (StringUtil.isBlank(taskReq.getTaskDesc())) {
            return TaskCode.TASK_DESC_NULL;
        }
        taskReq.setTaskDesc(taskReq.getTaskDesc().trim());
        if (taskReq.getTaskDesc().length() > TaskConstant.TASK_DESC_MAX_LENGTH) {
            return TaskCode.TASK_DESC_RULE;
        }
        if (StringUtil.isBlank(taskReq.getExecutor()) || StringUtil.isBlank(taskReq.getExecutorName())) {
            return TaskCode.EXECUTOR_NULL;
        }
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("empId", taskReq.getExecutor());
        String url = ConfigManager.read(ConfigName.OA_PROJECT_URL) + "httpclientOA/task/v1/findEmpByEmpId";
        Response response = HttpClientUtil.post(url, jsonParam, request);
        if (null != response && null == response.getData()) {
            return TaskCode.EXECUTOR_DELETE;
        }
        TaskRecord record = recordMapper.selectByPrimaryKey(taskReq.getRecordId());
        if (null == record) {
            return TaskCode.TASK_DELETE;
        }
        //判断是否指派给了除当前编辑指派人员以外的人员
        List<TaskRecord> records = recordMapper.findByTaskId(taskReq.getTaskId());
        if (null != records && records.size() > 0) {
            for (TaskRecord re :
                    records) {
                if (!re.getRecordId().equals(taskReq.getRecordId()) && re.getExecutor().equals(taskReq.getExecutor())) {
                    return TaskCode.TASK_IS_SAME;
                }
            }
        }
        if (taskReq.getType().equals(TaskConstant.UPDATE_TASK_TYPE)) {
            if (null != record && record.getTaskStatus().equals(TaskConstant.TASK_IS_DOING)) {
                return TaskCode.TASK_STATUS_DOING;
            }
            if (null != record && record.getTaskStatus().equals(TaskConstant.TASK_IS_FINISHED)) {
                return TaskCode.TASK_STATUS_FINISH;
            }
            if (null != record && record.getTaskStatus().equals(TaskConstant.TASK_OVER_TIME)) {
                return TaskCode.TASK_STATUS_OVERTIME;
            }
        }
        return null;
    }
}
