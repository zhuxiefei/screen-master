package com.betel.estatemgmt.business.web.lease.model;

import com.betel.estatemgmt.business.web.lease.code.WebLeaseCode;
import com.betel.estatemgmt.common.Page;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * <p>
 * 文件说明
 * </p>
 * Author: geyf  <br/>
 * Date: 2018/5/8 16:24 <br/>
 * Version: 1.0 <br/>
 */
public class WebFindAllLeaseReq extends Page{

    private Date startTime;

    private Date endTime;

    @Pattern(regexp = "[^\\\\<>%'\"]{0,50}", message = WebLeaseCode.WEBCOMPANYNAMEANDCOMPANYNAME_FORMAT)
    private String companyName;

    @Pattern(regexp = "[^\\\\<>%'\"]{0,50}", message = WebLeaseCode.WEBCOMPANYNAMEANDCOMPANYNAME_FORMAT)
    private String address;

    @NotNull(message = WebLeaseCode.PARMA_NULL)
    private Integer isUpload;

    @NotNull(message = WebLeaseCode.PARMA_NULL)
    private Integer isInvalid;

    private String startTimeStr;

    private String endTimeStr;

    private Integer buildingId;

    private Integer unitId;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getStartTimeStr() {
        return startTimeStr;
    }

    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }

    public Integer getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Integer buildingId) {
        this.buildingId = buildingId;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getIsUpload() {
        return isUpload;
    }

    public void setIsUpload(Integer isUpload) {
        this.isUpload = isUpload;
    }

    public Integer getIsInvalid() {
        return isInvalid;
    }

    public void setIsInvalid(Integer isInvalid) {
        this.isInvalid = isInvalid;
    }

    @Override
    public String toString() {
        return "WebFindAllLeaseReq{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", companyName='" + companyName + '\'' +
                ", address='" + address + '\'' +
                ", isUpload=" + isUpload +
                ", isInvalid=" + isInvalid +
                ", startTimeStr='" + startTimeStr + '\'' +
                ", endTimeStr='" + endTimeStr + '\'' +
                ", buildingId=" + buildingId +
                ", unitId=" + unitId +
                '}';
    }
}