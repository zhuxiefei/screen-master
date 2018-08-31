package com.betel.estatemgmt.business.propertyapp.assign.model;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: UserInfoResp <br/>
 * Author: Cui.xx  <br/>
 * Date: 2017/12/4 19:58 <br/>
 * Version: 1.0 <br/>
 */
public class UserInfoResp {

    String employeeName;

    String departmentName;

    String positionName;

    String photo;

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserInfoResp{");
        sb.append("employeeName='").append(employeeName).append('\'');
        sb.append(", departmentName='").append(departmentName).append('\'');
        sb.append(", positionName='").append(positionName).append('\'');
        sb.append(", photo='").append(photo).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
