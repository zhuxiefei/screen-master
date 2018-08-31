package com.betel.estatemgmt.common;

import com.betel.estatemgmt.shiro.ActiveUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * <p>
 * 已登录管理员
 * </p>
 * ClassName: AdminService <br/>
 * Author: Zhang Li <br/>
 * Date: 2017/5/18 18:21 <br/>
 * Version: 1.0 <br/>
 */
public class ActiveAdminInfo {
    /**
     * 获得当前登陆的管理员信息
     * Author:Zhang Li
     * Date: 2017/5/17 18:39
     *
     * @return
     */
    public static ActiveUser getActiveAdmin() {
        Subject subject = SecurityUtils.getSubject();
        ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
        return activeUser;
    }
}
