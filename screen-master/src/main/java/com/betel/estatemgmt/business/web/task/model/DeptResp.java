package com.betel.estatemgmt.business.web.task.model;

/**
 * <p>
 * Describe this class...
 * </p>
 *
 * @ClassName: DeptResp <br/>
 * @author: jian.z  <br/>
 * @date: 2017/11/10 10:01 <br/>
 * @Version: 1.0
 */
public class DeptResp {
    private String departmentId;
    private String departmentName;
    private String departmentDesc;
    private String parentId;
    private Integer depth;
    private String depId;

    public String getDepId() {
        return depId;
    }

    public void setDepId(String depId) {
        this.depId = depId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DeptResp{");
        sb.append("departmentId='").append(departmentId).append('\'');
        sb.append(", departmentName='").append(departmentName).append('\'');
        sb.append(", departmentDesc='").append(departmentDesc).append('\'');
        sb.append(", parentId='").append(parentId).append('\'');
        sb.append(", depth=").append(depth);
        sb.append(", depId='").append(depId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
