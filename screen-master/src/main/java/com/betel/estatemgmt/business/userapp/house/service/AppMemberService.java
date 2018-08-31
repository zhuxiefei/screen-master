package com.betel.estatemgmt.business.userapp.house.service;

import com.betel.estatemgmt.business.userapp.house.model.Member;
import com.betel.estatemgmt.common.model.house.MemberAuth;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 成员管理service层
 * </p>
 * ClassName: AppMemberService <br/>
 * Author: zhouye  <br/>
 * Date: 2017/6/22 15:38 <br/>
 * Version: 1.0 <br/>
 */
public interface AppMemberService {

    /**
     * <p>
     * 查询房屋成员
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/6/22 15:39
     * return response
     */
    List<Member> findAllMemberByHouseId(String houseId, HttpServletRequest request) throws Exception;

    /**
     * <p>
     * 删除房屋成员
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/6/22 15:43
     * return response
     */
    String deleteMemberBymemberId(String memberId) throws Exception;

}
