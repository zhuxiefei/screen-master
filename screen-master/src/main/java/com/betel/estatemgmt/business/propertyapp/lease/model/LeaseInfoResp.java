package com.betel.estatemgmt.business.propertyapp.lease.model;

import java.util.Date;

/**
 * <p>
 * 文件说明
 * </p>
 * Author: geyf  <br/>
 * Date: 2018/5/8 15:09 <br/>
 * Version: 1.0 <br/>
 */
public class LeaseInfoResp {

    private String companyName;

    private String tenant;

    private String tenantPhone;

    private String address;

    private Double acreage;

    private Double rentUnitPrice;

    private Integer houseStatus;

    private Date signTime;

    private Date startTime;

    private Integer rentCycle;

    private Date endTime;

    private String propertyCompany;

    private Double propertyUnitPrice;

    private String propertyPhone;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getAcreage() {
        return acreage;
    }

    public void setAcreage(Double acreage) {
        this.acreage = acreage;
    }

    public Double getRentUnitPrice() {
        return rentUnitPrice;
    }

    public void setRentUnitPrice(Double rentUnitPrice) {
        this.rentUnitPrice = rentUnitPrice;
    }

    public Integer getHouseStatus() {
        return houseStatus;
    }

    public void setHouseStatus(Integer houseStatus) {
        this.houseStatus = houseStatus;
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Integer getRentCycle() {
        return rentCycle;
    }

    public void setRentCycle(Integer rentCycle) {
        this.rentCycle = rentCycle;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getPropertyCompany() {
        return propertyCompany;
    }

    public void setPropertyCompany(String propertyCompany) {
        this.propertyCompany = propertyCompany;
    }

    public Double getPropertyUnitPrice() {
        return propertyUnitPrice;
    }

    public void setPropertyUnitPrice(Double propertyUnitPrice) {
        this.propertyUnitPrice = propertyUnitPrice;
    }

    public String getPropertyPhone() {
        return propertyPhone;
    }

    public void setPropertyPhone(String propertyPhone) {
        this.propertyPhone = propertyPhone;
    }

    @Override
    public String toString() {
        return "LeaseInfoResp{" +
                "companyName='" + companyName + '\'' +
                ", tenant='" + tenant + '\'' +
                ", tenantPhone='" + tenantPhone + '\'' +
                ", address='" + address + '\'' +
                ", acreage=" + acreage +
                ", rentUnitPrice=" + rentUnitPrice +
                ", houseStatus=" + houseStatus +
                ", signTime=" + signTime +
                ", startTime=" + startTime +
                ", rentCycle=" + rentCycle +
                ", endTime=" + endTime +
                ", propertyCompany='" + propertyCompany + '\'' +
                ", propertyUnitPrice=" + propertyUnitPrice +
                ", propertyPhone='" + propertyPhone + '\'' +
                '}';
    }
}