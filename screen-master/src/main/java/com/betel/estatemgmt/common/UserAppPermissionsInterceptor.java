package com.betel.estatemgmt.common;

import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.business.web.login.constant.WebLoginConstant;
import com.betel.estatemgmt.business.web.user.constant.UserConstant;
import com.betel.estatemgmt.utils.HttpClientUtil;
import com.betel.estatemgmt.utils.encrypt.AESUtil;
import com.google.gson.Gson;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * <p>
 * app拦截器
 * </p>
 * ClassName: AppPermissionsInterceptor <br/>
 * Author: Cui.xx  <br/>
 * Date: 2017/5/16 17:39 <br/>
 * Version: 1.0 <br/>
 */
public class UserAppPermissionsInterceptor implements HandlerInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(UserAppPermissionsInterceptor.class);

    public UserAppPermissionsInterceptor() {
    }

    // 拦截的路径
    private String mappingURL;

    public void setMappingURL(String mappingURL) {
        this.mappingURL = mappingURL;
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        // 获取当前请求接口url
        String url = httpServletRequest.getRequestURI();
        if (mappingURL != null && url.contains(mappingURL)) {
            LOG.info("----------进入用户APP拦截器------------");
            Response response = new Response();
            // 获取token和userId
            String token = httpServletRequest.getHeader("token");
            String userId = httpServletRequest.getHeader("userId");
            LOG.info("----------userId------------"+userId);
            LOG.info("----------token------------"+token);
            // 鉴权
            if (null == userId || null == token || "".equals(userId) || "".equals(token)) {
                response.setCode(StatusCode.UNAUTHORIZED);
                responseOut(response, httpServletResponse);
                return false;
            } else {
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("userId",AESUtil.decrypt(userId));
                Response r = HttpClientUtil.post(ConfigManager.read(ConfigName.SMARTRE_PROJECT_URL)+"httpclientSocial/estatemgmt/v1/findUser",jsonParam,null);
                if (null != r){
                    JSONObject user = JSONObject.fromObject(r.getData());
                    if (user == null) {
                        response.setCode(StatusCode.FAILURE);
                        responseOut(response, httpServletResponse);
                        return false;
                    }
                    //判断用户是否被禁止登陆
                    if (user != null && UserConstant.USERSTATUS_NO_LOGIN.equals(((Double)user.get("userStatus")).intValue())) {
                        response.setCode(StatusCode.ACCOUNT_BANLOGIN);
                        responseOut(response, httpServletResponse);
                        return false;
                    }
                    //判断用户是否被删除
                    if (user != null && UserConstant.USERSTATUS_DELETED.equals(((Double)user.get("userStatus")).intValue())) {
                        response.setCode(StatusCode.ACCOUNT_DELETE);
                        responseOut(response, httpServletResponse);
                        return false;
                    }
                }
                //判断登陆是否过期
                String authToken = RedisManager.get(userId);
                if (authToken == null) {
                    response.setCode(StatusCode.TOKEN_EXPIRE);
                    responseOut(response, httpServletResponse);
                    return false;
                }
                if (!token.equals(authToken)) {
                    response.setCode(StatusCode.UNAUTHORIZED);
                    responseOut(response, httpServletResponse);
                    return false;
                }  else {
                    //更新token失效时间
                    String expireTimeStr = ConfigManager.read(WebLoginConstant.TOKEN_EXPIRE_TIME);
                    Long tokenExpireTime = Long.parseLong(expireTimeStr) * 60 * 60;
                    RedisManager.expire(AESUtil.encrypt(userId), tokenExpireTime.intValue());
                }
            }
            LOG.info("----------结束用户APP拦截器------------");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    /**
     * <p>
     * 输入gson
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2017/5/16 18:01
     *
     * @param obj
     * @param response
     * @throws IOException
     */
    private void responseOut(Object obj, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        out.print(gson.toJson(obj));
    }
}
