package com.betel.estatemgmt.business.web.house.model;

/**
 * <p>
 * 多个房屋编号入参
 * </p>
 * ClassName: HouseIdsReq <br/>
 * Author: xiayanxin <br/>
 * Date: 2017/6/19 14:43 <br/>
 * Version: 1.0 <br/>
 */
public class HouseIdsReq {
    private String houseIds;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private String userId;

    public String getUserIds() {
        return userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }

    private String userIds;

    public String getHouseIds() {
        return houseIds;
    }

    public void setHouseIds(String houseIds) {
        this.houseIds = houseIds;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HouseIdsReq{");
        sb.append("houseIds='").append(houseIds).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
