package com.betel.estatemgmt.business.userapp.repair.constant;

import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.common.ConfigManager;
import com.betel.estatemgmt.common.RedisManager;


/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: RepairConstant <br/>
 * Author: zhouye  <br/>
 * Date: 2017/9/13 17:16 <br/>
 * Version: 1.0 <br/>
 */
public interface RepairConstant {
    /**
     * 图片上传位置
     */
    String FILE_DIR_PATH = ConfigManager.read(ConfigName.FILE_DIR) + "repair/";

    /**
     * 报修区域 :个人
     */
    Integer ORDER_AREA_PERSON = 1;

    /**
     * 报修区域 :公共
     */
    Integer ORDER_AREA_PUBLIC = 2;

    /**
     * 维修描述的长度
     */
    int ORDER_DES_LENGTH = 100;

    /**
     * 报修订单类型 待维修:1
     */
    Integer ORDER_STATUS_START = 1;

    /**
     * 报修订单类型 已完成:3
     */
    Integer ORDER_STATUS_END = 3;

    /**
     * 报修订单类型 已取消:4
     */
    Integer ORDER_STATUS_CANCEL = 4;

    /**
     * 报修订单类型 已接单:2
     */
    Integer ORDER_STATUS_DOING = 2;

    /**
     * 报修订单的标识
     */
    String BX_FLAG = "BX";

    /**
     * 维修收费标准property
     */
    String REPAIRCHARGESTANDARD = "repairChargeStandard";

    /**
     * 报修订单未评价
     */
    int NOTREPLY = 1;
    /**
     * 报修订单已评价
     */
    int ISREPLY = 2;

    /**
     * 数字0
     */
    int ZERO = 0;

    /**
     * 数字0
     */
    int ONE = 1;

    /**
     * 数字999
     */
    int THREENINE = 999;

    /**
     * 数字9
     */
    int NINE = 9;

    /**
     * 昵称格式校验
     */
    String NAME_RULE = "[^\\\\<>%'\"]{1,15}";

    /**
     * 用户新增保修单sendNo
     */
    String USER_ADD_REPAIR_SENDNO = "1";

    String USER_ADD_REPAIR_SENDTITLE = "用户新增报修单";

    String USER_ADD_REPAIR_SENDCONTENT = "您有一个新的待维修单，请及时指派相关人员维修";

    /**
     * 用户取消修单sendNo
     */
    String USER_CANCEL_REPAIR_SENDNO = "2";

    String USER_CANCEL_REPAIR_SENDTITLE = "用户取消报修单";

    String USER_CANCEL_REPAIR_SENDCONTENT = "您有一个维修单被取消，维修单号：";

    /**
     * 用户催单sendNo
     */
    String USER_URGE_REPAIR_SENDNO = "3";

    String USER_URGE_REPAIR_SENDTITLE = "用户催单";

    String USER_URGE_REPAIR_SENDCONTENT = "您有一个维修单被催单，请尽快处理！维修单号：";

    /**
     * 新增报修单启用线程
     */
    Integer ADD_REPAIR_THREAD = 1;

    /**
     * 催单启用线程
     */
    Integer URGE_REPAIR_THREAD = 2;

    /**
     * 取消报修单启用线程
     */
    Integer CANCEL_REPAIR_THREAD = 3;

    /**
     * 用户删除维修单标识
     */
    Integer USER_DELETE_REPAIR = 1;

    /**
     * 系统通知sendType
     */
    String PUSH_SENDTYPE = "smart05";
}
