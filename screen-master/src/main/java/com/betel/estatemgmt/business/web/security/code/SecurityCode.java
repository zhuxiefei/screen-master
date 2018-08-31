package com.betel.estatemgmt.business.web.security.code;

/**
 * Created by zhangjian on 2018/2/27.
 */
public interface SecurityCode {
    /**
     * 区域名称非法
     */
    String AREA_NAME_IS_RILE_ERROR = "X3001";
    /**
     * 区域下签到地点非法
     */
    String SIGNIN_ADDRESS_IS_RILE_ERROR = "X3002";
    /**
     * 区域名称重复
     */
    String AREA_NAME_HAS_EXIST_ERROR = "X3003";
    /**
     * 区域下签到地址重复
     */
    String AREA_SIGNIN_ADDRES_HAS_EXIST_ERROR = "X3004";
    /**
     * 区域已被删除
     */
    String AREA_IS_NOT_EXIST = "X3005";
    /**
     * 区域下签到地点已被删除
     */
    String SIGNIN_ADDRESS_IS_NOT_EXIST = "X3006";
    /**
     * 编号不合法
     */
    String CONTENT_NO_RULE_ERROR = "X3007";
    /**
     * 巡逻内容已被删除
     */
    String CONTENT_IS_NOT_EXIST = "X3008";
    /**
     * 定时发布巡逻任务数量不能超过10个
     */
    String INSPECTIONTIME_MORE_THAN_TEN = "X3009";
    /**
     * 巡逻内容不能长度超过500
     */
    String CONTENT_DESC_MORE_FIVE_HUNDRED = "X3010";
    /**
     * 区域id存在签到地址不能删除
     */
    String SIGINID_ADRESS_UDDER_AREA = "X3011";
    /**
     * 签到地址已经关联巡逻内容，所以不能删除
     */
    String CONTENT_UNDER_SIGNIN = "X3012";
    /**
     * 巡逻记录被删除
     */
    String RECORD_IS_NOT_EXIST = "X3013";
    /**
     * 定时任务的格式不对
     */
    String INSPECTION_TIME_IS_NOT_TRUE="X3014";
    /**
     * 签到地点和区域已经存在
     */
    String AREA_SIGNIN_ADDRESS_IS_EXIST="X3015";
}
