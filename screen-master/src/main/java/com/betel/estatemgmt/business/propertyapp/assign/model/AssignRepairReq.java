package com.betel.estatemgmt.business.propertyapp.assign.model;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: AssignRepairReq <br/>
 * Author: Cui.xx  <br/>
 * Date: 2017/12/22 17:37 <br/>
 * Version: 1.0 <br/>
 */
public class AssignRepairReq {

    String orderNo;

    String operatorId;

    String operatorDepartment;

    String operatorName;

    String photo;

    String description;

    String phoneNum;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOperatorDepartment() {
        return operatorDepartment;
    }

    public void setOperatorDepartment(String operatorDepartment) {
        this.operatorDepartment = operatorDepartment;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AssignRepairReq{");
        sb.append("orderNo='").append(orderNo).append('\'');
        sb.append(", operatorId='").append(operatorId).append('\'');
        sb.append(", operatorDepartment='").append(operatorDepartment).append('\'');
        sb.append(", operatorName='").append(operatorName).append('\'');
        sb.append(", photo='").append(photo).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", phoneNum='").append(phoneNum).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
