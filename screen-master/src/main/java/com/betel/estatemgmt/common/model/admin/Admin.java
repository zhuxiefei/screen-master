package com.betel.estatemgmt.common.model.admin;

import java.util.Date;

public class Admin {
    private String adminId;

    private String adminName;

    private String adminPassword;

    private Integer isInit;

    private String shiroKey;

    private Date lastLoginTime;

    private String lastLoginIp;

    private Date createTime;

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId == null ? null : adminId.trim();
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName == null ? null : adminName.trim();
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword == null ? null : adminPassword.trim();
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
        this.shiroKey = shiroKey == null ? null : shiroKey.trim();
    }

    public Date getLastLoginTime()
    {
        if (lastLoginTime == null)
        {
            return null;
        }
        return (Date)lastLoginTime.clone();
    }

    public void setLastLoginTime(Date lastLoginTime)
    {
        if (lastLoginTime == null)
        {
            this.lastLoginTime = null;
        } else {
            this.lastLoginTime = (Date)lastLoginTime.clone();
        }
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp == null ? null : lastLoginIp.trim();
    }

    public Date getCreateTime()
    {
        if (createTime == null)
        {
            return null;
        }
        return (Date)createTime.clone();
    }

    public void setCreateTime(Date createTime)
    {
        if (createTime == null)
        {
            this.createTime = null;
        } else {
            this.createTime = (Date)createTime.clone();
        }
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminId='" + adminId + '\'' +
                ", adminName='" + adminName + '\'' +
                ", adminPassword='" + adminPassword + '\'' +
                ", isInit=" + isInit +
                ", shiroKey='" + shiroKey + '\'' +
                ", lastLoginTime=" + lastLoginTime +
                ", lastLoginIp='" + lastLoginIp + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}