package com.betel.estatemgmt.business.web.cleaning.model;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: FindContentReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/2/28 15:45 <br/>
 * Version: 1.0 <br/>
 */
public class FindContentReq {

    private String contentId;

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FindContentReq{");
        sb.append("contentId='").append(contentId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
