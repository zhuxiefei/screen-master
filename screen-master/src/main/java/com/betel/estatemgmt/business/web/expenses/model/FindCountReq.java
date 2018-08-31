package com.betel.estatemgmt.business.web.expenses.model;

/**
 * <p>
 * 查询预缴合计入参
 * </p>
 * ClassName: FindCountReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/10/16 15:19 <br/>
 * Version: 1.0 <br/>
 */
public class FindCountReq {

    private String itemId;

    private String houseId;

    private String months;

    private String buildingId;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FindCountReq{");
        sb.append("itemId='").append(itemId).append('\'');
        sb.append(", houseId='").append(houseId).append('\'');
        sb.append(", months='").append(months).append('\'');
        sb.append(", buildingId='").append(buildingId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
