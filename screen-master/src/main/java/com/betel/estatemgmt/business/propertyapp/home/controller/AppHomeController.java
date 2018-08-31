package com.betel.estatemgmt.business.propertyapp.home.controller;

import com.betel.estatemgmt.business.userapp.decoration.service.DecorationService;
import com.betel.estatemgmt.business.userapp.repair.service.RepairService;
import com.betel.estatemgmt.business.propertyapp.home.model.HomeReq;
import com.betel.estatemgmt.business.propertyapp.home.model.HomeResp;
import com.betel.estatemgmt.business.propertyapp.home.service.AppHomeService;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.model.decoration.DecoApplyRecord;
import com.betel.estatemgmt.utils.StringUtil;
import com.betel.estatemgmt.utils.encrypt.AESUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: HomeController <br/>
 * Author: Cui.xx  <br/>
 * Date: 2017/12/23 16:43 <br/>
 * Version: 1.0 <br/>
 */
@RestController
@RequestMapping("httpclientProperty/home")
public class AppHomeController {

    private static final Logger LOG = LoggerFactory.getLogger(AppHomeController.class);

    @Autowired
    RepairService repairService;
    @Autowired
    DecorationService decorationService;
    @Autowired
    AppHomeService homeService;

    /**
     * <p>
     * 查询待维修维修单
     * </p>
     * Author: jian.z <br/>
     * Date: 2018/2/3
     *
     * @return response
     */
    @ResponseBody
    @RequestMapping(value = "v1/countWaitRepair", consumes = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public Response<String> countWaitRepair(HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("--------app/home/v1/countWaitRepair----------start----");
        }
        Response<String> response = new Response<>();
        try {
            String estateId = request.getHeader("estateId");
            if (StringUtil.isBlank(estateId)){
                estateId = AESUtil.encrypt("1");
            }
            response.setData(String.valueOf(repairService.countWaitRepair(AESUtil.decrypt(estateId))));
            response.setCode(StatusCode.SUCCESS);
        } catch (Exception e) {
            LOG.error("--------app/home/v1/countWaitRepair--------erro--------", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("--------app/home/v1/countWaitRepair--------end--------" + response);
        }
        return response;
    }

    /**
     * <p>
     * 查询待处理维修单
     * </p>
     * Author: jian.z <br/>
     * Date: 2018/2/3
     *
     * @return response
     */
    @ResponseBody
    @RequestMapping(value = "v1/countPendingRepair", consumes = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public Response<String> countPendingRepair(HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("---app/home/v1/countPendingRepair----------start----");
        }
        Response<String> response = new Response<>();
        try {
            //获取当前物业人员ID，作为指派者id
            String userId = request.getHeader("userId");
            if (userId == null) {
                response.setCode(StatusCode.UNAUTHORIZED);
            } else {
                String estateId = request.getHeader("estateId");
                if (StringUtil.isBlank(estateId)){
                    estateId = AESUtil.encrypt("1");
                }
                response.setData(String.valueOf(repairService.countPendingRepair(AESUtil.decrypt(userId),AESUtil.decrypt(estateId))));
            }
        } catch (Exception e) {
            LOG.error("---app/home/v1/countPendingRepair----------error----", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("---app/home/v1/countPendingRepair----------end----" + response);
        }
        return response;
    }


    /**
     * <p>
     * 只有报修指派模块权限
     * </p>
     * Author: jian.z <br/>
     * Date: 2018/2/3
     *
     * @return response
     */
    @ResponseBody
    @RequestMapping(value = "v1/statisticsAssign", consumes = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public Response<HomeResp> statisticsAssign(HttpServletRequest request, @RequestBody HomeReq homeReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("---app/home/v1/statisticsAssign----------start----");
        }
        Response<HomeResp> response = new Response<>();
        try {
            //获取当前物业人员ID，作为指派者id
            String userId = request.getHeader("userId");
            if (userId == null) {
                response.setCode(StatusCode.UNAUTHORIZED);
            } else {
                homeReq.setUserId(AESUtil.decrypt(userId));
                response.setData(homeService.statisticsAssign(homeReq));
            }
        } catch (Exception e) {
            LOG.error("---app/home/v1/statisticsAssign----------error----", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("---app/home/v1/statisticsAssign----------end----" + response);
        }
        return response;
    }

    /**
     * <p>
     * 报修指派模块、报修处理模块权限
     * </p>
     * Author: jian.z <br/>
     * Date: 2018/2/3
     *
     * @return response
     */
    @ResponseBody
    @RequestMapping(value = "v1/statisticsAssignDispose", consumes = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public Response<HomeResp> statisticsAssignDispose(HttpServletRequest request, @RequestBody HomeReq homeReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("--------app/home/v1/statisticsAssignDispose--------start--------");
        }
        Response<HomeResp> response = new Response<>();
        try {
            //获取当前物业人员ID，作为指派者id
            String userId = request.getHeader("userId");
            if (userId == null) {
                response.setCode(StatusCode.UNAUTHORIZED);
            } else {
                homeReq.setUserId(AESUtil.decrypt(userId));
                response.setData(homeService.statisticsAssignDispose(homeReq));
            }
        } catch (Exception e) {
            LOG.error("---------app/home/v1/statisticsAssignDispose--------error--------", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("--------app/home/v1/statisticsAssignDispose--------end--------" + response);
        }
        return response;
    }

    /**
     * <p>
     * 只有报修处理模块权限
     * </p>
     * Author: jian.z <br/>
     * Date: 2018/2/3
     *
     * @return response
     */
    @ResponseBody
    @RequestMapping(value = "v1/statisticsnDispose", consumes = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public Response<HomeResp> statisticsnDispose(HttpServletRequest request, @RequestBody HomeReq homeReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("--------app/home/v1/statisticsnDispose--------start--------");
        }
        Response<HomeResp> response = new Response<>();
        try {
            //获取当前物业人员ID，作为指派者id
            String userId = request.getHeader("userId");
            if (userId == null) {
                response.setCode(StatusCode.UNAUTHORIZED);
            } else {
                homeReq.setUserId(AESUtil.decrypt(userId));
                response.setData(homeService.statisticsnDispose(homeReq));
            }
        } catch (Exception e) {
            LOG.error("--------app/home/v1/statisticsnDispose--------error----", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("---app/home/v1/statisticsnDispose----------end----" + response);
        }
        return response;
    }

    /**
     * <p>
     * 待处理的装修申请
     * </p>
     * Author: jian.z <br/>
     * Date: 2018/2/3
     *
     * @return response
     */
    @ResponseBody
    @RequestMapping(value = "v1/countDecoration", consumes = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public Response<String> countDecoration(HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("---app/home/v1/countDecoration----------start----");
        }
        Response<String> response = new Response<>();
        try {
            //获取当前物业人员ID，作为指派者id
            String userId = request.getHeader("userId");
            if (userId == null) {
                response.setCode(StatusCode.UNAUTHORIZED);
            } else {
                String estateId = request.getHeader("estateId");
                if (StringUtil.isBlank(estateId)){
                    estateId = AESUtil.encrypt("1");
                }
                List<DecoApplyRecord> decoApplyRecordList = homeService.findApplyOnline(AESUtil.decrypt(estateId));
                if (null != decoApplyRecordList && decoApplyRecordList.size() > 0){
                    response.setData(String.valueOf(decoApplyRecordList.size()));
                }else {
                    response.setData("0");
                }

            }
        } catch (Exception e) {
            LOG.error("---app/home/v1/countDecoration----------error----", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("---app/home/v1/countDecoration----------end----" + response);
        }
        return response;
    }

    /**
     * <p>
     * 待巡检数
     * </p>
     * Author: jian.z <br/>
     * Date: 2018/2/3
     *
     * @return response
     */
    @ResponseBody
    @RequestMapping(value = "v1/pollingToPatrol", consumes = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public Response<String> pollingToPatrol(HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("---app/home/v1/pollingToPatrol----------start----");
        }
        Response<String> response = new Response<>();
        try {
            //获取当前物业人员ID，作为指派者id
            String userId = request.getHeader("userId");
            if (userId == null) {
                response.setCode(StatusCode.UNAUTHORIZED);
            } else {
                String estateId = request.getHeader("estateId");
                if (StringUtil.isBlank(estateId)){
                    estateId = AESUtil.encrypt("1");
                }
                String pollingToPatrol = homeService.pollingToPatrol(AESUtil.decrypt(estateId))+"";
                response.setData(pollingToPatrol);
            }
        } catch (Exception e) {
            LOG.error("---app/home/v1/pollingToPatrol----------error----", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("---app/home/v1/pollingToPatrol----------end----" + response);
        }
        return response;
    }

    /**
     * <p>
     * 待巡逻的数量
     * </p>
     * Author: jian.z <br/>
     * Date: 2018/2/3
     *
     * @return response
     */
    @ResponseBody
    @RequestMapping(value = "v1/findUnFinishSecurity", consumes = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public Response<String> findUnFinishSecurity(HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("---app/home/v1/findUnFinishSecurity----------start----");
        }
        Response<String> response = new Response<>();
        try {
            String estateId = request.getHeader("estateId");
            if (StringUtil.isBlank(estateId)){
                estateId = AESUtil.encrypt("1");
            }
            String securityCounts = homeService.findUnFinishSecurity(AESUtil.decrypt(estateId))+"";
            response.setData(securityCounts);
        } catch (Exception e) {
            LOG.error("---app/home/v1/findUnFinishSecurity----------error----", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("---app/home/v1/findUnFinishSecurity----------end----" + response);
        }
        return response;
    }
}
