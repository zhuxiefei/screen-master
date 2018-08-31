package com.betel.estatemgmt.common.model.system;

import java.util.Date;

public class Notice {
    private Long noticeId;

    private String noticeUserId;

    private String noticeContent;

    private Integer noticeStatus;

    private Integer noticeType;

    private Date createTime;

    public Long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public String getNoticeUserId() {
        return noticeUserId;
    }

    public void setNoticeUserId(String noticeUserId) {
        this.noticeUserId = noticeUserId == null ? null : noticeUserId.trim();
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent == null ? null : noticeContent.trim();
    }

    public Integer getNoticeStatus() {
        return noticeStatus;
    }

    public void setNoticeStatus(Integer noticeStatus) {
        this.noticeStatus = noticeStatus;
    }

    public Integer getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(Integer noticeType) {
        this.noticeType = noticeType;
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
        final StringBuilder sb = new StringBuilder("Notice{");
        sb.append("noticeId=").append(noticeId);
        sb.append(", noticeUserId='").append(noticeUserId).append('\'');
        sb.append(", noticeContent='").append(noticeContent).append('\'');
        sb.append(", noticeStatus=").append(noticeStatus);
        sb.append(", noticeType=").append(noticeType);
        sb.append(", createTime=").append(createTime);
        sb.append('}');
        return sb.toString();
    }
}