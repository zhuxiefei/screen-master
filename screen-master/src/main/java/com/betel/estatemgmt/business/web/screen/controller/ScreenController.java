package com.betel.estatemgmt.business.web.screen.controller;

//import com.betel.estatemgmt.business.web.role.model.PrivilegeInfo;
import com.betel.estatemgmt.business.web.screen.model.FindDataResp;
import com.betel.estatemgmt.business.web.screen.service.ScreenService;
import com.betel.estatemgmt.common.BaseController;
import com.betel.estatemgmt.common.Response;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.betel.estatemgmt.common.StatusCode.FAILURE;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: ScreenController <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/1/9 9:26 <br/>
 * Version: 1.0 <br/>
 */
@RestController
@RequestMapping("screen")
public class ScreenController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(ScreenController.class);

    @Autowired
    private ScreenService screenService;

    /**
     *  <p>
     * 集中网管数据查询
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2018/1/9 13:33
     *
     * @return
     */
    //@RequiresPermissions(value = "screen-findData")
    @RequestMapping(value = "/v1/findData", method = RequestMethod.POST, consumes = "application/json")
    public Response<FindDataResp> findData(HttpServletRequest request){
        if(LOG.isInfoEnabled()){
            LOG.info("======== web/screen/v1/findData start ========");
        }
        Response<FindDataResp> response = new Response();
        try {
            response.setData(screenService.findData(request));
        }catch(Exception e){
            LOG.error("findData error!", e);
            response.setCode(FAILURE);
        }
        if(LOG.isInfoEnabled()){
            LOG.info("======== web/screen/v1/findData end ========response=" + response);
        }
        return response;
    }
}
