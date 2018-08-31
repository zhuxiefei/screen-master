package com.betel.estatemgmt.business.web.login.model;

import com.betel.estatemgmt.business.oa.admin.model.PrivilegeInfo;

import java.util.List;

/**
 * Created by Administrator on 2018/5/8/008.
 */
public class FindSystemsResp {

    private List<PrivilegeInfo> privilegeInfos;

    private String estateId;

    public List<PrivilegeInfo> getPrivilegeInfos() {
        return privilegeInfos;
    }

    public void setPrivilegeInfos(List<PrivilegeInfo> privilegeInfos) {
        this.privilegeInfos = privilegeInfos;
    }

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
    }

    @Override
    public String toString() {
        return "FindSystemsResp{" +
                "privilegeInfos=" + privilegeInfos +
                ", estateId='" + estateId + '\'' +
                '}';
    }
}
