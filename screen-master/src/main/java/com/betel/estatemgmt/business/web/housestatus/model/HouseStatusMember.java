package com.betel.estatemgmt.business.web.housestatus.model;

/**
 * <p>
 * Describe this class...
 * </p>
 *
 * @className: HouseStatusMember <br/>
 * @author: jian.z  <br/>
 * @date: 2017/11/15 20:37 <br/>
 * @version: 1.0
 */
public class HouseStatusMember {
    private String houseId;
    private String memberName;
    private String memberPhoneNum;
    private String memberResidency;
    private String memberEthnic;
    private String memberReligion;
    private String memberStatus;

    @Override
    public String toString() {
        return "HouseStatusMember{" +
                "houseId='" + houseId + '\'' +
                ", memberName='" + memberName + '\'' +
                ", memberPhoneNum='" + memberPhoneNum + '\'' +
                ", memberResidency='" + memberResidency + '\'' +
                ", memberEthnic='" + memberEthnic + '\'' +
                ", memberReligion='" + memberReligion + '\'' +
                ", memberStatus='" + memberStatus + '\'' +
                '}';
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
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

    public String getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(String memberStatus) {
        this.memberStatus = memberStatus;
    }
}
