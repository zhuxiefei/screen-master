package com.betel.estatemgmt.business.web.security.model;

/**
 * Created by zhangjian on 2018/3/5.
 */
public class ContextInfo {
    private String contentId;

    private String contentNo;

    private String areaId;

    private String inspectionTime;

    private String signinId;

    @Override
    public String toString() {
        return "ContextInfo{" +
                "contentId='" + contentId + '\'' +
                ", contentNo='" + contentNo + '\'' +
                ", areaId='" + areaId + '\'' +
                ", inspectionTime='" + inspectionTime + '\'' +
                ", signinId='" + signinId + '\'' +
                '}';
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getContentNo() {
        return contentNo;
    }

    public void setContentNo(String contentNo) {
        this.contentNo = contentNo;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getInspectionTime() {
        return inspectionTime;
    }

    public void setInspectionTime(String inspectionTime) {
        this.inspectionTime = inspectionTime;
    }

    public String getSigninId() {
        return signinId;
    }

    public void setSigninId(String signinId) {
        this.signinId = signinId;
    }
}
