package com.betel.estatemgmt.shiro;

import com.betel.estatemgmt.business.web.login.model.Menu;
import com.betel.estatemgmt.business.web.login.service.WebLoginService;
import com.betel.estatemgmt.common.mapper.admin.AdminAccountMapper;
import com.betel.estatemgmt.common.mapper.admin.AdminEstateRelaMapper;
import com.betel.estatemgmt.common.mapper.admin.AdminMapper;
import com.betel.estatemgmt.common.mapper.admin.AdminRolePriRelaMapper;
import com.betel.estatemgmt.common.model.admin.Admin;
import com.betel.estatemgmt.common.model.admin.AdminAccount;
import com.betel.estatemgmt.common.model.admin.AdminEstateRela;
import com.betel.estatemgmt.common.model.admin.AdminPrivilege;
import com.betel.estatemgmt.utils.encrypt.AESUtil;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * shiro自定义realm（认证授权）
 * </p>
 * ClassName: CustomRealm <br/>
 * Author: Cui.xx  <br/>
 * Date: 2017/5/10 18:53 <br/>
 * Version: 1.0 <br/>
 */
public class CustomRealm extends AuthorizingRealm {

    private static Logger LOG = Logger.getLogger(CustomRealm.class);

    @Autowired
    private AdminAccountMapper adminAccountMapper;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminRolePriRelaMapper adminRolePriRelaMapper;

    @Autowired
    private WebLoginService webLoginService;

    @Autowired
    private AdminEstateRelaMapper adminEstateRelaMapper;

    @Autowired
    private HttpServletRequest request;

    /**
     * <p>
     * 设置realm名称
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2017/5/15 13:33
     *
     * @param name
     */
    @Override
    public void setName(String name) {
        super.setName(ShiroConstant.REALM_NAME);
    }

    /**
     * <p>
     * 授权
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2017/5/10 18:57
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Subject subject = SecurityUtils.getSubject();
        String tokenContent = (String)subject.getSession().getAttribute("tokenContent");
        LOG.info("======================tokenContent=============="+tokenContent);
        // 从 principals获取主身份信息
        ActiveUser activeUser = (ActiveUser) principals.getPrimaryPrincipal();

        // 鉴权
        if(activeUser != null) {
            // 鉴权
            boolean isExist = webLoginService.checkWebToken(tokenContent,activeUser.getUserId());
            LOG.info("======================isExist=============="+isExist);
            if (null == tokenContent || null == activeUser.getUserId() || !isExist) {
                return null;
            }
        }

        // 根据身份信息获取权限信息 // 从数据库获取到权限数据
        SimpleAuthorizationInfo simpleAuthorizationInfo = null;
        simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        try {
            String estateId = AESUtil.decrypt(request.getHeader("estateId"));
            if(null != activeUser){
                List<AdminPrivilege> permissionList = adminRolePriRelaMapper.findPermissionListByUserId(activeUser.getUserId(),estateId);
                // 单独定一个集合对象
                List<String> permissions = new ArrayList<String>();
                if (permissionList != null) {
                    for (AdminPrivilege privilege : permissionList) { // 将数据库中的权限标签符放入集合
                        permissions.add(privilege.getPrivilegeName());
                    }
                }
                // 将上边查询到授权信息填充到simpleAuthorizationInfo对象中
                simpleAuthorizationInfo.addStringPermissions(permissions);
            }
        }catch (Exception e){
            LOG.error("=======updateTokenExpireTime error=====",e);
        }
        return simpleAuthorizationInfo;
    }

    /**
     * <p>
     * 认证
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2017/5/10 18:56
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 第一步从token中取出账号
        String acctName = (String) token.getPrincipal();
        Admin admin = null;
        //根据账号查询用户信息
        AdminAccount adminAccount = adminAccountMapper.selectByPrimaryKey(acctName);
        if (null != adminAccount) {
            admin = adminMapper.selectByPrimaryKey(adminAccount.getAdminId());
            //如果查询不到user返回null
            if (null == admin) {
                return null;
            }
        } else {
            return null;
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = null;
        try {

            ActiveUser activeUser = new ActiveUser();
            activeUser.setUserId(admin.getAdminId());
            activeUser.setAdminName(admin.getAdminName());
            activeUser.setIsInit(admin.getIsInit());
            AdminEstateRela rela = new AdminEstateRela();
            rela.setEstateId(AESUtil.decrypt(request.getHeader("estateId")));
            rela.setAdminId(admin.getAdminId());
            String roleId = adminEstateRelaMapper.selectByPrimaryKey(rela).getRoleId();
            // 根据roleId查询系统权限
            List<AdminPrivilege> privileges = adminRolePriRelaMapper.findSystemByRoleId(Long.parseLong(roleId));
            List<Menu> menuList = new ArrayList<>();
            if (null != privileges && privileges.size() > 0){
                for (AdminPrivilege privilege:
                     privileges) {
                    Menu menu = new Menu();
                    menu.setMenuId(privilege.getPrivilegeId().toString());
                    menu.setMenuName(privilege.getPrivilegeName());
                    menu.setParentId(privilege.getParentId().toString());
                    menu.setMenuType(privilege.getPrivilegeType().toString());
                    menuList.add(menu);
                    //判断是否有当前系统的权限
                    if (privilege.getPrivilegeName().equals("estatemgmt")){
                        //查询所有一级菜单
                        List<Menu> menus = adminRolePriRelaMapper.findMenuListByRoleIdAndParentId(Long.parseLong(roleId),
                                privilege.getPrivilegeId());
                        if (null != menus && menus.size() > 0){
                            menuList.addAll(menus);
                            for (Menu m:
                                 menus) {
                                m.setRoleId(Long.parseLong(roleId));
                            }
                            //查询所有二级菜单
                            List<Menu> childMenus = adminRolePriRelaMapper.findChildMenu(menus);
                            if (null != childMenus && childMenus.size() > 0){
                                menuList.addAll(childMenus);
                            }
                        }
                    }
                }
                //查询common权限
                Menu common = adminRolePriRelaMapper.findCommon(Long.parseLong(roleId));
                if (null != common){
                    menuList.add(common);
                }
                activeUser.setMenus(menuList);
                if (LOG.isInfoEnabled()){
                    LOG.info("--------------activeUser----------------"+activeUser);
                }
            }
            simpleAuthenticationInfo = new SimpleAuthenticationInfo(activeUser, admin.getAdminPassword(), this.getName());
            if (LOG.isInfoEnabled()){
                LOG.info("--------------activeUser----------------"+activeUser);
            }
        } catch (Exception e) {
            LOG.error("========doGetAuthenticationInfo error=======", e);
        }
        return simpleAuthenticationInfo;
    }
}
