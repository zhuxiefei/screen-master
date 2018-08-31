package com.betel.estatemgmt.business.oa.login.controller;

import com.betel.estatemgmt.business.oa.login.model.FindCitiesReq;
import com.betel.estatemgmt.business.oa.login.service.LoginSupportService;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.model.estate.Estate;
import com.betel.estatemgmt.common.model.estate.EstateCity;
import com.betel.estatemgmt.utils.encrypt.AESUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2018/5/7/007.
 */
@RestController
@RequestMapping("httpclientProperty/loginSupport")
public class LoginSupportController {
    private static final Logger LOG = LoggerFactory.getLogger(LoginSupportController.class);

    @Autowired
    LoginSupportService loginSupportService;

    /**
     *  <p>
     * 查询当前登录人拥有楼盘的城市列表
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/12/21 20:30
     *
     * @return
     */
    @RequestMapping(value = "v1/findCities", method = RequestMethod.POST)
    public Response<List<EstateCity>> findCities(@RequestBody FindCitiesReq citiesReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("--------property/task/v1/findCities-------start==citiesReq="+citiesReq);
        }
        Response<List<EstateCity>> response = new Response<>();
        try {
            response.setData(loginSupportService.findByEstateIds(citiesReq.getEstateIds(),citiesReq.getCityName()));
        } catch (Exception e) {
            LOG.error("----property/task/v1/findCities----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("--------property/task/v1/findCities--------end,response=" + response);
        }
        return response;
    }

    /**
     *  <p>
     * 查询当前登录人拥有的楼盘
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/12/21 20:30
     *
     * @return
     */
    @RequestMapping(value = "v1/findEstates", method = RequestMethod.POST)
    public Response<List<Estate>> findEstates(@RequestBody FindCitiesReq citiesReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("--------property/task/v1/findEstates-------start==citiesReq="+citiesReq);
        }
        Response<List<Estate>> response = new Response<>();
        try {
            response.setData(loginSupportService.findEstateByEstateIds(citiesReq.getEstateIds(),citiesReq.getCityId()));
        } catch (Exception e) {
            LOG.error("----property/task/v1/findEstates----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("--------property/task/v1/findEstates--------end,response=" + response);
        }
        return response;
    }
}
