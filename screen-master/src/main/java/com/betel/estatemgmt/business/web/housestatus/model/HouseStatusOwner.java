package com.betel.estatemgmt.business.web.housestatus.model;

/**
 * <p>
 * Describe this class...
 * </p>
 *
 * @className: HouseOwner <br/>
 * @author: jian.z  <br/>
 * @date: 2017/11/15 9:01 <br/>
 * @version: 1.0
 */
public class HouseStatusOwner {
    private String houseId;
    private String ownerId;
    private String ownerName;
    private String ownerPhoneNum;
    private String ownerResidency;
    private String ownerEthnic;
    private String ownerReligion;
    private String ownerStatus;
    private String ownerUserId;

    @Override
    public String toString() {
        return "HouseStatusOwner{" +
                "houseId='" + houseId + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", ownerPhoneNum='" + ownerPhoneNum + '\'' +
                ", ownerResidency='" + ownerResidency + '\'' +
                ", ownerEthnic='" + ownerEthnic + '\'' +
                ", ownerReligion='" + ownerReligion + '\'' +
                ", ownerStatus='" + ownerStatus + '\'' +
                ", ownerUserId='" + ownerUserId + '\'' +
                '}';
    }

    public String getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(String ownerUserId) {
        this.ownerUserId = ownerUserId;
    }
    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
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

    public String getOwnerStatus() {
        return ownerStatus;
    }

    public void setOwnerStatus(String ownerStatus) {
        this.ownerStatus = ownerStatus;
    }
}
