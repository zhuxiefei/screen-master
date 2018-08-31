package com.betel.estatemgmt.common.model.house;

import java.util.Date;

public class HouseAuthNotice {
    private Long noticeId;

    private Long authId;

    private String noticeUserId;

    private String noticeContent;

    private int noticeStatus;

    private Date createTime;

    public Long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public Long getAuthId() {
        return authId;
    }

    public void setAuthId(Long authId) {
        this.authId = authId;
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

    public int getNoticeStatus() {
        return noticeStatus;
    }

    public void setNoticeStatus(int noticeStatus) {
        this.noticeStatus = noticeStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HouseAuthNotice{");
        sb.append("authId=").append(authId);
        sb.append(", noticeId=").append(noticeId);
        sb.append(", noticeUserId='").append(noticeUserId).append('\'');
        sb.append(", noticeContent='").append(noticeContent).append('\'');
        sb.append(", noticeStatus=").append(noticeStatus);
        sb.append(", createTime=").append(createTime);
        sb.append('}');
        return sb.toString();
    }
}