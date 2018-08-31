package com.betel.estatemgmt.business.web.repair.model;

import java.util.Date;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: FindAllRecordResp <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/1/5 15:45 <br/>
 * Version: 1.0 <br/>
 */
public class FindAllRecordResp {

    private String orderNo;

    private String operatorName;

    private Date createTime;

    private String description;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FindAllRecordResp{");
        sb.append("orderNo='").append(orderNo).append('\'');
        sb.append(", operatorName='").append(operatorName).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
