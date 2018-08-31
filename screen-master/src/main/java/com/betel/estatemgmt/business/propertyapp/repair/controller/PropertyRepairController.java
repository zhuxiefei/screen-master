package com.betel.estatemgmt.business.propertyapp.repair.controller;

import com.betel.estatemgmt.business.userapp.repair.controller.RepairController;
import com.betel.estatemgmt.business.userapp.repair.service.RepairService;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.model.estate.Estate;
import com.betel.estatemgmt.common.model.repair.RepairOrderType;
import com.betel.estatemgmt.utils.encrypt.AESUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: PropertyRepairController <br/>
 * Author: Cui.xx  <br/>
 * Date: 2018/2/1 14:50 <br/>
 * Version: 1.0 <br/>
 */
@Controller
@RequestMapping(value = "propertyApp/repair")
public class PropertyRepairController {

    private static final Logger LOG = LoggerFactory.getLogger(PropertyRepairController.class);

    @Autowired
    RepairService repairService;

    @ResponseBody
    @RequestMapping(value = "v1/findOrderType", method = RequestMethod.GET)
    public Response findOrderType(String typeType, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("---propertyApp/repair/v1/findOrderType----------start----");
        }
        Response response = new Response();
        List<RepairOrderType> repairOrderTypes;
        try {
            String estateId = AESUtil.decrypt(request.getHeader("estateId"));
            //查询楼盘标识
            Estate estate = repairService.findByEstateId(estateId);
            //查询全部报修类型
            repairOrderTypes = repairService.findAllRepairOrderType(typeType,Integer.parseInt(estate.getEstateType()));
            if (LOG.isDebugEnabled()) {
                LOG.debug("---repairOrderTypes----" + repairOrderTypes);
            }
            response.setData(repairOrderTypes);
        } catch (Exception e) {
            LOG.error("---propertyApp/repair/v1/findOrderType----------error----", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("---propertyApp/repair/v1/findOrderType----------end----" + response);
        }
        return response;
    }
}
