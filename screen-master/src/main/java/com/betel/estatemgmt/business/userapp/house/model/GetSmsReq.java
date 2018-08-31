package com.betel.estatemgmt.business.userapp.house.model;

/**
 * <p>
 * 户主激活获取短信验证码入参
 * </p>
 * ClassName: GetSmsReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/11/15 19:06 <br/>
 * Version: 1.0 <br/>
 */
public class GetSmsReq {
    private String houseId;

    private String ownerName;

    private String ownerPhone;

    private String validateCode;

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GetSmsReq{");
        sb.append("houseId='").append(houseId).append('\'');
        sb.append(", ownerName='").append(ownerName).append('\'');
        sb.append(", ownerPhone='").append(ownerPhone).append('\'');
        sb.append(", validateCode='").append(validateCode).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
