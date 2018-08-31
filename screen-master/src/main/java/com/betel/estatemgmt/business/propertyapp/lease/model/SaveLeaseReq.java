package com.betel.estatemgmt.business.propertyapp.lease.model;

import com.betel.estatemgmt.business.propertyapp.lease.code.LeaseCode;

import javax.validation.constraints.*;
import java.util.Arrays;
import java.util.Date;

/**
 * <p>
 * 文件说明
 * </p>
 * Author: geyf  <br/>
 * Date: 2018/5/8 10:07 <br/>
 * Version: 1.0 <br/>
 */
public class SaveLeaseReq {

    private String rentId;

    @NotNull(message = LeaseCode.COMPANYNAME_FORMAT_ERROR)
    private String companyName;

    @NotNull(message = LeaseCode.TENANT_FORMAT_ERROR)
    private String tenant;

    @NotNull(message = LeaseCode.TENANTPHONE_FORMAT_ERROR)
    private String tenantPhone;

    @NotBlank(message = LeaseCode.ADDRESS_FORMAT_ERROR)
    @Pattern(regexp = "[^\\\\<>%'\"]{1,50}",message = LeaseCode.ADDRESS_FORMAT_ERROR)
    private String address;

    @NotNull(message = LeaseCode.ACREAGE_FORMAT_ERROR)
    private Double acreage;

    @NotNull(message = LeaseCode.RENTUNITPRICE_ERROR)
    @DecimalMax(value = "1000000", message = LeaseCode.RENTUNITPRICE_ERROR)
    @DecimalMin(value = "0", message = LeaseCode.RENTUNITPRICE_ERROR)
    private Double rentUnitPrice;

    private Date signTime;

    private Date startTime;

    @NotNull(message = LeaseCode.RENTCYCLE_ERROR)
    @Min(value = 1, message = LeaseCode.RENTCYCLE_ERROR)
    @Max(value = 1000, message = LeaseCode.RENTCYCLE_ERROR)
    private Integer rentCycle;

    private Date endTime;

    @NotNull(message = LeaseCode.PROPERTYCOMPANY_FORMAT_ERROR)
    private String propertyCompany;

    @NotNull(message = LeaseCode.PROPERTYUNITPRICE_ERROR)
    @DecimalMax(value = "1000", message = LeaseCode.PROPERTYUNITPRICE_ERROR)
    @DecimalMin(value = "0", message = LeaseCode.PROPERTYUNITPRICE_ERROR)
    private Double propertyUnitPrice;

    @NotNull(message = LeaseCode.TENANTPHONE_FORMAT_ERROR)
    private String propertyPhone;

    private Byte isUpload;

    private String fileId;

    private Date createTime;

    @NotBlank(message = LeaseCode.TIME_NULL)
    private String signTimeStr;

    @NotBlank(message = LeaseCode.TIME_NULL)
    private String startTimeStr;

    @NotBlank(message = LeaseCode.TIME_NULL)
    private String endTimeStr;

    public String getRentId() {
        return rentId;
    }

    public String getSignTimeStr() {
        return signTimeStr;
    }

    public void setSignTimeStr(String signTimeStr) {
        this.signTimeStr = signTimeStr;
    }

    public String getStartTimeStr() {
        return startTimeStr;
    }

    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
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

    public Byte getIsUpload() {
        return isUpload;
    }

    public void setIsUpload(Byte isUpload) {
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
}