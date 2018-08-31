package com.betel.estatemgmt.business.web.housestatus.model;

/**
 * <p>
 * Describe this class...
 * </p>
 *
 * @className: HouseStatusReq <br/>
 * @author: jian.z  <br/>
 * @date: 2017/11/14 11:36 <br/>
 * @version: 1.0
 */
public class HouseStatusReq {
    //zyxs
    private String houseId;
    private String isOthers;
    private String buildingId;


    private String unitId;
    private String houseStatus;
    private String authStatus;
    private String petStatus;


    private String tenantId;
    private String tenantName;
    private String phoneNum;
    private String tenantResidency;
    private String tenantEthnic;
    private String tenantReligion;


    //hzxx
    private String ownerName;
    private String ownerPhoneNum;
    private String ownerResidency;
    private String ownerEthnic;
    private String ownerReligion;
    //cyxx
    private String memberName;
    private String memberPhoneNum;
    private String memberResidency;
    private String memberEthnic;
    private String memberReligion;

    @Override
    public String toString() {
        return "HouseStatusReq{" +
                "houseId='" + houseId + '\'' +
                ", isOthers='" + isOthers + '\'' +
                ", buildingId='" + buildingId + '\'' +
                ", unitId='" + unitId + '\'' +
                ", houseStatus='" + houseStatus + '\'' +
                ", authStatus='" + authStatus + '\'' +
                ", petStatus='" + petStatus + '\'' +
                ", tenantId='" + tenantId + '\'' +
                ", tenantName='" + tenantName + '\'' +
                ", PhoneNum='" + phoneNum + '\'' +
                ", tenantResidency='" + tenantResidency + '\'' +
                ", tenantEthnic='" + tenantEthnic + '\'' +
                ", tenantReligion='" + tenantReligion + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", ownerPhoneNum='" + ownerPhoneNum + '\'' +
                ", ownerResidency='" + ownerResidency + '\'' +
                ", ownerEthnic='" + ownerEthnic + '\'' +
                ", ownerReligion='" + ownerReligion + '\'' +
                ", memberName='" + memberName + '\'' +
                ", memberPhoneNum='" + memberPhoneNum + '\'' +
                ", memberResidency='" + memberResidency + '\'' +
                ", memberEthnic='" + memberEthnic + '\'' +
                ", memberReligion='" + memberReligion + '\'' +
                '}';
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getIsOthers() {
        return isOthers;
    }

    public void setIsOthers(String isOthers) {
        this.isOthers = isOthers;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getHouseStatus() {
        return houseStatus;
    }

    public void setHouseStatus(String houseStatus) {
        this.houseStatus = houseStatus;
    }

    public String getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(String authStatus) {
        this.authStatus = authStatus;
    }

    public String getPetStatus() {
        return petStatus;
    }

    public void setPetStatus(String petStatus) {
        this.petStatus = petStatus;
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
        phoneNum = phoneNum;
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


    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerPhoneNum() {
        return ownerPhoneNum;
    }

    public void setOwnerPhoneNum(String ownerPhoneNum) {
        this.ownerPhoneNum = ownerPhoneNum;
    }

    public String getOwnerResidency() {
        return ownerResidency;
    }

    public void setOwnerResidency(String ownerResidency) {
        this.ownerResidency = ownerResidency;
    }

    public String getOwnerEthnic() {
        return ownerEthnic;
    }

    public void setOwnerEthnic(String ownerEthnic) {
        this.ownerEthnic = ownerEthnic;
    }

    public String getOwnerReligion() {
        return ownerReligion;
    }

    public void setOwnerReligion(String ownerReligion) {
        this.ownerReligion = ownerReligion;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberPhoneNum() {
        return memberPhoneNum;
    }

    public void setMemberPhoneNum(String memberPhoneNum) {
        this.memberPhoneNum = memberPhoneNum;
    }

    public String getMemberResidency() {
        return memberResidency;
    }

    public void setMemberResidency(String memberResidency) {
        this.memberResidency = memberResidency;
    }

    public String getMemberEthnic() {
        return memberEthnic;
    }

    public void setMemberEthnic(String memberEthnic) {
        this.memberEthnic = memberEthnic;
    }

    public String getMemberReligion() {
        return memberReligion;
    }

    public void setMemberReligion(String memberReligion) {
        this.memberReligion = memberReligion;
    }
}
