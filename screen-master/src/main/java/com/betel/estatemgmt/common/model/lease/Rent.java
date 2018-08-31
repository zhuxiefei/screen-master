package com.betel.estatemgmt.common.model.lease;

import java.util.Date;

public class Rent {
    private String rentId;

    private String companyName;

    private String tenant;

    private String tenantPhone;

    private String houseId;

    private Double acreage;

    private Double rentUnitPrice;

    private Date signTime;

    private Date startTime;

    private Integer rentCycle;

    private Date endTime;

    private String propertyCompany;

    private Double propertyUnitPrice;

    private String propertyPhone;

    private Integer isUpload;

    private String fileId;

    private Date createTime;

    private String rentNo;

    public String getRentNo() {
        return rentNo;
    }

    public void setRentNo(String rentNo) {
        this.rentNo = rentNo;
    }

    public String getRentId() {
        return rentId;
    }

    public void setRentId(String rentId) {
        this.rentId = rentId == null ? null : rentId.trim();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant == null ? null : tenant.trim();
    }

    public String getTenantPhone() {
        return tenantPhone;
    }

    public void setTenantPhone(String tenantPhone) {
        this.tenantPhone = tenantPhone == null ? null : tenantPhone.trim();
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId == null ? null : houseId.trim();
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
        this.propertyCompany = propertyCompany == null ? null : propertyCompany.trim();
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
        this.propertyPhone = propertyPhone == null ? null : propertyPhone.trim();
    }

    public Integer getIsUpload() {
        return isUpload;
    }

    public void setIsUpload(Integer isUpload) {
        this.isUpload = isUpload;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId == null ? null : fileId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Rent{" +
                "rentId='" + rentId + '\'' +
                ", companyName='" + companyName + '\'' +
                ", tenant='" + tenant + '\'' +
                ", tenantPhone='" + tenantPhone + '\'' +
                ", houseId='" + houseId + '\'' +
                ", acreage=" + acreage +
                ", rentUnitPrice=" + rentUnitPrice +
                ", signTime=" + signTime +
                ", startTime=" + startTime +
                ", rentCycle=" + rentCycle +
                ", endTime=" + endTime +
                ", propertyCompany='" + propertyCompany + '\'' +
                ", propertyUnitPrice=" + propertyUnitPrice +
                ", propertyPhone='" + propertyPhone + '\'' +
                ", isUpload=" + isUpload +
                ", fileId='" + fileId + '\'' +
                ", createTime=" + createTime +
                ", rentNo='" + rentNo + '\'' +
                '}';
    }
}