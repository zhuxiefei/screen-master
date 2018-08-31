package com.betel.estatemgmt.common;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * <p>
 * 父类
 * </p>
 * ClassName: BaseController <br/>
 * Author: Cui.xx  <br/>
 * Date: 2017/5/16 15:57 <br/>
 * Version: 1.0 <br/>
 */
public class BaseController {

    /**
     * 无权限异常处理
     *
     * @param ex
     * @return
     * @author:cxx
     * @Date:2016年10月14日
     */
    @ExceptionHandler(UnauthorizedException.class)
    public Response<String> handleException(UnauthorizedException ex) {
        Response<String> response = new Response<String>();
        response.setCode(StatusCode.UNAUTHORIZED);
        return response;
    }

    @ExceptionHandler(AuthorizationException.class)
    public Response<String> handleException(AuthorizationException ex) {
        Response<String> response = new Response<String>();
        response.setCode(StatusCode.UNAUTHORIZED);
        return response;
    }
}
