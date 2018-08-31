package com.betel.estatemgmt.business.oa.admin.service.impl;

import com.betel.estatemgmt.business.oa.admin.model.*;
import com.betel.estatemgmt.business.oa.admin.service.AdminSupportService;
import com.betel.estatemgmt.business.web.login.model.AdminAdmin;
import com.betel.estatemgmt.common.mapper.admin.*;
import com.betel.estatemgmt.common.model.admin.*;
import com.betel.estatemgmt.utils.encrypt.AESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: AdminSupportServiceImpl <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/1/30 15:41 <br/>
 * Version: 1.0 <br/>
 */
@Service("AdminSupportService")
@Transactional(rollbackFor = Exception.class)
public class AdminSupportServiceImpl implements AdminSupportService {

    @Autowired
    AdminRoleMapper roleMapper;

    @Autowired
    AdminRolePriRelaMapper rolePriRelaMapper;

    @Autowired
    AdminMapper adminMapper;

    @Autowired
    AdminAccountMapper adminAccountMapper;

    @Autowired
    AdminPrivilegeMapper adminPrivilegeMapper;

    @Autowired
    AdminEstateRelaMapper adminEstateRelaMapper;

    @Override
    public void deleteRole(String roleId) {
        roleMapper.deleteByPrimaryKey(Long.parseLong(roleId));
        rolePriRelaMapper.deteleByRoleId(Long.parseLong(roleId));
    }

    @Override
    public void updateRole(UpdateRoleReq roleReq) {
        AdminRole adminRole = new AdminRole();
        adminRole.setRoleId(Long.parseLong(roleReq.getRoleId()));
        adminRole.setRoleName(roleReq.getRoleName());
        adminRole.setRoleDesc(roleReq.getRoleDesc());
        roleMapper.updateByPrimaryKeySelective(adminRole);
        rolePriRelaMapper.deteleByRoleId(Long.parseLong(roleReq.getRoleId()));
        addRolePrivilege(Long.parseLong(roleReq.getRoleId()),roleReq.getPrivilegeIds());
    }

    @Override
    public void addRole(UpdateRoleReq roleReq) throws Exception{
        AdminRole adminRole = new AdminRole();
        adminRole.setRoleName(roleReq.getRoleName());
        adminRole.setRoleDesc(roleReq.getRoleDesc());
        adminRole.setCreateTime(new Date(System.currentTimeMillis()));
        adminRole.setEstateId(roleReq.getEstateId());
        roleMapper.insertSelective(adminRole);
        addRolePrivilege(adminRole.getRoleId(), roleReq.getPrivilegeIds());
    }

    @Override
    public void deleteAdmin(String adminIds) {
        String[] adminIdArray = adminIds.split(",");
        adminMapper.deleteByAdminIds(adminIdArray);
        adminAccountMapper.deleteAdminAccountByAdminIds(adminIdArray);
    }

    @Override
    public void updateAdmin(UpdateAdminReq updateAdminReq) {
        AdminEstateRela rela = new AdminEstateRela();
        rela.setRoleId(updateAdminReq.getRoleId());
        rela.setEstateId(updateAdminReq.getEstateId());
        rela.setAdminId(updateAdminReq.getAdminId());
        rela.setIsEnableAccount(Integer.parseInt(updateAdminReq.getIsEnableAccount()));
        adminEstateRelaMapper.updateByPrimaryKeySelective(rela);
    }

    @Override
    public void addAdmin(AddAdminReq adminReq) throws Exception{
        Admin admin = new Admin();
        admin.setAdminId(adminReq.getAdminId());
        admin.setIsInit(Integer.parseInt(adminReq.getIsInit()));
        admin.setAdminName(adminReq.getAdminName());
        admin.setCreateTime(new Date(System.currentTimeMillis()));
        admin.setAdminPassword(adminReq.getAdminPassword());
        adminMapper.insertSelective(admin);
        AdminAccount adminAccount = new AdminAccount();
        adminAccount.setAcctName(adminReq.getAcctName());
        adminAccount.setAcctType(Integer.parseInt(adminReq.getAcctType()));
        adminAccount.setAdminId(adminReq.getAdminId());
        adminAccount.setCreateTime(new Date(System.currentTimeMillis()));
        adminAccountMapper.insertSelective(adminAccount);
        AdminEstateRela adminEstateRela = new AdminEstateRela();
        adminEstateRela.setEstateId(adminReq.getEstateId());
        adminEstateRela.setAdminId(adminReq.getAdminId());
        adminEstateRela.setCreateTime(new Date(System.currentTimeMillis()));
        adminEstateRela.setRoleId(adminReq.getRoleId());
        adminEstateRela.setIsEnableAccount(Integer.parseInt(adminReq.getIsEnableAccount()));
        adminEstateRelaMapper.insertSelective(adminEstateRela);
    }

