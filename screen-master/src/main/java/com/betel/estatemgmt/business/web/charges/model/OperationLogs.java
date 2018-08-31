package com.betel.estatemgmt.business.web.charges.model;

/**
 * Created by zhangjian on 2017/9/19.
 */
public class OperationLogs {
    private String historyId;
    private String adminId;
    private String adminName;
    private String createTime;
    private String historyDesc;
    private String itemId;

    @Override
    public String toString() {
        return "OperationLogs{" +
                "historyId='" + historyId + '\'' +
                ", adminId='" + adminId + '\'' +
                ", adminName='" + adminName + '\'' +
                ", createTime='" + createTime + '\'' +
                ", historyDesc='" + historyDesc + '\'' +
                ", itemId='" + itemId + '\'' +
                '}';
    }

    public String getHistoryId() {
        return historyId;
    }

    public void setHistoryId(String historyId) {
        this.historyId = historyId;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getHistoryDesc() {
        return historyDesc;
    }

    public void setHistoryDesc(String historyDesc) {
        this.historyDesc = historyDesc;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }


}
