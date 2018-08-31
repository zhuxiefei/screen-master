package com.betel.estatemgmt.business.web.login.model;

/**
 * 管理员密码
 * ClassName: AdminPwd
 * Author: Zhang li
 * Date: 2017/5/18 13:29
 * Version: 1.0
 */
public class AdminPwd {

    private String adminId;

    private String adminName;
    //数据库密码
    private String adminPassword;
    //新密码
    private String newPwd;
    //输入原密码
    private String opwd;
    //原密码
    private String oldPwd;

    private String AgainNewPwd;

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    public String getOpwd() {
        return opwd;
    }

    public void setOpwd(String opwd) {
        this.opwd = opwd;
    }

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getAgainNewPwd() {
        return AgainNewPwd;
    }

    public void setAgainNewPwd(String againNewPwd) {
        AgainNewPwd = againNewPwd;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AdminPwd{");
        sb.append("adminId='").append(adminId).append('\'');
        sb.append(", adminName='").append(adminName).append('\'');
        sb.append(", adminPassword='").append(adminPassword).append('\'');
        sb.append(", newPwd='").append(newPwd).append('\'');
        sb.append(", opwd='").append(opwd).append('\'');
        sb.append(", oldPwd='").append(oldPwd).append('\'');
        sb.append(", AgainNewPwd='").append(AgainNewPwd).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
