package com.betel.estatemgmt.business.userapp.house.controller;

import com.betel.estatemgmt.business.userapp.house.code.HouseCode;
import com.betel.estatemgmt.business.userapp.house.constant.HouseConstant;
import com.betel.estatemgmt.business.userapp.house.model.AuthHouse;
import com.betel.estatemgmt.business.userapp.house.model.Member;
import com.betel.estatemgmt.business.userapp.house.service.AppAuthService;
import com.betel.estatemgmt.business.userapp.house.service.AppMemberService;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.model.house.HouseMember;
import com.betel.estatemgmt.utils.encrypt.AESUtil;
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
 * app成员管理模块管理操作
 * </p>
 * ClassName: MemberController <br/>
 * Author: zhouye  <br/>
 * Date: 2017/6/21 8:52 <br/>
 * Version: 1.0 <br/>
 */
@RestController
@RequestMapping("userApp/member")
public class MemberController {
    private static final Logger LOG = LoggerFactory.getLogger(MemberController.class);

    @Autowired
    AppMemberService appMemberService;

    @Autowired
    AppAuthService appAuthService;


    /**
     * <p>
     * 查询成员信息
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/6/26 16:52
     * return response
     */

    @RequestMapping(value = "v1/findMember", method = RequestMethod.GET)
    public Response<List<Member>> findMember(String houseId, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("------app/member/v1/findMember-------------start----" + houseId);
        }
        Response<List<Member>> response = new Response<>();
        //验证房屋id是否为空
        if (houseId == null) {
            response.setCode(HouseCode.HOUSEIDNULL);
        } else {
            try {
                String userId = request.getHeader("userId");
                List<AuthHouse> lists = appAuthService.findHouseAuth(AESUtil.decrypt(userId), HouseConstant.TWO);
                boolean flag = false;
                for (AuthHouse list : lists) {
                    if (list.getHouseId().equals(houseId.toString())) {
                        flag = true;
                    }
                }
                if (flag) {
                    List<Member> members = appMemberService.findAllMemberByHouseId(houseId,request);
                    response.setData(members);
                } else {
                    response.setCode(HouseCode.NOT_HOUSE_MEMBER);
                }
            } catch (Exception e) {
                LOG.info("------app/member/v1/findMember----------error----", e);
                response.setCode(StatusCode.FAILURE);
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("------app/member/v1/findMember-------------end--" + response.toString());
        }
        return response;
    }

    /**
     * <p>
     * 删除成员
     * </p>
     * Author:zhouye<br/>
     * Date:2017/6/26 16:52
     * return response
     */

    @RequestMapping(value = "v1/deleteMember", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response deleteMember(@RequestBody HouseMember houseMember) {
        if (LOG.isInfoEnabled()) {
            LOG.info("------app/member/v1/deleteMember-------------start----" + houseMember.getMemberId());
        }
        Response response = new Response();
        try {
            String code = appMemberService.deleteMemberBymemberId(houseMember.getMemberId());
            response.setCode(code);
        } catch (Exception e) {
            LOG.info("------app/member/v1/deleteMember-------------error----", e);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("------app/member/v1/deleteMember-------------end--" + response);
        }
        return response;
    }
}
