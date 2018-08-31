package com.betel.estatemgmt.common.model.user;

import java.util.Date;

public class UserPrivilege {
    private Long privilegeId;

    private String privilegeName;

    private String privilegeDesc;

    private Long parentId;

    private Integer privilegeType;

    private Date createTime;

    public Long getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(Long privilegeId) {
        this.privilegeId = privilegeId;
    }

    public String getPrivilegeName() {
        return privilegeName;
    }

    public void setPrivilegeName(String privilegeName) {
        this.privilegeName = privilegeName == null ? null : privilegeName.trim();
    }

    public String getPrivilegeDesc() {
        return privilegeDesc;
    }

    public void setPrivilegeDesc(String privilegeDesc) {
        this.privilegeDesc = privilegeDesc == null ? null : privilegeDesc.trim();
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getPrivilegeType() {
        return privilegeType;
    }

    public void setPrivilegeType(Integer privilegeType) {
        this.privilegeType = privilegeType;
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
        final StringBuilder sb = new StringBuilder("UserPrivilege{");
        sb.append("privilegeId=").append(privilegeId);
        sb.append(", privilegeName='").append(privilegeName).append('\'');
        sb.append(", privilegeDesc='").append(privilegeDesc).append('\'');
        sb.append(", parentId=").append(parentId);
        sb.append(", privilegeType=").append(privilegeType);
        sb.append(", createTime=").append(createTime);
        sb.append('}');
        return sb.toString();
    }
}