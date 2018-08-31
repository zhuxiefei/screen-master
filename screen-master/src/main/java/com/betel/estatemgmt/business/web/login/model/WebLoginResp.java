package com.betel.estatemgmt.business.web.login.model;

import java.util.List;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: WebLoginResp <br/>
 * Author: Cui.xx  <br/>
 * Date: 2017/5/15 14:24 <br/>
 * Version: 1.0 <br/>
 */
public class WebLoginResp {

    /**
     * 管理员昵称
     */
    private String name;
    /**
     * 菜单list
     */
    private List<Menu> menuList;

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WebLoginResp{");
        sb.append("name='").append(name).append('\'');
        sb.append(", menuList=").append(menuList);
        sb.append('}');
        return sb.toString();
    }
}
