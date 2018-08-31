package com.betel.estatemgmt.business.web.screen.model;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: DepTaskData <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/1/9 11:30 <br/>
 * Version: 1.0 <br/>
 */
public class DepTaskData {

    private String depName;

    private Integer allTasks;

    private Integer finishTasks;

    private Integer unFinishTasks;

    private String finishPercentage;

    private String unFinishPercentage;

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public Integer getAllTasks() {
        return allTasks;
    }

    public void setAllTasks(Integer allTasks) {
        this.allTasks = allTasks;
    }

    public Integer getFinishTasks() {
        return finishTasks;
    }

    public void setFinishTasks(Integer finishTasks) {
        this.finishTasks = finishTasks;
    }

    public Integer getUnFinishTasks() {
        return unFinishTasks;
    }

    public void setUnFinishTasks(Integer unFinishTasks) {
        this.unFinishTasks = unFinishTasks;
    }

    public String getFinishPercentage() {
        return finishPercentage;
    }

    public void setFinishPercentage(String finishPercentage) {
        this.finishPercentage = finishPercentage;
    }

    public String getUnFinishPercentage() {
        return unFinishPercentage;
    }

    public void setUnFinishPercentage(String unFinishPercentage) {
        this.unFinishPercentage = unFinishPercentage;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DepTaskData{");
        sb.append("depName='").append(depName).append('\'');
        sb.append(", allTasks=").append(allTasks);
        sb.append(", finishTasks=").append(finishTasks);
        sb.append(", unFinishTasks=").append(unFinishTasks);
        sb.append(", finishPercentage='").append(finishPercentage).append('\'');
        sb.append(", unFinishPercentage='").append(unFinishPercentage).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

