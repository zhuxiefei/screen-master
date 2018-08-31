package com.betel.estatemgmt.business.userapp.hotline.controller;

import com.betel.estatemgmt.business.userapp.hotline.model.Hotline;
import com.betel.estatemgmt.business.userapp.hotline.service.HotlineService;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 客服电话接口
 * </p>
 * ClassName: HotlineController <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/11/10 16:48 <br/>
 * Version: 1.0 <br/>
 */
@RestController
@RequestMapping("userApp/hotline")
public class
HotlineController {

    private static final Logger LOG = LoggerFactory.getLogger(HotlineController.class);

    @Autowired
    private HotlineService hotlineService;

    /**
     * <p>
     * 查询客服电话
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/10/12 16:18
     * @return response
     *
     * */
    @RequestMapping(value = "v1/findHotlines", method = RequestMethod.GET)
    public Response<List<Hotline>> findHotlines() {
        if (LOG.isInfoEnabled()) {
            LOG.info("========app/hotline/v1/findHotlines start========");
        }
        Response<List<Hotline>> response = new Response<>();
        try {
            //查询
            List<Hotline> list = hotlineService.findHotlines();
            response.setData(list);
        } catch (Exception e) {
            LOG.error("========app/hotline/v1/findHotlines error!=========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========app/hotline/v1/findHotlines end========response" + response);
        }
        return response;
    }
}
