package com.betel.estatemgmt.common.mapper.repair;

import com.betel.estatemgmt.business.propertyapp.assign.model.FindRepairInfoResp;
import com.betel.estatemgmt.business.propertyapp.assign.model.FindRepairListResp;
import com.betel.estatemgmt.business.propertyapp.home.model.HomeReq;
import com.betel.estatemgmt.business.userapp.repair.model.AppRepairOrder;
import com.betel.estatemgmt.business.userapp.repair.model.RepairPage;
import com.betel.estatemgmt.business.web.repair.model.OrderDetail;
import com.betel.estatemgmt.business.web.repair.model.OrderInfo;
import com.betel.estatemgmt.business.web.repair.model.OrderPic;
import com.betel.estatemgmt.business.web.repair.model.RepairPageReq;
import com.betel.estatemgmt.business.web.screen.model.RepairData;
import com.betel.estatemgmt.business.web.task.model.TaskCountReq;
import com.betel.estatemgmt.common.model.repair.RepairOrder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RepairOrderMapper {
    int deleteByPrimaryKey(String orderNo);

    int insert(RepairOrder record);

    int insertSelective(RepairOrder record);

    RepairOrder selectByPrimaryKey(String orderNo);

    int updateByPrimaryKeySelective(RepairOrder record);

    int updateByPrimaryKey(RepairOrder record);

    List<RepairOrder> findByHouseId(String houseId);

    void deleteOrderList(List<String> orderNos);

    List<OrderInfo> findAllHouseOrders(RowBounds rowBounds, RepairPageReq repairPageReq);

    List<OrderInfo> findAllOfficeOrders(RowBounds rowBounds, RepairPageReq repairPageReq);

    /**
     * 条件查询维修订单
     * Create By ZhouYe ON 2017/9/15 16:38
     */
    List<AppRepairOrder> findAllRepair(RowBounds rowBounds, RepairPage repairPage);

    OrderPic selectPicByOrderNo(String orderNo);

    OrderDetail selectDetailByOrderNo(String orderNo);

    OrderInfo selectInfoByOrderNo(String orderNo);

    /**
     * 根据年月和完成状态查询
     *
     * @param date   年-月
     * @param status 1为未完成  2为已完成  其他为查所有
     * @return
     */
    Long countOrders(@Param("date") String date, @Param("status") Integer status);

    Integer countWaitRepair(String estateId);

    Integer countPendingRepair(@Param("assigner")String assigner,@Param("estateId")String estateId);

    List<FindRepairListResp> findAllUnassignList(RowBounds rowBounds, String estateId);

    List<FindRepairListResp> findAllAssignedList(RowBounds rowBounds, String estateId);

    FindRepairInfoResp findRepairInfo(String orderNo);

    List<FindRepairListResp> findAllRepairListByStatus(RowBounds rowBounds, @Param("status") String status, @Param("userId") String userId, @Param("estateId") String estateId);

    List<FindRepairListResp> findAllOfficeRepairListByStatus(RowBounds rowBounds, @Param("status") String status, @Param("userId") String userId, @Param("estateId") String estateId);
    /**
     * 今天待维修数量，全部
     *
     * @param homeReq
     * @return
     */
    Integer queryTodayReapirNum(HomeReq homeReq);

    /**
     * 今天已指派维修数量，全部
     *
     * @param homeReq
     * @return
     */
    Integer queryAllassignNum(HomeReq homeReq);

    /**
     * 查询所有未指派的报修数量
     *
     * @return
     */
    Integer queryAllUnassignNum();

    /**
     * 查询所有指派给我前去维修的报修数量
     * 已接单
     *
     * @param operatorId
     * @return
     */
    Integer assignMeNumNow(String operatorId);

    /**
     * 今日已完成的报修单数量
     *
     * @param homeReq
     * @return
     */
    Integer finishRepairNum(HomeReq homeReq);
    /**
     * 今日已完成的报修单数量
     *
     * @param homeReq
     * @return
     */
    Integer finishRepairNumOfMySelf(HomeReq homeReq);

    RepairData findRepairData();

    /**
     * 查询待维修数量
     * @return
     */
    Map<String,Long> findHomePageNew();

    List<FindRepairListResp> findAllOfficeUnassignList(RowBounds rowBounds, String estateId);

    List<FindRepairListResp> findAllOfficeAssignedList(RowBounds rowBounds, String estateId);

    FindRepairInfoResp findOfficeRepairInfo(String orderNo);

    List<RepairOrder> findRepairs(TaskCountReq taskCountReq);
}