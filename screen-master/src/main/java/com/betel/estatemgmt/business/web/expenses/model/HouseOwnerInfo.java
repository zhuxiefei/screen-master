package com.betel.estatemgmt.business.web.expenses.model;

/**
 * <p>
 * 户主信息
 * </p>
 * ClassName: HouseOwnerInfo <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/10/9 10:40 <br/>
 * Version: 1.0 <br/>
 */
public class HouseOwnerInfo {

    private String ownerName;

    private String phone;

    private String userId;

    private String houseId;

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HouseOwnerInfo{");
        sb.append("ownerName='").append(ownerName).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", houseId=").append(houseId);
        sb.append('}');
        return sb.toString();
    }
}
