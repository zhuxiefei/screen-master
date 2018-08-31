package com.betel.estatemgmt.business.web.task.controller;

import com.betel.estatemgmt.business.web.cleaning.service.CleaningService;
import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.business.web.task.code.TaskCode;
import com.betel.estatemgmt.business.web.task.constant.TaskConstant;
import com.betel.estatemgmt.business.web.task.model.*;
import com.betel.estatemgmt.business.web.task.model.DeptReq;
import com.betel.estatemgmt.business.web.task.model.FindAllRecordReq;
import com.betel.estatemgmt.business.web.task.model.FindAllRecordResp;
import com.betel.estatemgmt.business.web.task.model.FindExecutorReq;
import com.betel.estatemgmt.business.web.task.service.TaskService;
import com.betel.estatemgmt.business.web.task.util.WorkUtil;
import com.betel.estatemgmt.common.*;
import com.betel.estatemgmt.common.model.cleaning.CleaningArea;
import com.betel.estatemgmt.common.model.work.TaskType;
import com.betel.estatemgmt.common.returncode.GlobalCode;
import com.betel.estatemgmt.utils.HttpClientUtil;
import com.betel.estatemgmt.utils.StringUtil;
import com.betel.estatemgmt.utils.Validate;
import com.betel.estatemgmt.utils.encrypt.AESUtil;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import net.sf.json.JSONObject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static com.betel.estatemgmt.common.StatusCode.FAILURE;

/**
 * <p>
 * 任务接口
 * </p>
 * ClassName: TaskController <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/12/21 8:56 <br/>
 * Version: 1.0 <br/>
 */
@RestController("webTask")
@RequestMapping("web/task")
public class TaskController extends BaseController {

    private static Logger LOG = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private TaskService taskService;

    @Autowired
    private CleaningService cleaningService;

    /**
     * <p>
     * 添加任务类型
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/12/5 9:06
     *
     * @param typeReq
     * @return
     */
    @RequiresPermissions("taskType-addTaskType")
    @RequestMapping(value = "v1/addTaskType", method = RequestMethod.POST, consumes = "application/json")
    public Response<String> addTaskType(@RequestBody AddTaskTypeReq typeReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/task/v1/addTaskType------start-" + typeReq);
        }
        Response<String> response = new Response<>();

