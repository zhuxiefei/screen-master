package com.betel.estatemgmt.business.web.login.model;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: Menu <br/>
 * Author: Cui.xx  <br/>
 * Date: 2017/5/15 13:39 <br/>
 * Version: 1.0 <br/>
 */
public class Menu {

    /**
     * 菜单id
     */
    private String menuId;
    /**
     * 菜单名称
     */
    private String menuName;
    /**
     * 菜单父id
     */
    private String parentId;

    private String menuType;

    private Long roleId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Menu{");
        sb.append("menuId='").append(menuId).append('\'');
        sb.append(", menuName='").append(menuName).append('\'');
        sb.append(", parentId='").append(parentId).append('\'');
        sb.append(", menuType='").append(menuType).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
