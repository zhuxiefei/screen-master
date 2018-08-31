package com.betel.estatemgmt.business.web.login.service.impl;

import com.betel.estatemgmt.business.oa.admin.model.PrivilegeInfo;
import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.business.web.login.code.WebLoginCode;
import com.betel.estatemgmt.business.web.login.constant.WebLoginConstant;
import com.betel.estatemgmt.business.web.login.model.AdminAdmin;
import com.betel.estatemgmt.business.web.login.model.FindSystemsReq;
import com.betel.estatemgmt.business.web.login.model.ReLoginInfo;
import com.betel.estatemgmt.business.web.login.model.WebLoginReq;
import com.betel.estatemgmt.business.web.login.service.WebLoginService;
import com.betel.estatemgmt.common.ConfigManager;
import com.betel.estatemgmt.common.RedisManager;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.mapper.admin.*;
import com.betel.estatemgmt.common.mapper.estate.EstateMapper;
import com.betel.estatemgmt.common.model.admin.Admin;
import com.betel.estatemgmt.common.model.admin.AdminAccount;
import com.betel.estatemgmt.common.model.admin.AdminEstateRela;
import com.betel.estatemgmt.common.model.admin.AdminPrivilege;
import com.betel.estatemgmt.common.model.estate.Estate;
import com.betel.estatemgmt.utils.HttpClientUtil;
import com.betel.estatemgmt.utils.StringUtil;
import com.betel.estatemgmt.utils.encrypt.AESUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 登录业务实现类
 * </p>
 * ClassName: WebLoginServiceImpl <br/>
 * Author: Cui.xx  <br/>
 * Date: 2017/5/15 15:23 <br/>
 * Version: 1.0 <br/>
 */
@Service("WebLoginService")
@Transactional
public class WebLoginServiceImpl implements WebLoginService {

    private static final Logger LOG = LoggerFactory.getLogger(WebLoginServiceImpl.class);

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    AdminRolePriRelaMapper rolePriRelaMapper;

    @Autowired
    AdminAccountMapper adminAccountMapper;

    @Autowired
    AdminEstateRelaMapper adminEstateRelaMapper;

    @Autowired
    AdminPrivilegeMapper adminPrivilegeMapper;

    @Override
    public String updateToken(String userId) throws Exception {
        Date date = new Date();
        // 生成token内容
        String authToken = UUID.randomUUID() + "-" + date.getTime();
        // 读取配置库中token失效时间（小时）
        String expireTimeStr = ConfigManager.read(WebLoginConstant.TOKEN_EXPIRE_TIME);
        // 失效时间设为当前时间加上配置库时间
        Long tokenExpireTime = Long.parseLong(expireTimeStr) * 60 * 60;

        //去redis查询该用户是否有token信息
        String tokenContent = RedisManager.get(AESUtil.encrypt(userId));
        if (StringUtil.isBlank(tokenContent)) {
            RedisManager.add(AESUtil.encrypt(userId), authToken, tokenExpireTime.intValue());
        } else {
            //先删除，再添加
            RedisManager.delete(AESUtil.encrypt(userId));
            RedisManager.add(AESUtil.encrypt(userId), authToken, tokenExpireTime.intValue());
        }
        return authToken;
    }

    @Override
    public void updateLoginIpAndLoginTime(String ip, String userId) {
        Admin admin = new Admin();
        admin.setAdminId(userId);
        admin.setLastLoginTime(new Date());
        admin.setLastLoginIp(ip);
        adminMapper.updateByPrimaryKeySelective(admin);
    }

    @Override
    public boolean checkWebToken(String tokenContent, String userId) {
        // 查询token是否存在
        String token = null;
        try {
            LOG.info("-------------userId------------"+AESUtil.encrypt(userId));
            token = RedisManager.get(AESUtil.encrypt(userId));
            LOG.info("-------------token------------"+token);
        } catch (Exception e) {
            LOG.error("------------token null----------", e);
        }
        if (StringUtil.isBlank(token) || (!StringUtil.isBlank(token) && !token.equals(tokenContent))) {
            return false;
        }
        return true;
    }

    @Override
    public ReLoginInfo findAdminLoginInfoById(String adminId) {
        return adminMapper.findAdminLoginInfoById(adminId);
    }

