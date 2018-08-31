package com.betel.estatemgmt.business.smartre.user.service;

import com.betel.estatemgmt.business.smartre.user.model.UserReq;
import com.betel.estatemgmt.business.web.housestatus.model.HouseStatusRegisterUser;
import com.betel.estatemgmt.common.model.user.User;
import com.betel.estatemgmt.common.model.user.UserAccount;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhangjian on 2018/1/20.
 */
public interface UserService {
    /**
     * 根据账号查询用户账号信息
     *
     * @param accountName
     * @return
     */
    UserAccount findByAccountName(String accountName, HttpServletRequest request) throws Exception;

    /**
     * 根据userId查询用户账号信息
     *
     * @param userId
     * @return
     */
    UserAccount findByUserId(String userId, HttpServletRequest request) throws Exception;

    /**
     * 查询用户是否存在
     *
     * @return
     */
    User findUser(UserReq userReq, HttpServletRequest request) throws Exception;

    /**
     * 验证用户昵称是否重复
     *
     * @param userName
     * @return
     */
    Boolean validateUserName(String userName, HttpServletRequest request) throws Exception;

    /**
     * 房屋状态注册用户
     *
     * @param memberRealName
     * @param memberPhoneNum
     * @return
     */
    HouseStatusRegisterUser register(String memberRealName, String memberPhoneNum, HttpServletRequest request) throws Exception;
}
