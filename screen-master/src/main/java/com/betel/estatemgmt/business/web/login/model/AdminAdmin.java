package com.betel.estatemgmt.business.web.login.model;

import java.util.Date;

/**
 *
 */
public class AdminAdmin {
    private String adminId;

    private String adminName;

    private String adminPassword;

    private Integer isInit;

    private String shiroKey;

    private Long roleId;

    private Date lastLoginTime;

    private String lastLoginIp;

    private Date createTime;

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

    public Integer getIsInit() {
        return isInit;
    }

    public void setIsInit(Integer isInit) {
        this.isInit = isInit;
    }

    public String getShiroKey() {
        return shiroKey;
    }

    public void setShiroKey(String shiroKey) {
        this.shiroKey = shiroKey;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Date getLastLoginTime() {
        if (lastLoginTime == null) {
            return null;
        }
        return (Date) lastLoginTime.clone();
    }

    public void setLastLoginTime(Date lastLoginTime) {
        if (lastLoginTime == null) {
            this.lastLoginTime = null;
        } else {
            this.lastLoginTime = (Date) lastLoginTime.clone();
        }
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public Date getCreateTime() {
        if (createTime == null) {
            return null;
        }
        return (Date) createTime.clone();
    }

    @Override
    public String toString() {
        return "AdminAdmin{" +
                "adminId='" + adminId + '\'' +
                ", adminName='" + adminName + '\'' +
                ", adminPassword='" + adminPassword + '\'' +
                ", isInit=" + isInit +
                ", shiroKey='" + shiroKey + '\'' +
                ", roleId=" + roleId +
                ", lastLoginTime=" + lastLoginTime +
                ", lastLoginIp='" + lastLoginIp + '\'' +
                ", createTime=" + createTime +
                '}';
    }

    public void setCreateTime(Date createTime) {
        if (createTime == null) {
            this.createTime = null;
        } else {
            this.createTime = (Date) createTime.clone();
        }
    }
}
