package com.betel.estatemgmt.business.userapp.pay.model;

import java.math.BigDecimal;

/**
 * <p>
 * 代缴费订单列表model
 * </p>
 * ClassName: AllPayList <br/>
 * Author: zhouye  <br/>
 * Date: 2017/9/18 13:29 <br/>
 * Version: 1.0 <br/>
 */
public class AllPayList {
    private PayPage page;
    private BigDecimal total;

    public PayPage getPage() {
        return page;
    }

    public void setPage(PayPage page) {
        this.page = page;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "AllPayList{" +
                "page=" + page +
                ", total=" + total +
                '}';
    }
}
