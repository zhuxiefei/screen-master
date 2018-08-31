package com.betel.estatemgmt.business.web.charges.model;

import java.util.Date;

/**
 * Created by zhangjian on 2017/9/19.
 */
public class ChargesItem {
    private String itemId;
    private String itemName;
    private String chargeType;
    private String itemPrice;
    private String itemCycle;
    private String chargeCycle;
    private String overdueFine;
    private String buildings;
    private String itemType;
    private String standardInfo;
    private Date startTime;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemCycle() {
        return itemCycle;
    }

    public void setItemCycle(String itemCycle) {
        this.itemCycle = itemCycle;
    }

    public String getChargeCycle() {
        return chargeCycle;
    }

    public void setChargeCycle(String chargeCycle) {
        this.chargeCycle = chargeCycle;
    }

    public String getOverdueFine() {
        return overdueFine;
    }

    public void setOverdueFine(String overdueFine) {
        this.overdueFine = overdueFine;
    }

    public String getBuildings() {
        return buildings;
    }

    public void setBuildings(String buildings) {
        this.buildings = buildings;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getStandardInfo() {
        return standardInfo;
    }

    public void setStandardInfo(String standardInfo) {
        this.standardInfo = standardInfo;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ChargesItem{");
        sb.append("itemId='").append(itemId).append('\'');
        sb.append(", itemName='").append(itemName).append('\'');
        sb.append(", chargeType='").append(chargeType).append('\'');
        sb.append(", itemPrice='").append(itemPrice).append('\'');
        sb.append(", itemCycle='").append(itemCycle).append('\'');
        sb.append(", chargeCycle='").append(chargeCycle).append('\'');
        sb.append(", overdueFine='").append(overdueFine).append('\'');
        sb.append(", buildings='").append(buildings).append('\'');
        sb.append(", itemType='").append(itemType).append('\'');
        sb.append(", standardInfo='").append(standardInfo).append('\'');
        sb.append(", startTime=").append(startTime);
        sb.append('}');
        return sb.toString();
    }
}
