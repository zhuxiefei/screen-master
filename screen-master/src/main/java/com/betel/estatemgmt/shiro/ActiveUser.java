package com.betel.estatemgmt.shiro;

import com.betel.estatemgmt.business.web.login.model.Menu;

import java.util.List;

/**
 * <p>
 * 返回前台的用户信息
 * </p>
 * ClassName: ActiveUser <br/>
 * @author: Cui.xx  <br/>
 * Date: 2017/5/15 10:06 <br/>
 * Version: 1.0 <br/>
 */
public class ActiveUser {

    private String userId;
    private String adminName;
    private String token;
    private Integer isInit;
    private List<Menu> menus;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
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

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ActiveUser{");
        sb.append("userId='").append(userId).append('\'');
        sb.append(", adminName='").append(adminName).append('\'');
        sb.append(", token='").append(token).append('\'');
        sb.append(", isInit=").append(isInit);
        sb.append(", menus=").append(menus);
        sb.append('}');
        return sb.toString();
    }
}
