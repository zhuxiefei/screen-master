package com.betel.estatemgmt.business.web.housetype.model;

import com.betel.estatemgmt.common.Page;

/**
 * <p>
 * web关键词搜索入参
 * </p>
 * ClassName: PageKeyWord <br/>
 * Author: geyf  <br/>
 * Date: 2017/6/20 14:13 <br/>
 * Version: 1.0 <br/>
 */
public class PageKeyWord extends Page {


    private String typeName;
    private String startTime;
    private String endTime;

    @Override
    public String toString() {
        return "PageKeyWord{" +
                "typeName='" + typeName + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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
