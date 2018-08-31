package com.betel.estatemgmt.business.propertyapp.security.model;

import java.util.Date;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: SignInfo <br/>
 * Author: Cui.xx  <br/>
 * Date: 2018/3/1 9:31 <br/>
 * Version: 1.0 <br/>
 */
public class SignInfo {

    private Date createTime;

    private String address;

    private String remarks;

    private String longitude;

    private String latitude;

    private String signinEmpName;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getSigninEmpName() {
        return signinEmpName;
    }

    public void setSigninEmpName(String signinEmpName) {
        this.signinEmpName = signinEmpName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SignInfo{");
        sb.append("createTime=").append(createTime);
        sb.append(", address='").append(address).append('\'');
        sb.append(", remarks='").append(remarks).append('\'');
        sb.append(", longitude='").append(longitude).append('\'');
        sb.append(", latitude='").append(latitude).append('\'');
        sb.append(", signinEmpName='").append(signinEmpName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
