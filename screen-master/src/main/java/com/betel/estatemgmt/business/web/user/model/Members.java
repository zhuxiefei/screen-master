package com.betel.estatemgmt.business.web.user.model;

/**
 * <p>
 * 管理员管理业务
 * </p>
 * ClassName: Members <br/>
 * Author: Zhang Li  <br/>
 * Date: 2017/7/18 15:28 <br/>
 * Version: 1.0 <br/>
 */
public class Members {

    private String houseId;

    private String userId;

    private String acctName;

    private String houseNum;

    private String ownerId;

    private String memberId;

    private Integer ownerStatus;

    public Integer getOwnerStatus() {
        return ownerStatus;
    }

    public void setOwnerStatus(Integer ownerStatus) {
        this.ownerStatus = ownerStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getHouseNum() {
        return houseNum;
    }

    public void setHouseNum(String houseNum) {
        this.houseNum = houseNum;
    }

    public String getAcctName() {
        return acctName;
    }

    public void setAcctName(String acctName) {
        this.acctName = acctName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Members{");
        sb.append("houseId=").append(houseId);
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", acctName='").append(acctName).append('\'');
        sb.append(", houseNum='").append(houseNum).append('\'');
        sb.append(", ownerId='").append(ownerId).append('\'');
        sb.append(", memberId='").append(memberId).append('\'');
        sb.append(", ownerStatus=").append(ownerStatus);
        sb.append('}');
        return sb.toString();
    }
}
