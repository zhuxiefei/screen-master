package com.betel.estatemgmt.business.propertyapp.startpage.controller;

import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.common.ConfigManager;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.mapper.estate.StartPageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 启动页广告页面
 * </p>
 * ClassName: IndexAdController <br/>
 * Author: Cui.xx  <br/>
 * Date: 2018/5/9 17:07 <br/>
 * Version: 1.0 <br/>
 */
@RestController
@RequestMapping(value = "propertyApp/startPage")
public class StartPageController {

    private static final Logger LOG = LoggerFactory.getLogger(StartPageController.class);

    @Autowired
    StartPageMapper startPageMapper;

    @ResponseBody
    @RequestMapping(value = "v1/findStartPage", method = RequestMethod.GET)
    public Response findStartPage() {
        Response response = new Response();
        if (LOG.isInfoEnabled()) {
            LOG.info("---propertyApp/startPage/v1/findStartPage----------start----");
        }
        List<String> list = startPageMapper.findStartPage();
        List<String> list2 = new ArrayList<>();
        for(String url : list){
            url = ConfigManager.read(ConfigName.FILE_SERVER)+url;
            list2.add(url);
        }
        response.setData(list2);
        if (LOG.isInfoEnabled()) {
            LOG.info("---propertyApp/startPage/v1/findStartPage----------end----" + response);
        }
        return response;
    }
}
