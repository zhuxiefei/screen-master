package com.betel.estatemgmt.business.propertyapp.check.model;

/**
 * <p>
 * 查询设备信息接口入参类.
 * </p>
 * ClassName: FindDeviceInfoReq <br/>
 * Author: Cui.xx  <br/>
 * Date: 2017/12/5 13:55 <br/>
 * Version: 1.0 <br/>
 */
public class FindDeviceInfoReq {

    String equipmentId;

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FindDeviceInfoReq{");
        sb.append("equipmentId='").append(equipmentId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
