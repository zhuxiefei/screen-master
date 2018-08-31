package com.betel.estatemgmt.business.web.expenses.code;

/**
 * Created by zhangjian on 2017/9/17.
 */
public interface ExpensesCode {
    /**
     * 房屋号字数字数不能大于6位且不能包含特殊字符（英文\<>'"%）
     */
    String HOUSE_NUM_IS_NOT_LEGAL = "F10001";
    /**
     * 户主姓名字数不能大于10位且不能包含特殊字符（英文\<>'"%）
     */
    String HOUSE_MASTER_IS_NOT_LEGAL = "F10002";
    /**
     * 出账开始时间格式必须满足yyyy-MM-dd
     */
    String EXPORT_BILL_START_TIME_IS_NOT_LEGAL = "F10003";
    /**
     * 出账结束时间格式必须满足yyyy-MM-dd
     */
    String EXPORT_BILL_END_TIME_IS_NOT_LEGAL = "F10004";
    /**
     * 出账开始时间不能大于出账结束时间
     */
    String EXPORT_BILL_START_TIME_IS_MORE_THAN_END_TIME = "F10014";
    /**
     * 楼宇ID为空
     */
    String BUILDING_ID_NULL = "F10005";
    /**
     * 单元ID为空
     */
    String UNIT_ID_IS_NULL = "F10006";
    /**
     * 收费项目类型不在本系统范围内
     */
    String ITEM_TYPE_IS_NOT_EXIST = "F10007";
    /**
     * （缴费）收费状态不在本系统范围内
     */
    String BILL_STATUS_IS_NOT_EXIST = "F10008";
    /**
     * 账单id为空
     */
    String BILL_ID_IS_NULL = "F10009";
    /**
     * 选中的账单中含非欠缴费账单，请选择欠缴费账单
     */
    String CHOOSE_BILL_HAS_PART_OF_PAYMENT_BILL = "F10010";
    /**
     * 选中的账单不是同一个房屋的账单，请选择同一个房屋下的账单
     */
    String CHOOSE_BILL_IS_NOT_THE_SAME_AS_BILL_UNDER_THE_SAME_HOUSE = "F10011";
    /**
     * 账单已被删除
     */
    String BILL_IS_NOT_EXIST = "F10013";
    /**
     * 交钱（缴费）方式不在系统范围内: 支付宝，微信，银行卡，现金
     */
    String PAYMENT_MENTHOD_SYSTEM_DOES_NOT_EXIST = "F10012";
    /**
     * 选中的账单含有欠缴费账单，请选择已缴费账单
     */
    String CHOOSE_BILL_CHECK_FOR_THE_BILL = "F10015";
    /**
     * 楼宇不存在
     */
    String BUILDING_IS_NOT_EXIST = "F10016";
    /**
     * 单元不存在
     */
    String UNIT_IS_NOT_EXIST = "F10017";
    /**
     * 您选择缴费的账单已经被锁定支付状态，不能缴费
     */
    String BILL_LOCKING = "F10018";
    /**
     * 您选择缴费的账单已经超过5分钟有效时间，请重新生成账单
     */
    String BILL_EXPIRED = "F10019";
    /**
     * 账单缴费状态不是唯一
     */
    String BILL_STATUS_NOT_UNIQUE = "F10020";

    /**
     * 费用的sendNo
     */
    String EXPENSES_SENDNO = "23";

    /**
     * 缴费成功的sendNo
     */
    String EXPENSES_SUCCESS_SENDNO = "25";

    /**
     * 系统通知sndType
     */
    String SENDTYPE = "smart02";

    /**
     * 物业费的收费标准
     */
    String CHARGESTANDARD = "propertyChargeStandard";

    /**
     * 支付方式 :现金
     */
    String PAYMENT_IN_CASH = "1";

    /**
     * 支付方式 :刷卡
     */
    String PAYMENT_IN_CARD = "2";
    /**
     * 支付方式 :支付宝
     */
    String PAYMENT_IN_ALIPAY = "3";
    /**
     * 支付方式 :微信
     */
    String PAYMENT_IN_WEIXIN = "4";

    /**
     * 流水状态 :未支付
     */
    String PAYSTATUS_NON__PAYMENT = "1";
    /**
     * 流水状态 :待支付
     */
    String PAYSTATUS_UNPAID = "2";
    /**
     * 流水状态 :已支付
     */
    String PAYSTATUS_PAID = "2";
    /**
     * 物业费
     */
    String PROPERTY_FEE = "wy";
    /**
     * 生成缴费流水号失败
     */
    String CREATE_FLOW_FAILURE = "F10021";
    /**
     * 选中的账单，有部分被删除
     */
    String CHOOSE_BILL_HAS_DELETE_PART_OF_ALL_BILL = "F10022";
    /**
     * 您选择的账单有部分无户主信息，请认证户主
     */
    String HOUSE_MASTER_NOT_EXIST_PART_OF_CHOOSE_BILL = "F10024";
    /**
     * 您选择的账单无户主信息，请认证户主
     */
    String HOUSE_MASTER_NOT_EXIST_OF_CHOOSE_BILL = "F10023";
    /**
     * 您选择的账单有部分帐单未逾期的账单，请选择逾期账单
     */
    String CHOOSE_BILL_HAS_OVER_PART_OF_BILL = "F10025";
    /**
     * 您选择的账单未逾期的账单，请选择逾期账单
     */
    String CHOOSE_BILL_HAS_OVER_OF_ALL_BILL = "F10026";
    /**
     * F10027	您选择的账单已经缴费完
     */
    String CHOOSE_BILL_HAS_ALL_PAY = "F10027";
    /**
     * F10028	您选择的账单有部分账单已经缴费完，请重新选择账单
     */
    String CHOOSE_BILL_HAS_PART_PAY = "F10028";

    //xyx
    /**
     * 房屋ID为空
     */
    String HOUSE_ID_NULL = "F0051";

    /**
     * 收费类型为空
     */
    String ITEM_TYPE_NULL = "F0052";

    /**
     * 房屋被删除
     */
    String HOUSE_IS_DELETE = "F0053";

    /**
     * 房屋交付时间不能为空
     */
    String HOUSE_DELIVERTIME_NULL = "F0054";

    /**
     * 没有该收费类型的收费项
     */
    String ITEM_IS_NULL = "F0055";

    /**
     * 房屋没有绑定收费项
     */
    String HOUSE_NOTRELA_ITEM = "F0056";

    /**
     * 收费项ID为空
     */
    String EXPENSE_ID_NULL = "F0057";

    /**
     * 月数为空
     */
    String MONTHS_NULL = "F0058";

    /**
     * 收费项被删除
     */
    String EXPENSE_ITEM_DELETE = "F0059";

    /**
     * 该房屋没有购买停车位
     */
    String HOUSE_BUYSPACE_NULL = "F0060";

    /**
     * 该房屋没有租赁停车位
     */
    String HOUSE_RENTSPACE_NULL = "F0061";

    /**
     * 房屋信息已更改，请刷新页面重新操作
     */
    String HOUSE_IS_CHANGE = "F0062";

    /**
     * 账单未缴费
     */
    String BILL_NOT_PAY = "F0063";

    /**
     * 收据已提供
     */
    String BILL_IS_DEMAND_STATUS = "F0064";
    /**
     * app端已索要
     */
    String BILL_APP_IS_YI_SUO_YAO="F0065";
    /**
     * 收据状态空错误
     */
    String DEMANDBILLSTATUS_NULL_ERROR = "F0066";
    /**
     * 收据状态错误
     */
    String DEMANDBILLSTATUS_RULE_ERROR = "F0067";

}
