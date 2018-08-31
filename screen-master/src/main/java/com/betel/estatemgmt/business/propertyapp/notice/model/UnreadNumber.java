package com.betel.estatemgmt.business.propertyapp.notice.model;

/**
 * <p>
 * 文件说明
 * </p>
 * ClassName: UnreadNumber <br/>
 * Author: geyf  <br/>
 * Date: 2017/7/31 14:58 <br/>
 * Version: 1.0 <br/>
 */
public class UnreadNumber {

    private String replyNumber;
    private String systemNumber;
    private Integer authNumber;

    public String getReplyNumber() {
        return replyNumber;
    }

    public void setReplyNumber(String replyNumber) {
        this.replyNumber = replyNumber;
    }

    public String getSystemNumber() {
        return systemNumber;
    }

    public void setSystemNumber(String systemNumber) {
        this.systemNumber = systemNumber;
    }

    public Integer getAuthNumber() {
        return authNumber;
    }

    public void setAuthNumber(Integer authNumber) {
        this.authNumber = authNumber;
    }
}
