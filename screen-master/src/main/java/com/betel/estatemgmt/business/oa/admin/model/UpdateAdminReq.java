package com.betel.estatemgmt.business.oa.admin.model;

/**
 * Created by Administrator on 2018/5/7/007.
 */
public class UpdateAdminReq {

    private String adminId;

    private String isEnableAccount;

    private String roleId;

    private String estateId;

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getIsEnableAccount() {
        return isEnableAccount;
    }

    public void setIsEnableAccount(String isEnableAccount) {
        this.isEnableAccount = isEnableAccount;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "UpdateAdminReq{" +
                "adminId='" + adminId + '\'' +
                ", isEnableAccount='" + isEnableAccount + '\'' +
                ", roleId='" + roleId + '\'' +
                ", estateId='" + estateId + '\'' +
                '}';
    }
}
