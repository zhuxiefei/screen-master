package com.betel.estatemgmt.business.web.repair.model;

/**
 * <p>
 * 查询指派人员入参
 * </p>
 * ClassName: FindExecutorReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/12/21 16:43 <br/>
 * Version: 1.0 <br/>
 */
public class FindExecutorReq {

    private String empName;

    private String phone;

    private String empNo;

    private String departmentId;

    private Integer depth;

    private String depId;

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

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FindExecutorReq{");
        sb.append("empName='").append(empName).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", empNo='").append(empNo).append('\'');
        sb.append(", departmentId='").append(departmentId).append('\'');
        sb.append(", depth=").append(depth);
        sb.append(", depId='").append(depId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
