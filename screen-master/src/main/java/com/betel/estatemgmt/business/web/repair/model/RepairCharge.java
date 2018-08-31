package com.betel.estatemgmt.business.web.repair.model;

/**
 * <p>
 * 维修收费标准回参
 * </p>
 * ClassName: RepairCharge <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/9/19 18:48 <br/>
 * Version: 1.0 <br/>
 */
public class RepairCharge {

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
        final StringBuilder sb = new StringBuilder("RepairCharge{");
        sb.append("confName='").append(confName).append('\'');
        sb.append(", confValue='").append(confValue).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
