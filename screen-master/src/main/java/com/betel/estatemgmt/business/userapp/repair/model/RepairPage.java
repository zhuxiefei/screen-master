package com.betel.estatemgmt.business.userapp.repair.model;

import com.betel.estatemgmt.utils.pagination.model.Paging;

/**
 * <p>
 * 分页model
 * </p>
 * ClassName: RepairPage <br/>
 * Author: zhouye  <br/>
 * Date: 2017/9/15 16:13 <br/>
 * Version: 1.0 <br/>
 */
public class RepairPage extends Paging{

    private String estateId;

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
    }

    private Integer orderStatus;

    private String userId;
    public RepairPage(int curPage, int pageSize) {
        super(curPage, pageSize);
    }

    public RepairPage(){
    }

    public RepairPage(int curPage, Integer orderStatus) {
        super(curPage);
        this.orderStatus = orderStatus;
    }

    public RepairPage(int curPage, int pageSize, Integer orderStatus) {
        super(curPage, pageSize);
        this.orderStatus = orderStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }
}
