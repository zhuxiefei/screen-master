package com.betel.estatemgmt.business.web.login.model;

/**
 * <p>
 * 登录入参
 * </p>
 * ClassName: WebLoginReq <br/>
 * Author: Cui.xx  <br/>
 * Date: 2017/5/15 14:13 <br/>
 * Version: 1.0 <br/>
 */
public class WebLoginReq {

    /**
     * 账号
     */
    private String account;
    /**
     * 密码
     */
    private String password;

    private String validateCode;

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WebLoginReq{");
        sb.append("account='").append(account).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
