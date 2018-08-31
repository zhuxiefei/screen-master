package com.betel.estatemgmt.business.web.login.controller;

import com.betel.estatemgmt.business.web.login.code.WebLoginCode;
import com.betel.estatemgmt.business.web.login.constant.WebLoginConstant;
import com.betel.estatemgmt.business.web.login.model.*;
import com.betel.estatemgmt.business.web.login.service.WebLoginService;
import com.betel.estatemgmt.common.RedisManager;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.model.admin.Admin;
import com.betel.estatemgmt.common.model.estate.Estate;
import com.betel.estatemgmt.shiro.ActiveUser;
import com.betel.estatemgmt.utils.IpAddrUtil;
import com.betel.estatemgmt.utils.StringUtil;
import com.betel.estatemgmt.utils.Validate;
import com.betel.estatemgmt.utils.ValidateCode;
import com.betel.estatemgmt.utils.encrypt.AESUtil;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.omg.CORBA.Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 后台管理登录业务处理
 * </p>
 * ClassName: AdminLoginController <br/>
 * Author: Cui.xx  <br/>
 * Date: 2017/5/15 14:09 <br/>
 * Version: 1.0 <br/>
 */
@RequestMapping(value = "/web/admin")
@RestController
public class AdminLoginController {

    private static Logger LOG = Logger.getLogger(AdminLoginController.class);

    @Autowired
    private WebLoginService webLoginService;

