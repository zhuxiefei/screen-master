package com.betel.estatemgmt.business.userapp.decoration.model;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: EmpInfo <br/>
 * Author: Cui.xx  <br/>
 * Date: 2018/1/29 17:42 <br/>
 * Version: 1.0 <br/>
 */
public class EmpInfo {

    private String employeeId;

    private String phoneNum;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EmpInfo{");
        sb.append("employeeId='").append(employeeId).append('\'');
        sb.append(", phoneNum='").append(phoneNum).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
