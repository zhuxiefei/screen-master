package com.betel.estatemgmt.business.web.repair.model;

import com.betel.estatemgmt.common.Page;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: FindAllRecordReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/1/5 15:45 <br/>
 * Version: 1.0 <br/>
 */
public class FindAllRecordReq extends Page {
    private String orderNo;

    private String startTime;

    private String endTime;

    private String estateId;

    /**
     * 1为新增维修单
     2为取消维修单
     3为指派维修单
     4为完成维修单
     */
    private String historyType;

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "FindAllRecordReq{" +
                "orderNo='" + orderNo + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", estateId='" + estateId + '\'' +
                ", historyType='" + historyType + '\'' +
                '}';
    }
}
