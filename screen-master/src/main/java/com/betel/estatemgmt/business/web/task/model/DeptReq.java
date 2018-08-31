package com.betel.estatemgmt.business.web.task.model;

/**
 * <p>
 * Describe this class...
 * </p>
 *
 * @ClassName: DeptReq <br/>
 * @author: jian.z  <br/>
 * @date: 2017/11/10 10:03 <br/>
 * @Version: 1.0
 */
public class DeptReq {
    private String departmentId;
    private String departmentName;
    private String departmentDesc;
    private String parentDepartment;
    private String depId;
    private Integer depth;

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public String getDepId() {
        return depId;
    }

    public void setDepId(String depId) {
        this.depId = depId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentDesc() {
        return departmentDesc;
    }

    public void setDepartmentDesc(String departmentDesc) {
        this.departmentDesc = departmentDesc;
    }

    public String getParentDepartment() {
        return parentDepartment;
    }

    public void setParentDepartment(String parentDepartment) {
        this.parentDepartment = parentDepartment;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DeptReq{");
        sb.append("departmentId='").append(departmentId).append('\'');
        sb.append(", departmentName='").append(departmentName).append('\'');
        sb.append(", departmentDesc='").append(departmentDesc).append('\'');
        sb.append(", parentDepartment='").append(parentDepartment).append('\'');
        sb.append(", depId='").append(depId).append('\'');
        sb.append(", depth=").append(depth);
        sb.append('}');
        return sb.toString();
    }
}
