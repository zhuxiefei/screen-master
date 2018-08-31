package com.betel.estatemgmt.business.web.repair.service;

import com.betel.estatemgmt.business.web.repair.model.*;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.model.estate.Estate;
import com.betel.estatemgmt.common.model.house.House;
import com.betel.estatemgmt.common.model.repair.RepairOrder;
import com.betel.estatemgmt.common.model.system.Picture;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 维修管理接口
 * </p>
 * ClassName: RepairService <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/9/13 16:08 <br/>
 * Version: 1.0 <br/>
 */
public interface RepairService {
    List<OrderInfo> findAllOrders(Paging<OrderInfo> pager, RepairPageReq repairPageReq);

    RepairOrder findByOrderNo(String orderNo);

    OrderPic findPicByOrderNo(String orderNo);

    boolean findByHouseId(String houseId,String estateId);

    void addRepairOrder(AddOrderReq orderReq,HttpServletRequest request) throws Exception;

    List<TypeInfo> findByArea(Integer orderArea,Integer estateType);

    List<HouseInfo> findByIds(String buildingId, String unitId,String estateId);

    OrderDetail findDetailByOrderNo(String orderNo,String estateId);

    void updateOrder(UpdateOrderReq orderReq,HttpServletRequest request) throws Exception;

    void updateCharge(UpdateStandardReq req);

    void uploadPic(Picture pic, UploadOrderPicReq picReq);

    RepairCharge findByConfName(String name,String estateId);

    OrderInfo findInfoByOrderNo(String orderNo,String estateId);

    OrderPic uploadEditPic(Picture picture, UploadEditPicReq picReq);

    JSONObject validateOperatorExist(String operatorId,HttpServletRequest request) throws Exception;

    String cancelOrder(String orderNo, String userId,HttpServletRequest request) throws Exception;

    List<FindAllRecordResp> findAllRecords(Paging<FindAllRecordResp> paging, FindAllRecordReq recordReq);

    Response findExecutors(FindExecutorReq executorReq,HttpServletRequest request) throws Exception;

    Response findDeptList(HttpServletRequest request) throws Exception;

    Response findPositions(DeptReq deptReq,HttpServletRequest request) throws Exception;

    Estate findByEstateId(String estateId);
}
