package com.betel.estatemgmt.business.propertyapp.task.controller;

import com.betel.estatemgmt.business.propertyapp.check.code.CheckCode;
import com.betel.estatemgmt.business.propertyapp.check.constant.CheckConstant;
import com.betel.estatemgmt.business.propertyapp.check.service.CheckService;
import com.betel.estatemgmt.business.propertyapp.task.code.TaskCode;
import com.betel.estatemgmt.business.propertyapp.task.constant.TaskConstant;
import com.betel.estatemgmt.business.propertyapp.task.model.*;
import com.betel.estatemgmt.business.propertyapp.task.model.FindAllTasksReq;
import com.betel.estatemgmt.business.propertyapp.task.model.FindAllTasksResp;
import com.betel.estatemgmt.business.propertyapp.task.model.FindTaskResp;
import com.betel.estatemgmt.business.propertyapp.task.service.TaskService;
import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.business.web.task.model.*;
import com.betel.estatemgmt.common.*;
import com.betel.estatemgmt.common.model.system.SystemPicture;
import com.betel.estatemgmt.common.model.work.TaskType;
import com.betel.estatemgmt.common.returncode.GlobalCode;
import com.betel.estatemgmt.utils.FileUtil;
import com.betel.estatemgmt.utils.StringUtil;
import com.betel.estatemgmt.utils.UuidUtil;
import com.betel.estatemgmt.utils.Validate;
import com.betel.estatemgmt.utils.encrypt.AESUtil;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 任务接口
 * </p>
 * ClassName: TaskController <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/12/21 17:23 <br/>
 * Version: 1.0 <br/>
 */