    @Override
    public int updateAdminPwd(AdminAdmin adminAdmin,HttpServletRequest request) throws Exception{
        //同步oa
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("adminId",adminAdmin.getAdminId());
        jsonParam.put("adminPassword",adminAdmin.getAdminPassword());
        jsonParam.put("isInit",WebLoginConstant.PWD_NO_NEED_CHANGED.toString());
        String url = ConfigManager.read(ConfigName.OA_PROJECT_URL) + "httpclientOA/admin/v1/updatePwd";
        HttpClientUtil.post(url, jsonParam, request);
        //同步smartre
        JSONObject jsonParam1 = new JSONObject();
        jsonParam1.put("adminId",adminAdmin.getAdminId());
        jsonParam1.put("adminPassword",adminAdmin.getAdminPassword());
        jsonParam1.put("isInit",WebLoginConstant.PWD_NO_NEED_CHANGED.toString());
        String url1 = ConfigManager.read(ConfigName.SMARTRE_PROJECT_URL) + "httpclientSocial/admin/v1/updatePwd";
        HttpClientUtil.post(url1, jsonParam1, request);
        //同步home
        JSONObject jsonParam2 = new JSONObject();
        jsonParam2.put("adminId",adminAdmin.getAdminId());
        jsonParam2.put("adminPassword",adminAdmin.getAdminPassword());
        jsonParam2.put("isInit",WebLoginConstant.PWD_NO_NEED_CHANGED.toString());
        String url2 = ConfigManager.read(ConfigName.HOME_PROJECT_URL) + "httpclientHome/admin/v1/updatePwd";
        HttpClientUtil.post(url2, jsonParam2, request);
        return adminMapper.updateAdminPwd(adminAdmin);
    }

    @Override
    public Admin findByAdminId(String adminId) {
        return adminMapper.findByAdminId(adminId);
    }

    @Override
    public boolean validateSystemPri(String adminId,String estateId) {
        boolean flag = false;
        Admin admin = adminMapper.findByAdminId(adminId);
        if (null != admin){
            AdminEstateRela adminEstateRela = new AdminEstateRela();
            adminEstateRela.setEstateId(estateId);
            adminEstateRela.setAdminId(adminId);
            String roleId = adminEstateRelaMapper.selectByPrimaryKey(adminEstateRela).getRoleId();
            List<AdminPrivilege> systemByRoleId = rolePriRelaMapper.findSystemByRoleId(Long.parseLong(roleId));
            if (null != systemByRoleId && systemByRoleId.size() > 0){
                for (AdminPrivilege pri:
                     systemByRoleId) {
                    if (pri.getPrivilegeName().equals("estatemgmt")){
                        flag = true;
                    }
                }
            }
        }
        return flag;
    }

    @Override
    public boolean validateBanLogin(String adminId,String estateId) {
        AdminEstateRela rela = new AdminEstateRela();
        rela.setAdminId(adminId);
        rela.setEstateId(estateId);
        AdminEstateRela rela1 = adminEstateRelaMapper.selectByPrimaryKey(rela);
        if (rela1 != null && rela1.getIsEnableAccount().equals(WebLoginConstant.ACCOUNT_ENABLE)){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public String validateAccount(WebLoginReq webLoginReq) throws Exception{
        Admin admin = null;
        //根据账号查询用户信息
        AdminAccount adminAccount = adminAccountMapper.selectByAcctName(webLoginReq.getAccount());
        if (null != adminAccount) {
            admin = adminMapper.selectByPrimaryKey(adminAccount.getAdminId());
            //如果查询不到user返回null
            if (null == admin) {
                return WebLoginCode.ACCOUNT_PASSWORD_ERROR;
            }
            if (!AESUtil.decrypt(admin.getAdminPassword()).equals(webLoginReq.getPassword())) {
                return WebLoginCode.ACCOUNT_PASSWORD_ERROR;
            }
        }else {
            return WebLoginCode.ACCOUNT_PASSWORD_ERROR;
        }
        return null;
    }

    @Override
    public Admin findByAcctName(String acctName) {
        return adminMapper.findByAcctName(acctName);
    }

    @Override
    public List<Estate> findEstateByAdminId(String adminId) {
        return adminEstateRelaMapper.findByAdminId(adminId);
    }

    @Override
    public List<PrivilegeInfo> findBySystemReq(FindSystemsReq systemsReq) {
        AdminEstateRela adminEstateRela = new AdminEstateRela();
        adminEstateRela.setAdminId(systemsReq.getAdminId());
        adminEstateRela.setEstateId(systemsReq.getEstateId());
        String roleId = adminEstateRelaMapper.selectByPrimaryKey(adminEstateRela).getRoleId();
        return adminPrivilegeMapper.findSystems(roleId);
    }
}
