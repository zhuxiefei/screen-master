package com.betel.estatemgmt.business.propertyapp.cleaning.model;

import com.betel.estatemgmt.common.Page;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: FindCleaningListReq <br/>
 * Author: Cui.xx  <br/>
 * Date: 2018/2/28 10:56 <br/>
 * Version: 1.0 <br/>
 */
public class FindCleaningListReq extends Page{

    String areaId;

    String typeId;

    String cycle;

    String keyword;

    String estateId;

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FindCleaningListReq{");
        sb.append("areaId='").append(areaId).append('\'');
        sb.append(", typeId='").append(typeId).append('\'');
        sb.append(", cycle='").append(cycle).append('\'');
        sb.append(", keyword='").append(keyword).append('\'');
        sb.append(", estateId='").append(estateId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
