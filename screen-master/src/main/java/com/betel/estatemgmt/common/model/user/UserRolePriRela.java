package com.betel.estatemgmt.common.model.user;

import java.util.Date;

public class UserRolePriRela {
    private Long rpRela;

    private Long roleId;

    private Long privilegeId;

    private Date createTime;

    public Long getRpRela() {
        return rpRela;
    }

    public void setRpRela(Long rpRela) {
        this.rpRela = rpRela;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(Long privilegeId) {
        this.privilegeId = privilegeId;
    }

    public Date getCreateTime()
    {
        if (createTime == null)
        {
            return null;
        }
        return (Date)createTime.clone();
    }

    public void setCreateTime(Date createTime)
    {
        if (createTime == null)
        {
            this.createTime = null;
        } else {
            this.createTime = (Date)createTime.clone();
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserRolePriRela{");
        sb.append("rpRela=").append(rpRela);
        sb.append(", roleId=").append(roleId);
        sb.append(", privilegeId=").append(privilegeId);
        sb.append(", createTime=").append(createTime);
        sb.append('}');
        return sb.toString();
    }
}