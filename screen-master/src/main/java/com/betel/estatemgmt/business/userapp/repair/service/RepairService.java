package com.betel.estatemgmt.business.userapp.repair.service;

import com.betel.estatemgmt.business.userapp.repair.model.AppRepairOrder;
import com.betel.estatemgmt.business.userapp.repair.model.CancelRepairReq;
import com.betel.estatemgmt.business.userapp.repair.model.RepairPage;
import com.betel.estatemgmt.common.model.estate.Estate;
import com.betel.estatemgmt.common.model.system.Picture;
import com.betel.estatemgmt.common.model.repair.RepairOrderReply;
import com.betel.estatemgmt.common.model.repair.RepairOrderType;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 我的家维修service层
 * </p>
 * ClassName: RepairService <br/>
 * Author: zhouye  <br/>
 * Date: 2017/9/13 17:17 <br/>
 * Version: 1.0 <br/>
 */
public interface RepairService {

    void addPicture(List<Picture> pictures);

    List<RepairOrderType> findAllRepairOrderType(String typeType,Integer estateType);

    String addRepair(AppRepairOrder appRepairOrder, HttpServletRequest request) throws Exception;

    String findFeeScale(String estateId);

    List<AppRepairOrder> findAllRepair(RepairPage repairPage);

    AppRepairOrder findRepair(String orderNo);

    String addRepairReply(RepairOrderReply repairOrderReply);

    String urgeRepair(String orderNo,HttpServletRequest request) throws Exception;

    /**
     * 查询待维修数量
     *
     * @return
     */
    Integer countWaitRepair(String estateId);
    /**
     * 查询待处理
     *
     * @return
     */
    Integer countPendingRepair(String userId,String estateId);

    String cancelRepair(CancelRepairReq repairReq,HttpServletRequest request) throws Exception;

    String deleteRepair(String orderNo);

    Estate findByEstateId(String estateId);
}
