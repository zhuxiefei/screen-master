package com.betel.estatemgmt.business.userapp.auth.controller;

import com.betel.estatemgmt.business.userapp.auth.code.AuthCode;
import com.betel.estatemgmt.business.userapp.auth.constant.AuthConstant;
import com.betel.estatemgmt.business.userapp.auth.model.AppMemberAuth;
import com.betel.estatemgmt.business.userapp.auth.model.AuthNoticeParam;
import com.betel.estatemgmt.business.userapp.auth.model.DealWithAuthReq;
import com.betel.estatemgmt.business.userapp.auth.model.MemberAuthInfo;
import com.betel.estatemgmt.business.userapp.auth.service.MemberAuthService;
import com.betel.estatemgmt.business.userapp.house.code.HouseCode;
import com.betel.estatemgmt.common.Page;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.model.house.House;
import com.betel.estatemgmt.common.model.house.HouseAuthNotice;
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

import static com.betel.estatemgmt.common.StatusCode.FAILURE;

/**
 * <p>
 * 个人消息通知，成员认证
 * </p>
 * ClassName: MemberAuthController <br/>
 * Author: geyf  <br/>
 * Date: 2017/6/23 9:36 <br/>
 * Version: 1.0 <br/>
 */
@RestController
@RequestMapping("userApp/auth")
public class MemberAuthController {

    private static final Logger LOG = LoggerFactory.getLogger(MemberAuthController.class);

    @Autowired
    private MemberAuthService memberAuthService;

    /**
     * <p>
     * 查询成员认证列表
     * </p>
     * Author: geyf <br/>
     * Date: 2017/6/23 10:02
     *
     * @param page 分页基本参数
     * @return response
     */
    @RequestMapping(value = "/v1/findAllMemberAuth", method = RequestMethod.GET)
    public Response findAllMemberAuth(Page page, HttpServletRequest request){
        if(LOG.isInfoEnabled()){
              LOG.info("======== app/auth/v1/findAllMemberAuth start ========page="+page);
        }
        Response response = new Response();
        // 获取用户ID
        String userId = request.getHeader("userId");
        try {
            //解密
            userId = AESUtil.decrypt(userId);
        } catch (Exception e) {
            LOG.error("findAllMemberAuth error!", e);
            response.setCode(FAILURE);
        }
        try {
            //查询用户下是否存在房屋
            List<House> houseOwnerByuserId = memberAuthService.findHouseOwnerByuserId(userId);
            if(houseOwnerByuserId == null || houseOwnerByuserId.size() == 0){
                // 不存在
                response.setCode(AuthCode.USER_ISNOT_HOUSEOWNER);
            }else{
                Paging<AppMemberAuth> pager = new Paging<>(page.getCurPage(), page.getPageSize());
                AuthNoticeParam authNoticeParam = new AuthNoticeParam();
                authNoticeParam.setNoticeUserId(userId);
                authNoticeParam.setHouses(houseOwnerByuserId);
                List<AppMemberAuth> allAuthNotices = memberAuthService.findAllAuthNotices(pager,authNoticeParam);
                pager.result(allAuthNotices);
                response.setData(pager);
            }
        } catch (Exception e){
              LOG.error("findAllMemberAuth error!", e);
              response.setCode(FAILURE);
        }
        if(LOG.isInfoEnabled()){
              LOG.info("======== app/auth/v1/findAllMemberAuth end ========response=" + response);
          }
        return response;
    }


    /**
     * <p>
     * 查看认证详情
     * </p>
     * Author: geyf <br/>
     * Date: 2017/6/23 14:00
     *
     * @param houseAuthNotice 认证通知ID
     * @return response
     */
    @RequestMapping(value = "/v1/findMemberAuthDetail", method = RequestMethod.GET)
    public Response findMemberAuthDetail(HouseAuthNotice houseAuthNotice){
        if(LOG.isInfoEnabled()){
              LOG.info("======== app/auth/v1/findMemberAuthDetail start ========houseAuthNotice="+houseAuthNotice);
        }
        Response response = new Response();
        if(houseAuthNotice.getNoticeId() != null){
            try {
                MemberAuthInfo authInfo = memberAuthService.findAuthInfo(houseAuthNotice.getNoticeId());
                if (authInfo!=null){
                    if(authInfo.getAuthStatus() == AuthConstant.AUTHISREVOKE){
                        // 认证已经撤销
                        response.setCode(HouseCode.AUTHREVOKE);
                    }else if(authInfo.getAuthStatus() == AuthConstant.AUTHISDELETE){
                        // 认证已经删除
                        response.setCode(HouseCode.AUTHDELETE);
                    }else{
                        // 查询成功
                        response.setData(authInfo);
                    }
                }
            } catch (Exception e){
                LOG.error("findMemberAuthDetail error!", e);
                response.setCode(FAILURE);
            }
        }else{
            // 通知ID为空错误
            response.setCode(HouseCode.NOTICE_ID_NULL);
        }
        if(LOG.isInfoEnabled()){
            LOG.info("======== app/auth/v1/findMemberAuthDetail end ========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 户主认证成员认证
     * </p>
     * Author: geyf <br/>
     * Date: 2017/6/26 9:45
     *
     * @param dealWithAuthReq 认证ID 审核结果
     * @return response
     */
    @RequestMapping(value = "/v1/dealWithMemberAuth", method = RequestMethod.POST, consumes = "application/json")
    public Response dealWithMemberAuth(@RequestBody DealWithAuthReq dealWithAuthReq, HttpServletRequest request){
        if(LOG.isInfoEnabled()){
            LOG.info("======== app/auth/v1/dealWithMemberAuth start ========dealWithAuthReq="+dealWithAuthReq);
        }
        Response response = new Response();
        String userId = request.getHeader("userId");
        try {
            //解密
            userId = AESUtil.decrypt(userId);
        } catch (Exception e) {
            LOG.error("dealWithMemberAuth error!", e);
            response.setCode(FAILURE);
        }
        if(dealWithAuthReq.getAuthId() == null){
            // 认证ID为空
            response.setCode(HouseCode.AUTHIDNULL);
        }else if(dealWithAuthReq.getAuthStatus() == null){
            // 审核结果为空
            response.setCode(HouseCode.AUTHCONSULENULL);
        }else if(dealWithAuthReq.getAuthStatus().equals(AuthConstant.AUTHINFOAGREE) ||
                 dealWithAuthReq.getAuthStatus().equals(AuthConstant.AUTHINFOREFUSE)){
            // 处理认证 1 同意 ，2 拒绝
            try {
                String code = memberAuthService.dealWithMemberAuth(dealWithAuthReq, userId,request);
                response.setCode(code);
            }catch(Exception e){
                LOG.error("dealWithMemberAuth error!", e);
                response.setCode(FAILURE);
            }
        }else{
            // 认证信息错误（不是1也不是2）
            response.setCode(HouseCode.AUTHCONSULEFORMAT);
        }
        if(LOG.isInfoEnabled()){
            LOG.info("======== app/auth/v1/dealWithMemberAuth end ========response=" + response);
        }
        return response;
    }










}
