package com.betel.estatemgmt.business.web.cleaning.model;

import com.betel.estatemgmt.common.Page;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: FindAllContentRecordsReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/2/28 16:46 <br/>
 * Version: 1.0 <br/>
 */
public class FindAllContentRecordsReq extends Page{

    private String contentId;

    private String startTime;

    private String endTime;

    private String isStandard;

    private String estateId;

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
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

    public String getIsStandard() {
        return isStandard;
    }

    public void setIsStandard(String isStandard) {
        this.isStandard = isStandard;
    }

    @Override
    public String toString() {
        return "FindAllContentRecordsReq{" +
                "contentId='" + contentId + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", isStandard='" + isStandard + '\'' +
                ", estateId='" + estateId + '\'' +
                '}';
    }
}
