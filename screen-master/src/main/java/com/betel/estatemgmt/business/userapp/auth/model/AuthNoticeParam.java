package com.betel.estatemgmt.business.userapp.auth.model;


import com.betel.estatemgmt.common.model.house.House;

import java.util.List;

/**
 * <p>
 * 文件说明
 * </p>
 * ClassName: AuthNoticeParam <br/>
 * Author: geyf  <br/>
 * Date: 2017/6/23 10:48 <br/>
 * Version: 1.0 <br/>
 */
public class AuthNoticeParam {

    private List<House> houses;

    private String noticeUserId;

    public List<House> getHouses() {
        return houses;
    }

    public void setHouses(List<House> houses) {
        this.houses = houses;
    }

    public String getNoticeUserId() {
        return noticeUserId;
    }

    public void setNoticeUserId(String noticeUserId) {
        this.noticeUserId = noticeUserId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AuthNoticeParam{");
        sb.append("houses=").append(houses);
        sb.append(", noticeUserId='").append(noticeUserId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
