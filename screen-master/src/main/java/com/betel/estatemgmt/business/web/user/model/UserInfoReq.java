package com.betel.estatemgmt.business.web.user.model;

import com.betel.estatemgmt.common.Page;

import java.util.Date;

/**
 * 用户信息
 * ClassName: UserInfoReq
 * Author: Zhang li
 * Date: 2017/5/15 13:56
 * Version: 1.0
 */
public class UserInfoReq extends Page {
    private String userId;

    private String pictureUrl;

    private String pictureId;

    private String userIds;

    private String userName;

    private String userPassword;

    private Integer userGender;

    private String shiroKey;

    private Integer userStatus;

    private Long userHead;

    private Long roleId;

    private Date lastLoginTime;

    private String lastLoginIp;

    private Date createTime;

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getUserIds() {
        return userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds;
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
        this.lastLoginIp = lastLoginIp == null ? null : lastLoginIp.trim();
    }

    public Date getCreateTime() {
        if (createTime == null) {
            return null;
        }
        return (Date) createTime.clone();
    }

    public void setCreateTime(Date createTime) {
        if (createTime == null) {
            this.createTime = null;
        } else {
            this.createTime = (Date) createTime.clone();
        }
    }

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserInfoReq{");
        sb.append("createTime=").append(createTime);
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", pictureUrl='").append(pictureUrl).append('\'');
        sb.append(", pictureId='").append(pictureId).append('\'');
        sb.append(", userIds='").append(userIds).append('\'');
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", userPassword='").append(userPassword).append('\'');
        sb.append(", userGender=").append(userGender);
        sb.append(", shiroKey='").append(shiroKey).append('\'');
        sb.append(", userStatus=").append(userStatus);
        sb.append(", userHead=").append(userHead);
        sb.append(", roleId=").append(roleId);
        sb.append(", lastLoginTime=").append(lastLoginTime);
        sb.append(", lastLoginIp='").append(lastLoginIp).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
