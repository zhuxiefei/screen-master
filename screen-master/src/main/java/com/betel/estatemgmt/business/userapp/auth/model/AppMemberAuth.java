package com.betel.estatemgmt.business.userapp.auth.model;


import java.util.Date;

/**
 * <p>
 * 文件说明
 * </p>
 * ClassName: MemberAuth <br/>
 * Author: geyf  <br/>
 * Date: 2017/6/23 9:38 <br/>
 * Version: 1.0 <br/>
 */
public class AppMemberAuth {

    private Long noticeId;

    private String noticeStatus;

    private Date createTime;

    private Integer authStatus;

    private Integer reviewStatus;

    public Integer getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(Integer reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public Long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public String getNoticeStatus() {
        return noticeStatus;
    }

    public void setNoticeStatus(String noticeStatus) {
        this.noticeStatus = noticeStatus;
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

    public Integer getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(Integer authStatus) {
        this.authStatus = authStatus;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MemberAuth{");
        sb.append("noticeId=").append(noticeId);
        sb.append(", noticeStatus='").append(noticeStatus).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", authStatus=").append(authStatus);
        sb.append(", reviewStatus=").append(reviewStatus);
        sb.append('}');
        return sb.toString();
    }
}
