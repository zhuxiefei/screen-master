package com.betel.estatemgmt.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * <p>
 * 自定义判断session会话过期
 * </p>
 * ClassName: CustomFormAuthenticationFilter <br/>
 * Author: Cui.xx  <br/>
 * Date: 2017/5/12 9:44 <br/>
 * Version: 1.0 <br/>
 */
public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {


    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = SecurityUtils.getSubject();
        if (null == subject.getSession() ) {
            return false;
        }
        return true;
    }
}
