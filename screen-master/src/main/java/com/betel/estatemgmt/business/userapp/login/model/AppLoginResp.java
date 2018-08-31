package com.betel.estatemgmt.business.userapp.login.model;

/**
 * <p>
 * app登录返回信息
 * </p>
 * ClassName: AppLoginResp <br/>
 * Author: Cui.xx  <br/>
 * Date: 2017/5/15 17:20 <br/>
 * Version: 1.0 <br/>
 */
public class AppLoginResp {

    /**
     * 用户id
     */
    private String userId;
    /**
     * token值
     */
    private String token;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AppLoginResp{");
        sb.append("userId='").append(userId).append('\'');
        sb.append(", token='").append(token).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
