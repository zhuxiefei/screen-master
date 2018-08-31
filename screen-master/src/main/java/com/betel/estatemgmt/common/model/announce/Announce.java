package com.betel.estatemgmt.common.model.announce;

import java.util.Date;

public class Announce {
    private Long announceId;

    private String authorId;

    private String announceTitle;

    private Integer announceStatus;

    private Date createTime;

    private String lastModifyId;

    private Date updateTime;

    private String announceContent;

    private Integer isTop;

    private Date topTime;

    public Long getAnnounceId() {
        return announceId;
    }

    public void setAnnounceId(Long announceId) {
        this.announceId = announceId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId == null ? null : authorId.trim();
    }

    public String getAnnounceTitle() {
        return announceTitle;
    }

    public void setAnnounceTitle(String announceTitle) {
        this.announceTitle = announceTitle == null ? null : announceTitle.trim();
    }

    public Integer getAnnounceStatus() {
        return announceStatus;
    }

    public void setAnnounceStatus(Integer announceStatus) {
        this.announceStatus = announceStatus;
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

    public String getAnnounceContent() {
        return announceContent;
    }

    public void setAnnounceContent(String announceContent) {
        this.announceContent = announceContent == null ? null : announceContent.trim();
    }

    public Integer getIsTop() {
        return isTop;
    }

    public void setIsTop(Integer isTop) {
        this.isTop = isTop;
    }

    public Date getTopTime() {
        return topTime;
    }

    public void setTopTime(Date topTime) {
        this.topTime = topTime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Announce{");
        sb.append("announceId=").append(announceId);
        sb.append(", authorId='").append(authorId).append('\'');
        sb.append(", announceTitle='").append(announceTitle).append('\'');
        sb.append(", announceStatus=").append(announceStatus);
        sb.append(", createTime=").append(createTime);
        sb.append(", lastModifyId='").append(lastModifyId).append('\'');
        sb.append(", updateTime=").append(updateTime);
        sb.append(", announceContent='").append(announceContent).append('\'');
        sb.append(", isTop=").append(isTop);
        sb.append(", topTime=").append(topTime);
        sb.append('}');
        return sb.toString();
    }
}