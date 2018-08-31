package com.betel.estatemgmt.business.web.login.constant;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: WebLoginConstant <br/>
 * Author: Cui.xx  <br/>
 * Date: 2017/5/15 16:24 <br/>
 * Version: 1.0 <br/>
 */
public interface WebLoginConstant {
    /**
     * token失效时间
     */
    String TOKEN_EXPIRE_TIME = "token.expire.time";

    /**
     * 密码不需要重新修改
     */
    Integer PWD_NO_NEED_CHANGED = 1;

    /**
     * 账号已启用状态
     */
    Integer ACCOUNT_ENABLE = 1;
}
