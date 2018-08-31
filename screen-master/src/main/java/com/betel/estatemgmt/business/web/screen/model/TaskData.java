package com.betel.estatemgmt.business.web.screen.model;

import java.util.List;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: TaskData <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/1/9 11:24 <br/>
 * Version: 1.0 <br/>
 */
public class TaskData {

    private List<TodayTaskData> todayTaskDatas;

    private String todayFinishPercentage;

    private String todayUnFinishPercentage;

    private List<DepTaskData> depTaskData;

    public List<TodayTaskData> getTodayTaskDatas() {
        return todayTaskDatas;
    }

    public void setTodayTaskDatas(List<TodayTaskData> todayTaskDatas) {
        this.todayTaskDatas = todayTaskDatas;
    }

    public String getTodayFinishPercentage() {
        return todayFinishPercentage;
    }

    public void setTodayFinishPercentage(String todayFinishPercentage) {
        this.todayFinishPercentage = todayFinishPercentage;
    }

    public String getTodayUnFinishPercentage() {
        return todayUnFinishPercentage;
    }

    public void setTodayUnFinishPercentage(String todayUnFinishPercentage) {
        this.todayUnFinishPercentage = todayUnFinishPercentage;
    }

    public List<DepTaskData> getDepTaskData() {
        return depTaskData;
    }

    public void setDepTaskData(List<DepTaskData> depTaskData) {
        this.depTaskData = depTaskData;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TaskData{");
        sb.append("todayTaskDatas=").append(todayTaskDatas);
        sb.append(", todayFinishPercentage='").append(todayFinishPercentage).append('\'');
        sb.append(", todayUnFinishPercentage='").append(todayUnFinishPercentage).append('\'');
        sb.append(", depTaskData=").append(depTaskData);
        sb.append('}');
        return sb.toString();
    }
}
