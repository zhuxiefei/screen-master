package com.betel.estatemgmt.business.web.remind.model;

/**
 * <p>
 * 保存提醒天数入参
 * </p>
 * ClassName: AddRemindDaysReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/9/15 15:31 <br/>
 * Version: 1.0 <br/>
 */
public class AddRemindDaysReq {
    private String confName;

    private Integer remindDays;

    private String estateId;

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
    }

    public String getConfName() {
        return confName;
    }

    public void setConfName(String confName) {
        this.confName = confName;
    }

    public Integer getRemindDays() {
        return remindDays;
    }

    public void setRemindDays(Integer remindDays) {
        this.remindDays = remindDays;
    }

    @Override
    public String toString() {
        return "AddRemindDaysReq{" +
                "confName='" + confName + '\'' +
                ", remindDays=" + remindDays +
                ", estateId='" + estateId + '\'' +
                '}';
    }
}
