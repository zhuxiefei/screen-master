package com.betel.estatemgmt.business.web.expenses.model;

import com.betel.estatemgmt.common.Page;

/**
 * <p>
 * 分页查询所有预交费账单入参
 * </p>
 * ClassName: PrePayPageReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/10/19 12:44 <br/>
 * Version: 1.0 <br/>
 */
public class PrePayPageReq extends Page {

    private String billNos;

    public String getBillNos() {
        return billNos;
    }

    public void setBillNos(String billNos) {
        this.billNos = billNos;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PrePayPageReq{");
        sb.append("billNos='").append(billNos).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
