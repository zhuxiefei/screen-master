package com.betel.estatemgmt.business.propertyapp.task.model;

/**
 * <p>
 * 文件说明
 * </p>
 * Author: geyf  <br/>
 * Date: 2018/5/11 10:57 <br/>
 * Version: 1.0 <br/>
 */
public class CleanResp {

    private int finishCleanings;

    private int unFinishCleanings;

    private int allCleanings;

    private String areaId;

    private String areaName;

    public int getFinishCleanings() {
        return finishCleanings;
    }

    public void setFinishCleanings(int finishCleanings) {
        this.finishCleanings = finishCleanings;
    }

    public int getUnFinishCleanings() {
        return unFinishCleanings;
    }

    public void setUnFinishCleanings(int unFinishCleanings) {
        this.unFinishCleanings = unFinishCleanings;
    }

    public int getAllCleanings() {
        return allCleanings;
    }

    public void setAllCleanings(int allCleanings) {
        this.allCleanings = allCleanings;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    @Override
    public String toString() {
        return "CleanResp{" +
                "finishCleanings=" + finishCleanings +
                ", unFinishCleanings=" + unFinishCleanings +
                ", allCleanings=" + allCleanings +
                ", areaId=" + areaId +
                ", areaName='" + areaName + '\'' +
                '}';
    }
}