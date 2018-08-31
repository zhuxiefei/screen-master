package com.betel.estatemgmt.business.web.expenses.model;

/**
 * <p>
 * 查询预缴合计回参
 * </p>
 * ClassName: FindCountResp <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/10/16 15:20 <br/>
 * Version: 1.0 <br/>
 */
public class FindCountResp {

    private String totalAmount;

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FindCountResp{");
        sb.append("totalAmount=").append(totalAmount);
        sb.append('}');
        return sb.toString();
    }
}
