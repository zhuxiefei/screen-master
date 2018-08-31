package com.betel.estatemgmt.business.web.auth.controller;

import com.betel.estatemgmt.business.web.auth.service.AuthService;
import com.betel.estatemgmt.common.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 文件说明
 * </p>
 * ClassName: WebAuthController <br/>
 * Author: zhanglei  <br/>
 * Date: 2017/6/20 9:18 <br/>
 * Version: 1.0 <br/>
 */
@RestController
@RequestMapping("web/auth/")
public class WebAuthController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(WebAuthController.class);


    @Autowired
    private AuthService authService;

//    /**
//     * <p>
//     * 户主认证列表
//     * </p>
//     * Author: zhanglei <br/>
//     * Date: 2017/6/20 9:33
//     *
//     * @param houseOwnePageReq 入参信息
//     * @return Paging<HouseOwneAuth>
//     */
//@RequiresPermissions("auth-findAllHouseOwneAuth")
//    @RequestMapping(value = "v1/findAllHouseOwneAuth", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
//    public Response<Paging<HouseOwneAuth>> findAllHouseOwneAuth(@RequestBody HouseOwnePageReq houseOwnePageReq) {
//        if (LOG.isInfoEnabled()) {
//            LOG.info("========web/auth/v1/findAllHouseOwneAuth start========houseOwnePageReq=" + houseOwnePageReq);
//        }
//        Response<Paging<HouseOwneAuth>> response = new Response<>();
//        //认证状态处理
//        if(StringUtil.isBlank(houseOwnePageReq.getReviewStatus())){
//            houseOwnePageReq.setReviewStatus(null);
//        }
//        if(StringUtil.isBlank(houseOwnePageReq.getAuthStatus())){
//            houseOwnePageReq.setAuthStatus(null);
//        }
//        if(StringUtil.isBlank(houseOwnePageReq.getStartTime())){
//            houseOwnePageReq.setStartTime(null);
//        }
//        if(StringUtil.isBlank(houseOwnePageReq.getEndTime())){
//            houseOwnePageReq.setEndTime(null);
//        }
//        //验证申请人姓名
//        if(!StringUtil.isBlank(houseOwnePageReq.getApplicantName())){
//            if(houseOwnePageReq.getApplicantName().length()> HouseAuthConstant.NUM_OFFEN_TEN){
//                response.setCode(HouseCode.APPLICANT_NAME_OUT_LENGTH);
//                return response;
//            }else if(!Validate.isRealName(houseOwnePageReq.getApplicantName())){
//                response.setCode(HouseCode.APPLICANT_NAME_FORMAT_WRONG);
//                return response;
//            }else {
//                //申请人姓名前后去空格
//                houseOwnePageReq.setApplicantName(houseOwnePageReq.getApplicantName().trim());
//            }
//        }else {
//            houseOwnePageReq.setApplicantName(null);
//        }
//        //验证房屋名称
//        if(!StringUtil.isBlank(houseOwnePageReq.getHouseInfo())){
//            if(houseOwnePageReq.getHouseInfo().length()>HouseAuthConstant.NUM_OFFEN_FIFTY){
//                response.setCode(HouseCode.HOUSEINFO_OUTOFLENGTH);
//                return response;
//            } else if(!Validate.isHouseName(houseOwnePageReq.getHouseInfo())){
//                response.setCode(HouseCode.HOUSEINFO_FORMATWRONG);
//                return response;
//            }else {
//                //房屋名称前后去空格
//                houseOwnePageReq.setHouseInfo(houseOwnePageReq.getHouseInfo().trim());
//            }
//        }else {
//            houseOwnePageReq.setHouseInfo(null);
//        }
//        try {
//            Paging<HouseOwneAuth> paging = new Paging<>(houseOwnePageReq.getCurPage(), houseOwnePageReq.getPageSize());
//            List<HouseOwneAuth> houseOwneAuthList = authService.findAllHouseOwneAuth(paging, houseOwnePageReq);
//            if(LOG.isDebugEnabled()){
//                LOG.debug("---------houseOwneAuthList-----------"+houseOwneAuthList);
//            }
//            paging.result(houseOwneAuthList);
//            response.setData(paging);
//        } catch (Exception e) {
//            LOG.error("========web/auth/v1/findAllHouseOwneAuth error!=========", e);
//            response.setCode(StatusCode.FAILURE);
//        }
//        if (LOG.isInfoEnabled()) {
//            LOG.info("========web/auth/v1/findAllHouseOwneAuth end========response" + response);
//        }
//        return response;
//    }
//
//    /**
//     * 户主认证详情
//     * @param houseAuth 认证ID
//     * @return HouseOwneAuthInfo
//     */
//@RequiresPermissions("auth-findHouseOwneAuthDetail")
//    @RequestMapping(value = "v1/findHouseOwneAuthDetail", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
//    public Response<HouseOwneAuthInfo> findHouseOwneAuthDetail(@RequestBody HouseAuth houseAuth) {
//        if (LOG.isInfoEnabled()) {
//            LOG.info("========web/auth/v1/findHouseOwneAuthDetail start========houseAuth=" + houseAuth);
//        }
//        Response<HouseOwneAuthInfo> response = new Response<>();
//        String code;
//        // 判断认证ID和认证状态是否符合要求
//        code = findHouseOwneAuthJudge(houseAuth);
//        if (StringUtil.isEmpty(code)){
//            try {
//                HouseOwneAuthInfo houseOwneAuthInfo = authService.findHouseOwneAuthDetail(houseAuth);
//                if(LOG.isDebugEnabled()){
//                    LOG.debug("---------houseOwneAuthInfo-----------"+houseOwneAuthInfo);
//                }
//                response.setData(houseOwneAuthInfo);
//            } catch (Exception e) {
//                LOG.error("========web/auth/v1/findHouseOwneAuthDetail error!=========", e);
//                response.setCode(StatusCode.FAILURE);
//            }
//        }else{
//            response.setCode(code);
//        }
//        if (LOG.isInfoEnabled()) {
//            LOG.info("========web/auth/v1/findHouseOwneAuthDetail end========");
//        }
//        return response;
//    }
//
//    /**
//     * 管理员进行户主审核
//     * @param dealWithAuth 入参
//     * @return Response
//     */
//@RequiresPermissions("auth-dealWithAuth")
//    @RequestMapping(value = "v1/dealWithAuth", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
//    public Response dealWithAuth(@RequestBody DealWithAuth dealWithAuth) {
//        if (LOG.isInfoEnabled()) {
//            LOG.info("========web/auth/v1/dealWithAuth start========dealWithAuth=" + dealWithAuth);
//        }
//        Response response = new Response<>();
//        String code;
//        // 判断认证ID和认证状态是否符合要求
//        code = dealWithAuthJudge(dealWithAuth);
//        if (StringUtil.isEmpty(code)){
//            try {
//                if(LOG.isDebugEnabled()){
//                    LOG.debug("---------dealWithAuth-----------"+dealWithAuth);
//                }
//                authService.dealWithAuth(dealWithAuth);
//            } catch (Exception e) {
//                LOG.error("========web/auth/v1/dealWithAuth error!=========", e);
//                response.setCode(StatusCode.FAILURE);
//            }
//        }else{
//            response.setCode(code);
//        }
//        if (LOG.isInfoEnabled()) {
//            LOG.info("========web/auth/v1/dealWithAuth end========response" + response);
//        }
//        return response;
//    }
//
//    /**
//     * <p>
//     * 判断ID和认证状态
//     * </p>
//     * Author: zhanglei <br/>
//     * Date: 2017/6/20 11:34
//     *
//     * @param houseAuth 认证判断信息
//     * @return code
//     */
//    private String findHouseOwneAuthJudge(HouseAuth houseAuth) {
//        String code = null;
//        // 判断认证ID是否为空
//        if (null == houseAuth.getAuthId()) {
//            code = HouseCode.AUTHIDNULL;
//            return code;
//        }
//        // 通过认证ID查询认证信息
//        houseAuth = authService.findHouseAuth(houseAuth.getAuthId());
//        if (null==houseAuth) {
//            code = HouseCode.AUTHINFONULL;
//            return code;
//        }
//        return code;
//    }
//
//    /**
//     * 审核数据判断
//     * @param dealWithAuth 入参
//     * @return code
//     */
//    private String dealWithAuthJudge(DealWithAuth dealWithAuth){
//        String code = null;
//        // 判断认证ID是否为空
//        if (null == dealWithAuth.getAuthId()) {
//            code = HouseCode.AUTHIDNULL;
//            return code;
//        }
//        // 判断审核结果是否为空
//        if (null==dealWithAuth.getAuthStatus()){
//            code = HouseCode.AUTHCONSULENULL;
//            return code;
//        }
//        // 判断审核内容是否为空
//        if (StringUtil.isEmpty(dealWithAuth.getAuthContent())){
//            code = HouseCode.NOTNULLAUTHCONTENT;
//            return code;
//        }
//        // 判断审核内容长度是否大于限制
//        if (dealWithAuth.getAuthContent().length()>AUTH_CONTENT_LENGTH){
//            code = HouseCode.AUTHLENGTHOUTOFSIZE;
//            return code;
//        }
//        // 通过认证ID查询认证信息
//        HouseAuth houseAuth = authService.findHouseAuth(dealWithAuth.getAuthId());
//        //为空代表该房屋被删除
//        if (null==houseAuth) {
//            code = com.betel.estatemgmt.business.web.house.code.HouseCode.HOUSE_IS_DELETE;
//            return code;
//        }
//        // 判断审核是否已结束
//        if (1 == houseAuth.getAuthStatus() && 1 != houseAuth.getReviewStatus()) {
//            code = HouseCode.AUTHEND;
//            return code;
//        }
//        // 判断审核是否已撤销
//        if (AUTH_STATUS_BACKOUT == houseAuth.getAuthStatus()) {
//            code = HouseCode.AUTHREVOKE;
//            return code;
//        }
//        // 判断审核是否已删除
//        if (AUTH_STATUS_DELETED == houseAuth.getAuthStatus()) {
//            code = HouseCode.AUTHDELETE;
//            return code;
//        }
//        return code;
//    }

}
