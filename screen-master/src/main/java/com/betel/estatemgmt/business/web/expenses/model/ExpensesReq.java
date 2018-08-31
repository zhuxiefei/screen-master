package com.betel.estatemgmt.business.web.expenses.model;

/**
 * Created by zhangjian on 2017/9/17.
 */
public class ExpensesReq {

    private String houseNum;
    private String houseMaster;
    private String exportBillStartTime;
    private String exportBillEndTime;
    private String buildingId;
    private String unitId;
    private String itemType;
    private String billStatus;
    private String billNos;
    private String billingStartTime;
    private String billingEndTime;
    private String chargeWays;
    private String isOnline;

    /**
     * 索取账单票据状态：
     * 1为未索要，
     * 2为已索要
     * 3为已送达
     */
    private String demandBillStatus;
    private String isOthers;

    /**
     * 分页数据
     */
    private int curPage = 1;
    private int pageSize = 10;

    public String getDemandBillStatus() {
        return demandBillStatus;
    }

    public void setDemandBillStatus(String demandBillStatus) {
        this.demandBillStatus = demandBillStatus;
    }

    public String getHouseNum() {
        return houseNum;
    }


    public String getIsOthers() {
        return isOthers;
    }

    public void setIsOthers(String isOthers) {
        this.isOthers = isOthers;
    }

    public void setHouseNum(String houseNum) {
        this.houseNum = houseNum;
    }

    public String getHouseMaster() {
        return houseMaster;
    }

    public void setHouseMaster(String houseMaster) {
        this.houseMaster = houseMaster;
    }

    public String getExportBillStartTime() {
        return exportBillStartTime;
    }

    public void setExportBillStartTime(String exportBillStartTime) {
        this.exportBillStartTime = exportBillStartTime;
    }

    public String getExportBillEndTime() {
        return exportBillEndTime;
    }

    public void setExportBillEndTime(String exportBillEndTime) {
        this.exportBillEndTime = exportBillEndTime;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(String billStatus) {
        this.billStatus = billStatus;
    }

    public String getBillNos() {
        return billNos;
    }

    public void setBillNos(String billNos) {
        this.billNos = billNos;
    }

    public String getBillingStartTime() {
        return billingStartTime;
    }

    public void setBillingStartTime(String billingStartTime) {
        this.billingStartTime = billingStartTime;
    }

    public String getBillingEndTime() {
        return billingEndTime;
    }

    public void setBillingEndTime(String billingEndTime) {
        this.billingEndTime = billingEndTime;
    }

    public String getChargeWays() {
        return chargeWays;
    }

    public void setChargeWays(String chargeWays) {
        this.chargeWays = chargeWays;
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

    public String getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(String isOnline) {
        this.isOnline = isOnline;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ExpensesReq{");
        sb.append("houseNum='").append(houseNum).append('\'');
        sb.append(", houseMaster='").append(houseMaster).append('\'');
        sb.append(", exportBillStartTime='").append(exportBillStartTime).append('\'');
        sb.append(", exportBillEndTime='").append(exportBillEndTime).append('\'');
        sb.append(", buildingId='").append(buildingId).append('\'');
        sb.append(", unitId='").append(unitId).append('\'');
        sb.append(", itemType='").append(itemType).append('\'');
        sb.append(", billStatus='").append(billStatus).append('\'');
        sb.append(", billNos='").append(billNos).append('\'');
        sb.append(", billingStartTime='").append(billingStartTime).append('\'');
        sb.append(", billingEndTime='").append(billingEndTime).append('\'');
        sb.append(", chargeWays='").append(chargeWays).append('\'');
        sb.append(", isOnline=").append(isOnline);
        sb.append(", demandBillStatus='").append(demandBillStatus).append('\'');
        sb.append(", isOthers='").append(isOthers).append('\'');
        sb.append(", curPage=").append(curPage);
        sb.append(", pageSize=").append(pageSize);
        sb.append('}');
        return sb.toString();
    }
}
