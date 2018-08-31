package com.betel.estatemgmt.business.propertyapp.solve.model;

import com.betel.estatemgmt.common.Page;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: RepairListStatusReq <br/>
 * Author: Cui.xx  <br/>
 * Date: 2017/12/23 14:31 <br/>
 * Version: 1.0 <br/>
 */
public class RepairListStatusReq extends Page{

    String orderStatus;

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RepairListStatusReq{");
        sb.append("orderStatus='").append(orderStatus).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
