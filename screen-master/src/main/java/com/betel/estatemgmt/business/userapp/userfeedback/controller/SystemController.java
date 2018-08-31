package com.betel.estatemgmt.business.userapp.userfeedback.controller;

import com.betel.estatemgmt.business.smartre.user.model.UserReq;
import com.betel.estatemgmt.business.smartre.user.service.UserService;
import com.betel.estatemgmt.business.userapp.userfeedback.code.SystemCode;
import com.betel.estatemgmt.business.userapp.userfeedback.constant.SystemDataValidation;
import com.betel.estatemgmt.business.userapp.userfeedback.model.AddFeedBackInfo;
import com.betel.estatemgmt.business.userapp.userfeedback.service.SystemService;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.model.system.Feedback;
import com.betel.estatemgmt.common.model.user.User;
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
import java.util.Date;

/**
 * <p>
 * 系统操作接口
 * </p>
 * ClassName: SystemController <br/>
 * Author: jians.z <br/>
 * Date: 2017/5/17 15:10 <br/>
 * Version: 1.0 <br/>
 */
@RestController
@RequestMapping("userApp/system")
public class SystemController {

    private static final Logger LOG = LoggerFactory.getLogger(SystemController.class);

    @Autowired
    private SystemService systemService;

    @Autowired
    private UserService userService;

    /**
     * <p>
     * 意见反馈
     * </p>
     * Author: jian.z <br/>
     * Date: 2017/5/15 17:14
     *
     * @param httpServletRequest 头部信息
     * @param info               反馈内容
     * @return response
     */
    @RequestMapping(value = "v1/addFeedBack", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> addFeedBack(HttpServletRequest httpServletRequest, @RequestBody AddFeedBackInfo info) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========/userapp/system/v1/addFeedBack start========info=" + info.toString());
        }
        Response<String> response = new Response<>();
        //获取userId
        String userId = httpServletRequest.getHeader("userId");
        //数据校验
        if (StringUtil.isEmpty(info.getFeedBackContent())) {
            response.setCode(SystemCode.FEEDBACK_CONTENT_NULL);
        } else if (SystemDataValidation.FEEDBACK_CONTENT_SIZE < info.getFeedBackContent().length()) {
            response.setCode(SystemCode.FEEDBACK_CONTENT_SIZE);
        } else if (StringUtil.isEmpty(info.getBackType()+"")) {//geyf 校验反馈类型是否正确
            response.setCode(SystemCode.FEEDBACK_TYPE_ISNULL);
        } else if (info.getBackType() != 1 && info.getBackType() != SystemDataValidation.TWO){// 反馈类型不正确
            response.setCode(SystemCode.FEEDBACK_TYPE_ERROR);
        } else if (StringUtil.isEmpty(info.getPhoneType())){//手机型号为空
            response.setCode(SystemCode.FEEDBACK_PHONE_ISNULL);
        } else if (StringUtil.isEmpty(info.getPhoneSystem())){//手机系统版本号为空
            response.setCode(SystemCode.FEEDBACK_PHONESYSTEM_ISNULL);
        } else if (StringUtil.isEmpty(info.getApplicationType())){//APP版本号为空
            response.setCode(SystemCode.FEEDBACK_APP_ISNULL);
        } else {
            //将相关数据放入Feedback对象中
            Feedback feedback = new Feedback();
            feedback.setCreateTime(new Date(System.currentTimeMillis()));
            feedback.setFeedbackStatus(SystemDataValidation.FEEDBACK_STATUS_UNREAD);
            feedback.setFeedbackContent(info.getFeedBackContent());
            feedback.setFeedbackType(info.getBackType());
            feedback.setClientModel(info.getPhoneType());
            feedback.setClientVersion(info.getPhoneSystem());
            feedback.setAppVersion(info.getApplicationType());
            feedback.setAppType(SystemDataValidation.USER_APP_TYPE);
            try {
                String estateId = httpServletRequest.getHeader("estateId");
                if (StringUtil.isBlank(estateId)){
                    estateId = AESUtil.encrypt("1");
                }
                feedback.setEstateId(AESUtil.decrypt(estateId));
                //解密userId并存入
                feedback.setAuthorId(AESUtil.decrypt(userId));
                UserReq userReq = new UserReq();
                userReq.setUserId(feedback.getAuthorId());
                User user = userService.findUser(userReq,httpServletRequest);
                if (user != null){
                    feedback.setAuthorName(user.getUserName());
                }
                systemService.addFeedback(feedback);
            } catch (Exception e) {
                LOG.error("========/userapp/system/v1/addFeedBack error!=========", e);
                response.setCode(StatusCode.FAILURE);
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========/userapp/system/v1/addFeedBack end========response=" + response);
        }
        return response;
    }

}
