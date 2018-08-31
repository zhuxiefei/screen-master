package com.betel.estatemgmt.business.web.login.model;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: LoginInfo <br/>
 * Author: Cui.xx  <br/>
 * Date: 2017/5/15 14:25 <br/>
 * Version: 1.0 <br/>
 */
public class LoginInfo {
    /**
     * 管理员id（主键）
     */
    private String userId;
    /**
     * token值
     */
    private String token;
    /**
     * 密码是否需要修改：1：不需要 2：需要
     */
    private Integer isInit;

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

    public Integer getIsInit() {
        return isInit;
    }

    public void setIsInit(Integer isInit) {
        this.isInit = isInit;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LoginInfo{");
        sb.append("userId='").append(userId).append('\'');
        sb.append(", token='").append(token).append('\'');
        sb.append(", isInit=").append(isInit);
        sb.append('}');
        return sb.toString();
    }
}
