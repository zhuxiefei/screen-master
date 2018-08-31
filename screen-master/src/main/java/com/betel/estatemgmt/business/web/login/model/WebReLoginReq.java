package com.betel.estatemgmt.business.web.login.model;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 二次登陆入参
 *
 * @author: Du.Hx
 * @date: 2018/1/24 13:53
 * @since: JDK1.8
 * @version: 1.0
 */
public class WebReLoginReq {

    @NotBlank(message = "adminId null")
    private String adminId;

    @NotBlank(message = "token null")
    private String tokenContent;

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getTokenContent() {
        return tokenContent;
    }

    public void setTokenContent(String tokenContent) {
        this.tokenContent = tokenContent;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WebReLoginReq{");
        sb.append("adminId='").append(adminId).append('\'');
        sb.append(", tokenContent='").append(tokenContent).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
