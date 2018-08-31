package com.betel.estatemgmt.business.web.charges.model;

import java.util.Date;

/**
 * Created by zhangjian on 2017/9/22.
 */
public class ExpenseItemHistoryInfo {
    private Long historyId;

    private Long itemId;

    private String adminId;

    private String historyDesc = "";

    private Date createTime;
    private String adminName = "";

    @Override
    public String toString() {
        return "ExpenseItemHistoryInfo{" +
                "historyId=" + historyId +
                ", itemId=" + itemId +
                ", adminId='" + adminId + '\'' +
                ", historyDesc='" + historyDesc + '\'' +
                ", createTime=" + createTime +
                ", adminName='" + adminName + '\'' +
                '}';
    }

    public Long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getHistoryDesc() {
        return historyDesc;
    }

    public void setHistoryDesc(String historyDesc) {
        this.historyDesc = historyDesc;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }
}
