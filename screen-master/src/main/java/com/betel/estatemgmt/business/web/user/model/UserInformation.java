package com.betel.estatemgmt.business.web.user.model;

/**
 * 用户信息
 * ClassName: UserInformation
 * Author: Zhang li
 * Date: 2017/5/15 13:56
 * Version: 1.0
 */
public class UserInformation {
    private String userId;

    private String userName;

    private String acctName;

    private Integer userStatus;

    private String createTime;

    private Integer userGender;

    private String userHead;

    /*
    图像路径
     */
    private String pictureUrl;

    private String lastLoginTime;

    private String lastLoginIp;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAcctName() {
        return acctName;
    }

    public void setAcctName(String acctName) {
        this.acctName = acctName;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getUserGender() {
        return userGender;
    }

    public void setUserGender(Integer userGender) {
        this.userGender = userGender;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserInformation{");
        sb.append("acctName='").append(acctName).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", userStatus=").append(userStatus);
        sb.append(", createTime='").append(createTime).append('\'');
        sb.append(", userGender=").append(userGender);
        sb.append(", userHead='").append(userHead).append('\'');
        sb.append(", pictureUrl='").append(pictureUrl).append('\'');
        sb.append(", lastLoginTime='").append(lastLoginTime).append('\'');
        sb.append(", lastLoginIp='").append(lastLoginIp).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
