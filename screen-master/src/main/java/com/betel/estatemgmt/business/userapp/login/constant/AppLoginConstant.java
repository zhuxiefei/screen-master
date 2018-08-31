package com.betel.estatemgmt.business.userapp.login.constant;

/**
 * <p>
 * app登录常量
 * </p>
 * ClassName: AppLoginConstant <br/>
 * Author: Cui.xx  <br/>
 * Date: 2017/5/15 17:16 <br/>
 * Version: 1.0 <br/>
 */
public interface AppLoginConstant {

    /**
     * 登录方式：密码登录
     */
    String LOGINTYPE_PASSWORD = "1";
    /**
     * 登录方式：验证码登录
     */
    String LOGINTYPE_VALIDATECODE = "2";
    /**
     * 用户状态：3 禁止登录
     */
    Integer LOGIN_FORBIDDEN = 3;
    /**
     * 登录发送短信验证码mark
     */
    String MARK_LOGIN = "Login";

    /**
     * 注册发送短信验证码mark
     */
    String MARK_REGISTER = "register";

    /**
     * 通用发送短信验证码mark
     */
    String MARK_COMMON = "common";

    /**
     * 数字2
     */
    int TWO = 2;

    /**
     * 数字3
     */
    int THREE = 3;
}
