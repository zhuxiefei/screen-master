package com.betel.estatemgmt.business.oa.admin.model;

/**
 * <p>
 * 文件说明
 * </p>
 * ClassName: PrivilegeInfo <br/>
 * Author: geyf  <br/>
 * Date: 2017/7/31 14:44 <br/>
 * Version: 1.0 <br/>
 */
public class PrivilegeInfo {

    private Long privilegeId;

    private String privilegeName;

    private Long parentId;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

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
        this.privilegeName = privilegeName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PrivilegeInfo{");
        sb.append("privilegeId=").append(privilegeId);
        sb.append(", privilegeName='").append(privilegeName).append('\'');
        sb.append(", parentId=").append(parentId);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
