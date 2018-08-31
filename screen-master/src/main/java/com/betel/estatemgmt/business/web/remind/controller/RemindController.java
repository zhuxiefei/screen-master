package com.betel.estatemgmt.business.web.remind.controller;

import com.betel.estatemgmt.business.web.remind.code.RemindCode;
import com.betel.estatemgmt.business.web.remind.constant.RemindDataValidation;
import com.betel.estatemgmt.business.web.remind.model.AddRemindDaysReq;
import com.betel.estatemgmt.business.web.remind.model.FindRemindDaysReq;
import com.betel.estatemgmt.business.web.remind.model.RemindDays;
import com.betel.estatemgmt.business.web.remind.service.RemindService;
import com.betel.estatemgmt.common.BaseController;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.utils.StringUtil;
import com.betel.estatemgmt.utils.encrypt.AESUtil;
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
 * 设置提醒天数接口
 * </p>
 * ClassName: RemindController <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/9/13 17:36 <br/>
 * Version: 1.0 <br/>
 */
@RestController
@RequestMapping("web/remind")
public class RemindController extends BaseController {

    @Autowired
    private RemindService remindService;

    private static final Logger LOG = LoggerFactory.getLogger(RemindController.class);

    /**
     * <p>
     * 保存提醒天数
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/9/15 15:20
     * @param addRemindDaysReq
     * @return response
     * */
    @RequiresPermissions(value = "paymentRemind-addRemindDays")
    @RequestMapping(value = "v1/addRemindDays", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> addRemindDays(@RequestBody AddRemindDaysReq addRemindDaysReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/paymentRemind/v1/addRemindDays start========addRemindDaysReq=" + addRemindDaysReq);
        }
        Response<String> response = new Response<>();
        if (StringUtil.isBlank(addRemindDaysReq.getConfName())) {
            response.setCode(RemindCode.CONFIG_NULL_ERROR);
        } else if(addRemindDaysReq.getConfName().equals(RemindDataValidation.PAYMENT_REMIND_DAYS)
                && addRemindDaysReq.getRemindDays()==null){
            response.setCode(RemindCode.PAYREMIND_NULL_ERROR);
        } else if(addRemindDaysReq.getConfName().equals(RemindDataValidation.OVERDUE_REMIND_DAYS)
                && addRemindDaysReq.getRemindDays()==null){
            response.setCode(RemindCode.OVERREMIND_NULL_ERROR);
        } else {
            try {
                String estateId = request.getHeader("estateId");
                if (StringUtil.isBlank(estateId)){
                    estateId = AESUtil.encrypt("1");
                }
                addRemindDaysReq.setEstateId(AESUtil.decrypt(estateId));
                //添加
                remindService.addRemindDays(addRemindDaysReq);
            } catch (Exception e) {
                LOG.error("========web/paymentRemind/v1/addRemindDays error========error", e);
                response.setCode(StatusCode.FAILURE);
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/paymentRemind/v1/addRemindDays end========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 查询提醒天数
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/9/15 15:44
     * @param findRemindDaysReq
     * @return response
     * */
    @RequiresPermissions(value = "paymentRemind-findRemindDays")
    @RequestMapping(value = "v1/findRemindDays", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<List<RemindDays>> findRemindDays(@RequestBody FindRemindDaysReq findRemindDaysReq,HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/paymentRemind/v1/findRemindDays start========findRemindDaysReq=" + findRemindDaysReq);
        }
        Response<List<RemindDays>> response = new Response<>();
        if (StringUtil.isBlank(findRemindDaysReq.getConfName())) {
            response.setCode(RemindCode.CONFIG_NULL_ERROR);
        } else {
            try {
                //查询
                String estateId = request.getHeader("estateId");
                if (StringUtil.isBlank(estateId)){
                    estateId = AESUtil.encrypt("1");
                }
                response.setData(remindService.findByConfName(findRemindDaysReq.getConfName(),AESUtil.decrypt(estateId)));
            } catch (Exception e) {
                LOG.error("========web/paymentRemind/v1/findRemindDays error========error", e);
                response.setCode(StatusCode.FAILURE);
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/paymentRemind/v1/findRemindDays end========response=" + response);
        }
        return response;
    }
}
