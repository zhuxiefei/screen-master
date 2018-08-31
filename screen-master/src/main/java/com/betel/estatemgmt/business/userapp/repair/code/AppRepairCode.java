package com.betel.estatemgmt.business.userapp.repair.code;

/**
 * <p>
 * 报修相关code码
 * </p>
 * ClassName: AppRepairCode <br/>
 * Author: zhouye  <br/>
 * Date: 2017/9/13 17:16 <br/>
 * Version: 1.0 <br/>
 */
public interface AppRepairCode {

    /**
     * 报修联系人姓名为空
     */
    String CONTACT_NULL = "R0001";

    /**
     * 报修联系人姓名格式错误
     */
    String CONTACT_FORMAT_WRONG = "R0002";

    /**
     * 报修联系人号码为空
     */
    String CONTACT_PHONE_NULL = "R0003";

    /**
     * 报修联系人号码格式错误
     */
    String CONTACT_PHONE_FORMAT_WRONG = "R0004";

    /**
     * 维修类型为空
     */
    String ORDER_TYPE_NULL = "R0005";

    /**
     * 房屋为空
     */
    String HOUSEID_NULL = "R0006";

    /**
     * 维修区域为空
     */
    String ORDER_AREA_NULL = "R0007";

    /**
     * 房屋不存在
     */
    String HOUSE_DELETE = "R0008";

    /**
     * 维修描述长度过长
     */
    String ORDER_DES = "R0009";

    /**
     * 订单未完成
     */
    String ORDER_NOT_END = "R0010";

    /**
     * 订单已评论
     */
    String ORDER_REPLY = "R0011";

    /**
     * 上门服务准时评价为空，数字类型1-5
     */
    String ORDER_REPLY_TIME = "R0012";

    /**
     * 服务态度评价为空,数字类型1-5
     */
    String ORDER_REPLY_ATTITUDE = "R0013";

    /**
     * 服务质量为空,数字类型1-5
     */
    String ORDER_REPLY_QUALITY = "R0014";

    /**
     * 服务评价过长，不超过100个字，包括标点符号
     */
    String ORDER_REPLY_DES = "R0015";

    /**
     * 报修编号为空
     */
    String ORDER_NO_NULL = "R0016";

    /**
     * 预约维修时间早于当前时间
     */
    String APPOINTTIME_EARLY_NOW = "R0017";

    /**
     * 订单为空
     */
    String REPAIR_NULL = "R0018";

    /**
     * 订单已完成
     */
    String REPAIR_END = "R0019";

    /**
     * 离上次催单还未满1小时，请稍后再试
     */
    String URGEREPAIROFTEN = "R0021";

    /**
     * 报修单正在处理中，请耐心等候
     */
    String FIRST_URGE_DENY = "R0022";

    /**
     * 订单已取消
     */
    String REPAIR_CANCEL = "R0023";

    /**
     * 订单未完成、取消，不可删除
     */
    String REPAIR_NOT_CANCEL_OR_FINISH = "R0024";

    /**
     * 订单已被删除
     */
    String REPAIR_IS_DELETE = "R0025";
}
