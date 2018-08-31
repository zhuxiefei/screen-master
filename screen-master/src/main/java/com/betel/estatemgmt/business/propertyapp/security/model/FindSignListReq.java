package com.betel.estatemgmt.business.propertyapp.security.model;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: FindSignListReq <br/>
 * Author: Cui.xx  <br/>
 * Date: 2018/3/1 9:33 <br/>
 * Version: 1.0 <br/>
 */
public class FindSignListReq {

    private String recordId;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FindSignListReq{");
        sb.append("recordId='").append(recordId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
