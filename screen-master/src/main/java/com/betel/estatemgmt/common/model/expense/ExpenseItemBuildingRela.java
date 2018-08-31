package com.betel.estatemgmt.common.model.expense;

import java.util.Date;

public class ExpenseItemBuildingRela {
    private Long ibId;

    private Long itemId;

    private Long buildingId;

    private Date createTime;

    public Long getIbId() {
        return ibId;
    }

    public void setIbId(Long ibId) {
        this.ibId = ibId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}