    @Override
    public void updatePwd(AdminAdmin adminAdmin) {
        adminMapper.updateAdminPwd(adminAdmin);
    }

    @Override
    public void resetPwd(AdminAdmin adminAdmin) {
        adminMapper.resetPwdByAdminId(adminAdmin);
    }

    @Override
    public void updateAdminAccount(UpdateAccountReq adminAccount) {
        adminAccountMapper.deleteByPrimaryKey(adminAccount.getOldAcctName());
        AdminAccount account = new AdminAccount();
        account.setAcctName(adminAccount.getNewAcctName());
        account.setAcctType(1);
        account.setAdminId(adminAccount.getAdminId());
        account.setCreateTime(new Date(System.currentTimeMillis()));
        adminAccountMapper.insertSelective(account);
    }

    @Override
    public void deleteAdminEstateRela(String adminIds, HttpServletRequest request) throws Exception{
        String[] adminIdArray = adminIds.split(",");
        adminEstateRelaMapper.deleteByAdminIdsAndEstateId(adminIdArray, AESUtil.decrypt(request.getHeader("estateId")));
    }

    @Override
    public void addAdminEstateRela(AddAdminReq addAdminReq) {
        AdminEstateRela adminEstateRela = new AdminEstateRela();
        adminEstateRela.setEstateId(addAdminReq.getEstateId());
        adminEstateRela.setAdminId(addAdminReq.getAdminId());
        adminEstateRela.setCreateTime(new Date(System.currentTimeMillis()));
        adminEstateRela.setRoleId(addAdminReq.getRoleId());
        adminEstateRela.setIsEnableAccount(Integer.parseInt(addAdminReq.getIsEnableAccount()));
        adminEstateRelaMapper.insertSelective(adminEstateRela);
    }

    private List<AdminRolePriRela> addRolePrivilege(Long roleId, String privilegeIds){
        // 获取父权限集合ID
        String[] privileges = privilegeIds.split(",");
        List<AdminPrivilege> privilegeges = new ArrayList<>();
        //根据权限ids查出权限
        List<AdminPrivilege> privilegesByIds = adminPrivilegeMapper.findfindPrivilegesByIds(privileges);
        privilegeges.addAll(privilegesByIds);
        // 根据父权限查询出所有子权限包括父权限一起返回
        for (AdminPrivilege privilegesById:privilegesByIds) {
            //如果该权限是系统权限或者一级菜单权限，则不查询他的子权限
            if(privilegesById.getParentId()!=0 && privilegesById.getParentId()!=107
                    && privilegesById.getParentId()!=320 && privilegesById.getParentId()!=393){
                List<AdminPrivilege> privilegesByParentId = adminPrivilegeMapper.findPrivilegesByParentId(privilegesById.getPrivilegeId());
                privilegeges.addAll(privilegesByParentId);
            }
            //如果该权限是一级菜单，并且下面没有二级菜单，则查出该权限下面的所有接口权限
            if (privilegesById.getParentId()==107
                    || privilegesById.getParentId()==320 || privilegesById.getParentId()==393) {
                //查询该权限下的二级菜单权限
                List<PrivilegeInfo> menus = new ArrayList<>();
                PrivilegeInfo privilegeInfo = new PrivilegeInfo();
                privilegeInfo.setPrivilegeId(privilegesById.getPrivilegeId());
                menus.add(privilegeInfo);
                List<PrivilegeInfo> childMenu = adminPrivilegeMapper.findChildMenu(menus);
                if (null == childMenu || (null != childMenu && childMenu.size() == 0)) {
                    //查出该权限下的所有接口权限
                    List<AdminPrivilege> privilegesByParentId = adminPrivilegeMapper.findPrivilegesByParentId(privilegesById.getPrivilegeId());
                    privilegeges.addAll(privilegesByParentId);
                }
            }
        }
        // 获取所有权限后入关系表
        List<AdminRolePriRela> adminRolePriRelaList = new ArrayList<>();
        for (AdminPrivilege aa: privilegeges) {
            AdminRolePriRela adminRolePriRela = new AdminRolePriRela();
            adminRolePriRela.setRoleId(roleId);
            adminRolePriRela.setPrivilegeId(aa.getPrivilegeId());
            adminRolePriRela.setCreateTime(new Date());
            adminRolePriRelaList.add(adminRolePriRela);
        }
        //添加通用权限
        AdminRolePriRela adminRolePriRela = new AdminRolePriRela();
        adminRolePriRela.setRoleId(roleId);
        adminRolePriRela.setCreateTime(new Date());
        adminRolePriRela.setPrivilegeId(104L);
        adminRolePriRelaList.add(adminRolePriRela);
        rolePriRelaMapper.insertRolePrivileges(adminRolePriRelaList);
        return adminRolePriRelaList;
    }
}
