package com.betel.estatemgmt.business.web.charges.model;

import java.util.Date;

/**
 * <p>
 * 计算出账时间回参
 * </p>
 * ClassName: CountBillTimeResp <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/10/19 16:19 <br/>
 * Version: 1.0 <br/>
 */
public class CountBillTimeResp {

    private Date startTime;

    private Date endTime;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