@RequestMapping("propertyApp/task")
@RestController
public class TaskController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private TaskService taskService;

    @Autowired
    private com.betel.estatemgmt.business.web.task.service.TaskService webTaskService;

    @Autowired
    private CheckService checkService;

    @Autowired
    private com.betel.estatemgmt.business.web.task.service.TaskService taskServiceWeb;

    /**
     * <p>
     * 查询发出/收到任务列表
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/12/21 19:26
     *
     * @param taskReq
     * @return
     */
    @RequestMapping(value = "v1/findAllTasks", method = RequestMethod.GET)
    public Response<Paging<FindAllTasksResp>> findAllTasks(FindAllTasksReq taskReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("--------app/task/v1/findAllTasks-------start,taskReq=" + taskReq);
        }
        Response<Paging<FindAllTasksResp>> response = new Response<>();
        //获取用户ID
        String userId = request.getHeader("userId");
        if (userId == null) {
            //用户没有登录
            response.setCode(StatusCode.UNAUTHORIZED);
            return response;
        }
        Paging<FindAllTasksResp> paging = new Paging<>(taskReq.getCurPage(), taskReq.getPageSize());
        try {
            String estateId = request.getHeader("estateId");
            if (StringUtil.isBlank(estateId)){
                estateId = AESUtil.encrypt("1");
            }
            if (StringUtil.isBlank(estateId)) {
                response.setCode(GlobalCode.ESTATE_ID_IS_NULL);
            } else {
                taskReq.setUserId(AESUtil.decrypt(userId));
                taskReq.setEstateId(AESUtil.decrypt(estateId));
                paging.result(taskService.findAllTasks(taskReq, paging));
                response.setData(paging);
            }
        } catch (Exception e) {
            LOG.error("----app/task/v1/findAllTasks----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("--------app/task/v1/findAllTasks--------end,response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 查询任务详情
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/12/21 20:30
     *
     * @param taskReq
     * @return
     */
    @RequestMapping(value = "v1/findTask", method = RequestMethod.GET)
    public Response<FindTaskResp> findTask(FindTaskReq taskReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("--------app/task/v1/findTask-------start,taskReq=" + taskReq);
        }
        Response<FindTaskResp> response = new Response<>();
        try {
            FindTaskResp resp = taskService.findTask(taskReq, request);
            if (null == resp) {
                response.setCode(TaskCode.TASK_DELETE);
            } else {
                response.setData(resp);
            }
        } catch (Exception e) {
            LOG.error("----app/task/v1/findTask----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("--------app/task/v1/findTask--------end,response=" + response);
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
    @RequestMapping(value = "v1/findTaskTypes", method = RequestMethod.GET)
    public Response<List<TaskType>> findTaskTypes(HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------app/task/v1/findTaskTypes------start-");
        }
        Response<List<TaskType>> response = new Response<>();
        try {
            String estateId = request.getHeader("estateId");
            if (StringUtil.isBlank(estateId)){
                estateId = AESUtil.encrypt("1");
            }
            if (StringUtil.isBlank(estateId)) {
                response.setCode(GlobalCode.ESTATE_ID_IS_NULL);
            } else {
                response.setData(webTaskService.findTaskTyps(AESUtil.decrypt(estateId)));
            }
        } catch (Exception e) {
            LOG.error("----app/task/v1/findTaskTypes----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------app/task/v1/findTaskTypes------end-" + response);
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
    @RequestMapping(value = "v1/addTask", method = RequestMethod.POST, consumes = "application/json")
    public Response<String> addTask(@RequestBody AddTaskReq taskReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------app/task/v1/addTask------start-" + taskReq);
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
            String estateId = request.getHeader("estateId");
            if (StringUtil.isBlank(estateId)){
                estateId = AESUtil.encrypt("1");
            }
            if (StringUtil.isBlank(estateId)) {
                response.setCode(GlobalCode.ESTATE_ID_IS_NULL);
            } else {
                taskReq.setEstateId(AESUtil.decrypt(estateId));
                String code = webTaskService.addTask(taskReq, AESUtil.decrypt(userId), 2, request);
                if (!StringUtil.isBlank(code)) {
                    response.setCode(code);
                }
            }
        } catch (Exception e) {
            LOG.error("----app/task/v1/addTask----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------app/task/v1/addTask------end-" + response);
        }
        return response;
    }

    /**
     * <p>
     * 上传图片（支持多张）
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/12/5 17:30
     *
     * @param pictureFile
     * @return
     */
    @RequestMapping(value = "v1/uploadPic", method = RequestMethod.POST)
    public Response<Object> uploadPic(@RequestParam(value = "pictureFile") List<MultipartFile> pictureFile) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========app/task/v1/uploadPic start===========");
        }
        Response<Object> response = new Response<>();
        if (pictureFile.size() < 1) {
            response.setCode(CheckCode.IMAGE_NULL);
            //验证图片的数量
        } else if (pictureFile.size() > CheckConstant.IMAGE_MAX_NUM) {
            response.setCode(CheckCode.IMAGE_OUT_OF_NUM);
        } else {
            List<SystemPicture> pictures = new ArrayList<>();
            for (MultipartFile picFile : pictureFile) {
                SystemPicture picture = new SystemPicture();
                String image_name = picFile.getOriginalFilename();
                String picType = image_name.substring(image_name.lastIndexOf(".") + 1);
                //图片名长度验证
                if (image_name.length() > TaskConstant.IMAGE_NAME_LENGTH) {
                    response.setCode(TaskCode.IMAGE_NAME_LENGTH_OUT);
                } else if (!Validate.isImage(picType)) {
                    //图片类型错误
                    response.setCode(TaskCode.IMAGE_FORMAT_WRONG);
                } else if (picFile.getSize() > TaskConstant.IMAGE_LENGTH_MAX) {
                    //图片过大
                    response.setCode(TaskCode.IMAGE_OUT_SIZE);
                } else {
                    //设置图片保存路径
                    String name;
                    try {
                        //返回上传后成功的图片名
                        name = FileUtil.uploadFile(picFile, ConfigManager.read(ConfigName.FILE_DIR) + "task/");
                    } catch (Exception e) {
                        response.setCode(StatusCode.FAILURE);
                        LOG.error("----------app/task/v1/uploadPic========  error!", e);
                        return response;
                    }
                    //封装
                    picture.setPictureName(image_name);
                    picture.setCreateTime(new Date());
                    picture.setPictureUrl("task/" + name);
                    picture.setPictureId(UuidUtil.create());
                    pictures.add(picture);
                }
            }
            checkService.savePictures(pictures);
            StringBuffer picIds = new StringBuffer();
            int j = pictures.size();
            for (int i = 0; i < j; i++) {
                if (i == 0) {
                    picIds.append(pictures.get(i).getPictureId());
                } else {
                    picIds.append("," + pictures.get(i).getPictureId());
                }
            }
            //返回图片的id组成的字符串
            response.setData(picIds.toString());
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========app/task/v1/uploadPic end==========");
        }
        return response;
    }

    /**
     * <p>
     * 指派任务
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/12/21 14:46
     *
     * @param taskReq
     * @return
     */
    @RequestMapping(value = "v1/assignTask", method = RequestMethod.POST, consumes = "application/json")
    public Response<String> assignTask(@RequestBody UpdateTaskReq taskReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------app/task/v1/assignTask------start-" + taskReq);
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
            String code = webTaskService.updateTask(taskReq, AESUtil.decrypt(userId), 2, request);
            if (!StringUtil.isBlank(code)) {
                response.setCode(code);
            }
        } catch (Exception e) {
            LOG.error("----app/task/v1/assignTask----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------app/task/v1/assignTask------end-" + response);
        }
        return response;
    }

    /**
     * <p>
     * 接受/完成任务
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/12/22 10:25
     *
     * @param taskReq
     * @return
     */
    @RequestMapping(value = "v1/doTask", method = RequestMethod.POST, consumes = "application/json")
    public Response<String> doTask(@RequestBody DoTaskReq taskReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------app/task/v1/doTask------start-" + taskReq);
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
            taskReq.setUserId(AESUtil.decrypt(userId));
            String code = taskService.doTask(taskReq, request);
            if (!StringUtil.isBlank(code)) {
                response.setCode(code);
            }
        } catch (Exception e) {
            LOG.error("----app/task/v1/doTask----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------app/task/v1/doTask------end-" + response);
        }
        return response;
    }

    /**
     * <p>
     * 删除任务记录
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/12/21 16:04
     *
     * @param taskReq
     * @return
     */
    @RequestMapping(value = "v1/deleteTask", method = RequestMethod.POST, consumes = "application/json")
    public Response<String> deleteTask(@RequestBody DeleteRecordReq taskReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------app/task/v1/deleteTask------start-" + taskReq);
        }
        Response<String> response = new Response<>();
        try {
            String code = taskService.deleteTask(taskReq);
            if (!StringUtil.isBlank(code)) {
                response.setCode(code);
            }
        } catch (Exception e) {
            LOG.error("----app/task/v1/deleteTask----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------app/task/v1/deleteTask------end-" + response);
        }
        return response;
    }

    /**
     * 任务统计
     * @param taskCountReq
     * @param request
     * @return
     */
    @RequestMapping(value = "v1/findTaskCount", method = RequestMethod.POST)
    public Response<AppTaskCount> findTaskCount(@RequestBody TaskCountReq taskCountReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("--------app/task/v1/findTaskCount-------start");
        }
        Response<AppTaskCount> response = new Response<>();
        try {
            taskCountReq.setEstateId(AESUtil.decrypt(request.getHeader("estateId")));
            response.setData(taskServiceWeb.appFindTaskCount(taskCountReq));
        } catch (Exception e) {
            LOG.error("--------app/task/v1/findTaskCount--------error--------", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("--------app/task/v1/findTaskCount-------end,response=" + response);
        }
        return response;
    }
}
