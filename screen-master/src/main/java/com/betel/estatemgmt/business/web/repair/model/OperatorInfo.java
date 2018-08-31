package com.betel.estatemgmt.business.web.repair.model;

/**
 * <p>
 * 查询维修人员回参
 * </p>
 * ClassName: OperatorInfo <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/9/13 17:07 <br/>
 * Version: 1.0 <br/>
 */
public class OperatorInfo {

    private Long operatorId;

    private String operatorName;

    private String operatorPhone;

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperatorPhone() {
        return operatorPhone;
    }

    public void setOperatorPhone(String operatorPhone) {
        this.operatorPhone = operatorPhone;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OperatorInfo{");
        sb.append("operatorId=").append(operatorId);
        sb.append(", operatorName='").append(operatorName).append('\'');
        sb.append(", operatorPhone='").append(operatorPhone).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
