package com.betel.estatemgmt.business.web.charges.model;

/**
 * Created by zhangjian on 2017/9/19.
 */
public class ChargesItemReq {
    /**
     * 分页数据
     */
    private int curPage = 1;
    private int pageSize = 10;
    private String chargeType;
    private String itemName;
    private String itemPrice;
    private String itemCycle;
    private String overdueFine;
    private String itemType;
    private String chargeCycle;
    private String itemId;
    private String itemIds;
    private String itemActionTime;
    private String standard;
    private String buildingIds;
    private String estateId;

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
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

    public String getOverdueFine() {
        return overdueFine;
    }

    public void setOverdueFine(String overdueFine) {
        this.overdueFine = overdueFine;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getChargeCycle() {
        return chargeCycle;
    }

    public void setChargeCycle(String chargeCycle) {
        this.chargeCycle = chargeCycle;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemIds() {
        return itemIds;
    }

    public void setItemIds(String itemIds) {
        this.itemIds = itemIds;
    }

    public String getItemActionTime() {
        return itemActionTime;
    }

    public void setItemActionTime(String itemActionTime) {
        this.itemActionTime = itemActionTime;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getBuildingIds() {
        return buildingIds;
    }

    public void setBuildingIds(String buildingIds) {
        this.buildingIds = buildingIds;
    }

    @Override
    public String toString() {
        return "ChargesItemReq{" +
                "curPage=" + curPage +
                ", pageSize=" + pageSize +
                ", chargeType='" + chargeType + '\'' +
                ", itemName='" + itemName + '\'' +
                ", itemPrice='" + itemPrice + '\'' +
                ", itemCycle='" + itemCycle + '\'' +
                ", overdueFine='" + overdueFine + '\'' +
                ", itemType='" + itemType + '\'' +
                ", chargeCycle='" + chargeCycle + '\'' +
                ", itemId='" + itemId + '\'' +
                ", itemIds='" + itemIds + '\'' +
                ", itemActionTime='" + itemActionTime + '\'' +
                ", standard='" + standard + '\'' +
                ", buildingIds='" + buildingIds + '\'' +
                ", estateId='" + estateId + '\'' +
                '}';
    }
}
