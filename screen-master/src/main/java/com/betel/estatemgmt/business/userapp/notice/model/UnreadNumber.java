package com.betel.estatemgmt.business.userapp.notice.model;

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

    private Integer replyNumber;

    private Integer systemNumber;

    private Integer authNumber;

    public Integer getReplyNumber() {
        return replyNumber;
    }

    public void setReplyNumber(Integer replyNumber) {
        this.replyNumber = replyNumber;
    }

    public Integer getSystemNumber() {
        return systemNumber;
    }

    public void setSystemNumber(Integer systemNumber) {
        this.systemNumber = systemNumber;
    }

    public Integer getAuthNumber() {
        return authNumber;
    }

    public void setAuthNumber(Integer authNumber) {
        this.authNumber = authNumber;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UnreadNumber{");
        sb.append("replyNumber=").append(replyNumber);
        sb.append(", systemNumber=").append(systemNumber);
        sb.append(", authNumber=").append(authNumber);
        sb.append('}');
        return sb.toString();
    }
}
