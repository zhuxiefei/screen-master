package com.betel.estatemgmt.common.model.ad;

import java.util.Date;

public class Advertisement {
    private Long adId;

    private String authorId;

    private String adTitle;

    private Date createTime;

    private String lastModifyId;

    private Date updateTime;

    private String adContent;

    public Long getAdId() {
        return adId;
    }

    public void setAdId(Long adId) {
        this.adId = adId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId == null ? null : authorId.trim();
    }

    public String getAdTitle() {
        return adTitle;
    }

    public void setAdTitle(String adTitle) {
        this.adTitle = adTitle == null ? null : adTitle.trim();
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

    public String getLastModifyId() {
        return lastModifyId;
    }

    public void setLastModifyId(String lastModifyId) {
        this.lastModifyId = lastModifyId == null ? null : lastModifyId.trim();
    }

    public Date getUpdateTime()
    {
        if (updateTime == null)
        {
            return null;
        }
        return (Date)updateTime.clone();
    }

    public void setUpdateTime(Date updateTime)
    {
        if (updateTime == null)
        {
            this.updateTime = null;
        } else {
            this.updateTime = (Date)updateTime.clone();
        }
    }

    public String getAdContent() {
        return adContent;
    }

    public void setAdContent(String adContent) {
        this.adContent = adContent == null ? null : adContent.trim();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Advertisement{");
        sb.append("adId=").append(adId);
        sb.append(", authorId='").append(authorId).append('\'');
        sb.append(", adTitle='").append(adTitle).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", lastModifyId='").append(lastModifyId).append('\'');
        sb.append(", updateTime=").append(updateTime);
        sb.append(", adContent='").append(adContent).append('\'');
        sb.append('}');
        return sb.toString();
    }
}