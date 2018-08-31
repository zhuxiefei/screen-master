package com.betel.estatemgmt.business.propertyapp.task.service.impl;

import com.betel.estatemgmt.business.propertyapp.check.code.CheckCode;
import com.betel.estatemgmt.business.propertyapp.task.code.TaskCode;
import com.betel.estatemgmt.business.propertyapp.task.constant.TaskConstant;
import com.betel.estatemgmt.business.propertyapp.task.model.*;
import com.betel.estatemgmt.business.propertyapp.task.service.TaskService;
import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.business.web.task.model.FindTaskReq;
import com.betel.estatemgmt.common.ConfigManager;
import com.betel.estatemgmt.common.RedisManager;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.SendMessage;
import com.betel.estatemgmt.common.mapper.admin.AdminMapper;
import com.betel.estatemgmt.common.mapper.system.NoticeMapper;
import com.betel.estatemgmt.common.mapper.system.SystemPictureMapper;
import com.betel.estatemgmt.common.mapper.work.*;
import com.betel.estatemgmt.common.model.admin.Admin;
import com.betel.estatemgmt.common.model.system.Notice;
import com.betel.estatemgmt.common.model.work.Task;
import com.betel.estatemgmt.common.model.work.TaskHistory;
import com.betel.estatemgmt.common.model.work.TaskRecord;
import com.betel.estatemgmt.common.model.work.TaskRecordPic;
import com.betel.estatemgmt.common.msgpush.MsgPushUtils;
import com.betel.estatemgmt.common.msgpush.PropertyPushUtil;
import com.betel.estatemgmt.utils.GsonUtil;
import com.betel.estatemgmt.utils.HttpClientUtil;
import com.betel.estatemgmt.utils.StringUtil;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: TaskServiceImpl <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/12/21 17:25 <br/>
 * Version: 1.0 <br/>
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TaskServiceImpl implements TaskService {

    private static final Logger LOG = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Autowired
    TaskMapper taskMapper;

    @Autowired
    TaskTypeMapper typeMapper;

    @Autowired
    TaskRecordMapper recordMapper;

    @Autowired
    TaskRecordPicMapper recordPicMapper;

    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    SystemPictureMapper pictureMapper;

    @Autowired
    TaskHistoryMapper historyMapper;

    @Autowired
    AdminMapper adminMapper;

    @Override
    public List<FindAllTasksResp> findAllTasks(FindAllTasksReq req, Paging<FindAllTasksResp> paging) {
        return recordMapper.findAllAppTasks(req,paging.getRowBounds());
    }

    @Override
    public FindTaskResp findTask(FindTaskReq req, HttpServletRequest request) throws Exception{
        FindTaskResp resp = recordMapper.findAppByRecordId(req.getRecordId());
        List<String> pictures = recordPicMapper.findPicUrlByRecordId(req.getRecordId());
        if (null != req){
            //完成图片
            if (pictures!=null && pictures.size()>0) {
                List<String> pics = new ArrayList<>();
                for (String s:
                        pictures) {
                    pics.add(TaskConstant.FILE_SERVER+s);
                }
                resp.setPictures(pics);
            }
            //头像
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("empId",resp==null?"":resp.getExecutor());
            String url = ConfigManager.read(ConfigName.OA_PROJECT_URL) + "httpclientOA/task/v1/findHeadByEmpId";
            Response response = HttpClientUtil.post(url, jsonParam, request);
            if (null != response && null != response.getData()){
                String head = response.getData().toString();
                if (!StringUtil.isBlank(head)){
                    resp.setHead(TaskConstant.FILE_SERVER+head);
                }
            }
        }
        return resp;
    }

    @Override
    public String doTask(DoTaskReq taskReq,HttpServletRequest request) throws Exception{
        String code = validateDoParams(taskReq);
        if (!StringUtil.isBlank(code)){
            return code;
        }
        TaskRecord record = new TaskRecord();
        record.setRecordId(taskReq.getRecordId());
        //获取任务发布人ID和手机号，用于通知
        TaskRecord record1 = recordMapper.selectByPrimaryKey(taskReq.getRecordId());
        Task task = taskMapper.selectByPrimaryKey(record1.getTaskId());
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("empId",record1.getExecutor());
        String url = ConfigManager.read(ConfigName.OA_PROJECT_URL) + "httpclientOA/task/v1/findEmpByEmpId";
        Response response = HttpClientUtil.post(url, jsonParam, request);
        String executorName = null;
        if (null != response){
            JSONObject employee = JSONObject.fromObject(response.getData());
            executorName = employee.getString("employeeName").toString();
        }
        String userId = task.getCreateUser();
        String taskDesc = task.getTaskDesc();
        if (!StringUtil.isBlank(taskDesc) && taskDesc.length()>TaskConstant.TASK_DESC_NOTICE_LENGTH){
            taskDesc = task.getTaskDesc().substring(0,200)+"...";
        }
        //操作记录
        TaskHistory history = new TaskHistory();
        history.setCreateTime(new Date(System.currentTimeMillis()));
        history.setOperatorId(record1.getExecutor());
        history.setOperatorType(2);
        history.setTaskId(record1.getTaskId());
        //判断发布人是否为后台管理员，若是则不发通知
        boolean isAdmin = false;
        Admin admin = adminMapper.selectByPrimaryKey(userId);
        if (null != admin){
            isAdmin = true;
        }
        //接受任务
        if (taskReq.getType().equals(TaskConstant.DO_ACCEPT)){
            history.setHistoryType(6);
            history.setHistoryDesc("接受任务");
            record.setTaskStatus(TaskConstant.TASK_IS_DOING);
            record.setAcceptTime(new Date(System.currentTimeMillis()));
            if (!isAdmin) {
                String content = TaskConstant.ACCEPT_TASK_SEND_CONTENT + executorName + "接受，任务概述：" + taskDesc;
                push(userId, content, TaskConstant.ACCEPT_TASK_SENDNO, TaskConstant.ACCEPT_TASK_SEND_TITLE, request);
            }
        }
        //完成任务
        if (taskReq.getType().equals(TaskConstant.DO_FINISH)){
            history.setHistoryType(4);
            history.setHistoryDesc("完成任务");
            record.setTaskStatus(TaskConstant.TASK_IS_FINISHED);
            record.setFinishTime(new Date(System.currentTimeMillis()));
            //添加完成图片
            String[] picIdArray = taskReq.getPicIds().split(",");
            List<TaskRecordPic> recordPicList = new ArrayList<>();
            for (String s:
                 picIdArray) {
                TaskRecordPic pic = new TaskRecordPic();
                pic.setRecordId(taskReq.getRecordId());
                pic.setPictureId(s);
                pic.setCreateTime(new Date(System.currentTimeMillis()));
                pic.setUpdateTime(new Date(System.currentTimeMillis()));
                recordPicList.add(pic);
            }
            if (recordPicList.size()>0){
                recordPicMapper.insertList(recordPicList);
            }
            if (!isAdmin) {
                //通知
                String content = TaskConstant.FINISH_TASKSEND_CONTENT + executorName + "完成，任务概述：" + taskDesc;
                push(userId, content, TaskConstant.FINISH_TASK_SENDNO, TaskConstant.FINISH_TASK_SEND_TITLE, request);
            }
        }
        historyMapper.insertSelective(history);
        recordMapper.updateByPrimaryKeySelective(record);
        return null;
    }

    @Override
    public String deleteTask(DeleteRecordReq recordReq) {
        TaskRecord record = recordMapper.selectByPrimaryKey(recordReq.getRecordId());
        if (null != record){
            //判断是否已完成
            if (record.getTaskStatus().equals(TaskConstant.TASK_IS_DOING)){
                return TaskCode.TASK_STATUS_DOING;
            }
            if (record.getTaskStatus().equals(TaskConstant.TASK_WAIT_ACCEPT)){
                return TaskCode.TASK_STATUS_WAIT;
            }
            //删除记录
            TaskRecord taskRecord = new TaskRecord();
            taskRecord.setRecordId(recordReq.getRecordId());
            if (recordReq.getFlag().equals("1")){
                taskRecord.setExecutorDelete(1);
            }else if (recordReq.getFlag().equals("2")){
                taskRecord.setCreateUserDelete(1);
            }
            recordMapper.deleteAppRecord(taskRecord);
        }
        return null;
    }

    /**
     * 校验执行任务入参
     * @param req
     * @return
     */
    private String validateDoParams(DoTaskReq req){
        TaskRecord record = recordMapper.selectByPrimaryKey(req.getRecordId());
        if (null == record){
            return TaskCode.TASK_DELETE;
        }
        //接受任务则判断任务状态是否是待接受
        if (req.getType().equals(TaskConstant.DO_ACCEPT)){
            if (record.getTaskStatus().equals(TaskConstant.TASK_IS_DOING)){
                return TaskCode.TASK_STATUS_DOING;
            }
            if (record.getTaskStatus().equals(TaskConstant.TASK_IS_FINISHED)){
                return TaskCode.TASK_STATUS_FINISH;
            }
            if (record.getTaskStatus().equals(TaskConstant.TASK_IS_OVER)){
                return TaskCode.TASK_STATUS_OVERTIME;
            }
            //判断任务是否指派给别人
            if (!req.getUserId().equals(record.getExecutor())){
                return TaskCode.TASK_ASSIGN_OTHER;
            }
        }
        //完成任务则判断任务状态是否是进行中
        if (req.getType().equals(TaskConstant.DO_FINISH)){
            if (record.getTaskStatus().equals(TaskConstant.TASK_WAIT_ACCEPT)){
                return TaskCode.TASK_STATUS_WAIT;
            }
            if (record.getTaskStatus().equals(TaskConstant.TASK_IS_FINISHED)){
                return TaskCode.TASK_STATUS_FINISH;
            }
            if (record.getTaskStatus().equals(TaskConstant.TASK_IS_OVER)){
                return TaskCode.TASK_STATUS_OVERTIME;
            }
            if (StringUtil.isBlank(req.getPicIds())){
                return CheckCode.IMAGE_NULL;
            }
            String[] picIdArray = req.getPicIds().split(",");
            if (picIdArray.length > TaskConstant.PIC_MAX_SIZE){
                return CheckCode.IMAGE_OUT_OF_NUM;
            }
        }
        return null;
    }

    /**
     * 推送通知
     * @param noticeUserId 通知人id（个推）
     * @param content 通知内容
     * @param sendNo 通知编号
     * @param sendTitle 通知标题
     */
    public void push(String noticeUserId, String content, Integer sendNo, String sendTitle,HttpServletRequest request) throws Exception{
        //创建系统通知对象，将通知存到数据库
        Notice notice = new Notice();
        notice.setNoticeStatus(1);
        notice.setNoticeType(sendNo);
        notice.setCreateTime(new Date(System.currentTimeMillis()));
        notice.setNoticeUserId(noticeUserId);
        notice.setNoticeContent(content);
        if (LOG.isDebugEnabled()) {
            LOG.debug("========before addNotice========notice=" + notice);
        }
        noticeMapper.insertSelective(notice);
        //返回主键
        Long noticeId = notice.getNoticeId();
        if (LOG.isDebugEnabled()) {
            LOG.debug("========after addNotice========notice=" + notice);
        }
        SendMessage send = new SendMessage();
        send.setSendId(noticeId.toString());
        send.setSendTitle(sendTitle);
        send.setSendNo(sendNo.toString());
        send.setSendType(TaskConstant.PUSH_SENDTYPE);
        send.setSendContent(content);
        if (LOG.isDebugEnabled()) {
            LOG.debug("========SendMessage========" + send);
        }
        //转json
        String pushInfo = GsonUtil.object2Gson(send);
        if (LOG.isDebugEnabled()) {
            LOG.debug("========SendMessage to json========" + pushInfo);
        }
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("empId",noticeUserId);
        String url = ConfigManager.read(ConfigName.OA_PROJECT_URL) + "httpclientOA/task/v1/findEmpByEmpId";
        Response response = HttpClientUtil.post(url, jsonParam, request);
        String phone = null;
        if (null != response){
            JSONObject employee = JSONObject.fromObject(response.getData());
            phone = employee.getString("phoneNum").toString();
        }
        if(phone != null){
            //推送
            PropertyPushUtil.push(phone,pushInfo);
        }
    }
}
