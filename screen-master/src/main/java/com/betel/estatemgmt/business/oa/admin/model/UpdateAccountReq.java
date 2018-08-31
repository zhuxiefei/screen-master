package com.betel.estatemgmt.business.oa.admin.model;

/**
 * Created by Administrator on 2018/3/5/005.
 */
public class UpdateAccountReq {

    private String oldAcctName;

    private String adminId;

    private String newAcctName;

    public String getOldAcctName() {
        return oldAcctName;
    }

    public void setOldAcctName(String oldAcctName) {
        this.oldAcctName = oldAcctName;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getNewAcctName() {
        return newAcctName;
    }

    public void setNewAcctName(String newAcctName) {
        this.newAcctName = newAcctName;
    }

    @Override
    public String toString() {
        return "UpdateAccountReq{" +
                "oldAcctName='" + oldAcctName + '\'' +
                ", adminId='" + adminId + '\'' +
                ", newAcctName='" + newAcctName + '\'' +
                '}';
    }
}
