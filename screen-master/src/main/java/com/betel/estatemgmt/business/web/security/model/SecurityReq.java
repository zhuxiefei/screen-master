package com.betel.estatemgmt.business.web.security.model;


public class SecurityReq {
    @Override
    public String toString() {
        return "SecurityReq{" +
                "isPatrol='" + isPatrol + '\'' +
                ", areaId='" + areaId + '\'' +
                ", areaName='" + areaName + '\'' +
                ", signinAddress='" + signinAddress + '\'' +
                ", signinId='" + signinId + '\'' +
                ", contentId='" + contentId + '\'' +
                ", contentNo='" + contentNo + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", contentDesc='" + contentDesc + '\'' +
                ", inspectionTime='" + inspectionTime + '\'' +
                ", recordId='" + recordId + '\'' +
                ", estateId='" + estateId + '\'' +
                ", curPage=" + curPage +
                ", pageSize=" + pageSize +
                '}';
    }

    /**
     * 巡逻状态：
     * 1 待巡逻
     * 2 已巡逻
     * 3 逾期未巡逻
     */
    private String isPatrol;
    /**
     * 区域id
     */
    private String areaId;
    private String areaName;
    private String signinAddress;
    private String signinId;
    /**
     * 巡逻内容
     */
    private String contentId;
    private String contentNo;
    private String startTime;
    private String endTime;
    private String contentDesc;
    private String inspectionTime;
    private String recordId;
    private String estateId;



    /**
     * 页码
     */
    private Integer curPage = 1;
    /**
     * 每一页条数
     */
    private Integer pageSize = 10;

    public Integer getCurPage() {
        return curPage;
    }

    public void setCurPage(Integer curPage) {
        this.curPage = curPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getSigninId() {
        return signinId;
    }

    public void setSigninId(String signinId) {
        this.signinId = signinId;
    }

    public String getSigninAddress() {
        return signinAddress;
    }

    public void setSigninAddress(String signinAddress) {
        this.signinAddress = signinAddress;
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

    public String getContentNo() {
        return contentNo;
    }

    public void setContentNo(String contentNo) {
        this.contentNo = contentNo;
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

    public String getContentDesc() {
        return contentDesc;
    }

    public void setContentDesc(String contentDesc) {
        this.contentDesc = contentDesc;
    }

    public String getInspectionTime() {
        return inspectionTime;
    }

    public void setInspectionTime(String inspectionTime) {
        this.inspectionTime = inspectionTime;
    }

    public String getIsPatrol() {
        return isPatrol;
    }

    public void setIsPatrol(String isPatrol) {
        this.isPatrol = isPatrol;
    }

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
    }
}
