package com.betel.estatemgmt.business.web.remind.service;

import com.betel.estatemgmt.business.web.remind.model.AddRemindDaysReq;
import com.betel.estatemgmt.business.web.remind.model.RemindDays;

import java.util.List;

/**
 * <p>
 * 设置提醒天数接口
 * </p>
 * ClassName: RemindService <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/9/13 17:40 <br/>
 * Version: 1.0 <br/>
 */
public interface RemindService {
    void addRemindDays(AddRemindDaysReq addRemindDaysReq);

    List<RemindDays> findByConfName(String confName,String estateId);

    void payMentRemind() throws Exception;

    void overDueRemind() throws Exception;

    void payUpNotice() throws Exception;
}
