package com.betel.estatemgmt.business.propertyapp.security.controller;

import com.betel.estatemgmt.business.propertyapp.security.code.SecurityCode;
import com.betel.estatemgmt.business.propertyapp.security.constant.SecurityConstant;
import com.betel.estatemgmt.business.propertyapp.security.model.*;
import com.betel.estatemgmt.business.propertyapp.security.service.SecurityService;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.model.security.SecurityRecord;
import com.betel.estatemgmt.common.returncode.GlobalCode;
import com.betel.estatemgmt.utils.StringUtil;
import com.betel.estatemgmt.utils.encrypt.AESUtil;
import com.betel.estatemgmt.utils.pagination.model.Paging;
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
 * app安保巡逻控制层
 * </p>
 * ClassName: SecurityController <br/>
 * Author: Cui.xx  <br/>
 * Date: 2018/2/28 15:39 <br/>
 * Version: 1.0 <br/>
 */
@RestController
@RequestMapping("propertyApp/security")
public class SecurityController {

    private static final Logger LOG = LoggerFactory.getLogger(SecurityController.class);

    @Autowired
    SecurityService securityService;

    /**
     * <p>
     * 查询巡逻列表
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2018/2/28 15:56
     *
     * @param findSecurityListReq
     * @return
     */
    @RequestMapping(value = "v1/findSecurityList", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<Paging<SecurityInfo>> findSecurityList(@RequestBody FindSecurityListReq findSecurityListReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========propertyApp/security/v1/findSecurityList start==========findSecurityListReq=" + findSecurityListReq);
        }
        Response<Paging<SecurityInfo>> response = new Response<>();
        Paging<SecurityInfo> pager = new Paging<SecurityInfo>(findSecurityListReq.getCurPage(), findSecurityListReq.getPageSize());
        try {
            String estateId = AESUtil.decrypt(request.getHeader("estateId"));
            if (StringUtil.isBlank(estateId)) {
                response.setCode(GlobalCode.ESTATE_ID_IS_NULL);
            } else {
                if (StringUtil.isBlank(findSecurityListReq.getStatus())) {
                    findSecurityListReq.setStatus("1");
                }
                List<SecurityInfo> list = securityService.findAllSecurityList(pager, findSecurityListReq.getStatus(), estateId);
                pager.result(list);
                response.setData(pager);
            }
        } catch (Exception e) {
            LOG.error("propertyApp/security/v1/findSecurityList error", e);
        }

        if (LOG.isInfoEnabled()) {
            LOG.info("========propertyApp/security/v1/findSecurityList end==========");
        }
        return response;
    }

    /**
     * <p>
     * 查询巡逻的签到记录
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2018/3/1 9:34
     *
     * @param findSignListReq
     * @return
     */
    @RequestMapping(value = "v1/findSignList", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<List<SignInfo>> findSignList(@RequestBody FindSignListReq findSignListReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========propertyApp/security/v1/findSignList start==========findSignListReq=" + findSignListReq);
        }
        Response<List<SignInfo>> response = new Response<>();
        List<SignInfo> list = securityService.findAllSignList(findSignListReq.getRecordId());
        response.setData(list);
        if (LOG.isInfoEnabled()) {
            LOG.info("========propertyApp/security/v1/findSignList end==========");
        }
        return response;
    }

    /**
     * <p>
     * 签到
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2018/3/1 13:29
     *
     * @param signReq
     * @return
     */
    @RequestMapping(value = "v1/sign", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<Object> sign(@RequestBody SignReq signReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========propertyApp/security/v1/sign start===========");
        }
        Response<Object> response = new Response<>();
        String userId = request.getHeader("userId");
        if (userId == null) {
            //用户没有登录
            response.setCode(StatusCode.UNAUTHORIZED);
        } else {
            try {
                //先判断是否已完成巡逻
                SecurityRecord record = securityService.findSecurityRecordById(signReq.getRecordId());
                if (record.getIsPatrol() == SecurityConstant.PATROLED) {
                    response.setCode(SecurityCode.PATROLED);
                } else {
                    securityService.sign(signReq, AESUtil.decrypt(userId), request);
                }
            } catch (Exception e) {
                LOG.error("========propertyApp/security/v1/sign error=======", e);
                response.setCode(StatusCode.FAILURE);
            }

        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========propertyApp/security/v1/sign end==========");
        }
        return response;
    }


    /**
     * <p>
     * 完成巡逻
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2018/3/1 13:33
     *
     * @param findSignListReq
     * @return
     */
    @RequestMapping(value = "v1/finish", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<Object> finish(@RequestBody FindSignListReq findSignListReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========propertyApp/security/v1/finish start=======recordId====" + findSignListReq.getRecordId());
        }
        Response<Object> response = new Response<>();
        String userId = request.getHeader("userId");
        if (userId == null) {
            //用户没有登录
            response.setCode(StatusCode.UNAUTHORIZED);
        } else {
            try {
                //先判断是否已完成巡逻
                SecurityRecord record = securityService.findSecurityRecordById(findSignListReq.getRecordId());
                if (record.getIsPatrol() == SecurityConstant.PATROLED) {
                    response.setCode(SecurityCode.PATROLED);
                } else {
                    securityService.finish(findSignListReq.getRecordId(), AESUtil.decrypt(userId), request);
                }
            } catch (Exception e) {
                LOG.error("========propertyApp/security/v1/finish error=======", e);
                response.setCode(StatusCode.FAILURE);
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========propertyApp/security/v1/finish end==========");
        }
        return response;
    }
}
