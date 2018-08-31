package com.betel.estatemgmt.business.web.screen.model;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: RepairData <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/1/9 9:48 <br/>
 * Version: 1.0 <br/>
 */
public class RepairData {

    private Integer todayWorkedRepairs;

    private Integer todayRepairs;

    private Integer allUntreatedRepairs;

    private Integer todayAcceptRepairs;

    private Integer todayWaitRepairs;

    private Integer todayFinishRepairs;

    private String todayAcceptPercentage;

    private String todayWaitPercentage;

    private String todayFinishPercentage;

    public Integer getTodayWorkedRepairs() {
        return todayWorkedRepairs;
    }

    public void setTodayWorkedRepairs(Integer todayWorkedRepairs) {
        this.todayWorkedRepairs = todayWorkedRepairs;
    }

    public Integer getTodayRepairs() {
        return todayRepairs;
    }

    public void setTodayRepairs(Integer todayRepairs) {
        this.todayRepairs = todayRepairs;
    }

    public Integer getAllUntreatedRepairs() {
        return allUntreatedRepairs;
    }

    public void setAllUntreatedRepairs(Integer allUntreatedRepairs) {
        this.allUntreatedRepairs = allUntreatedRepairs;
    }

    public Integer getTodayAcceptRepairs() {
        return todayAcceptRepairs;
    }

    public void setTodayAcceptRepairs(Integer todayAcceptRepairs) {
        this.todayAcceptRepairs = todayAcceptRepairs;
    }

    public Integer getTodayWaitRepairs() {
        return todayWaitRepairs;
    }

    public void setTodayWaitRepairs(Integer todayWaitRepairs) {
        this.todayWaitRepairs = todayWaitRepairs;
    }

    public Integer getTodayFinishRepairs() {
        return todayFinishRepairs;
    }

    public void setTodayFinishRepairs(Integer todayFinishRepairs) {
        this.todayFinishRepairs = todayFinishRepairs;
    }

    public String getTodayAcceptPercentage() {
        return todayAcceptPercentage;
    }

    public void setTodayAcceptPercentage(String todayAcceptPercentage) {
        this.todayAcceptPercentage = todayAcceptPercentage;
    }

    public String getTodayWaitPercentage() {
        return todayWaitPercentage;
    }

    public void setTodayWaitPercentage(String todayWaitPercentage) {
        this.todayWaitPercentage = todayWaitPercentage;
    }

    public String getTodayFinishPercentage() {
        return todayFinishPercentage;
    }

    public void setTodayFinishPercentage(String todayFinishPercentage) {
        this.todayFinishPercentage = todayFinishPercentage;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RepairData{");
        sb.append("todayWorkedRepairs=").append(todayWorkedRepairs);
        sb.append(", todayRepairs=").append(todayRepairs);
        sb.append(", allUntreatedRepairs=").append(allUntreatedRepairs);
        sb.append(", todayAcceptRepairs=").append(todayAcceptRepairs);
        sb.append(", todayWaitRepairs=").append(todayWaitRepairs);
        sb.append(", todayFinishRepairs=").append(todayFinishRepairs);
        sb.append(", todayAcceptPercentage=").append(todayAcceptPercentage);
        sb.append(", todayWaitPercentage=").append(todayWaitPercentage);
        sb.append(", todayFinishPercentage=").append(todayFinishPercentage);
        sb.append('}');
        return sb.toString();
    }
}
