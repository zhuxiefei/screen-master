package com.betel.estatemgmt.business.oa.admin.controller;

import com.betel.estatemgmt.business.oa.admin.model.*;
import com.betel.estatemgmt.business.oa.admin.service.AdminSupportService;
import com.betel.estatemgmt.business.web.login.model.AdminAdmin;
import com.betel.estatemgmt.common.BaseController;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.model.admin.Admin;
import com.betel.estatemgmt.common.model.admin.AdminAccount;
import com.betel.estatemgmt.utils.encrypt.AESUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: AdminSupportController <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/1/30 15:42 <br/>
 * Version: 1.0 <br/>
 */
@RestController
@RequestMapping("httpclientProperty/admin")
public class AdminSupportController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(AdminSupportController.class);

    @Autowired
    AdminSupportService adminSupportService;

    /**
     * <p>
     * 删除角色
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/12/5 9:06
     *
     * @param roleReq
     * @return
     */
    @RequestMapping(value = "v1/deleteRole", method = RequestMethod.POST, consumes = "application/json")
    public Response<String> deleteRole(@RequestBody DeleteRoleReq roleReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------oa/admin/v1/deleteRole------start-" + roleReq);
        }
        Response<String> response = new Response<>();
        try {
            adminSupportService.deleteRole(roleReq.getRoleId());
        } catch (Exception e) {
            LOG.error("----oa/admin/v1/deleteRole----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------oa/admin/v1/deleteRole------end-" + response);
        }
        return response;
    }

    /**
     * <p>
     * 更新角色
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/12/5 9:06
     *
     * @param roleReq
     * @return
     */
    @RequestMapping(value = "v1/updateRole", method = RequestMethod.POST, consumes = "application/json")
    public Response<String> updateRole(@RequestBody UpdateRoleReq roleReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------oa/admin/v1/updateRole------start-" + roleReq);
        }
        Response<String> response = new Response<>();
        try {
            adminSupportService.updateRole(roleReq);
        } catch (Exception e) {
            LOG.error("----oa/admin/v1/updateRole----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------oa/admin/v1/updateRole------end-" + response);
        }
        return response;
    }

    /**
     * <p>
     * 新增角色
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/12/5 9:06
     *
     * @param roleReq
     * @return
     */
    @RequestMapping(value = "v1/addRole", method = RequestMethod.POST, consumes = "application/json")
    public Response<String> addRole(@RequestBody UpdateRoleReq roleReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------oa/admin/v1/addRole------start-" + roleReq);
        }
        Response<String> response = new Response<>();
        try {
            roleReq.setEstateId(AESUtil.decrypt(request.getHeader("estateId")));
            adminSupportService.addRole(roleReq);
        } catch (Exception e) {
            LOG.error("----oa/admin/v1/addRole----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------oa/admin/v1/addRole------end-" + response);
        }
        return response;
    }

    /**
     * <p>
     * 删除管理员
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/12/5 9:06
     *
     * @param adminReq
     * @return
     */
    @RequestMapping(value = "v1/deleteAdmin", method = RequestMethod.POST, consumes = "application/json")
    public Response<String> deleteAdmin(@RequestBody DeleteAdminReq adminReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------oa/admin/v1/deleteAdmin------start-" + adminReq);
        }
        Response<String> response = new Response<>();
        try {
            adminSupportService.deleteAdmin(adminReq.getAdminIds());
        } catch (Exception e) {
            LOG.error("----oa/admin/v1/deleteAdmin----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------oa/admin/v1/deleteAdmin------end-" + response);
        }
        return response;
    }

    /**
     * <p>
     * 更新管理员
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/12/5 9:06
     *
     * @param updateAdminReq
     * @return
     */
    @RequestMapping(value = "v1/updateAdmin", method = RequestMethod.POST, consumes = "application/json")
    public Response<String> updateAdmin(@RequestBody UpdateAdminReq updateAdminReq,HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------oa/admin/v1/updateAdmin------start-" + updateAdminReq);
        }
        Response<String> response = new Response<>();
        try {
            updateAdminReq.setEstateId(AESUtil.decrypt(request.getHeader("estateId")));
            adminSupportService.updateAdmin(updateAdminReq);
        } catch (Exception e) {
            LOG.error("----oa/admin/v1/updateAdmin----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------oa/admin/v1/updateAdmin------end-" + response);
        }
        return response;
    }

    /**
     * <p>
     * 新增管理员
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/12/5 9:06
     *
     * @param adminReq
     * @return
     */
    @RequestMapping(value = "v1/addAdmin", method = RequestMethod.POST, consumes = "application/json")
    public Response<String> addAdmin(@RequestBody AddAdminReq adminReq,HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------oa/admin/v1/addAdmin------start-" + adminReq);
        }
        Response<String> response = new Response<>();
        try {
            adminReq.setEstateId(AESUtil.decrypt(request.getHeader("estateId")));
            adminSupportService.addAdmin(adminReq);
        } catch (Exception e) {
            LOG.error("----oa/admin/v1/addAdmin----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------oa/admin/v1/addAdmin------end-" + response);
        }
        return response;
    }

    /**
     * <p>
     * 修改管理员密码
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/12/5 9:06
     *
     * @param adminReq
     * @return
     */
    @RequestMapping(value = "v1/updatePwd", method = RequestMethod.POST, consumes = "application/json")
    public Response<String> updatePwd(@RequestBody AdminAdmin adminReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------oa/admin/v1/updatePwd------start-" + adminReq);
        }
        Response<String> response = new Response<>();
        try {
            adminSupportService.updatePwd(adminReq);
        } catch (Exception e) {
            LOG.error("----oa/admin/v1/updatePwd----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------oa/admin/v1/updatePwd------end-" + response);
        }
        return response;
    }

    /**
     * <p>
     * 重置管理员密码
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/12/5 9:06
     *
     * @param adminReq
     * @return
     */
    @RequestMapping(value = "v1/resetPwd", method = RequestMethod.POST, consumes = "application/json")
    public Response<String> resetPwd(@RequestBody AdminAdmin adminReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------oa/admin/v1/resetPwd------start-" + adminReq);
        }
        Response<String> response = new Response<>();
        try {
            adminSupportService.resetPwd(adminReq);
        } catch (Exception e) {
            LOG.error("----oa/admin/v1/resetPwd----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------oa/admin/v1/resetPwd------end-" + response);
        }
        return response;
    }

    /**
     * <p>
     * 更新管理员账号
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/12/5 9:06
     *
     * @param adminAccount
     * @return
     */
    @RequestMapping(value = "v1/updateAdminAccount", method = RequestMethod.POST, consumes = "application/json")
    public Response<String> updateAdminAccount(@RequestBody UpdateAccountReq adminAccount) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------oa/admin/v1/updateAdminAccount------start-" + adminAccount);
        }
        Response<String> response = new Response<>();
        try {
            adminSupportService.updateAdminAccount(adminAccount);
        } catch (Exception e) {
            LOG.error("----oa/admin/v1/updateAdminAccount----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------oa/admin/v1/updateAdminAccount------end-" + response);
        }
        return response;
    }

    /**
     * <p>
     * 删除管理员账号关系
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/12/5 9:06
     *
     * @param adminReq
     * @return
     */
    @RequestMapping(value = "v1/deleteAdminEstateRela", method = RequestMethod.POST, consumes = "application/json")
    public Response<String> deleteAdminEstateRela(@RequestBody DeleteAdminReq adminReq,HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------oa/admin/v1/deleteAdminEstateRela------start-" + adminReq);
        }
        Response<String> response = new Response<>();
        try {
            adminSupportService.deleteAdminEstateRela(adminReq.getAdminIds(),request);
        } catch (Exception e) {
            LOG.error("----oa/admin/v1/deleteAdminEstateRela----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------oa/admin/v1/deleteAdminEstateRela------end-" + response);
        }
        return response;
    }

    /**
     * <p>
     * 添加管理员楼盘关系
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/12/5 9:06
     *
     * @return
     */
    @RequestMapping(value = "v1/addAdminEstateRela", method = RequestMethod.POST, consumes = "application/json")
    public Response<String> addAdminEstateRela(@RequestBody AddAdminReq adminReq,HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------oa/admin/v1/addAdminEstateRela------start-" + adminReq);
        }
        Response<String> response = new Response<>();
        try {
            adminReq.setEstateId(AESUtil.decrypt(request.getHeader("estateId")));
            adminSupportService.addAdminEstateRela(adminReq);
        } catch (Exception e) {
            LOG.error("----oa/admin/v1/addAdminEstateRela----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------oa/admin/v1/addAdminEstateRela------end-" + response);
        }
        return response;
    }
}
