package com.betel.estatemgmt.business.web.cleaning.model;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: FindRecordReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/3/1 9:42 <br/>
 * Version: 1.0 <br/>
 */
public class FindRecordReq {

    private String recordId;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FindRecordReq{");
        sb.append("recordId='").append(recordId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
