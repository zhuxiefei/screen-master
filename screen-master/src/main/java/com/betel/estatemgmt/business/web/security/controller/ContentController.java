package com.betel.estatemgmt.business.web.security.controller;


import com.betel.estatemgmt.business.web.security.code.SecurityCode;
import com.betel.estatemgmt.business.web.security.model.SecurityContentResp;
import com.betel.estatemgmt.business.web.security.model.SecurityReq;
import com.betel.estatemgmt.business.web.security.model.SecurityResp;
import com.betel.estatemgmt.business.web.security.service.ContentService;
import com.betel.estatemgmt.common.BaseController;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.returncode.GlobalCode;
import com.betel.estatemgmt.utils.RegexRule;
import com.betel.estatemgmt.utils.StringUtil;
import com.betel.estatemgmt.utils.TimeUtils;
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
 * 安保巡逻内容
 * </p>
 * ClassName: ContentController <br/>
 * Author: jian.z  <br/>
 * Date: 2018/2/28 <br/>
 * Version: 1.0 <br/>
 */
@RestController
@RequestMapping("web/securityContent")
public class ContentController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(AreaController.class);
    @Autowired
    private ContentService contentService;

    /**
     * 查询安保巡逻内容列表
     * Author: jians.z <br/>
     *
     * @param securityReq
     * @return
     */
    @RequiresPermissions(value = "securityContent-findContentList")
    @RequestMapping(value = "v1/findContentList", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<Paging<SecurityResp>> findContentList(@RequestBody SecurityReq securityReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/securityContent/v1/findContentList------start-" + securityReq);
        }
        Response<Paging<SecurityResp>> response = new Response<>();
        try {
            String estateId = AESUtil.decrypt(request.getHeader("estateId"));
            if (StringUtil.isBlank(estateId)) {
                response.setCode(GlobalCode.ESTATE_ID_IS_NULL);
            } else {
                securityReq.setEstateId(estateId);
                //校验入参
                String code = check(trim(securityReq));
                if (!StringUtil.isBlank(code)) {
                    response.setCode(code);
                } else {
                    if (!StringUtil.isBlank(securityReq.getContentNo()) && securityReq.getContentNo().contains("_")) {
                        securityReq.setContentNo(securityReq.getContentNo().replace("_", "\\_"));
                    }
                    Paging<SecurityResp> pager = new Paging<SecurityResp>(securityReq.getCurPage(), securityReq.getPageSize());
                    List<SecurityResp> list = contentService.findAllContentList(pager, securityReq);
                    pager.result(list);
                    response.setData(pager);
                }
            }
        } catch (Exception e) {
            LOG.error("----web/securityContent/v1/findContentList----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/securityContent/v1/findContentList------end-" + response);
        }
        return response;
    }

    private SecurityReq trim(SecurityReq securityReq) {
        if (!StringUtil.isEmpty(securityReq.getContentNo())) {
            securityReq.setContentNo(securityReq.getContentNo().trim());
        }
        if (!StringUtil.isEmpty(securityReq.getStartTime())) {
            securityReq.setStartTime(securityReq.getStartTime().trim());
        }
        if (!StringUtil.isEmpty(securityReq.getEndTime())) {
            securityReq.setEndTime(securityReq.getEndTime().trim());
        }
        return securityReq;
    }

    private String check(SecurityReq securityReq) {
        if (!StringUtil.isBlank(securityReq.getContentNo())) {
            if (!securityReq.getContentNo().matches(RegexRule.SPECIAL_WORD_RULE_TWENTY)) {
                return SecurityCode.CONTENT_NO_RULE_ERROR;
            }
        }
        if (!StringUtil.isBlank(securityReq.getStartTime())) {
            if (!securityReq.getStartTime().matches(RegexRule.START_END_TIME_RULE_Y_M_D_H_M_S)) {
                return GlobalCode.START_TIME_RULE_ERROR;
            }
        }
        if (!StringUtil.isBlank(securityReq.getEndTime())) {
            if (!securityReq.getEndTime().matches(RegexRule.START_END_TIME_RULE_Y_M_D_H_M_S)) {
                return GlobalCode.END_TIME_RULE_ERROR;
            }
        }
        //校验startTime和endTime的大小
        if (TimeUtils.compareDate(securityReq.getStartTime(), securityReq.getEndTime(), RegexRule.TIME_FARMAT_Y_M_D_H_M_S)) {
            return GlobalCode.START_TIME_THAN_END_TIME_ERROR;
        }
        return null;
    }


    /**
     * 查询安保巡逻内容
     * Author: jians.z <br/>
     *
     * @param securityReq
     * @return
     */
    @RequiresPermissions(value = "securityContent-findContent")
    @RequestMapping(value = "v1/findContent", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<SecurityResp> findContent(@RequestBody SecurityReq securityReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/securityContent/v1/findContent------start-" + securityReq);
        }
        Response<SecurityResp> response = new Response<>();
        try {
            SecurityResp securityResp = contentService.findContent(securityReq);
            if (securityResp == null) {
                response.setCode(SecurityCode.CONTENT_IS_NOT_EXIST);
            } else {
                response.setData(securityResp);
            }
        } catch (Exception e) {
            LOG.error("----web/securityContent/v1/findContent----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/securityContent/v1/findContent------end-" + response);
        }
        return response;
    }

    /**
     * 编辑安保巡逻内容
     * Author: jians.z <br/>
     *
     * @param securityReq
     * @return
     */
    @RequiresPermissions(value = "securityContent-updateContent")
    @RequestMapping(value = "v1/updateContent", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> updateContent(@RequestBody SecurityReq securityReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/securityContent/v1/updateContent------start-" + securityReq);
        }
        Response<String> response = new Response<>();
        try {
            response.setCode(contentService.updateContent(securityReq));
        } catch (Exception e) {
            LOG.error("----web/securityContent/v1/updateContent----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/securityContent/v1/findContent------end-" + response);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/securityContent/v1/updateContent------end-" + response);
        }
        return response;
    }

    /**
     * 新增安保巡逻内容
     * Author: jians.z <br/>
     *
     * @param securityReq
     * @return
     */
    @RequiresPermissions(value = "securityContent-addContent")
    @RequestMapping(value = "v1/addContent", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> addContent(@RequestBody SecurityReq securityReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/securityContent/v1/addContent------start-" + securityReq);
        }
        Response<String> response = new Response<>();
        try {
            String estateId = AESUtil.decrypt(request.getHeader("estateId"));
            if (StringUtil.isBlank(estateId)) {
                response.setCode(GlobalCode.ESTATE_ID_IS_NULL);
            } else{
                securityReq.setEstateId(estateId);
                response.setCode(contentService.addContent(securityReq));
            }
        } catch (Exception e) {
            LOG.error("----web/securityContent/v1/addContent----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/securityContent/v1/addContent------end-" + response);
        }
        return response;
    }

    /**
     * 删除安保巡逻内容
     * Author: jians.z <br/>
     *
     * @param securityReq
     * @return
     */
    @RequiresPermissions(value = "securityContent-deleteContent")
    @RequestMapping(value = "v1/deleteContent", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> deleteContent(@RequestBody SecurityReq securityReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/securityContent/v1/deleteContent------start-" + securityReq);
        }
        Response<String> response = new Response<>();
        try {
            response.setCode(contentService.deleteContent(securityReq));
        } catch (Exception e) {
            LOG.error("----web/securityContent/v1/deleteContent----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/securityContent/v1/deleteContent------end-" + response);
        }
        return response;
    }

    /**
     * 查询内容模块下安保巡逻记录
     * Author: jians.z <br/>
     *
     * @param securityReq
     * @return
     */
    @RequiresPermissions(value = "securityContent-findAllRecord")
    @RequestMapping(value = "v1/findAllRecord", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<Paging<SecurityResp>> findAllRecord(@RequestBody SecurityReq securityReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/securityContent/v1/findAllRecord------start-" + securityReq);
        }
        Response<Paging<SecurityResp>> response = new Response<>();
        try {
            //校验入参
            String code = checkSigninNumRecord(trim(securityReq));
            if (!StringUtil.isBlank(code)) {
                response.setCode(code);
            } else {
                Paging<SecurityResp> pager = new Paging<SecurityResp>(securityReq.getCurPage(), securityReq.getPageSize());
                List<SecurityResp> list = contentService.findAllRecord(pager, securityReq);
                pager.result(list);
                response.setData(pager);
            }
        } catch (Exception e) {
            LOG.error("----web/securityContent/v1/findAllRecord----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/securityContent/v1/findAllRecord------end-" + response);
        }
        return response;
    }

    /**
     * 查询内容模块下安保巡逻签到记录
     * Author: jians.z <br/>
     *
     * @param securityReq
     * @return
     */
    @RequiresPermissions(value = "securityContent-findRecordContent")
    @RequestMapping(value = "v1/findRecordContent", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<SecurityContentResp> findRecordContent(@RequestBody SecurityReq securityReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/securityContent/v1/findRecordContent------start-" + securityReq);
        }
        Response<SecurityContentResp> response = new Response<>();
        try {
            SecurityContentResp securityContent = contentService.findRecordContent(securityReq);
            if (securityContent == null) {
                response.setCode(SecurityCode.CONTENT_IS_NOT_EXIST);
            } else {
                response.setData(securityContent);
            }
        } catch (Exception e) {
            LOG.error("----web/securityContent/v1/findRecordContent----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/securityContent/v1/findRecordContent------end-" + response);
        }
        return response;
    }

    private String checkSigninNumRecord(SecurityReq securityReq) {
        if (!StringUtil.isBlank(securityReq.getStartTime())) {
            if (!securityReq.getStartTime().matches(RegexRule.START_END_TIME_RULE_Y_M_D_H_M_S)) {
                return GlobalCode.START_TIME_RULE_ERROR;
            }
        }
        if (!StringUtil.isBlank(securityReq.getEndTime())) {
            if (!securityReq.getEndTime().matches(RegexRule.START_END_TIME_RULE_Y_M_D_H_M_S)) {
                return GlobalCode.END_TIME_RULE_ERROR;
            }
        }
        //校验startTime和endTime的大小
        if (TimeUtils.compareDate(securityReq.getStartTime(), securityReq.getEndTime(), RegexRule.TIME_FARMAT_Y_M_D_H_M_S)) {
            return GlobalCode.START_TIME_THAN_END_TIME_ERROR;
        }
        //查询内容是否被删除
        SecurityContentResp securityContent = contentService.findRecordContent(securityReq);
        if (securityContent == null) {
            return SecurityCode.CONTENT_IS_NOT_EXIST;
        }
        return null;
    }
}
