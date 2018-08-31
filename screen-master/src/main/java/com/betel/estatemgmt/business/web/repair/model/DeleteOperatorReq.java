package com.betel.estatemgmt.business.web.repair.model;

/**
 * <p>
 * 删除维修人员入参
 * </p>
 * ClassName: DeleteOperatorReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/9/13 17:27 <br/>
 * Version: 1.0 <br/>
 */
public class DeleteOperatorReq {

    private String operatorId;

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DeleteOperatorReq{");
        sb.append("operatorId=").append(operatorId);
        sb.append('}');
        return sb.toString();
    }
}
