package com.betel.estatemgmt.business.userapp.house.service;

import com.betel.estatemgmt.business.userapp.house.model.AuthHouse;
import com.betel.estatemgmt.business.userapp.house.model.GetSmsReq;
import com.betel.estatemgmt.common.model.house.*;
import com.betel.estatemgmt.common.model.user.UserAccount;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: AppAuthService <br/>
 * Author: zhouye  <br/>
 * Date: 2017/6/21 9:14 <br/>
 * Version: 1.0 <br/>
 */
public interface AppAuthService {

    /**
     * <p>
     * 查询用户的房屋列表 1,认证记录 2，拥有的房屋
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/6/21 9:21
     * return response
     */
    List<AuthHouse> findHouseAuth(String userId, int type);


    /**
     * <p>
     * 申请房屋成员认证
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/6/30 9:24
     * return response
     */
    String addHouseAuth(MemberAuth houseAuth, String userId,HttpServletRequest request) throws Exception;

    /**
     * <p>
     * 根据id查询认证详情
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/6/21 12:44
     * return response
     */
    MemberAuth findAuthById(Long authId);

    /**
     * <p>
     * 根据认证id修改认证信息
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/6/21 15:23
     * return response
     */
    String updateAuth(MemberAuth memberAuth) throws Exception;


    /**
     * <p>
     * 查询房屋信息
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/6/21 16:38
     * return response
     */
    House findHouseByHouseId(String houseId);

//    /**
//     * <p>
//     * 根据用户id查询用户的号码
//     * </p>
//     * Author: zhouye <br/>
//     * Date: 2017/6/22 13:25
//     * return response
//     */
//    UserAccount selectUserPhoneByUserId(String userId);

    /**
     * <p>
     * 添加认证通知
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/11/14 18:19
     */
    void addnotice(HouseAuthNotice houseAuthnotice);

    /**
     * <p>
     * 根据房屋 姓名 手机号 查询户主信息
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/11/15 19:40
     *
     * @param status 1代表待激活 2代表已激活
     */
    HouseOwner findBySmsReq(GetSmsReq smsReq, Integer status);

    /**
     * <p>
     * 激活户主
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/11/16 9:20
     *
     * @param flag true代表已激活的户主  false代表待激活的户主
     */
    void activateOwner(HouseOwner houseOwner, GetSmsReq smsReq, boolean flag,HttpServletRequest request) throws Exception;


    /**
     * <p>
     * 根据房屋 姓名 手机号 查询已激活的成员信息
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/11/16 9:58
     */
    HouseMember findMemBySmsReq(GetSmsReq smsReq);

    /**
     * <p>
     * 户主添加成员
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/11/16 10:40
     */
    void addMember(GetSmsReq smsReq,HttpServletRequest request) throws Exception;

    /**
     * <p>
     * 我的家列表
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/11/17 11:26
     */
    List<AuthHouse> findMyHomeList(String userId);

    /**
     * <p>
     * 根据房屋ID 手机号 查询户主
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/11/22 10:03
     */
    HouseOwner findOwnerBySmsReq(GetSmsReq smsReq,HttpServletRequest request) throws Exception;
}
