package com.betel.estatemgmt.business.web.repair.model;

/**
 * <p>
 * 查询指派人员入参
 * </p>
 * ClassName: FindExecutorResp <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/12/21 16:45 <br/>
 * Version: 1.0 <br/>
 */
public class FindExecutorResp {

    private String empId;

    private String empName;

    private String phone;

    private String depName;

    private String empNo;

    private String head;

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
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

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FindExecutorResp{");
        sb.append("empId='").append(empId).append('\'');
        sb.append(", empName='").append(empName).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", depName='").append(depName).append('\'');
        sb.append(", empNo='").append(empNo).append('\'');
        sb.append(", head='").append(head).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
