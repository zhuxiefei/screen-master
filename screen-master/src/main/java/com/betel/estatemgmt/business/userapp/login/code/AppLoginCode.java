package com.betel.estatemgmt.business.userapp.login.code;

/**
 * <p>
 * app登录业务返回码
 * </p>
 * ClassName: AppLoginCode <br/>
 * Author: Cui.xx  <br/>
 * Date: 2017/5/15 17:22 <br/>
 * Version: 1.0 <br/>
 */
public interface AppLoginCode {

    /**
     * 账号或密码错误
     */
    String ACCOUNT_PASSWORD_ERROR = "U0001";
    /**
     * 账号禁止登录
     */
    String ACCOUNT_FORBIDDEN_LOGIN = "U0002";
    /**
     * 验证码错误
     */
    String VALIDATECODE_ERROR = "U0003";
    /**
     * 账号不存在
     */
    String ACCOUNT_NOT_EXIST = "U0004";
}
