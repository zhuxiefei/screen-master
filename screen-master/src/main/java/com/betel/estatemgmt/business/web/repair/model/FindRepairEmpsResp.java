package com.betel.estatemgmt.business.web.repair.model;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: FindRepairEmpsResp <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/2/1 8:55 <br/>
 * Version: 1.0 <br/>
 */
public class FindRepairEmpsResp {

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
        final StringBuilder sb = new StringBuilder("FindRepairEmpsResp{");
        sb.append("employeeId='").append(employeeId).append('\'');
        sb.append(", phoneNum='").append(phoneNum).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
