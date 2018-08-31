package com.betel.estatemgmt.business.web.repair.model;

/**
 * <p>
 * 查询收费标准入参
 * </p>
 * ClassName: FindRepairChargeReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/9/19 18:47 <br/>
 * Version: 1.0 <br/>
 */
public class FindRepairChargeReq {

    private String confName;

    public String getConfName() {
        return confName;
    }

    public void setConfName(String confName) {
        this.confName = confName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FindRepairChargeReq{");
        sb.append("confName='").append(confName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
