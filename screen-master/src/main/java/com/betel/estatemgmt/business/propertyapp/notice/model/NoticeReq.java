package com.betel.estatemgmt.business.propertyapp.notice.model;


import com.betel.estatemgmt.common.model.system.Notice;
import org.apache.xmlbeans.impl.xb.xsdschema.LocalSimpleType;

import java.util.List;

public class NoticeReq {
    private String userId;
    private String phone;
    private String content;
    /**
     * noticeType
     */
    private String sendNo;



    public String getContentSend() {
        return contentSend;
    }

    public void setContentSend(String contentSend) {
        this.contentSend = contentSend;
    }

    private String contentSend;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendNo() {
        return sendNo;
    }

    public void setSendNo(String sendNo) {
        this.sendNo = sendNo;
    }
}
