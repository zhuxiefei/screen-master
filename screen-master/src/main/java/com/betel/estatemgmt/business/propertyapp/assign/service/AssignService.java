package com.betel.estatemgmt.business.propertyapp.assign.service;


import com.betel.estatemgmt.business.propertyapp.assign.model.AssignRepairReq;
import com.betel.estatemgmt.business.propertyapp.assign.model.FindRepairListResp;
import com.betel.estatemgmt.business.web.repair.model.AddOrderReq;
import com.betel.estatemgmt.business.web.repair.model.UploadOrderPicReq;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.model.system.Picture;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * AssignService层
 * </p>
 * ClassName: AssignService <br/>
 * Author: cuixx  <br/>
 * Date: 2017/12/22 13:53 <br/>
 * Version: 1.0 <br/>
 */
public interface AssignService {

    List<FindRepairListResp> findUnassignList(Paging<FindRepairListResp> page,String estateId);

    List<FindRepairListResp> findAssignedList(Paging<FindRepairListResp> page,String estateId);

    Response findRepairInfo(String orderNo,String estateId);

    void updateOperatorAndInsertRecord(
            AssignRepairReq assignRepairReq, JSONObject json, String assigner,HttpServletRequest request) throws Exception;

    String cancelRepair(String orderNo, JSONObject json, String userId,HttpServletRequest request) throws Exception;

    void addRepairOrder(AddOrderReq addOrderReq, JSONObject json, String assigner,HttpServletRequest request)throws Exception;

    List<FindRepairListResp> findRepairListByStatus(Paging<FindRepairListResp> page, String status, String userId,String estateId);

    String deleteRepair(String orderNo, JSONObject json, String userId)throws Exception;

    void uploadPic(Picture picture, UploadOrderPicReq picReq, JSONObject json, String userId);
}
