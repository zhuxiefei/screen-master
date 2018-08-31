package com.betel.estatemgmt.business.web.login.service;


import com.betel.estatemgmt.business.oa.admin.model.PrivilegeInfo;
import com.betel.estatemgmt.business.web.login.model.AdminAdmin;
import com.betel.estatemgmt.business.web.login.model.FindSystemsReq;
import com.betel.estatemgmt.business.web.login.model.ReLoginInfo;
import com.betel.estatemgmt.business.web.login.model.WebLoginReq;
import com.betel.estatemgmt.common.model.admin.Admin;
import com.betel.estatemgmt.common.model.estate.Estate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 登录业务
 * </p>
 * ClassName: WebLoginService <br/>
 * Author: Cui.xx  <br/>
 * Date: 2017/5/15 15:22 <br/>
 * Version: 1.0 <br/>
 */
public interface WebLoginService {
    /**
     * <p>
     * 更新token
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2017/5/15 16:22
     *
     * @param userId
     * @return
     */
    String updateToken(String userId) throws Exception;

    /**
     * <p>
     * 更新用户登录ip 登录时间
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2017/5/15 16:22
     *
     * @param
     * @return
     */
    void updateLoginIpAndLoginTime(String ip, String userId);

    boolean checkWebToken(String tokenContent, String userId);

    /**
     * <p>
     * 根据管理员ID查询登录的关键信息（账号、密码）
     *
     * @param adminId 管理员ID
     * @return 登录信息
     * @author: Du.hx <br/>
     * @date: 2018/1/24 15:33
     */
    ReLoginInfo findAdminLoginInfoById(String adminId);

    int updateAdminPwd(AdminAdmin adminAdmin,HttpServletRequest request) throws Exception;

    Admin findByAdminId(String adminId);

    boolean validateSystemPri(String adminId,String estateId);

    boolean validateBanLogin(String adminId,String estateId);

    String validateAccount(WebLoginReq webLoginReq) throws Exception;

    Admin findByAcctName(String acctName);

    List<Estate> findEstateByAdminId(String adminId);

    List<PrivilegeInfo> findBySystemReq(FindSystemsReq systemsReq);
}
