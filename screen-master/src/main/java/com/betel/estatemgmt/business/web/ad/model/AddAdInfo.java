package com.betel.estatemgmt.business.web.ad.model;

/**
 * <p>
 * 广告新增入参
 * </p>
 * ClassName: AddAdInfo <br/>
 * Author: xiayanxin <br/>
 * Date: 2017/5/16 15:36 <br/>
 * Version: 1.0 <br/>
 */
public class AddAdInfo {

    private String adContent;

    private String contentPicId;

    public String getAdContent() {
        return adContent;
    }

    public void setAdContent(String adContent) {
        this.adContent = adContent;
    }

    public String getContentPicId() {
        return contentPicId;
    }

    public void setContentPicId(String contentPicId) {
        this.contentPicId = contentPicId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AddAdInfo{");
        sb.append("adContent='").append(adContent).append('\'');
        sb.append(", contentPicId='").append(contentPicId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
