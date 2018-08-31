package com.betel.estatemgmt.business.web.login.code;

/**
 * <p>
 * web登录业务返回码
 * </p>
 * ClassName: WebLoginCode <br/>
 * Author: Cui.xx  <br/>
 * Date: 2017/5/15 15:57 <br/>
 * Version: 1.0 <br/>
 */
public interface WebLoginCode {

    /**
     * 账号或密码错误
     */
    String ACCOUNT_PASSWORD_ERROR = "L0001";

    /**
     * 图形验证码为空
     */
    String VALIDATECODE_NULL = "L0002";
    /**
     * 图形验证码错误
     */
    String VALIDATECODE_WRONG = "L0003";

    //密码格式错误
    String PASSWORD_FORMAT_ERROR = "M0007";

    //原密码错误
    String OLD_PWD_ERROR = "M0009";

    //密码为空
    String PASSWORD_NULL = "M0006";

    String SYSTEM_PRIVILEGE = "L0004";

    /**
     * 验证码失效
     */
    String VALIDATECODE_INVALID = "L0005";

    /**
     * 账号未启用
     */
    String ACCOUNT_BAN_LOGIN = "L0006";
}
