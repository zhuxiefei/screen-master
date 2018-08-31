package com.betel.estatemgmt.business.propertyapp.security.model;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: SignReq <br/>
 * Author: Cui.xx  <br/>
 * Date: 2018/3/1 11:23 <br/>
 * Version: 1.0 <br/>
 */
public class SignReq {

    private String recordId;

    private String address;

    private String longitude;

    private String latitude;

    private String remarks;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SignReq{");
        sb.append("recordId='").append(recordId).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", longitude='").append(longitude).append('\'');
        sb.append(", latitude='").append(latitude).append('\'');
        sb.append(", remarks='").append(remarks).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
