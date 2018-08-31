package com.betel.estatemgmt.business.userapp.house.controller;

import com.betel.estatemgmt.business.userapp.house.code.HouseCode;
import com.betel.estatemgmt.business.userapp.house.constant.HouseConstant;
import com.betel.estatemgmt.business.userapp.house.model.AuthHouse;
import com.betel.estatemgmt.business.userapp.house.model.GetSmsReq;
import com.betel.estatemgmt.business.userapp.house.service.AppAuthService;
import com.betel.estatemgmt.business.userapp.house.service.HouseService;
import com.betel.estatemgmt.business.userapp.login.constant.AppLoginConstant;
import com.betel.estatemgmt.business.userapp.user.code.UserCode;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.model.house.HouseMember;
import com.betel.estatemgmt.common.model.house.HouseOwner;
import com.betel.estatemgmt.common.model.house.MemberAuth;
import com.betel.estatemgmt.utils.*;
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
import java.util.List;

/**
 * <p>
 * app我的家模块控制层
 * </p>
 * ClassName: HouseController <br/>
 * Author: zhouye  <br/>
 * Date: 207/6/19 16:41 <br/>
 * Version: 1.0 <br>
 */
@RestController
@RequestMapping("userApp/auth")
public class AuthController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    AppAuthService appAuthService;

    @Autowired
    HouseService houseService;

    /**
     * <p>
     * 获取用户认证过的房屋列表
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/6/27 8:49
     * return response
     */
    @RequestMapping(value = "v1/findHouse", method = RequestMethod.GET)
    public Response<List<AuthHouse>> findAllHouse(HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----app/auth/v1/findHouse------start-");
        }
        Response<List<AuthHouse>> response = new Response<>();
        //获取用户Id
        String userId = request.getHeader("userId");
        //获得用户的房屋认证申请列表
        try {
            userId = AESUtil.decrypt(userId).trim();
            List<AuthHouse> lists = appAuthService.findHouseAuth(userId, HouseConstant.TWO);
            response.setData(lists);
        } catch (Exception e) {
            response.setCode(StatusCode.FAILURE);
            LOG.error("----app/auth/v1/findHouse------error-", e);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----app/auth/v1/findHouse------end--" + response);
        }
        return response;
    }


    /**
     * <p>
     * 查询用户申请房屋认证列表
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/6/21 9:11
     * return response
     */
    @RequestMapping(value = "v1/findHouseAuth", method = RequestMethod.GET)
    public Response<List<AuthHouse>> findHouseAuth(HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----app/auth/v1/findHouseAuth------start-");
        }
        Response<List<AuthHouse>> response = new Response<>();
        //获取用户Id
        String userId = request.getHeader("userId");
        try {
            userId = AESUtil.decrypt(userId).trim();
            List<AuthHouse> lists = appAuthService.findMyHomeList(userId);

            response.setData(lists);
        } catch (Exception e) {
            response.setCode(StatusCode.FAILURE);
            LOG.error("----app/auth/v1/findHouseAuth------error-", e);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----app/auth/v1/findHouseAuth------end--" + response);
        }
        return response;
    }

    /**
     * <p>
     * 修改认证
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/6/21 12:43
     * return response
     */
    @RequestMapping(value = "v1/updateAuth", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> updateAuthRevoke(@RequestBody MemberAuth memberAuth) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----app/auth/v1/updateAuth------start-" + memberAuth);
        }
        Response<String> response = new Response();
        try {
            String code = appAuthService.updateAuth(memberAuth);
            if (!StringUtil.isBlank(code)){
                response.setCode(code);
            }
        } catch (Exception e) {
            response.setCode(StatusCode.FAILURE);
            LOG.error("----app/auth/v1/updateAuth------error-", e);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----app/auth/v1/updateAuth------end--" + response);
        }
        return response;
    }
    /**
     * <p>
     * 申请成员认证
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/6/21 17:27
     * return response
     */
    @RequestMapping(value = "v1/addHouseMember", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response addHouseMember(@RequestBody MemberAuth houseAuth,HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----app/auth/v1/addHouseMember------start-" + houseAuth);
        }
        Response response = new Response();
        try {
            String code = appAuthService.addHouseAuth(houseAuth,AESUtil.decrypt(request.getHeader("userId")),request);
            if (!StringUtil.isBlank(code)){
                response.setCode(code);
            }
        } catch (Exception e) {
            response.setCode(StatusCode.FAILURE);
            LOG.error("----app/auth/v1/updateAuth------error-", e);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----app/auth/v1/addHouseMember------end--" + response);
        }
        return response;
    }

    /**
     * <p>
     * 户主激活获取短信验证码
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/11/15 19:08
     * */
    @RequestMapping(value = "v1/getSmsCode", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> getSmsCode(@RequestBody GetSmsReq getSmsCodeReq,HttpServletRequest request){
        if(LOG.isInfoEnabled()) {
            LOG.info("========/app/auth/v1/getSmsCode start=========getSmsCodeReq=" + getSmsCodeReq);
        }
        Response<String> response = new Response();
        try {
            //获取用户ID
            String userId = request.getHeader("userId");
            if(StringUtil.isBlank(userId)){
                //用户没有登录
                response.setCode(StatusCode.FAILURE);
                return response;
            }
            userId = AESUtil.decrypt(userId);
            //去空格
            trimSmsReq(getSmsCodeReq);
            //校验
            String res = validateSmsReq(getSmsCodeReq,true,userId,request);
            if (StringUtil.isBlank(res)){
                //发验证码
                response.setCode(getSMS(getSmsCodeReq));
            }else {
                response.setCode(res);
            }
        } catch (Exception e) {
            response.setCode(StatusCode.FAILURE);
            LOG.error("----app/auth/v1/getSmsCode------error-", e);
        }
        if(LOG.isInfoEnabled()) {
            LOG.info("========/app/auth/v1/getSmsCode end=========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 户主激活
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/11/15 19:08
     * */
    @RequestMapping(value = "v1/activateHouseOwner", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> activateHouseOwner(@RequestBody GetSmsReq getSmsCodeReq,HttpServletRequest request){
        if(LOG.isInfoEnabled()) {
            LOG.info("========/app/auth/v1/activateHouseOwner start=========getSmsCodeReq=" + getSmsCodeReq);
        }
        Response<String> response = new Response();
        try {
            //获取用户ID
            String userId = request.getHeader("userId");
            if(StringUtil.isBlank(userId)){
                //用户没有登录
                response.setCode(StatusCode.FAILURE);
                return response;
            }
            userId = AESUtil.decrypt(userId);
            //去空格
            trimSmsReq(getSmsCodeReq);
            //校验房号 手机号 姓名
            String res = validateSmsReq(getSmsCodeReq,true,userId,request);
            if (!StringUtil.isBlank(res)) {
                response.setCode(res);
                return response;
            }
            //校验验证码是否正确
            boolean isTrue = BaiduSmsUtil.checkValidateCode(getSmsCodeReq.getOwnerPhone(),AppLoginConstant.MARK_COMMON,getSmsCodeReq.getValidateCode());
            if (!isTrue){
                response.setCode(HouseCode.VALIDATE_CODE_ERROR);
                return response;
            }
            //判断激活的是已经激活过的户主还是待激活的户主
            HouseOwner owner = null;
            //判断是否有该已激活的户主信息
            HouseOwner owner1 = appAuthService.findBySmsReq(getSmsCodeReq,HouseConstant.TWO);
            //判断是否有待激活的户主信息
            HouseOwner owner2 = appAuthService.findBySmsReq(getSmsCodeReq,HouseConstant.ONE);
            if (owner1!=null){owner = owner1;}
            if (owner2!=null){owner = owner2;}
            //创建HouseOwner对象
            HouseOwner houseOwner = new HouseOwner();
            houseOwner.setOwnerId(owner.getOwnerId());
            houseOwner.setOwnerStatus(HouseConstant.OWNER_IS_STATUS);
            houseOwner.setUserId(userId);
            houseOwner.setUpdateTime(new Date(System.currentTimeMillis()));
            if (owner1!=null){
                appAuthService.activateOwner(houseOwner,getSmsCodeReq,true,request);
            }
            if (owner2!=null){
                appAuthService.activateOwner(houseOwner,getSmsCodeReq,false,request);
            }
        } catch (Exception e) {
            response.setCode(StatusCode.FAILURE);
            LOG.error("----app/auth/v1/activateHouseOwner------error-", e);
        }
        if(LOG.isInfoEnabled()) {
            LOG.info("========/app/auth/v1/activateHouseOwner end=========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 户主添加成员
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/11/15 19:08
     * */
    @RequestMapping(value = "v1/addMember", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> addMember(@RequestBody GetSmsReq getSmsCodeReq,HttpServletRequest request){
        if(LOG.isInfoEnabled()) {
            LOG.info("========/app/auth/v1/addMember start=========getSmsCodeReq=" + getSmsCodeReq);
        }
        Response<String> response = new Response();
        try {
            //去空格
            trimSmsReq(getSmsCodeReq);
            //校验
            String res = validateSmsReq(getSmsCodeReq,false,null,request);
            if (!StringUtil.isBlank(res)){
                response.setCode(res);
            }else {
                //添加成员
                appAuthService.addMember(getSmsCodeReq,request);
            }
        } catch (Exception e) {
            response.setCode(StatusCode.FAILURE);
            LOG.error("----app/auth/v1/addMember------error-", e);
        }
        if(LOG.isInfoEnabled()) {
            LOG.info("========/app/auth/v1/addMember end=========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 获取短信验证码
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/7/17 16:41
     *return response
     */
    private String getSMS(GetSmsReq smsReq){
        int i = BaiduSmsUtil.sendValidateCode(smsReq.getOwnerPhone(), AppLoginConstant.MARK_COMMON);
        if(i==AppLoginConstant.TWO){
            return UserCode.SMS_SEND_FAILURE;
        }else if(i==AppLoginConstant.THREE){
            return UserCode.SMS_SEND_TOO_FREQUENT;
        }
        return  StatusCode.SUCCESS;
    }

    /**
     * <p>
     * 去空格
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/11/15 19:21
     * */
    private void trimSmsReq(GetSmsReq smsReq){
        if (!StringUtil.isEmpty(smsReq.getOwnerName())){
            smsReq.setOwnerName(smsReq.getOwnerName().trim());
        }
        if (!StringUtil.isEmpty(smsReq.getOwnerPhone())){
            smsReq.setOwnerPhone(smsReq.getOwnerPhone().trim());
        }
    }

    /**
     * <p>
     * 校验户主激活入参
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/11/15 19:22
     * @param smsReq
     * @param flag true为户主  false为成员
     * */
    private String validateSmsReq(GetSmsReq smsReq,boolean flag,String userId,HttpServletRequest request) throws Exception {
        if (StringUtil.isBlank(smsReq.getHouseId())){
            return HouseCode.HOUSEIDNULL;
        }
        if (houseService.findHouseByHouseId(smsReq.getHouseId())==null){
            return HouseCode.HOUSEDELETE;
        }
        if (StringUtil.isBlank(smsReq.getOwnerName())){
            return HouseCode.USERNAMENULL;
        }
        if (smsReq.getOwnerName().length() > HouseConstant.OWNER_NAME_LENGTH){
            return HouseCode.USERNAMEFORMATWRONG;
        }
        if (!Validate.isPeopleName(smsReq.getOwnerName())){
            return HouseCode.USERNAMEFORMATWRONG;
        }
        if (StringUtil.isBlank(smsReq.getOwnerPhone())){
            return HouseCode.PHONENUM_NULL;
        }
        if (!Validate.isMobile(smsReq.getOwnerPhone())){
            return HouseCode.PHONENUM_FORMAT;
        }
        if (flag){
            //判断是否有该已激活的户主信息
            HouseOwner houseOwner = appAuthService.findBySmsReq(smsReq,HouseConstant.TWO);
            if (houseOwner!=null){
                if (houseOwner.getUserId().equals(userId)){
                    return HouseCode.MEMBER_IS_OWNER;
                }
            }else {
                //判断是否有该待激活的户主信息
                HouseOwner owner = appAuthService.findBySmsReq(smsReq,HouseConstant.ONE);
                if (owner==null) {
                    return HouseCode.NAME_PHONE_NOT_MATCH;
                }
            }
        }else {
            //判断是否有已激活的成员信息
            HouseMember member = appAuthService.findMemBySmsReq(smsReq);
            if (member!=null){
                return HouseCode.MEMBER_IS_EXIST;
            }
            //判断是否是户主
            HouseOwner owner = appAuthService.findOwnerBySmsReq(smsReq,request);
            if (owner!=null){
                return HouseCode.MEMBER_IS_OWNER;
            }
        }
        return null;
    }
}
