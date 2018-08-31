package com.betel.estatemgmt.common.thread;

import com.betel.estatemgmt.business.userapp.repair.constant.RepairConstant;
import com.betel.estatemgmt.business.web.repair.constant.RepairDataValidation;
import com.betel.estatemgmt.common.model.repair.RepairOrder;
import com.betel.estatemgmt.utils.HttpClientUtil;
import com.betel.estatemgmt.utils.StringUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: RepairThread <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/12/29 16:24 <br/>
 * Version: 1.0 <br/>
 */
public class RepairThread extends Thread {

    private static final Logger LOG = LoggerFactory.getLogger(RepairThread.class);

    private RepairOrder order;

    private Integer type;

    public RepairThread(RepairOrder order, Integer type){
        this.order = order;
        this.type = type;
    }

    public RepairOrder getOrder() {
        return order;
    }

    public void setOrder(RepairOrder order) {
        this.order = order;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public void run() {
        task();
    }

    public void task(){
        if (RepairConstant.ADD_REPAIR_THREAD.equals(type)){
            try {
                if (LOG.isDebugEnabled()){
                    LOG.debug("用户新增报修单（推送给：有报修指派权限的员工）");
                }
                //用户新增报修单（推送给：有报修指派权限的员工）
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("sendNo", RepairConstant.USER_ADD_REPAIR_SENDNO);
                jsonParam.put("sendTitle", RepairConstant.USER_ADD_REPAIR_SENDTITLE);
                jsonParam.put("sendContent", RepairConstant.USER_ADD_REPAIR_SENDCONTENT);
                jsonParam.put("orderNo", order.getOrderNo());
                HttpClientUtil.post(RepairDataValidation.OA_PROJECT_URL+"httpclientOA/task/v1/push",jsonParam,null);
            } catch (Exception e) {
                LOG.error("-------addRepair push error----------"+e);
            }
        }else if (RepairConstant.URGE_REPAIR_THREAD.equals(type)){
            try {
                if (order.getOrderStatus().equals(RepairConstant.ORDER_STATUS_START)){
                    if (LOG.isDebugEnabled()){
                        LOG.debug("若是未指派状态，给报修指派权限的员工发通知");
                    }
                    //若是未指派状态，给报修指派权限的员工发通知
                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("sendNo", RepairConstant.USER_URGE_REPAIR_SENDNO);
                    jsonParam.put("sendTitle", RepairConstant.USER_URGE_REPAIR_SENDTITLE);
                    jsonParam.put("sendContent", RepairConstant.USER_URGE_REPAIR_SENDCONTENT+order.getOrderNo());
                    HttpClientUtil.post(RepairDataValidation.OA_PROJECT_URL+"httpclientOA/task/v1/push",jsonParam,null);
                }else if (order.getOrderStatus().equals(RepairConstant.ORDER_STATUS_DOING)){
                    if (LOG.isDebugEnabled()){
                        LOG.debug("若是已结单状态，给被指派人员发通知");
                    }
                    //若是已结单状态，给被指派人员发通知
                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("sendNo", RepairConstant.USER_URGE_REPAIR_SENDNO);
                    jsonParam.put("sendTitle", RepairConstant.USER_URGE_REPAIR_SENDTITLE);
                    jsonParam.put("sendContent", RepairConstant.USER_URGE_REPAIR_SENDCONTENT+order.getOrderNo());
                    jsonParam.put("noticeUserId", order.getOperatorId());
                    HttpClientUtil.post(RepairDataValidation.OA_PROJECT_URL+"httpclientOA/task/v1/push",jsonParam,null);
                }
            } catch (Exception e) {
                LOG.error("-------updateUrgeRepair push error----------"+e);
            }
        }else if (RepairConstant.CANCEL_REPAIR_THREAD.equals(type)){
            try {
                if (LOG.isDebugEnabled()){
                    LOG.debug("用户取消报修单（推送给：有报修指派权限的员工和被指派人员）");
                }
                //用户取消报修单（推送给：有报修指派权限的员工和被指派人员）
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("sendNo", RepairConstant.USER_CANCEL_REPAIR_SENDNO);
                jsonParam.put("sendTitle", RepairConstant.USER_CANCEL_REPAIR_SENDTITLE);
                jsonParam.put("sendContent", RepairConstant.USER_CANCEL_REPAIR_SENDCONTENT+order.getOrderNo());
                if (!StringUtil.isBlank(order.getOperatorId())){
                    jsonParam.put("noticeUserId", order.getOperatorId());
                }
                HttpClientUtil.post(RepairDataValidation.OA_PROJECT_URL+"httpclientOA/task/v1/push",jsonParam,null);
            } catch (Exception e) {
                LOG.error("-------cancelRepair push error----------"+e);
            }
        }
    }
}
