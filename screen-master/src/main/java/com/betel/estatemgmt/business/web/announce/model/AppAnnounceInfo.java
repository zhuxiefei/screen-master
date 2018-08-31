package com.betel.estatemgmt.business.web.announce.model;

/**
 * <p>
 * App公告推送
 * </p>
 * ClassName: AppAnnounceInfo <br/>
 * Author: xiayanxin  <br/>
 * Date: 2017/5/15 18:39 <br/>
 * Version: 1.0 <br/>
 */
public class AppAnnounceInfo {
    private String sendType;

    private String sendNo;

    private String sendTitle;

    private String sendContent;

    private Long sendId;

    public AppAnnounceInfo(){}

    public AppAnnounceInfo(String sendType, String sendNo, String sendTitle, String sendContent, Long sendId) {
        this.sendType = sendType;
        this.sendNo = sendNo;
        this.sendTitle = sendTitle;
        this.sendContent = sendContent;
        this.sendId = sendId;
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    public String getSendNo() {
        return sendNo;
    }

    public void setSendNo(String sendNo) {
        this.sendNo = sendNo;
    }

    public String getSendTitle() {
        return sendTitle;
    }

    public void setSendTitle(String sendTitle) {
        this.sendTitle = sendTitle;
    }

    public String getSendContent() {
        return sendContent;
    }

    public void setSendContent(String sendContent) {
        this.sendContent = sendContent;
    }

    public Long getSendId() {
        return sendId;
    }

    public void setSendId(Long sendId) {
        this.sendId = sendId;
    }
}