        if (StringUtil.isBlank(typeReq.getTypeName())) {
            response.setCode(TaskCode.TASK_TYPE_NULL);
        } else if (!Validate.isCommonString(typeReq.getTypeName().trim(), TaskConstant.TYPE_MAX_LENGTH)) {
            response.setCode(TaskCode.TASK_TYPE_RULE);
        } else {
            try {
                String estateId = AESUtil.decrypt(request.getHeader("estateId"));
                //去空格
                typeReq.setEstateId(estateId);
                typeReq.setTypeName(typeReq.getTypeName().trim());
                String code = taskService.addTaskType(typeReq);
                if (!StringUtil.isBlank(code)) {
                    response.setCode(code);
                }
            } catch (Exception e) {
                LOG.error("----web/task/v1/addTaskType----error---", e);
                response.setCode(StatusCode.FAILURE);
            }
        }

        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/task/v1/addTaskType------end-" + response);
        }
        return response;
    }

    /**
     * <p>
     * 修改工作任务类型
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/12/21 9:54
     *
     * @param typeReq
     * @return
     */
    @RequiresPermissions("taskType-updateTaskType")
    @RequestMapping(value = "v1/updateTaskType", method = RequestMethod.POST, consumes = "application/json")
    public Response<String> updateTaskType(@RequestBody UpdateTaskTypeReq typeReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/task/v1/updateTaskType------start-" + typeReq);
        }
        Response<String> response = new Response<>();
        if (StringUtil.isBlank(typeReq.getTypeName())) {
            response.setCode(TaskCode.TASK_TYPE_NULL);
        } else if (!Validate.isCommonString(typeReq.getTypeName().trim(), TaskConstant.TYPE_MAX_LENGTH)) {
            response.setCode(TaskCode.TASK_TYPE_RULE);
        } else {
            try {
                //去空格
                String estateId = AESUtil.decrypt(request.getHeader("estateId"));
                if (StringUtil.isBlank(estateId)) {
                    response.setCode(GlobalCode.ESTATE_ID_IS_NULL);
                } else {
                    typeReq.setTypeName(typeReq.getTypeName().trim());
                    typeReq.setEstateId(estateId);
                    String code = taskService.updateTaskType(typeReq);
                    if (!StringUtil.isBlank(code)) {
                        response.setCode(code);
                    }
                }
            } catch (Exception e) {
                LOG.error("----web/task/v1/updateTaskType----error---", e);
                response.setCode(StatusCode.FAILURE);
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/task/v1/updateTaskType------end-" + response);
        }
        return response;
    }

    /**
     * <p>
     * 删除工作类型（批量）
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/12/21 10:02
     *
     * @param typeReq
     * @return
     */
    @RequiresPermissions("taskType-deleteTaskType")
    @RequestMapping(value = "v1/deleteTaskType", method = RequestMethod.POST, consumes = "application/json")
    public Response<String> deleteTaskType(@RequestBody DeleteTaskTypeReq typeReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/task/v1/deleteTaskType------start-" + typeReq);
        }
        Response<String> response = new Response<>();
        try {
            taskService.deleteTaskType(typeReq);
        } catch (Exception e) {
            LOG.error("----web/task/v1/deleteTaskType----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/task/v1/deleteTaskType------end-" + response);
        }
        return response;
    }

    /**
     * <p>
     * 分页搜索查询任务类型列表
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/12/21 10:11
     *
     * @param page
     * @return
     */
    @RequiresPermissions("taskType-findAllTaskTypes")
    @RequestMapping(value = "v1/findAllTaskTypes", method = RequestMethod.POST, consumes = "application/json")
    public Response<Paging<TaskType>> findAllTaskTypes(@RequestBody Page page, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/task/v1/findAllTaskTypes------start-" + page);
        }
        Response<Paging<TaskType>> response = new Response<>();
        Paging<TaskType> pager = new Paging<>(page.getCurPage(), page.getPageSize());
        try {
            String estateId = AESUtil.decrypt(request.getHeader("estateId"));
            if (StringUtil.isBlank(estateId)) {
                response.setCode(GlobalCode.ESTATE_ID_IS_NULL);
            } else {
                pager.result(taskService.findAllTaskTypes(pager, estateId));
                response.setData(pager);
            }
        } catch (Exception e) {
            LOG.error("----web/task/v1/findAllTaskTypes----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/task/v1/findAllTaskTypes------end-" + response);
        }
        return response;
    }

    /**
     * <p>
     * 查询任务类型详情
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/12/21 10:24
     *
     * @param typeReq
     * @return
     */
    @RequiresPermissions("taskType-findTaskType")
    @RequestMapping(value = "v1/findTaskType", method = RequestMethod.POST, consumes = "application/json")
    public Response<TaskType> findTaskType(@RequestBody FindTaskTypeReq typeReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/task/v1/findTaskType------start-" + typeReq);
        }
        Response<TaskType> response = new Response<>();
        try {
            TaskType taskType = taskService.findTaskType(typeReq);
            if (null == taskType) {
                response.setCode(TaskCode.TASK_TYPE_DELETE);
            } else {
                response.setData(taskType);
            }
        } catch (Exception e) {
            LOG.error("----web/task/v1/findTaskType----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/task/v1/findTaskType------end-" + response);
        }
        return response;
    }

    /**
     * <p>
     * 添加任务
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/12/21 10:33
     *
     * @param taskReq
     * @param request
     * @return
     */
    @RequiresPermissions("task-addTask")
    @RequestMapping(value = "v1/addTask", method = RequestMethod.POST, consumes = "application/json")
    public Response<String> addTask(@RequestBody AddTaskReq taskReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/task/v1/addTask------start-" + taskReq);
        }
        Response<String> response = new Response<>();
        //获取用户ID
        String userId = request.getHeader("userId");
        if (userId == null) {
            //用户没有登录
            response.setCode(StatusCode.UNAUTHORIZED);
            return response;
        }

        try {
            String estateId = AESUtil.decrypt(request.getHeader("estateId"));
            taskReq.setEstateId(estateId);
            String code = taskService.addTask(taskReq, AESUtil.decrypt(userId), 1, request);
            if (!StringUtil.isBlank(code)) {
                response.setCode(code);
            }
        } catch (Exception e) {
            LOG.error("----web/task/v1/addTask----error---", e);
            response.setCode(StatusCode.FAILURE);
        }


        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/task/v1/addTask------end-" + response);
        }
        return response;
    }

    /**
     * <p>
     * 分页查询任务列表
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/12/21 11:36
     *
     * @param taskReq
     * @return
     */
    @RequiresPermissions("task-findAllTasks")
    @RequestMapping(value = "v1/findAllTasks", method = RequestMethod.POST, consumes = "application/json")
    public Response<Paging<FindAllTasksResp>> findAllTasks(@RequestBody FindAllTasksReq taskReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/task/v1/findAllTasks------start-" + taskReq);
        }
        Response<Paging<FindAllTasksResp>> response = new Response<>();
        Paging<FindAllTasksResp> pager = new Paging<>(taskReq.getCurPage(), taskReq.getPageSize());
        try {
            //校验
            String code = validateTaskParam(taskReq, request);
            if (!StringUtil.isBlank(code)) {
                response.setCode(code);
                return response;
            }
            String estateId = AESUtil.decrypt(request.getHeader("estateId"));
            if (StringUtil.isBlank(estateId)) {
                response.setCode(GlobalCode.ESTATE_ID_IS_NULL);
            } else {
                taskReq.setEstateId(estateId);
                pager.result(taskService.findAllTasks(taskReq, pager, request));
                response.setData(pager);
            }
        } catch (Exception e) {
            LOG.error("----web/task/v1/findAllTasks----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/task/v1/findAllTasks------end-" + response);
        }
        return response;
    }

    /**
     * 校验任务列表入参
     *
     * @param taskReq
     * @return
     */
    private String validateTaskParam(FindAllTasksReq taskReq, HttpServletRequest request) throws Exception {
        //去空格
        if (!StringUtil.isEmpty(taskReq.getCreateUserName())) {
            taskReq.setCreateUserName(taskReq.getCreateUserName().trim());
        }
        if (!StringUtil.isEmpty(taskReq.getExecutorName())) {
            taskReq.setExecutorName(taskReq.getExecutorName().trim());
        }
        if (!StringUtil.isEmpty(taskReq.getTaskNo())) {
            taskReq.setTaskNo(taskReq.getTaskNo().trim());
        }
        //校验
        if (!StringUtil.isBlank(taskReq.getExecutorName()) && !Validate.isCommonString(taskReq.getExecutorName(), TaskConstant.NAME_MAX_LENGTH)) {
            return TaskCode.EXECUTOR_RULE;
        }
        if (!StringUtil.isBlank(taskReq.getCreateUserName()) && !Validate.isCommonString(taskReq.getCreateUserName(), TaskConstant.NAME_MAX_LENGTH)) {
            return TaskCode.CREATE_USER_RULE;
        }
        if (!StringUtil.isBlank(taskReq.getStartTime()) && !WorkUtil.validateTime(taskReq.getStartTime().trim())) {
            return TaskCode.TIME_RULE;
        }
        if (!StringUtil.isBlank(taskReq.getEndTime()) && !WorkUtil.validateTime(taskReq.getEndTime().trim())) {
            return TaskCode.TIME_RULE;
        }
        if (!StringUtil.isBlank(taskReq.getStartTime()) && !StringUtil.isBlank(taskReq.getEndTime())
                && WorkUtil.validateTime(taskReq.getStartTime().trim()) && WorkUtil.validateTime(taskReq.getEndTime().trim())
                && !WorkUtil.validateTimeDistance(taskReq.getStartTime(), taskReq.getEndTime())) {
            return TaskCode.START_LATE_END;
        }
        if (!StringUtil.isBlank(taskReq.getDepId())) {
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("departmentId", taskReq.getDepId());
            String url = ConfigManager.read(ConfigName.OA_PROJECT_URL) + "httpclientOA/task/v1/findDepByDepartmentId";
            Response response = HttpClientUtil.post(url, jsonParam, request);
            if (null != response && !StringUtil.isBlank(response.getCode()) && !response.getCode().equals(StatusCode.SUCCESS)) {
                return response.getCode();
            }
        }
        //任务类型
        if (!StringUtil.isBlank(taskReq.getTaskType())) {
            FindTaskTypeReq req = new FindTaskTypeReq();
            req.setTypeId(taskReq.getTaskType());
            if (null == taskService.findTaskType(req)) {
                return TaskCode.TASK_TYPE_DELETE;
            }
        }
        //任务编号
        if (!StringUtil.isBlank(taskReq.getTaskNo()) && !Validate.isCommonString(taskReq.getTaskNo(), TaskConstant.TASK_NO_MAX)) {
            return TaskCode.TASK_NO_RULE;
        }
        if (!StringUtil.isBlank(taskReq.getTaskNo()) && taskReq.getTaskNo().contains("_")) {
            taskReq.setTaskNo(taskReq.getTaskNo().replace("_", "\\_"));
        }
        if (!StringUtil.isBlank(taskReq.getCreateUserName()) && taskReq.getCreateUserName().contains("_")) {
            taskReq.setCreateUserName(taskReq.getCreateUserName().replace("_", "\\_"));
        }
        if (!StringUtil.isBlank(taskReq.getExecutorName()) && taskReq.getExecutorName().contains("_")) {
            taskReq.setExecutorName(taskReq.getExecutorName().replace("_", "\\_"));
        }
        return null;
    }

    /**
     * <p>
     * 查询任务详情
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/12/21 13:40
     *
     * @param taskReq
     * @return
     */
    @RequiresPermissions("task-findTask")
    @RequestMapping(value = "v1/findTask", method = RequestMethod.POST, consumes = "application/json")
    public Response<FindTaskResp> findTask(@RequestBody FindTaskReq taskReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/task/v1/findTask------start-" + taskReq);
        }
        Response<FindTaskResp> response = new Response<>();
        try {
            //校验
            FindTaskResp resp = taskService.findTask(taskReq);
            if (null == resp) {
                response.setCode(TaskCode.TASK_DELETE);
            } else {
                response.setData(resp);
            }
        } catch (Exception e) {
            LOG.error("----web/task/v1/findTask----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/task/v1/findTask------end-" + response);
        }
        return response;
    }

    /**
     * <p>
     * 修改任务
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/12/21 14:46
     *
     * @param taskReq
     * @return
     */
    @RequiresPermissions("task-updateTask")
    @RequestMapping(value = "v1/updateTask", method = RequestMethod.POST, consumes = "application/json")
    public Response<String> updateTask(@RequestBody UpdateTaskReq taskReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/task/v1/updateTask------start-" + taskReq);
        }
        Response<String> response = new Response<>();
        //获取用户ID
        String userId = request.getHeader("userId");
        if (userId == null) {
            //用户没有登录
            response.setCode(StatusCode.UNAUTHORIZED);
            return response;
        }
        try {
            String code = taskService.updateTask(taskReq, AESUtil.decrypt(userId), 1, request);
            if (!StringUtil.isBlank(code)) {
                response.setCode(code);
            }
        } catch (Exception e) {
            LOG.error("----web/task/v1/updateTask----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/task/v1/updateTask------end-" + response);
        }
        return response;
    }

    /**
     * <p>
     * 删除任务记录（批量）
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/12/21 16:04
     *
     * @param taskReq
     * @return
     */
    @RequiresPermissions("task-deleteTask")
    @RequestMapping(value = "v1/deleteTask", method = RequestMethod.POST, consumes = "application/json")
    public Response<String> deleteTask(@RequestBody DeleteTaskReq taskReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/task/v1/deleteTask------start-" + taskReq);
        }
        Response<String> response = new Response<>();
        //获取用户ID
        String userId = request.getHeader("userId");
        if (userId == null) {
            //用户没有登录
            response.setCode(StatusCode.UNAUTHORIZED);
            return response;
        }
        try {
            taskService.deleteTask(taskReq, AESUtil.decrypt(userId));
        } catch (Exception e) {
            LOG.error("----web/task/v1/deleteTask----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/task/v1/deleteTask------end-" + response);
        }
        return response;
    }

    /**
     * <p>
     * 查询所有任务类型
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/12/21 16:39
     *
     * @return
     */
    @RequiresPermissions("task-findTaskTypes")
    @RequestMapping(value = "v1/findTaskTypes", method = RequestMethod.POST, consumes = "application/json")
    public Response<List<TaskType>> findTaskTypes(HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/task/v1/findTaskTypes------start-");
        }
        Response<List<TaskType>> response = new Response<>();
        try {
            String estateId = AESUtil.decrypt(request.getHeader("estateId"));
            if (StringUtil.isBlank(estateId)) {
                response.setCode(GlobalCode.ESTATE_ID_IS_NULL);
            } else {
                response.setData(taskService.findTaskTyps(estateId));
            }
        } catch (Exception e) {
            LOG.error("----web/task/v1/findTaskTypes----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/task/v1/findTaskTypes------end-" + response);
        }
        return response;
    }

    /**
     * <p>
     * 分页搜索查询所有操作记录
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2018/1/5 15:48
     *
     * @param recordReq
     * @return
     */
    @RequiresPermissions(value = "task-findAllRecords")
    @RequestMapping(value = "v1/findAllRecords", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<Paging<FindAllRecordResp>> findAllRecords(@RequestBody FindAllRecordReq recordReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("---web/task/v1/findAllRecords----------start----" + recordReq);
        }
        Response<Paging<FindAllRecordResp>> response = new Response();
        Paging<FindAllRecordResp> pager = new Paging<>(recordReq.getCurPage(), recordReq.getPageSize());
        try {
            //校验入参
            String code = validateRecord(recordReq);
            if (!StringUtil.isBlank(code)) {
                response.setCode(code);
            } else {
                //查询
                String estateId = request.getHeader("estateId");
                if (StringUtil.isBlank(estateId)){
                    estateId = AESUtil.encrypt("1");
                }
                recordReq.setEstateId(AESUtil.decrypt(estateId));
                List<FindAllRecordResp> list = taskService.findAllRecords(pager, recordReq, request);
                pager.result(list);
                response.setData(pager);
            }
        } catch (Exception e) {
            LOG.error("---web/task/v1/findAllRecords----------error----", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("---web/task/v1/findAllRecords----------end----" + response);
        }
        return response;
    }

    /**
     * 校验操作记录入参
     *
     * @param req
     * @return
     */
    private String validateRecord(FindAllRecordReq req) {
        //去空格
        if (!StringUtil.isEmpty(req.getTaskNo())) {
            req.setTaskNo(req.getTaskNo().trim());
        }
        //校验
        if (!StringUtil.isBlank(req.getStartTime()) && !validateTime(req.getStartTime().trim())) {
            return TaskCode.TIME_RULE;
        } else if (!StringUtil.isBlank(req.getEndTime()) && !validateTime(req.getEndTime().trim())) {
            return TaskCode.TIME_RULE;
        } else if (!StringUtil.isBlank(req.getStartTime()) && !StringUtil.isBlank(req.getEndTime())
                && validateTime(req.getStartTime().trim()) && validateTime(req.getEndTime().trim())
                && !validateTimeDistance(req.getStartTime(), req.getEndTime())) {
            return TaskCode.START_LATE_END;
        } else if (!StringUtil.isBlank(req.getTaskNo())
                && !req.getTaskNo().trim().matches(TaskConstant.TASKNO_RULE)) {
            return TaskCode.TASK_NO_RULE;
        }
        return null;
    }

    /**
     * <p>
     * 校验日期格式
     * </p>
     * Author: xiayanxin <br/>
     * Date: 2017/8/1 16:15
     *
     * @param time 入参
     * @return flag
     */
    private boolean validateTime(String time) {
        boolean flag = true;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            format.parse(time);
        } catch (ParseException e) {
            flag = false;
        }
        return flag;
    }


    /**
     * <p>
     * 校验时间差
     * </p>
     * Author: xiayanxin <br/>
     * Date: 2017/8/1 16:29
     *
     * @param startTime,endTime 入参
     * @return flag
     */
    private boolean validateTimeDistance(String startTime, String endTime) {
        boolean flag = true;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            long time1 = format.parse(startTime).getTime();
            long time2 = format.parse(endTime).getTime();
            if (time1 > time2) {
                flag = false;
            }
        } catch (ParseException e) {
        }
        return flag;
    }

    /**
     * <p>
     * 条件查询指派人员
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2018/1/5 15:48
     *
     * @param executorReq
     * @return
     */
    @RequiresPermissions(value = "task-findExecutor")
    @RequestMapping(value = "v1/findExecutor", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<Object> findExecutors(@RequestBody FindExecutorReq executorReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("---web/task/v1/findExecutor----------start----" + executorReq);
        }
        Response<Object> response = new Response();
        try {
            //查询
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("empName", executorReq.getEmpName());
            jsonParam.put("phone", executorReq.getPhone());
            jsonParam.put("empNo", executorReq.getEmpNo());
            jsonParam.put("departmentId", executorReq.getDepartmentId());
            String url = ConfigManager.read(ConfigName.OA_PROJECT_URL) + "httpclientOA/repair/v1/findExecutors";
            Response resp = HttpClientUtil.post(url, jsonParam, request);
            if (null != resp) {
                response.setData(resp.getData());
            }
        } catch (Exception e) {
            LOG.error("---web/task/v1/findExecutor----------error----", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("---web/task/v1/findExecutor----------end----" + response);
        }
        return response;
    }

    /**
     * <p>
     * 查询部门列表
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/11/10 16:21
     */
    @RequiresPermissions("task-findDeptList")
    @RequestMapping(value = "v1/findDeptList", method = RequestMethod.POST, consumes = "application/json")
    public Response<Object> findDeptList(HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/task/v1/findDeptList-------start");
        }
        Response<Object> response = new Response<>();
        try {
            JSONObject jsonParam = new JSONObject();
            String url = ConfigManager.read(ConfigName.OA_PROJECT_URL) + "httpclientOA/task/v1/findDeptList";
            Response resp = HttpClientUtil.post(url, jsonParam, request);
            if (null != resp) {
                response.setData(resp.getData());
            }
        } catch (Exception e) {
            LOG.error("--------web/task/v1/findDeptList---------error-----", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/task/v1/findDeptList-------end---response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 查询子部门列表
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2018/1/5 8:47
     *
     * @param deptReq
     * @return
     */
    @RequiresPermissions("task-findPosition")
    @RequestMapping(value = "v1/findPosition", method = RequestMethod.POST)
    public Response<Object> findPosition(@RequestBody DeptReq deptReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/task/v1/findPosition-------start");
        }
        Response<Object> response = new Response<>();
        try {
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("departmentId", deptReq.getDepartmentId());
            String url = ConfigManager.read(ConfigName.OA_PROJECT_URL) + "httpclientOA/task/v1/findPosition";
            Response resp = HttpClientUtil.post(url, jsonParam, request);
            if (null != resp) {
                response.setCode(resp.getCode());
                response.setData(resp.getData());
            }
        } catch (Exception e) {
            LOG.error("--------web/task/v1/findPosition--------error--------", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/task/v1/findPosition-------end,response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 查询保洁区域列表
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2018/2/28 10:23
     *
     * @return
     */
    @RequiresPermissions(value = "taskCount-findAreaList")
    @RequestMapping(value = "/v1/findAreaList", method = RequestMethod.POST, consumes = "application/json")
    public Response<List<CleaningArea>> findAreaList(HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/taskCount/v1/findAreaList start ========");
        }
        Response<List<CleaningArea>> response = new Response();
        try {
            String estateId = AESUtil.decrypt(request.getHeader("estateId"));
            response.setData(cleaningService.findAllAreas(estateId));
        } catch (Exception e) {
            LOG.error("web/taskCount/v1/findAreaList error!", e);
            response.setCode(FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/taskCount/v1/findAreaList end ========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 任务统计
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2018/1/5 8:47
     *
     * @return
     */
    @RequiresPermissions("taskCount-findTaskCount")
    @RequestMapping(value = "v1/findTaskCount", method = RequestMethod.POST)
    public Response<TaskCountResp> findTaskCount(@RequestBody TaskCountReq taskCountReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/task/v1/findTaskCount-------start");
        }
        Response<TaskCountResp> response = new Response<>();
        try {
            taskCountReq.setEstateId(AESUtil.decrypt(request.getHeader("estateId")));
            response.setData(taskService.findTaskCount(taskCountReq));
        } catch (Exception e) {
            LOG.error("--------web/task/v1/findTaskCount--------error--------", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/task/v1/findTaskCount-------end,response=" + response);
        }
        return response;
    }
}
