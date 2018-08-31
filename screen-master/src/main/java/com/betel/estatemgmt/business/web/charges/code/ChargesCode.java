package com.betel.estatemgmt.business.web.charges.code;

/**
 * Created by zhangjian on 2017/9/19.
 */
public interface ChargesCode {
    /**
     * 计费方式不合法
     * 1为建面计费
     */
    String CHARGE_TYPE_IS_NOT_LEGAL = "S0001";
    /**
     * 收费项目名称字数不能大于10位且不能包含特殊字符（英文\<>'"%）
     */
    String ITEM_NAME_IS_NOT_LEGAL = "S0002";
    /**
     * 价格必须满足大于0小于等于50000的数，且精度的最多到2位小数
     */
    String ITEM_PRICE_IS_NOT_LEGAL = "S0003";
    /**
     * 收费周期格式必须满足大于0小于100的数
     */
    String ITEM_CYCLE_IS_NOT_LEGAL = "S0004";
    /**
     * 滞纳金数值必须满足百分之几或者(小于100且大于0的数）%，几最多到2位小数
     */
    String OVERDUE_FINE_PRICE_IS_NOT_LEGAL = "S0005";
    /**
     * 收费项目类型不再本系统范围内
     */
    String ITEM_TYPE_IS_NOT_EXIST = "S0006";
    /**
     * 缴费周期格式必须是大于9小于100的数
     */
    String CHARGECYCLE_IS_NOT_LEGAL = "S0007";
    /**
     * 收费项目id为空
     */
    String ITEM_ID_IS_NULL = "S0008";
    /**
     * 收费项目不存在
     */
    String ITEM_IS_NOT_EXIST = "S0009";
    /**
     * 该小区的全部楼宇已有部分楼宇被应用该类型的收费标准，不能选择应用全部楼宇
     */
    String BUILDING_HAS_APPLIED_STANDARD_OF_SPECIFIED_TYPE = "S0010";
    /**
     * 计费年月必须满足格式yyyy-MM，不满足报错
     */
    String BILLING_MONTHLY_IS_NOT_LEGAL = "S0011";
    /**
     * 判断选中收费项目是否已经应用楼宇，如果应用了则不能再次应用楼宇，报错
     */
    String ITEM_HAS_APPLIED_TO_THE_BULIDINGS = "S0012";
    /**
     * 楼宇不存在
     */
    String BUILDING_IS_NOT_EXIST = "S0013";
    /**
     * 计费方式为空
     */
    String CHARGE_TYPE_IS_NULL = "S0014";
    /**
     * 项目名称为空
     */
    String ITEM_NAME_IS_NULL = "S0015";
    /**
     * 价格为空
     */
    String PRICE_IS_NULL = "S0016";
    /**
     * 收费周期（单位：自然月）为空
     */
    String ITEM_CYCLE_IS_NULL = "S0017";
    /**
     * 滞纳金率为空
     */
    String OVERDUE_FINE_IS_NULL = "S0018";
    /**
     * 收费项类型为空
     */
    String ITEM_TYPE_IS_NULL = "S0019";
    /**
     * 缴费周期为空（单位：天）
     */
    String CHARGECYCLE_IS_NULL = "S0020";
    /**
     * 收费项目应用楼宇开始计费年月为空
     */
    String ITEM_HAS_APPLIED_TO_THE_BULIDINGS_CHARGES_TIME_IS_NULL = "S0021";
    /**
     * 选中的收费项目有部分被删除
     */
    String CHOOS_ITEM_HAS_NOT_EXIST_PART_OF_IETEMS = "S0022";

    /**
     * 楼宇id为空
     */
    String BUILDING_ID_IS_NULL = "S0023";
    /**
     * 选中的楼宇，有被删除的情况
     */
    String CHOOSE_BUILDING_HAS_PART_OF_DELETE = "S0024";
    /**
     * 收费标准长度超过5000
     */
    String RATE_STANDARD_LENGTH_IS_NOER_THAN_FIVE_QIAN = "S0025";
    /**
     * 您选择的楼宇/别墅有部分已经应用的收费项目
     */
    String CHOOSE_BUILDING_BS_HAS_APPLICATIE_ITEM = "N0026";
    /**
     * 项目名称重复
     */
    String ITEM_NAME_IS_REPETION = "S0027";
    /**
     * 您选择的收费项目已关联账单，不可以删除
     */
    String ITEM_HAS_BILL_NOT_DELETE = "S0028";
    /**
     * 您选择的收费项目中部分已关联账单，不可以删除
     */
    String ITEM_PART_HAS_BILL_NOT_DELETE = "S0029";

    /**
     * 能耗费最多1条
     */
    String ENERGY_MAX_ONE = "S0030";

    /**
     * 公摊水电费最多1条
     */
    String SHARE_MAX_ONE = "S0031";

    /**
     * 收费时间为空
     */
    String TIME_NULL_ERROR = "S0032";

    /**
     * 未到出账时间
     */
    String CREATETIME_NOT_ARRIVE = "S0033";

    /**
     * 房屋全部被删除
     */
    String ALL_HOUSE_DELETE = "S0034";

    /**
     * 系统内无楼宇信息，请添加相关信息后重新操作
     */
    String ALL_BUILDING_DELETE = "S0035";

    /**
     * 该时间段已经出过账单
     */
    String EXIST_BILL = "S0036";
}
