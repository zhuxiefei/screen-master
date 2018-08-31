package com.betel.estatemgmt.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * <p>
 * 登录用户信息
 * </p>
 * ClassName: ActiveUserInfo <br/>
 * Author: Cui.xx  <br/>
 * Date: 2017/5/16 15:24 <br/>
 * Version: 1.0 <br/>
 */
public class ActiveUserInfo {
    /**
     * <p>
     * 获取用户信息id
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2017/5/16 15:24
     *
     * @return
     */
    public static String getActiveUserId() throws Exception{
        Subject subject = SecurityUtils.getSubject();
        ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
        if(activeUser!=null){
            return activeUser.getUserId();
        }else {
            return null;
        }
    }
}
