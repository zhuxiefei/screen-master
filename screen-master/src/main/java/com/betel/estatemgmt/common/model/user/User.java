package com.betel.estatemgmt.common.model.user;

import java.util.Date;

public class User {
    private String userId;

    private String userName;

    private String userPassword;

    private Integer userGender;

    private Date userBirthday;

    private String shiroKey;

    private Integer userStatus;

    private Long userHead;

    private Long roleId;

    private Date lastLoginTime;

    private String lastLoginIp;

    private Date createTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword == null ? null : userPassword.trim();
    }

    public Integer getUserGender() {
        return userGender;
    }

    public void setUserGender(Integer userGender) {
        this.userGender = userGender;
    }

    public Date getUserBirthday()
    {
        if (userBirthday == null)
        {
            return null;
        }
        return (Date)userBirthday.clone();
    }

    public void setUserBirthday(Date userBirthday)
    {
        if (userBirthday == null)
        {
            this.userBirthday = null;
        } else {
            this.userBirthday = (Date)userBirthday.clone();
        }
    }

    public String getShiroKey() {
        return shiroKey;
    }

    public void setShiroKey(String shiroKey) {
        this.shiroKey = shiroKey == null ? null : shiroKey.trim();
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public Long getUserHead() {
        return userHead;
    }

    public void setUserHead(Long userHead) {
        this.userHead = userHead;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
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
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("userId='").append(userId).append('\'');
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", userPassword='").append(userPassword).append('\'');
        sb.append(", userGender=").append(userGender);
        sb.append(", userBirthday=").append(userBirthday);
        sb.append(", shiroKey='").append(shiroKey).append('\'');
        sb.append(", userStatus=").append(userStatus);
        sb.append(", userHead=").append(userHead);
        sb.append(", roleId=").append(roleId);
        sb.append(", lastLoginTime=").append(lastLoginTime);
        sb.append(", lastLoginIp='").append(lastLoginIp).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append('}');
        return sb.toString();
    }
}