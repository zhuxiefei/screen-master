package com.betel.estatemgmt.business.web.housestatus.service;

import com.betel.estatemgmt.business.web.housestatus.model.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * Describe this class...
 * </p>
 *
 * @className: HouseStatusService <br/>
 * @author: jian.z  <br/>
 * @date: 2017/11/13 17:31 <br/>
 * @version: 1.0
 */
public interface HouseStatusService {
    /**
     * 查询房屋状态
     *
     * @param houseStatusReq
     * @return
     */
    BuildingInfo findHouseStatus(HouseStatusReq houseStatusReq,String estateId);

    HouseStatusInfo findHouseDetail(HouseStatusReq houseStatusReq);

    List<HouseStatusOwner> findHouseStatusOwner(String houseId);

    List<HouseStatusMember> findHouseStatusMember(String houseId);

    List<HouseStatusTenant> findHouseStatusTenant(HouseStatusReq houseStatusReq);

    /**
     * 添加户主信息
     *
     * @param houseStatusReq
     * @return
     * @throws Exception
     * @author jians.z
     */
    String addHouseStatusOwner(HouseStatusReq houseStatusReq,HttpServletRequest request) throws Exception;

    String addHouseStatusMember(HouseStatusReq houseStatusReq, HttpServletRequest request) throws Exception;

    String addHouseStatusTenant(HouseStatusReq houseStatusReq);

    String deleteHouseStatusTenant(HouseStatusReq houseStatusReq);

    String updateHouseStatus(HouseStatusReq houseStatusReq);
}
