package com.betel.estatemgmt.business.web.home.controller;

import com.betel.estatemgmt.business.web.home.model.HomePage;
import com.betel.estatemgmt.business.web.home.service.HomeService;
import com.betel.estatemgmt.common.BaseController;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 首页
 * </p>
 * ClassName: HomeController <br/>
 * Author: xiayanxin <br/>
 * Date: 2017/8/1 9:20 <br/>
 * Version: 1.0 <br/>
 */
@RestController
@RequestMapping("web/home")
public class HomeController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    private HomeService homeService;

    /**
     * <p>
     * 查询活动审核列表
     * </p>
     * Author: zhangjian <br/>
     * Date: 2017/6/21 13:44
     *
     * @return response
     */
    @RequestMapping(value = "v1/findHomePage", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<HomePage> findHomePage() {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/audit/v1/findHomePage start========");
        }
        Response<HomePage> response = new Response<HomePage>();
        try {
            HomePage homePage = homeService.findHomePageNew();
            response.setData(homePage);
        } catch (Exception e) {
            LOG.error("========查询待审核数量========" + e);
            response.setCode(StatusCode.FAILURE);
            return response;
        }


        if (LOG.isInfoEnabled()) {
            LOG.info("========web/audit/v1/findHomePage end========response" + response);
        }
        return response;
    }


}
