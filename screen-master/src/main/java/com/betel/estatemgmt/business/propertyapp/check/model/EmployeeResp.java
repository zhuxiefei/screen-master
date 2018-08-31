package com.betel.estatemgmt.business.propertyapp.check.model;

/**
 * Created by zhangjian on 2018/2/22.
 */
public class EmployeeResp {
    private String employeeId;

    private String employeeNo;

    private String companyId;

    private String employeeName;

    private String idNum;

    @Override
    public String toString() {
        return "EmployeeResp{" +
                "employeeId='" + employeeId + '\'' +
                ", employeeNo='" + employeeNo + '\'' +
                ", companyId='" + companyId + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", idNum='" + idNum + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                '}';
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    private String phoneNum;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }
}
