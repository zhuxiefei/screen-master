package com.betel.estatemgmt.business.web.housestatus.model;

/**
 * <p>
 * Describe this class...
 * </p>
 *
 * @className: HouseStatusTenants <br/>
 * @author: jian.z  <br/>
 * @date: 2017/11/15 21:50 <br/>
 * @version: 1.0
 */
public class HouseStatusTenant {
    private String houseId;
    private String tenantId;
    private String tenantName;
    private String phoneNum;
    private String tenantResidency;
    private String tenantEthnic;
    private String tenantReligion;
    private String tenantStatus;

    @Override
    public String toString() {
        return "HouseStatusTenants{" +
                "houseId='" + houseId + '\'' +
                ", tenantId='" + tenantId + '\'' +
                ", tenantName='" + tenantName + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", tenantResidency='" + tenantResidency + '\'' +
                ", tenantEthnic='" + tenantEthnic + '\'' +
                ", tenantReligion='" + tenantReligion + '\'' +
                ", tenantStatus='" + tenantStatus + '\'' +
                '}';
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getTenantResidency() {
        return tenantResidency;
    }

    public void setTenantResidency(String tenantResidency) {
        this.tenantResidency = tenantResidency;
    }

    public String getTenantEthnic() {
        return tenantEthnic;
    }

    public void setTenantEthnic(String tenantEthnic) {
        this.tenantEthnic = tenantEthnic;
    }

    public String getTenantReligion() {
        return tenantReligion;
    }

    public void setTenantReligion(String tenantReligion) {
        this.tenantReligion = tenantReligion;
    }

    public String getTenantStatus() {
        return tenantStatus;
    }

    public void setTenantStatus(String tenantStatus) {
        this.tenantStatus = tenantStatus;
    }


}
