package com.betel.estatemgmt.business.web.user.model;

/**
 * 禁止用户入参
 * ClassName: BanUserReq
 * Author: Zhang li
 * Date: 2017/8/3 15:06
 * Version: 1.0
 */
public class BanUserReq {

    private String userIds;

    private Integer userStatus;

    public String getUserIds() {
        return userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BanUserReq{");
        sb.append("userIds='").append(userIds).append('\'');
        sb.append(", userStatus=").append(userStatus);
        sb.append('}');
        return sb.toString();
    }
}
