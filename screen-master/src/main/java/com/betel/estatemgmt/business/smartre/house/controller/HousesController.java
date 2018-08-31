package com.betel.estatemgmt.business.smartre.house.controller;

import com.betel.estatemgmt.business.smartre.house.model.HouseResp;
import com.betel.estatemgmt.business.userapp.house.model.AuthHouse;
import com.betel.estatemgmt.business.userapp.house.service.AppAuthService;
import com.betel.estatemgmt.business.web.house.code.HouseCode;
import com.betel.estatemgmt.business.web.house.model.HouseIdsReq;
import com.betel.estatemgmt.business.web.house.service.HouseService;
import com.betel.estatemgmt.common.BaseController;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.utils.Quantity;
import com.betel.estatemgmt.utils.StringUtil;
import com.betel.estatemgmt.utils.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 房屋
 * </p>
 * ClassName: HouseController <br/>
 * Author: jians.z  <br/>
 * Date: 2018/1/31 11:19 <br/>
 * Version: 1.0 <br/>
 */
@RestController
@RequestMapping("httpclientProperty/house")
public class HousesController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(HousesController.class);
    @Autowired
    AppAuthService appAuthService;
    @Autowired
    private HouseService houseService;

    /**
     * <p>
     * 根据用户id查询认证的房屋
     * </p>
     * Author: jians.z <br/>
     * Date: 2018/1/31 14:41
     *
     * @param houseIdsReq 房屋编号入参
     * @return response
     */
    @RequestMapping(value = "v1/findHouseAuth", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<List<HouseResp>> findHouseAuth(@RequestBody HouseIdsReq houseIdsReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========app/house/v1/findHouseAuth start========houseIdsReq=" + houseIdsReq);
        }
        Response<List<HouseResp>> response = new Response<>();
        //判断编号是否为空
        try {
            if (!StringUtil.isBlank(houseIdsReq.getUserId())) {
                List<AuthHouse> houseList = appAuthService.findHouseAuth(houseIdsReq.getUserId(), Quantity.TWO);
                List<HouseResp> houseResps = new ArrayList<>();
                for (int i = 0; i < houseList.size(); i++) {
                    HouseResp houseResp = new HouseResp();
                    houseResp.setHouseId(houseList.get(i).getHouseId());
                    houseResp.setHouseNum(houseList.get(i).getHouseName());
                    houseResp.setAuthType(houseList.get(i).getAuthType());
                    houseResps.add(houseResp);
                }
                response.setData(houseResps);
            }
        } catch (Exception e) {
            LOG.error("========app/house/v1/findHouseByUserId error========error", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========app/house/v1/findHouseAuth end========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 根据用户id删除房屋接口
     * </p>
     * Author: xiayanxin <br/>
     * Date: 2017/6/21 14:41
     *
     * @param houseIdsReq 房屋编号入参
     * @return response
     */
    @RequestMapping(value = "v1/deleteOwnerMemberByUserId", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> deleteOwnerMemberByUserId(@RequestBody HouseIdsReq houseIdsReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/house/v1/deleteOwnerMemberByUserId start========houseIdsReq=" + houseIdsReq);
        }
        Response<String> response = new Response<>();
        try {
            String[] userIds = Tool.getIdArrOfStringType(houseIdsReq.getUserIds());
            houseService.deleteOwnerMemberByUserId(userIds, request);
        } catch (Exception e) {
            LOG.error("========web/house/v1/deleteOwnerMemberByUserId error========error", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/house/v1/deleteOwnerMemberByUserId end========response=" + response);
        }
        return response;
    }
}
