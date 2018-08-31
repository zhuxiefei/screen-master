package com.betel.estatemgmt.business.propertyapp.lease.model;

/**
 * <p>
 * 房屋状态返回
 * </p>
 * Author: geyf  <br/>
 * Date: 2018/5/8 11:34 <br/>
 * Version: 1.0 <br/>
 */
public class HouseNumResp {

    private Integer leaseNumber;

    private Integer attractNumber;

    private Integer freeNumber;

    public Integer getLeaseNumber() {
        return leaseNumber;
    }

    public void setLeaseNumber(Integer leaseNumber) {
        this.leaseNumber = leaseNumber;
    }

    public Integer getAttractNumber() {
        return attractNumber;
    }

    public void setAttractNumber(Integer attractNumber) {
        this.attractNumber = attractNumber;
    }

    public Integer getFreeNumber() {
        return freeNumber;
    }

    public void setFreeNumber(Integer freeNumber) {
        this.freeNumber = freeNumber;
    }

    @Override
    public String toString() {
        return "HouseNumResp{" +
                "leaseNumber=" + leaseNumber +
                ", attractNumber=" + attractNumber +
                ", freeNumber=" + freeNumber +
                '}';
    }
}