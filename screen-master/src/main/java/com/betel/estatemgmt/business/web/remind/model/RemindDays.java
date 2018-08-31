package com.betel.estatemgmt.business.web.remind.model;

/**
 * <p>
 * 查询提醒天数回参
 * </p>
 * ClassName: RemindDays <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/9/15 15:45 <br/>
 * Version: 1.0 <br/>
 */
public class RemindDays {

    private String confName;

    private String confValue;

    public String getConfName() {
        return confName;
    }

    public void setConfName(String confName) {
        this.confName = confName;
    }

    public String getConfValue() {
        return confValue;
    }

    public void setConfValue(String confValue) {
        this.confValue = confValue;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RemindDays{");
        sb.append("confName='").append(confName).append('\'');
        sb.append(", confValue='").append(confValue).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
