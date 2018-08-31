package com.betel.estatemgmt.business.web.expenses.model;

/**
 * <p>
 * 查询物业费/停车费的计费周期入参
 * </p>
 * ClassName: FindItemCycleReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/10/13 10:20 <br/>
 * Version: 1.0 <br/>
 */
public class FindItemCycleReq {

    private String houseId;

    //收费类型
    private String itemType;

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FindItemCycleReq{");
        sb.append("houseId='").append(houseId).append('\'');
        sb.append(", itemType='").append(itemType).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
