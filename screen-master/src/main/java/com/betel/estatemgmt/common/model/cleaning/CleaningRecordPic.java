package com.betel.estatemgmt.common.model.cleaning;

import java.util.Date;

public class CleaningRecordPic extends CleaningRecordPicKey {
    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}