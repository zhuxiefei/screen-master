package com.betel.estatemgmt.business.propertyapp.notice.model;

import java.util.Date;

/**
 * <p>
 * 文件说明
 * </p>
 * ClassName: SystemNoticeInfo <br/>
 * Author: geyf  <br/>
 * Date: 2017/7/31 14:56 <br/>
 * Version: 1.0 <br/>
 */
public class SystemNoticeInfo {

    private Long noticeId;

    private Integer noticeStatus;

    private Integer noticeType;

    private String noticeContent;

    private Date createTime;

    private String noticeTitle;

    private String createDate;

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public Long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
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

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public Date getCreateTime() {
        if (createTime == null) {
            return null;
        }
        return (Date) createTime.clone();
    }

    public void setCreateTime(Date createTime) {
        if (createTime == null) {
            this.createTime = null;
        } else {
            this.createTime = (Date) createTime.clone();
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SystemNoticeInfo{");
        sb.append("noticeId=").append(noticeId);
        sb.append(", noticeStatus=").append(noticeStatus);
        sb.append(", noticeType=").append(noticeType);
        sb.append(", noticeContent='").append(noticeContent).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", noticeTitle='").append(noticeTitle).append('\'');
        sb.append(", createDate='").append(createDate).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
