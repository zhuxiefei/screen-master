package com.betel.estatemgmt.business.web.repair.model;

/**
 * <p>
 * 编辑维修单入参
 * </p>
 * ClassName: UpdateOrderReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/9/13 17:20 <br/>
 * Version: 1.0 <br/>
 */
public class UpdateOrderReq {

    private String orderNo;

    private String operatorId;

    private String operatorPhone;

    private String operatorDepartment;

    private String operatorHead;

    private String operatorName;

    public String getOperatorDepartment() {
        return operatorDepartment;
    }

    public void setOperatorDepartment(String operatorDepartment) {
        this.operatorDepartment = operatorDepartment;
    }

    public String getOperatorHead() {
        return operatorHead;
    }

    public void setOperatorHead(String operatorHead) {
        this.operatorHead = operatorHead;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

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

    public String getOperatorPhone() {
        return operatorPhone;
    }

    public void setOperatorPhone(String operatorPhone) {
        this.operatorPhone = operatorPhone;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UpdateOrderReq{");
        sb.append("orderNo='").append(orderNo).append('\'');
        sb.append(", operatorId='").append(operatorId).append('\'');
        sb.append(", operatorPhone='").append(operatorPhone).append('\'');
        sb.append(", operatorDepartment='").append(operatorDepartment).append('\'');
        sb.append(", operatorHead='").append(operatorHead).append('\'');
        sb.append(", operatorName='").append(operatorName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
