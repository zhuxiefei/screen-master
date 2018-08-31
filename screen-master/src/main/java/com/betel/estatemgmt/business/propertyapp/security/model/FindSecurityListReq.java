package com.betel.estatemgmt.business.propertyapp.security.model;

import com.betel.estatemgmt.common.Page;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: FindSecurityListReq <br/>
 * Author: Cui.xx  <br/>
 * Date: 2018/2/28 15:46 <br/>
 * Version: 1.0 <br/>
 */
public class FindSecurityListReq extends Page{

    private String status;

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
    }

    private String estateId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FindSecurityListReq{");
        sb.append("status='").append(status).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
