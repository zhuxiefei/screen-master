package com.betel.estatemgmt.business.web.ad.model;

import java.util.Date;

/**
 * <p>
 * 广告详情参数
 * </p>
 * ClassName: AdDetail <br/>
 * Author: xiayanxin <br/>
 * Date: 2017/5/17 16:36 <br/>
 * Version: 1.0 <br/>
 */
public class AdDetail {

    private Long adId;

    private Date createTime;

    private String adTitle;

    private String adContent;

    public Long getAdId() {
        return adId;
    }

    public void setAdId(Long adId) {
        this.adId = adId;
    }

    public Date getCreateTime() {
        if(createTime == null){
            return null;
        }
        return (Date)createTime.clone();
    }

    public void setCreateTime(Date createTime) {
        if(createTime == null){
            this.createTime = null;
        }else{
            this.createTime = (Date)createTime.clone();
        }
    }

    public String getAdTitle() {
        return adTitle;
    }

    public void setAdTitle(String adTitle) {
        this.adTitle = adTitle;
    }

    public String getAdContent() {
        return adContent;
    }

    public void setAdContent(String adContent) {
        this.adContent = adContent;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AdDetail{");
        sb.append("adId=").append(adId);
        sb.append(", createTime=").append(createTime);
        sb.append(", adTitle='").append(adTitle).append('\'');
        sb.append(", adContent='").append(adContent).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
