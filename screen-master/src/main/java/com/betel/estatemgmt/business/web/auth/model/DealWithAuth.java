package com.betel.estatemgmt.business.web.auth.model;

/**
 * <p>
 * 管理员认证请求
 * </p>
 * ClassName: DealWithAuth <br/>
 * Author: zhanglei  <br/>
 * Date: 2017/6/24 13:29 <br/>
 * Version: 1.0 <br/>
 */
public class DealWithAuth {

    /**
     * 认证ID
     */
    private Long authId;

    /**
     * 审核意见
     */
    private String authContent;

    /**
     * 审核结果：
     * 1为认证中，2为认证通过，3为不通过，4为已撤销，5为已删除
     */
    private Integer authStatus;

    public Long getAuthId() {
        return authId;
    }

    public void setAuthId(Long authId) {
        this.authId = authId;
    }

    public String getAuthContent() {
        return authContent;
    }

    public void setAuthContent(String authContent) {
        this.authContent = authContent == null ? null : authContent.trim();
    }

    public Integer getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(Integer authStatus) {
        this.authStatus = authStatus;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DealWithAuth{");
        sb.append("authId=").append(authId);
        sb.append(", authContent='").append(authContent).append('\'');
        sb.append(", authStatus=").append(authStatus);
        sb.append('}');
        return sb.toString();
    }
}
