package com.betel.estatemgmt.business.smartre.notice.model;

import java.util.Date;

/**
 * Created by zhangjian on 2018/9/2.
 */
public class NoticeInfoReq {
    private String noticeUserId;

    private String noticeContent;

    private String noticeType;

    @Override
    public String toString() {
        return "NoticeInfoReq{" +
                "noticeUserId='" + noticeUserId + '\'' +
                ", noticeContent='" + noticeContent + '\'' +
                ", noticeType='" + noticeType + '\'' +
                '}';
    }

    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }

    public String getNoticeUserId() {
        return noticeUserId;
    }

    public void setNoticeUserId(String noticeUserId) {
        this.noticeUserId = noticeUserId;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

}
