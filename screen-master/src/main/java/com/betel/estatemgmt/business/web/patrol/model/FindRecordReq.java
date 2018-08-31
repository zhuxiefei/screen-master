package com.betel.estatemgmt.business.web.patrol.model;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: FindRecordReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/1/26 13:28 <br/>
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
