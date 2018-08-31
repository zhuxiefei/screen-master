package com.betel.estatemgmt.business.propertyapp.assign.model;

import com.betel.estatemgmt.common.Page;
/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: FindRepairListReq <br/>
 * Author: Cui.xx  <br/>
 * Date: 2017/12/22 13:40 <br/>
 * Version: 1.0 <br/>
 */
public class FindRepairListReq extends Page {

    String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FindRepairListReq{");
        sb.append("type='").append(type).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
