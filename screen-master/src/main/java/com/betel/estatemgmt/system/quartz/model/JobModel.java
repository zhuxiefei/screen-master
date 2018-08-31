package com.betel.estatemgmt.system.quartz.model;

import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 定时任务模型
 * </p>
 * ClassName: JobModel <br/>
 * Author: zhouye  <br/>
 * Date: 2017/9/11 9:36 <br/>
 * Version: 1.0 <br/>
 */
public class JobModel {
    /*
    任务名称
     */
    String jobName;
    Date StartTime;
    Map dataMap;

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Date getStartTime() {
        return StartTime;
    }

    public void setStartTime(Date startTime) {
        StartTime = startTime;
    }

    public Map getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map dataMap) {
        this.dataMap = dataMap;
    }

    @Override
    public String toString() {
        return "JobModel{" +
                "jobName='" + jobName + '\'' +
                ", StartTime=" + StartTime +
                ", dataMap=" + dataMap +
                '}';
    }
}
