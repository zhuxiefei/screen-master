package com.betel.estatemgmt.business.web.screen.model;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: FindDepListResp <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/2/1 9:12 <br/>
 * Version: 1.0 <br/>
 */
public class FindDepListResp {
    private String departmentName;
    private String depId;

    public String getDepId() {
        return depId;
    }

    public void setDepId(String depId) {
        this.depId = depId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DeptResp{");
        sb.append("departmentName='").append(departmentName).append('\'');
        sb.append(", depId='").append(depId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
