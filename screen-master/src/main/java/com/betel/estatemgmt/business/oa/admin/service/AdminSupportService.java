package com.betel.estatemgmt.business.oa.admin.service;

import com.betel.estatemgmt.business.oa.admin.model.AddAdminReq;
import com.betel.estatemgmt.business.oa.admin.model.UpdateAccountReq;
import com.betel.estatemgmt.business.oa.admin.model.UpdateAdminReq;
import com.betel.estatemgmt.business.oa.admin.model.UpdateRoleReq;
import com.betel.estatemgmt.business.web.login.model.AdminAdmin;
import com.betel.estatemgmt.common.model.admin.Admin;
import com.betel.estatemgmt.common.model.admin.AdminAccount;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: AdminSupportService <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/1/30 15:40 <br/>
 * Version: 1.0 <br/>
 */
public interface AdminSupportService {

    void deleteRole(String roleId);

    void updateRole(UpdateRoleReq roleReq);

    void addRole(UpdateRoleReq roleReq) throws Exception;

    void deleteAdmin(String adminIds);

    void updateAdmin(UpdateAdminReq updateAdminReq);

    void addAdmin(AddAdminReq adminReq) throws Exception;

    void updatePwd(AdminAdmin adminAdmin);

    void resetPwd(AdminAdmin adminAdmin);

    void updateAdminAccount(UpdateAccountReq adminAccount);

    void deleteAdminEstateRela(String adminIds, HttpServletRequest request) throws Exception;

    void addAdminEstateRela(AddAdminReq addAdminReq);
}
