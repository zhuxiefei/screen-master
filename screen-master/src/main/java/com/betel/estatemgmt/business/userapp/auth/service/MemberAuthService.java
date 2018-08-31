package com.betel.estatemgmt.business.userapp.auth.service;

import com.betel.estatemgmt.business.userapp.auth.model.AppMemberAuth;
import com.betel.estatemgmt.business.userapp.auth.model.AuthNoticeParam;
import com.betel.estatemgmt.business.userapp.auth.model.DealWithAuthReq;
import com.betel.estatemgmt.business.userapp.auth.model.MemberAuthInfo;
import com.betel.estatemgmt.common.model.house.House;
import com.betel.estatemgmt.utils.pagination.model.Paging;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * app 成员认证
 * </p>
 * ClassName: MemberAuthService <br/>
 * Author: geyf  <br/>
 * Date: 2017/6/23 9:37 <br/>
 * Version: 1.0 <br/>
 */
public interface MemberAuthService {

    /**
     * <p>
     * 成员认证
     * </p>
     * Author: geyf <br/>
     * Date: 2017/6/26 10:14
     *
     * @param dealWithAuthReq 认证ID 审核结果
     *        userId 当前用户ID
     * @return String 返回的code
     */
     String dealWithMemberAuth(DealWithAuthReq dealWithAuthReq, String userId, HttpServletRequest request) throws Exception;


    /**
     * <p>
     * 查看认证详情
     * </p>
     * Author: geyf <br/>
     * Date: 2017/6/23 14:37
     *
     * @param noticeId 通知ID
     * @@return MemberAuthInfo 认证详情
     */
     MemberAuthInfo findAuthInfo(Long noticeId);



    /**
     * <p>
     * 根据户主查询其下房屋
     * </p>
     * Author: geyf <br/>
     * Date: 2017/6/23 11:08
     *
     * @param userId 户主ID
     * @return List<House> 房屋信息
     */
     List<House> findHouseOwnerByuserId(String userId);



    /**
     * <p>
     * 根据用户ID 查询用户认证通知列表
     * </p>
     * Author: geyf <br/>
     * Date: 2017/6/23 10:58
     *
     * @param authNoticeParam 用户id 房屋
     * @return List<MemberAuth> 认证通知集合
     */
    List<AppMemberAuth> findAllAuthNotices(Paging<AppMemberAuth> pager, AuthNoticeParam authNoticeParam);
}
