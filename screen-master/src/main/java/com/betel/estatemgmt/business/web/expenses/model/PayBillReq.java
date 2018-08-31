package com.betel.estatemgmt.business.web.expenses.model;

import java.util.Arrays;

/**
 * Created by zhangjian on 2017/9/21.
 */
public class PayBillReq {
    private String[] billNos;
    private String chargeWay;
    private String flowNo;

    @Override
    public String toString() {
        return "PayBillReq{" +
                "billNos=" + Arrays.toString(billNos) +
                ", chargeWay='" + chargeWay + '\'' +
                ", flowNo='" + flowNo + '\'' +
                '}';
    }

    public String[] getBillNos() {
        return billNos;
    }

    public void setBillNos(String[] billNos) {
        this.billNos = billNos;
    }

    public String getChargeWay() {
        return chargeWay;
    }

    public void setChargeWay(String chargeWay) {
        this.chargeWay = chargeWay;
    }

    public String getFlowNo() {
        return flowNo;
    }

    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo;
    }
}
