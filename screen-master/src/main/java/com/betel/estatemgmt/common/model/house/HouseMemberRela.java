package com.betel.estatemgmt.common.model.house;

import java.util.Date;

public class HouseMemberRela extends HouseMemberRelaKey {
    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}