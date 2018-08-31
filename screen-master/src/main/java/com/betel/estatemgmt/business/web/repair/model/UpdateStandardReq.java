package com.betel.estatemgmt.business.web.repair.model;

/**
 * <p>
 * 编辑收费标准入参
 * </p>
 * ClassName: UpdateStandardReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/9/13 17:23 <br/>
 * Version: 1.0 <br/>
 */
public class UpdateStandardReq {

    private String confName;

    private String repairChargeStandard;

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

    public String getRepairChargeStandard() {
        return repairChargeStandard;
    }

    public void setRepairChargeStandard(String repairChargeStandard) {
        this.repairChargeStandard = repairChargeStandard;
    }

    @Override
    public String toString() {
        return "UpdateStandardReq{" +
                "confName='" + confName + '\'' +
                ", repairChargeStandard='" + repairChargeStandard + '\'' +
                ", estateId='" + estateId + '\'' +
                '}';
    }
}
