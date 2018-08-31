package com.betel.estatemgmt.business.oa.task.controller;

import com.betel.estatemgmt.business.oa.task.model.FindEmpReq;
import com.betel.estatemgmt.business.oa.task.model.HomeReq;
import com.betel.estatemgmt.business.oa.task.service.PropertyTaskService;
import com.betel.estatemgmt.business.propertyapp.task.model.FindAllTasksResp;
import com.betel.estatemgmt.common.BaseController;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.utils.StringUtil;
import com.betel.estatemgmt.utils.encrypt.AESUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 任务支持接口
 * </p>
 * ClassName: TaskController <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/12/21 17:23 <br/>
 * Version: 1.0 <br/>
 */
@RequestMapping("httpclientProperty/task")
@RestController
public class PropertyTaskController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(PropertyTaskController.class);

    @Autowired
    private PropertyTaskService propertyTaskService;

    /**
     *  <p>
     * 根据人员ID查询任务管理待接受数量
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/12/21 20:30
     *
     * @param empReq
     * @return
     */
    @RequestMapping(value = "v1/findWaitTaskByEmpId", method = RequestMethod.POST)
    public Response<String> findWaitTaskByEmpId(@RequestBody FindEmpReq empReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("--------property/task/v1/findWaitTaskByEmpId-------start,empReq=" + empReq);
        }
        Response<String> response = new Response<>();
        try {
            String estateId = request.getHeader("estateId");
            if (StringUtil.isBlank(estateId)){
                estateId = AESUtil.encrypt("1");
            }
            response.setData(propertyTaskService.findWaitTaskByEmpId(empReq.getEmpId(),AESUtil.decrypt(estateId)));
        } catch (Exception e) {
            LOG.error("----property/task/v1/findWaitTaskByEmpId----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("--------property/task/v1/findWaitTaskByEmpId--------end,response=" + response);
        }
        return response;
    }

    /**
     *  <p>
     * 查询任务数量
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/12/21 20:30
     *
     * @return
     */
    @RequestMapping(value = "v1/queryAllAppTasks", method = RequestMethod.POST)
    public Response<String> queryAllAppTasks(@RequestBody HomeReq homeReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("--------property/task/v1/queryAllAppTasks-------start");
        }
        Response<String> response = new Response<>();
        try {
            response.setData(propertyTaskService.queryAllAppTasks(homeReq));
        } catch (Exception e) {
            LOG.error("----property/task/v1/queryAllAppTasks----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("--------property/task/v1/queryAllAppTasks--------end,response=" + response);
        }
        return response;
    }

    /**
     *  <p>
     * 查询任务数量2
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/12/21 20:30
     *
     * @return
     */
    @RequestMapping(value = "v1/queryAppTasksToMe", method = RequestMethod.POST)
    public Response<String> queryAppTasksToMe(@RequestBody HomeReq homeReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("--------property/task/v1/queryAppTasksToMe-------start");
        }
        Response<String> response = new Response<>();
        try {
            response.setData(propertyTaskService.queryAppTasksToMe(homeReq));
        } catch (Exception e) {
            LOG.error("----property/task/v1/queryAppTasksToMe----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("--------property/task/v1/queryAppTasksToMe--------end,response=" + response);
        }
        return response;
    }

    /**
     *  <p>
     * 查询任务数量3
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/12/21 20:30
     *
     * @return
     */
    @RequestMapping(value = "v1/hasFinishInAcceptToMe", method = RequestMethod.POST)
    public Response<String> hasFinishInAcceptToMe(@RequestBody HomeReq homeReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("--------property/task/v1/hasFinishInAcceptToMe-------start");
        }
        Response<String> response = new Response<>();
        try {
            response.setData(propertyTaskService.hasFinishInAcceptToMe(homeReq));
        } catch (Exception e) {
            LOG.error("----property/task/v1/hasFinishInAcceptToMe----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("--------property/task/v1/hasFinishInAcceptToMe--------end,response=" + response);
        }
        return response;
    }
}
