package com.betel.estatemgmt.common.model.ad;

import java.util.Date;

public class AdPic {
    private Long apId;

    private Long adId;

    private String pictureId;

    private Date createTime;

    public Long getApId() {
        return apId;
    }

    public void setApId(Long apId) {
        this.apId = apId;
    }

    public Long getAdId() {
        return adId;
    }

    public void setAdId(Long adId) {
        this.adId = adId;
    }

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    public Date getCreateTime()
    {
        if (createTime == null)
        {
            return null;
        }
        return (Date)createTime.clone();
    }

    public void setCreateTime(Date createTime)
    {
        if (createTime == null)
        {
            this.createTime = null;
        } else {
            this.createTime = (Date)createTime.clone();
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AdPic{");
        sb.append("apId=").append(apId);
        sb.append(", adId=").append(adId);
        sb.append(", pictureId=").append(pictureId);
        sb.append(", createTime=").append(createTime);
        sb.append('}');
        return sb.toString();
    }
}