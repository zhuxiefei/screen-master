package com.betel.estatemgmt.business.web.patrol.model;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: FindEquipNoReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/1/26 10:33 <br/>
 * Version: 1.0 <br/>
 */
public class FindEquipNoReq {

    private String equipmentNo;

    public String getEquipmentNo() {
        return equipmentNo;
    }

    public void setEquipmentNo(String equipmentNo) {
        this.equipmentNo = equipmentNo;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FindEquipNoReq{");
        sb.append("equipmentNo='").append(equipmentNo).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
