package com.betel.estatemgmt.business.web.lease.model;

import java.util.Date;

/**
 * <p>
 * 文件说明
 * </p>
 * Author: geyf  <br/>
 * Date: 2018/5/8 16:18 <br/>
 * Version: 1.0 <br/>
 */
public class WebLeaseList {

    private String rentId;

    private String rentNo;

    private String address;

    private String companyName;

    private String tenant;

    private String tenantPhone;

    private Integer isUpload;

    private Date endTime;

    private Date startTime;

    private Integer isInvalid;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getRentId() {
        return rentId;
    }

    public void setRentId(String rentId) {
        this.rentId = rentId;
    }

    public String getRentNo() {
        return rentNo;
    }

    public void setRentNo(String rentNo) {
        this.rentNo = rentNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public String getTenantPhone() {
        return tenantPhone;
    }

    public void setTenantPhone(String tenantPhone) {
        this.tenantPhone = tenantPhone;
    }

    public Integer getIsUpload() {
        return isUpload;
    }

    public void setIsUpload(Integer isUpload) {
        this.isUpload = isUpload;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getIsInvalid() {
        return isInvalid;
    }

    public void setIsInvalid(Integer isInvalid) {
        this.isInvalid = isInvalid;
    }

    @Override
    public String toString() {
        return "WebLeaseList{" +
                "rentId='" + rentId + '\'' +
                ", rentNo='" + rentNo + '\'' +
                ", address='" + address + '\'' +
                ", companyName='" + companyName + '\'' +
                ", tenant='" + tenant + '\'' +
                ", tenantPhone='" + tenantPhone + '\'' +
                ", isUpload=" + isUpload +
                ", endTime=" + endTime +
                ", startTime=" + startTime +
                ", isInvalid=" + isInvalid +
                '}';
    }
}