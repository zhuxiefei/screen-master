package com.betel.estatemgmt.business.propertyapp.notice.model;

/**
 * <p>
 * 接收通知id
 * </p>
 * ClassName: NoticeId <br/>
 * Author: geyf  <br/>
 * Date: 2017/5/16 16:59 <br/>
 * Version: 1.0 <br/>
 */
public class NoticeId {

    private Long noticeId;

    public Long getNoticeId() {

        return noticeId;
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("NoticeId{");
        sb.append("noticeId=").append(noticeId);
        sb.append('}');
        return sb.toString();
    }
}
