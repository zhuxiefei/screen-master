package com.betel.estatemgmt.business.propertyapp.task.model;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: DeleteRecordReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/1/5 17:42 <br/>
 * Version: 1.0 <br/>
 */
public class DeleteRecordReq {

    private String recordId;

    /**
     * 1为我收到的   2为我发出的
     */
    private String flag;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DeleteRecordReq{");
        sb.append("recordId='").append(recordId).append('\'');
        sb.append(", flag='").append(flag).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
