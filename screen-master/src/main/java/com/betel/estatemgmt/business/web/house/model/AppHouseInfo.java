package com.betel.estatemgmt.business.web.house.model;

/**
 * <p>
 * App删除房屋消息推送
 * </p>
 * ClassName: AppHouseInfo <br/>
 * Author: xiayanxin <br/>
 * Date: 2017/6/21 15:56 <br/>
 * Version: 1.0 <br/>
 */
public class AppHouseInfo {
    private String sendType;

    private String sendNo;

    private String sendTitle;

    private String sendContent;

    private Long sendId;

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AppHouseInfo{");
        sb.append("sendContent='").append(sendContent).append('\'');
        sb.append(", sendType='").append(sendType).append('\'');
        sb.append(", sendNo='").append(sendNo).append('\'');
        sb.append(", sendTitle='").append(sendTitle).append('\'');
        sb.append(", sendId=").append(sendId);
        sb.append('}');
        return sb.toString();
    }
}
