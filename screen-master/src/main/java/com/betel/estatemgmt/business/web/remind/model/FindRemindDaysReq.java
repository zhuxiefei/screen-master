package com.betel.estatemgmt.business.web.remind.model;

/**
 * <p>
 * 查询提醒天数入参
 * </p>
 * ClassName: FindRemindDaysReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/9/15 15:42 <br/>
 * Version: 1.0 <br/>
 */
public class FindRemindDaysReq {

    private String confName;

    public String getConfName() {
        return confName;
    }

    public void setConfName(String confName) {
        this.confName = confName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FindRemindDaysReq{");
        sb.append("confName='").append(confName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
