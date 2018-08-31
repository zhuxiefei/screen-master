package com.betel.estatemgmt.business.web.screen.model;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: PatrolData <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/1/9 9:57 <br/>
 * Version: 1.0 <br/>
 */
public class PatrolData {

    private Integer todayFinishEquipments;

    private Integer todayAbnormalEquipments;

    private Integer todayNormalEquipments;

    private String todayAbnormalPercentage;

    private String todayNormalPercentage;

    public Integer getTodayFinishEquipments() {
        return todayFinishEquipments;
    }

    public void setTodayFinishEquipments(Integer todayFinishEquipments) {
        this.todayFinishEquipments = todayFinishEquipments;
    }

    public Integer getTodayAbnormalEquipments() {
        return todayAbnormalEquipments;
    }

    public void setTodayAbnormalEquipments(Integer todayAbnormalEquipments) {
        this.todayAbnormalEquipments = todayAbnormalEquipments;
    }

    public Integer getTodayNormalEquipments() {
        return todayNormalEquipments;
    }

    public void setTodayNormalEquipments(Integer todayNormalEquipments) {
        this.todayNormalEquipments = todayNormalEquipments;
    }

    public String getTodayAbnormalPercentage() {
        return todayAbnormalPercentage;
    }

    public void setTodayAbnormalPercentage(String todayAbnormalPercentage) {
        this.todayAbnormalPercentage = todayAbnormalPercentage;
    }

    public String getTodayNormalPercentage() {
        return todayNormalPercentage;
    }

    public void setTodayNormalPercentage(String todayNormalPercentage) {
        this.todayNormalPercentage = todayNormalPercentage;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PatrolData{");
        sb.append("todayFinishEquipments=").append(todayFinishEquipments);
        sb.append(", todayAbnormalEquipments=").append(todayAbnormalEquipments);
        sb.append(", todayNormalEquipments=").append(todayNormalEquipments);
        sb.append(", todayAbnormalPercentage=").append(todayAbnormalPercentage);
        sb.append(", todayNormalPercentage=").append(todayNormalPercentage);
        sb.append('}');
        return sb.toString();
    }
}
