package com.betel.estatemgmt.common;

import com.betel.estatemgmt.utils.StringUtil;
import com.google.gson.Gson;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * <p>
 * httpClient拦截器
 * </p>
 * ClassName: AppPermissionsInterceptor <br/>
 * Author: Cui.xx  <br/>
 * Date: 2017/5/16 17:39 <br/>
 * Version: 1.0 <br/>
 */
public class HttpClientPermissionsInterceptor implements HandlerInterceptor {

    public HttpClientPermissionsInterceptor() {
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
            Response response = new Response();
            // 获取token和userId
            String token = httpServletRequest.getHeader("token");
            String userId = httpServletRequest.getHeader("userId");
            // 鉴权
            if (null == userId || null == token || "".equals(userId) || "".equals(token)) {
                return true;
            } else {
                String tokenContent = RedisManager.get(userId);
                if (StringUtil.isBlank(tokenContent)){
                    response.setCode(StatusCode.UNAUTHORIZED);
                    responseOut(response, httpServletResponse);
                    return false;
                }
            }
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
