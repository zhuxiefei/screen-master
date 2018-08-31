package com.betel.estatemgmt.business.oa.admin.model;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: DeleteAdminReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/1/30 18:35 <br/>
 * Version: 1.0 <br/>
 */
public class DeleteAdminReq {

    private String adminIds;

    public String getAdminIds() {
        return adminIds;
    }

    public void setAdminIds(String adminIds) {
        this.adminIds = adminIds;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DeleteAdminReq{");
        sb.append("adminIds='").append(adminIds).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
