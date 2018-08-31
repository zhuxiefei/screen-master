package com.betel.estatemgmt.business.web.cleaning.model;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: ContentRecordResp <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/2/28 16:53 <br/>
 * Version: 1.0 <br/>
 */
public class ContentRecordResp {

    private FindContentResp findContentResp;

    private ContentRecordInfo recordInfo;

    public FindContentResp getFindContentResp() {
        return findContentResp;
    }

    public void setFindContentResp(FindContentResp findContentResp) {
        this.findContentResp = findContentResp;
    }

    public ContentRecordInfo getRecordInfo() {
        return recordInfo;
    }

    public void setRecordInfo(ContentRecordInfo recordInfo) {
        this.recordInfo = recordInfo;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ContentRecordResp{");
        sb.append("findContentResp=").append(findContentResp);
        sb.append(", recordInfo=").append(recordInfo);
        sb.append('}');
        return sb.toString();
    }
}
