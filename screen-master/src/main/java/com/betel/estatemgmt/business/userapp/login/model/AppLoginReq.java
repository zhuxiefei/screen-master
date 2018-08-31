package com.betel.estatemgmt.business.userapp.login.model;

/**
 * <p>
 * app登录入参
 * </p>
 * ClassName: AppLoginReq <br/>
 * Author: Cui.xx  <br/>
 * Date: 2017/5/15 17:12 <br/>
 * Version: 1.0 <br/>
 */
public class AppLoginReq {

    /**
     * 手机号
     */
    private String mobile;
    /**
     * 密码
     */
    private String password;
    /**
     * 验证码
     */
    private String validateCode;
    /**
     * 登录类型：1：密码登录 2：验证码登录
     */
    private String loginType;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AppLoginReq{");
        sb.append("mobile='").append(mobile).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", validateCode='").append(validateCode).append('\'');
        sb.append(", loginType='").append(loginType).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
