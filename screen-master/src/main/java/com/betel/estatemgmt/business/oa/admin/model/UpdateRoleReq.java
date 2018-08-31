package com.betel.estatemgmt.business.oa.admin.model;

import com.betel.estatemgmt.common.model.admin.AdminRole;
import com.betel.estatemgmt.common.model.admin.AdminRolePriRela;

import java.util.List;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: UpdateRoleReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/1/30 18:22 <br/>
 * Version: 1.0 <br/>
 */
public class UpdateRoleReq {

    private String roleId;

    private String roleName;

    private String roleDesc;

    private String privilegeIds;

    private String estateId;

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public String getPrivilegeIds() {
        return privilegeIds;
    }

    public void setPrivilegeIds(String privilegeIds) {
        this.privilegeIds = privilegeIds;
    }

    @Override
    public String toString() {
        return "UpdateRoleReq{" +
                "roleId='" + roleId + '\'' +
                ", roleName='" + roleName + '\'' +
                ", roleDesc='" + roleDesc + '\'' +
                ", privilegeIds='" + privilegeIds + '\'' +
                ", estateId='" + estateId + '\'' +
                '}';
    }
}
