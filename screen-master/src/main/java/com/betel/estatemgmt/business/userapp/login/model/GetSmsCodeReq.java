package com.betel.estatemgmt.business.userapp.login.model;

/**
 * <p>
 * 发送登录验证码入参
 * </p>
 * ClassName: GetSmsCodeReq <br/>
 * Author: Cui.xx  <br/>
 * Date: 2017/5/17 13:33 <br/>
 * Version: 1.0 <br/>
 */
public class GetSmsCodeReq {

    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GetSmsCodeReq{");
        sb.append("mobile='").append(mobile).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