    /**
     * <p>
     * 后台登录
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2017/5/15 14:18
     *
     * @param webLoginReq
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/v1/login", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<LoginInfo> login(@RequestBody WebLoginReq webLoginReq, HttpServletRequest request, HttpSession session)
            throws Exception {
        if (LOG.isInfoEnabled()) {
            LOG.info("========/web/admin/v1/login start=========account=" + webLoginReq.getAccount());
        }
        Response<LoginInfo> response = new Response<>();
        //获取验证码
        String validateCode = (String) session.getAttribute("validateCode");
        if (webLoginReq.getValidateCode() == null) {
            response.setCode(WebLoginCode.VALIDATECODE_NULL);
        } else if (validateCode == null) {
            response.setCode(WebLoginCode.VALIDATECODE_INVALID);
        } else if (!validateCode.toLowerCase().equals(webLoginReq.getValidateCode().toLowerCase())) {
            response.setCode(WebLoginCode.VALIDATECODE_WRONG);
        } else {
            try {
                //校验账号密码
                String code = webLoginService.validateAccount(webLoginReq);
                if (!StringUtil.isBlank(code)) {
                    response.setCode(code);
                    return response;
                }
                //查询admin
                Admin admin = webLoginService.findByAcctName(webLoginReq.getAccount());
                //登录成功后 生成一个token，若存在就更新token
                String authctoken = webLoginService.updateToken(admin.getAdminId());

                //更新用户登录ip，登录时间
                String ip = IpAddrUtil.getIpAddress();
                webLoginService.updateLoginIpAndLoginTime(ip, admin.getAdminId());

                //用户基本信息
                LoginInfo adminInfo = new LoginInfo();
                adminInfo.setIsInit(admin.getIsInit());
                adminInfo.setUserId(AESUtil.encrypt(admin.getAdminId()));
                adminInfo.setToken(authctoken);
                response.setData(adminInfo);
            } catch (Exception e) {
                LOG.error("========/web/admin/v1/login error!=========", e);
                response.setCode(StatusCode.FAILURE);
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========/web/admin/v1/login end=========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 查询楼盘列表
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2017/5/15 14:18
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/v1/findEstates", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<List<Estate>> findEstates(HttpServletRequest request)
            throws Exception {
        if (LOG.isInfoEnabled()) {
            LOG.info("========/web/admin/v1/findEstates start=========");
        }
        Response<List<Estate>> response = new Response<>();
        String adminId = AESUtil.decrypt(request.getHeader("userId"));
        response.setData(webLoginService.findEstateByAdminId(adminId));
        if (LOG.isInfoEnabled()) {
            LOG.info("========/web/admin/v1/findEstates end=========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 查询系统权限
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2017/5/15 14:18
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/v1/findSystems", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<FindSystemsResp> findSystems(@RequestBody FindSystemsReq systemsReq, HttpServletRequest request)
            throws Exception {
        if (LOG.isInfoEnabled()) {
            LOG.info("========/web/admin/v1/findSystems start=========");
        }
        Response<FindSystemsResp> response = new Response<>();
        String adminId = AESUtil.decrypt(request.getHeader("userId"));
        //判断该楼盘账号是否启用
        if (!webLoginService.validateBanLogin(adminId, systemsReq.getEstateId())) {
            response.setCode(WebLoginCode.ACCOUNT_BAN_LOGIN);
            return response;
        }
        systemsReq.setAdminId(adminId);
        FindSystemsResp resp = new FindSystemsResp();
        resp.setPrivilegeInfos(webLoginService.findBySystemReq(systemsReq));
        resp.setEstateId(AESUtil.encrypt(systemsReq.getEstateId()));
        response.setData(resp);
        if (LOG.isInfoEnabled()) {
            LOG.info("========/web/admin/v1/findSystems end=========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 获取用户信息
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2017/5/22 10:50
     *
     * @return
     */
    @RequiresPermissions("common")
    @RequestMapping(value = "/v1/findAdminInfo", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<WebLoginResp> findAdminInfo() {
        if (LOG.isInfoEnabled()) {
            LOG.info("========/web/admin/v1/findAdminInfo start==========");
        }
        Response<WebLoginResp> response = new Response<>();
        WebLoginResp webLoginResp = new WebLoginResp();
        Subject subject = SecurityUtils.getSubject();
        ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
        if (activeUser != null) {
            webLoginResp.setMenuList(activeUser.getMenus());
            webLoginResp.setName(activeUser.getAdminName());
            response.setData(webLoginResp);
        } else {
            response.setCode(StatusCode.UNAUTHORIZED);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========/web/admin/v1/findAdminInfo end=========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 图形验证码
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/8/8 13:57
     * return response
     */
    @RequestMapping(value = "/v1/validateCode")
    public void validateCode(HttpServletResponse response, HttpServletRequest request) throws IOException {
        response.setHeader("Cache-Control", "no-cache");
        // 第二个参数用来指定验证码类型，第三个用来指定生成验证码的长度，其他见方法里的注释
        String verifyCode = ValidateCode.generateTextCode(request, ValidateCode.TYPE_ALL_MIXED, 4, null);
        request.getSession().setAttribute("validateCode", verifyCode);
        response.setContentType("image/jpeg");
        BufferedImage bim = ValidateCode.generateImageCode(verifyCode, 90, 30, 3, true, Color.WHITE, Color.BLACK, null);
        ImageIO.write(bim, "JPEG", response.getOutputStream());
    }

    /**
     * <p>
     * 退出登录
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/8/7 17:39
     * return response
     */
    @RequestMapping(value = "/v1/outOfLogin")
    public Response<Object> outoOfLogin() {
        if (LOG.isInfoEnabled()) {
            LOG.info("========/web/admin/v1/outOfLogin start==========");
        }
        Response<Object> response = new Response<Object>();
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            subject.logout(); // session 会销毁，在SessionListener监听session销毁，清理权限缓存
            if (LOG.isDebugEnabled()) {
                LOG.debug("-------------用户退出登录-------------");
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========/web/admin/v1/outOfLogin end=========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 二次登录
     *
     * @param webReLoginReq 二次登录入参
     * @return 登录信息
     * @author: Du.hx <br/>
     * @date: 2018/1/24 16:45
     */
    @RequestMapping(value = "/v1/reLogin", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<LoginInfo> reLogin(@RequestBody @Valid WebReLoginReq webReLoginReq,
                                       BindingResult bindingResult,
                                       HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========/web/admin/v1/reLogin start=========webReLoginReq=" + webReLoginReq);
        }
        Response<LoginInfo> response = new Response<>();

        //校验入参
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            response.setCode(errorList.get(0).getDefaultMessage());
            if (LOG.isInfoEnabled()) {
                LOG.info("========/web/admin/v1/reLogin parm error=========, response = " + response);
            }
            return response;
        }

        //将入参的adminId和token与缓存中对比，对比通过则登陆成功
        String token = RedisManager.get(webReLoginReq.getAdminId());
        if (StringUtil.isBlank(token) || (!StringUtil.isBlank(token) && !token.equals(webReLoginReq.getTokenContent()))) {
            response.setCode(StatusCode.TOKEN_EXPIRE);
            if (LOG.isInfoEnabled()) {
                LOG.info("========/web/admin/v1/reLogin error=========, response = " + response);
            }
            return response;
        }
        try {
            //为了沿用原有登陆代码，减小工作量，此处从服务器端通过adminId将账号和密码取出，模拟之前的登录方式
            //查询账号和密码
            ReLoginInfo reLoginInfo = webLoginService.findAdminLoginInfoById(AESUtil.decrypt(webReLoginReq.getAdminId()));
            if (null == reLoginInfo) {
                response.setCode(StatusCode.ACCOUNT_DELETE);
                if (LOG.isInfoEnabled()) {
                    LOG.info("========/web/admin/v1/reLogin error=========, response = " + response);
                }
                return response;
            }
            //将密码解密
            String password = AESUtil.decrypt(reLoginInfo.getAdminPassword());
            reLoginInfo.setAdminPassword(password);

            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken userToken;
            userToken = new UsernamePasswordToken(reLoginInfo.getAcctName(), reLoginInfo.getAdminPassword());
            //登录进shiro realm
            subject.login(userToken);
            ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
            if (subject.isAuthenticated()) {

                //判断是否有该系统的权限
                if (!webLoginService.validateSystemPri(activeUser.getUserId(), AESUtil.decrypt(request.getHeader("estateId")))) {
                    response.setCode(WebLoginCode.SYSTEM_PRIVILEGE);
                    return response;
                }

                subject.getSession().setAttribute("tokenContent", webReLoginReq.getTokenContent());

                //更新用户登录ip，登录时间
                String ip = IpAddrUtil.getIpAddress();
                webLoginService.updateLoginIpAndLoginTime(ip, activeUser.getUserId());

                //用户基本信息
                LoginInfo adminInfo = new LoginInfo();
                adminInfo.setIsInit(activeUser.getIsInit());
                adminInfo.setUserId(AESUtil.encrypt(activeUser.getUserId()));
                adminInfo.setToken(webReLoginReq.getTokenContent());
                response.setData(adminInfo);
            } else {
                response.setCode(WebLoginCode.ACCOUNT_PASSWORD_ERROR);
            }
        } catch (Exception e) {
            LOG.error("========/web/admin/v1/reLogin error========= e=", e);
            response.setCode(WebLoginCode.ACCOUNT_PASSWORD_ERROR);
        }

        if (LOG.isInfoEnabled()) {
            LOG.info("========/web/admin/v1/reLogin end=========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 校验修改密码
     * </p>
     * Author: Zhang Li <br/>
     * Date: 2017/5/18 14:38
     *
     * @param adminPwd
     * @return
     */
    private String checkUpdatePwd(AdminPwd adminPwd,String userId) throws Exception{
        String code = "";
        //原密码，新密码是否为空
        if (!StringUtil.isEmpty(adminPwd.getNewPwd()) && !StringUtil.isEmpty(adminPwd.getOldPwd())) {
            //根据id查询管理员
            Admin admin = webLoginService.findByAdminId(userId);
            // 获取数据库的原密码
            String oldPwdFromSql = AESUtil.decrypt(admin.getAdminPassword());
            // 输入原密码是否正确
            if (oldPwdFromSql.equals(adminPwd.getOldPwd())) {
                // 校验新密码格式
                boolean isPassword = Validate.isPassword(adminPwd.getNewPwd());
                if (!isPassword) {  // 密码格式错误
                    code = WebLoginCode.PASSWORD_FORMAT_ERROR;
                    return code;
                }
            } else {              // 原密码错误
                code = WebLoginCode.OLD_PWD_ERROR;
                return code;
            }
        } else {
            // 密码为空
            code = WebLoginCode.PASSWORD_NULL;
            return code;
        }
        return code;
    }

    /**
     * <p>
     * 修改管理员密码
     * </p>
     * Author: Zhang Li <br/>
     * Date: 2017/5/18 16:55
     *
     * @param adminPwd
     * @return
     */
    @RequestMapping(value = "v1/updatePassword", method = RequestMethod.POST, consumes = "application/json")
    public Response<Object> updatePassword(@RequestBody AdminPwd adminPwd, HttpServletRequest request) {
        LOG.info("========web/admin/v1/updatePassword start========updatePassword:=" + adminPwd.getNewPwd());
        Response<Object> response = new Response<Object>();
        try {
            String userId = AESUtil.decrypt(request.getHeader("userId"));
            String code = checkUpdatePwd(adminPwd,userId);
            if (StringUtil.isEmpty(code)) {
                //获得已登录管理员id
                String encryptNewPwd = AESUtil.encrypt(adminPwd.getNewPwd());
                //为AdminAdmin赋值
                AdminAdmin adminAdmin = new AdminAdmin();
                adminAdmin.setAdminId(userId);
                adminAdmin.setAdminPassword(encryptNewPwd);
                adminAdmin.setIsInit(WebLoginConstant.PWD_NO_NEED_CHANGED);
                //修改密码
                webLoginService.updateAdminPwd(adminAdmin, request);
            } else {
                response.setCode(code);
            }
        } catch (Exception e) {
            response.setCode(StatusCode.FAILURE);
            LOG.error("=========web/admin/v1/updatePassword  error!=========", e);
        }

        LOG.info("=========web/admin/v1/updatePassword  end!=========");
        return response;
    }
}
