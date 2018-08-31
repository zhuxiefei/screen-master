package com.betel.estatemgmt.common.model.admin;

import java.util.Date;

public class AdminEstateRela extends AdminEstateRelaKey {
    private String roleId;

    private Date createTime;

    private Integer isEnableAccount;

    public Integer getIsEnableAccount() {
        return isEnableAccount;
    }

    public void setIsEnableAccount(Integer isEnableAccount) {
        this.isEnableAccount = isEnableAccount;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}