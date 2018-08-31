package com.betel.estatemgmt.business.web.ad.model;

/**
 * <p>
 * 广告修改入参
 * </p>
 * ClassName: UpdateAdInfo <br/>
 * Author: xiayanxin <br/>
 * Date: 2017/5/17 11:49 <br/>
 * Version: 1.0 <br/>
 */
public class UpdateAdInfo {

    private Long adId;

    private String adContent;

    private String contentPicId;

    public Long getAdId() {
        return adId;
    }

    public void setAdId(Long adId) {
        this.adId = adId;
    }

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
        final StringBuilder sb = new StringBuilder("UpdateAdInfo{");
        sb.append("adId=").append(adId);
        sb.append(", adContent='").append(adContent).append('\'');
        sb.append(", contentPicId='").append(contentPicId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
