package com.betel.estatemgmt.business.web.login.model;

/**
 * <p>
 * 二次登录关键信息
 *
 * @author: Du.Hx
 * @date: 2018/1/24 15:42
 * @since: JDK1.8
 * @version: 1.0
 */
public class ReLoginInfo {

    private String adminId;

    private String adminName;

    private String acctName;

    private String adminPassword;

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

    public String getAcctName() {
        return acctName;
    }

    public void setAcctName(String acctName) {
        this.acctName = acctName;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ReLoginInfo{");
        sb.append("adminId='").append(adminId).append('\'');
        sb.append(", adminName='").append(adminName).append('\'');
        sb.append(", acctName='").append(acctName).append('\'');
        sb.append(", adminPassword='").append(adminPassword).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
