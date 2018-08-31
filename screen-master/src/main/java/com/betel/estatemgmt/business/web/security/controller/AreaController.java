package com.betel.estatemgmt.business.web.security.controller;


import com.betel.estatemgmt.business.web.patrol.model.Record;
import com.betel.estatemgmt.business.web.security.code.SecurityCode;
import com.betel.estatemgmt.business.web.security.model.SecurityReq;
import com.betel.estatemgmt.business.web.security.service.AreaService;
import com.betel.estatemgmt.common.BaseController;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.model.security.SecurityArea;
import com.betel.estatemgmt.common.model.security.SecuritySigninAddress;
import com.betel.estatemgmt.common.returncode.GlobalCode;
import com.betel.estatemgmt.utils.StringUtil;
import com.betel.estatemgmt.utils.encrypt.AESUtil;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
 * 安保巡逻区域
 * </p>
 * ClassName: AreaController <br/>
 * Author: jian.z  <br/>
 * Date: 2018/2/26 <br/>
 * Version: 1.0 <br/>
 */
@RestController
@RequestMapping("web/securityArea")
public class AreaController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(AreaController.class);
    @Autowired
    private AreaService areaService;

    /**
     * 查询安保巡逻区域列表
     * Author: jians.z <br/>
     *
     * @param
     * @return
     */
    @RequiresPermissions(value = "securityArea-findAreaList")
    @RequestMapping(value = "v1/findAreaList", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<List<SecurityArea>> findAreaList(HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/securityArea/v1/findAreaList------start");
        }
        Response<List<SecurityArea>> response = new Response<>();
        try {
            String estateId = AESUtil.decrypt(request.getHeader("estateId"));
            if (StringUtil.isBlank(estateId)) {
                response.setCode(GlobalCode.ESTATE_ID_IS_NULL);
            } else {
                response.setData(areaService.findAreaList(estateId));
            }
        } catch (Exception e) {
            LOG.error("----web/securityArea/v1/findAreaList----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/securityArea/v1/findAreaList------end-" + response);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/securityArea/v1/findAreaList------end-" + response);
        }
        return response;
    }

    /**
     * 查询安保巡逻区域下签到点列表
     * Author: jians.z <br/>
     *
     * @param securityReq
     * @return
     */
    @RequiresPermissions(value = "securityArea-findSignList")
    @RequestMapping(value = "v1/findSignList", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<List<SecuritySigninAddress>> findSignList(@RequestBody SecurityReq securityReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/securityArea/v1/findSignList------start-" + securityReq);
        }
        Response<List<SecuritySigninAddress>> response = new Response<>();
        try {
            if (StringUtil.isBlank(securityReq.getAreaId())) {
                response.setCode(GlobalCode.Global_ID_NULL_ERROR);
            } else {
                response.setData(areaService.findSignList(securityReq));
            }
        } catch (Exception e) {
            LOG.error("----web/securityArea/v1/findSignList----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/securityArea/v1/findSignList------end-" + response);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/securityArea/v1/findSignList------end-" + response);
        }
        return response;
    }


    /**
     * 添加安保巡逻区域
     * Author: jians.z <br/>
     *
     * @param securityReq
     * @return
     */
    @RequiresPermissions(value = "securityArea-addArea")
    @RequestMapping(value = "v1/addArea", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> addArea(@RequestBody SecurityReq securityReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/securityArea/v1/addArea------start-" + securityReq);
        }
        Response<String> response = new Response<>();
        try {
            String estateId = AESUtil.decrypt(request.getHeader("estateId"));
            if (StringUtil.isBlank(estateId)) {
                response.setCode(GlobalCode.ESTATE_ID_IS_NULL);
            } else{
                securityReq.setEstateId(estateId);
                response.setCode(areaService.addArea(securityReq));
            }
        } catch (Exception e) {
            LOG.error("----web/securityArea/v1/addArea----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/securityArea/v1/addArea------end-" + response);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/securityArea/v1/addArea------end-" + response);
        }
        return response;
    }

    /**
     * 添加安保巡逻区域下签到地点
     * Author: jians.z <br/>
     *
     * @param securityReq
     * @return
     */
    @RequiresPermissions(value = "securityArea-addSignin")
    @RequestMapping(value = "v1/addSignin", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> addSignin(@RequestBody SecurityReq securityReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/securityArea/v1/addSignin------start-" + securityReq);
        }
        Response<String> response = new Response<>();
        try {
            response.setCode(areaService.addSignin(securityReq));
        } catch (Exception e) {
            LOG.error("----web/securityArea/v1/addSignin----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/securityArea/v1/addSignin------end-" + response);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/securityArea/v1/addSignin------end-" + response);
        }
        return response;
    }

    /**
     * 编辑安保巡逻区域下签到地点
     * Author: jians.z <br/>
     *
     * @param securityReq
     * @return
     */
    @RequiresPermissions(value = "securityArea-updateArea")
    @RequestMapping(value = "v1/updateArea", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> updateArea(@RequestBody SecurityReq securityReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/securityArea/v1/updateArea------start-" + securityReq);
        }
        Response<String> response = new Response<>();
        try {
            response.setCode(areaService.updateArea(securityReq));
        } catch (Exception e) {
            LOG.error("----web/securityArea/v1/updateArea----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/securityArea/v1/updateArea------end-" + response);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/securityArea/v1/updateArea------end-" + response);
        }
        return response;
    }

    /**
     * 编辑安保巡逻区域下签到地点
     * Author: jians.z <br/>
     *
     * @param securityReq
     * @return
     */
    @RequiresPermissions(value = "securityArea-updateSignin")
    @RequestMapping(value = "v1/updateSignin", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> updateSignin(@RequestBody SecurityReq securityReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/securityArea/v1/updateSignin------start-" + securityReq);
        }
        Response<String> response = new Response<>();
        try {
            response.setCode(areaService.updateSignin(securityReq));
        } catch (Exception e) {
            LOG.error("----web/securityArea/v1/updateSignin----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/securityArea/v1/updateSignin------end-" + response);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/securityArea/v1/updateSignin------end-" + response);
        }
        return response;
    }

    /**
     * 查询安保巡逻区域
     * Author: jians.z <br/>
     *
     * @param securityReq
     * @return
     */
    @RequiresPermissions(value = "securityArea-findArea")
    @RequestMapping(value = "v1/findArea", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<SecurityArea> findArea(@RequestBody SecurityReq securityReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/securityArea/v1/findArea------start-" + securityReq);
        }
        Response<SecurityArea> response = new Response<>();
        try {
            SecurityArea securityArea = areaService.findArea(securityReq);
            if (securityArea == null) {
                response.setCode(SecurityCode.AREA_IS_NOT_EXIST);
            } else {
                response.setData(securityArea);
            }
        } catch (Exception e) {
            LOG.error("----web/securityArea/v1/findArea----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/securityArea/v1/findArea------end-" + response);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/securityArea/v1/findArea------end-" + response);
        }
        return response;
    }

    /**
     * 查询安保巡逻区域下签到地点
     * Author: jians.z <br/>
     *
     * @param securityReq
     * @return
     */
    @RequiresPermissions(value = "securityArea-findSignin")
    @RequestMapping(value = "v1/findSignin", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<SecuritySigninAddress> findSignin(@RequestBody SecurityReq securityReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/securityArea/v1/findSignin------start-" + securityReq);
        }
        Response<SecuritySigninAddress> response = new Response<>();
        try {
            SecuritySigninAddress securitySigninAddress = areaService.findSignin(securityReq);
            if (securitySigninAddress == null) {
                response.setCode(SecurityCode.SIGNIN_ADDRESS_IS_NOT_EXIST);
            } else {
                response.setData(securitySigninAddress);
            }
        } catch (Exception e) {
            LOG.error("----web/securityArea/v1/findArea----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/securityArea/v1/findArea------end-" + response);
        }

        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/securityArea/v1/findArea------end-" + response);
        }
        return response;
    }

    /**
     * 删除安保巡逻区域
     * Author: jians.z <br/>
     *
     * @param securityReq
     * @return
     */
    @RequiresPermissions(value = "securityArea-deleteArea")
    @RequestMapping(value = "v1/deleteArea", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> deleteArea(@RequestBody SecurityReq securityReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/securityArea/v1/deleteArea------start-" + securityReq);
        }
        Response<String> response = new Response<>();
        try {
            response.setCode(areaService.deleteArea(securityReq));
        } catch (Exception e) {
            LOG.error("----web/securityArea/v1/findArea----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/securityArea/v1/findArea------end-" + response);
        }

        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/securityArea/v1/findArea------end-" + response);
        }
        return response;
    }

    /**
     * 删除安保巡逻区域下签到地点
     * Author: jians.z <br/>
     *
     * @param securityReq
     * @return
     */
    @RequiresPermissions(value = "securityArea-deleteSignin")
    @RequestMapping(value = "v1/deleteSignin", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> deleteSignin(@RequestBody SecurityReq securityReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/securityArea/v1/deleteSignin------start-" + securityReq);
        }
        Response<String> response = new Response<>();
        try {
            response.setCode(areaService.deleteSignin(securityReq));
        } catch (Exception e) {
            LOG.error("----web/securityArea/v1/deleteSignin----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/securityArea/v1/deleteSignin------end-" + response);
        }

        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/securityArea/v1/deleteSignin------end-" + response);
        }
        return response;
    }
}
