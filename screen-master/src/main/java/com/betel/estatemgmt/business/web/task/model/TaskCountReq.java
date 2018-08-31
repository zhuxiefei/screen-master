package com.betel.estatemgmt.business.web.task.model;

/**
 * Created by Administrator on 2018/5/9/009.
 */
public class TaskCountReq {

    /**
     * 1代表月  2代表季   3代表年
     */
    private String flag;

    private String startTime;

    private String endTime;

    private String areaId;

    private String estateId;

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
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

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    @Override
    public String toString() {
        return "TaskCountReq{" +
                "flag='" + flag + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", areaId='" + areaId + '\'' +
                ", estateId='" + estateId + '\'' +
                '}';
    }
}
