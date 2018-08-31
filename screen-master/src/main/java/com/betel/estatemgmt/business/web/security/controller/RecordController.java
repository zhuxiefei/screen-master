package com.betel.estatemgmt.business.web.security.controller;

import com.betel.estatemgmt.business.web.security.code.SecurityCode;
import com.betel.estatemgmt.business.web.security.model.SecurityReq;
import com.betel.estatemgmt.business.web.security.model.SecurityResp;
import com.betel.estatemgmt.business.web.security.service.RecordService;
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
 * 安保巡逻记录
 * </p>
 * ClassName: RecordController <br/>
 * Author: jian.z  <br/>
 * Date: 2018/2/26 <br/>
 * Version: 1.0 <br/>
 */
@RestController
@RequestMapping("web/securityRecord")
public class RecordController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(RecordController.class);
    @Autowired
    private RecordService recordService;

    /**
     * 查询安保巡逻记录
     * Author: jians.z <br/>
     *
     * @param securityReq
     * @return
     */
    @RequiresPermissions(value = "securityRecord-findRecordList")
    @RequestMapping(value = "v1/findRecordList",method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<Paging<SecurityResp>> findRecordList(@RequestBody SecurityReq securityReq,HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/securityRecord/v1/findRecordList------start-" + securityReq);
        }
        Response<Paging<SecurityResp>> response = new Response<>();
        try {
            String estateId = AESUtil.decrypt(request.getHeader("estateId"));
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
                List<SecurityResp> list = recordService.findAllRecordList(pager, securityReq);
                pager.result(list);
                response.setData(pager);
            }
        } catch (Exception e) {
            LOG.error("----web/securityRecord/v1/findRecordList----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/securityRecord/v1/findRecordList------end-" + response);
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
     * 查询安保巡逻记录详情
     * Author: jians.z <br/>
     *
     * @param securityReq
     * @return
     */
    @RequiresPermissions(value = "securityRecord-findRecord")
    @RequestMapping(value = "v1/findRecord", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<SecurityResp> findRecord(@RequestBody SecurityReq securityReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/securityRecord/v1/findRecord------start-" + securityReq);
        }
        Response<SecurityResp> response = new Response<>();
        try {
            if (StringUtil.isBlank(securityReq.getRecordId())) {
                response.setCode(GlobalCode.Global_ID_NULL_ERROR);
            } else {
                SecurityResp securityResp = recordService.findRecord(securityReq);
                if (securityResp == null) {
                    response.setCode(SecurityCode.RECORD_IS_NOT_EXIST);
                } else {
                    response.setData(securityResp);
                }
            }
        } catch (Exception e) {
            LOG.error("----web/securityRecord/v1/findRecord----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/securityRecord/v1/findRecord------end-" + response);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/securityRecord/v1/findContent------end-" + response);
        }
        return response;
    }

    /**
     * 导出巡逻记录
     *
     * @param securityReq
     * @return
     */
    @RequiresPermissions(value = "securityRecord-exportList")
    @RequestMapping(value = "v1/exportList", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<List<SecurityResp>> exportList(@RequestBody SecurityReq securityReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/securityRecord/v1/exportList-------start,securityReq=" + securityReq);
        }
        Response<List<SecurityResp>> response = new Response<List<SecurityResp>>();
        try {
            //去空格再校验
            String estateId = AESUtil.decrypt(request.getHeader("estateId"));
            if (StringUtil.isBlank(estateId)) {
                response.setCode(GlobalCode.ESTATE_ID_IS_NULL);
            } else{
                securityReq.setEstateId(estateId);
                String code = check(trim(securityReq));
                if (!StringUtil.isBlank(code)) {
                    response.setCode(code);
                } else {
                    response.setData(recordService.exportList(securityReq));
                }
            }
        } catch (Exception e) {
            LOG.error("----web/securityRecord/v1/exportList--------报错--------", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/securityRecord/v1/findAllTypeList-------end,response=" + response);
        }
        return response;
    }
}
