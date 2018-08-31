package com.betel.estatemgmt.business.oa.task.model;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: FindEmpReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/1/20 11:45 <br/>
 * Version: 1.0 <br/>
 */
public class FindEmpReq {

    private String empId;

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FindEmpReq{");
        sb.append("empId='").append(empId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
