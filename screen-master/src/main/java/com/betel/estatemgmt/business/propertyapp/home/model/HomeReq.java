package com.betel.estatemgmt.business.propertyapp.home.model;

/**
 * Created by zhangjian on 2018/1/3.
 */
public class HomeReq {

    private String startTime;
    private String endTime;

    private String userId;

    @Override
    public String toString() {
        return "HomeReq{" +
                "startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

}
