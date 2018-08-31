package com.betel.estatemgmt.business.web.expenses.model;

/**
 * <p>
 * 修改收据状态
 * </p>
 * ClassName: UpdateDemandReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/11/10 17:07 <br/>
 * Version: 1.0 <br/>
 */
public class UpdateDemandReq {

    private String billNo;

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UpdateDemandReq{");
        sb.append("billNo='").append(billNo).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
