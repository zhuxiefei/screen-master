package com.betel.estatemgmt.business.userapp.auth.model;

/**
 * <p>
 * 文件说明
 * </p>
 * ClassName: DealWithAuthReq <br/>
 * Author: geyf  <br/>
 * Date: 2017/6/26 9:43 <br/>
 * Version: 1.0 <br/>
 */
public class DealWithAuthReq {

    private Long authId;

    private String authStatus;

    public Long getAuthId() {
        return authId;
    }

    public void setAuthId(Long authId) {
        this.authId = authId;
    }

    public String getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(String authStatus) {
        this.authStatus = authStatus;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DealWithAuthReq{");
        sb.append("authId=").append(authId);
        sb.append(", authStatus='").append(authStatus).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
