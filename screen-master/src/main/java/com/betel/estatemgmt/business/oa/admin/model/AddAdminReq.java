package com.betel.estatemgmt.business.oa.admin.model;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: AddAdminReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/1/30 19:24 <br/>
 * Version: 1.0 <br/>
 */
public class AddAdminReq {

    private String adminId;

    private String isInit;

    private String adminName;

    private String roleId;

    private String adminPassword;

    private String acctName;

    private String acctType;

    private String isEnableAccount;

    private String estateId;

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
    }

    public String getIsEnableAccount() {
        return isEnableAccount;
    }

    public void setIsEnableAccount(String isEnableAccount) {
        this.isEnableAccount = isEnableAccount;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getIsInit() {
        return isInit;
    }

    public void setIsInit(String isInit) {
        this.isInit = isInit;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getAcctName() {
        return acctName;
    }

    public void setAcctName(String acctName) {
        this.acctName = acctName;
    }

    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    @Override
    public String toString() {
        return "AddAdminReq{" +
                "adminId='" + adminId + '\'' +
                ", isInit='" + isInit + '\'' +
                ", adminName='" + adminName + '\'' +
                ", roleId='" + roleId + '\'' +
                ", adminPassword='" + adminPassword + '\'' +
                ", acctName='" + acctName + '\'' +
                ", acctType='" + acctType + '\'' +
                ", isEnableAccount='" + isEnableAccount + '\'' +
                ", estateId='" + estateId + '\'' +
                '}';
    }
}
