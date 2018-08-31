package com.betel.estatemgmt.business.oa.admin.model;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: DeleteRoleReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/1/30 17:22 <br/>
 * Version: 1.0 <br/>
 */
public class DeleteRoleReq {

    private String roleId;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DeleteRoleReq{");
        sb.append("roleId='").append(roleId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
