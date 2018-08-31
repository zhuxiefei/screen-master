package com.betel.estatemgmt.business.web.home.model;

/**
 * Created by zhangjian on 2017/9/14.
 */
public class ChartTimReq {
    private String startTime="";
    private String endTime="";



    @Override
    public String toString() {
        return "ChartTimReq{" +
                "startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
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
