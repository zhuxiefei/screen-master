package com.betel.estatemgmt.business.web.repair.model;

/**
 * <p>
 * 查询维修人员入参
 * </p>
 * ClassName: FindOperatorReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/9/13 17:06 <br/>
 * Version: 1.0 <br/>
 */
public class FindOperatorReq {

    private String empName;

    private String phone;

    private String empNo;

    private String depId;

    private String positionId;

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

    public String getDepId() {
        return depId;
    }

    public void setDepId(String depId) {
        this.depId = depId;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FindExecutorReq{");
        sb.append("empName='").append(empName).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", empNo='").append(empNo).append('\'');
        sb.append(", depId='").append(depId).append('\'');
        sb.append(", positionId='").append(positionId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
