package com.betel.estatemgmt.business.web.user.model;

 /**
 * 用户信息
 * ClassName: UserInfo
 * Author: Zhang li
 * Date: 2017/5/15 13:30
 * Version: 1.0
 */
public class UserInfor {
    private String userId;

    private String userName;

    private String acctName;

    private String userStatus;

    private String createTime;

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

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("UserInfor [userId=");
        builder.append(userId);
        builder.append(",userName=");
        builder.append(userName);
        builder.append(",acctName=");
        builder.append(acctName);
        builder.append(",userStatus=");
        builder.append(userStatus);
        builder.append(",createTime=");
        builder.append(createTime);
        builder.append("]");
        return builder.toString();
    }
}
