package com.betel.estatemgmt.business.web.security.constant;

import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.common.ConfigManager;

/**
 * <p>
 * Describe this class...
 * </p>
 *
 * @ClassName: SecurityConstant <br/>
 * @author: jian.z  <br/>
 * @date: 2017/12/4 17:26 <br/>
 * @Version: 1.0
 */
public interface SecurityConstant {
    /**
     * 待巡逻标题
     */
    String TO_SECURITY_TITLE = "待巡逻任务";
    /**
     * 待巡逻标题
     */
    String TO_SECURITY_SENDNO = "14";
    /**
     * 系统通知sendType
     */
    String PUSH_SENDTYPE = "smart05";
    /**
     * 系统通知sendContent
     */
    String TO_SENDTYPE__SEND_CONTENT = "您有一个待巡逻的任务需处理";
    /***
     * isPatrol:1 待巡逻    2 已完成巡逻
     */
    Integer IS_PATROL_ONE = 1;

    Integer IS_PATROL_TWO = 2;
}
