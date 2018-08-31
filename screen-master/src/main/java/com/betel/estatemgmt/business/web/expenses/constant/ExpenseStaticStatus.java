package com.betel.estatemgmt.business.web.expenses.constant;

/**
 * Created by zhangjian on 2017/9/17.
 */
public class ExpenseStaticStatus {
    /**
     * 收费类型:
     * 1为欠缴费
     * 2为已缴费
     * 3为预交费
     * 4为未缴费
     */
    public static final String TYPE_ONE = "1";
    public static final String TYPE_TWO = "2";
    public static final String TYPE_THREE = "3";
    public static final String TYPE_FOUR = "4";

    /**
     * 收费项目类型:
     * 1为物业费，
     * 2为公摊水电费
     * 3为购买停车费
     * 4为租赁停车费
     * 5为能耗费
     */
    public static final String ITEM_TYPE_ONE = "1";
    public static final String ITEM_TYPE_TWO = "2";
    public static final String ITEM_TYPE_THREE = "3";
    public static final String ITEM_TYPE_FOUR = "4";
    public static final String ITEM_TYPE_FIVE = "5";

    /**
     * 操作类型
     */
    public static final int ACTION_VALUE = 2;
    /**
     * 时间格式
     */
    public static final String TIME_TYPE = "yyyy-MM-dd";
    /**
     * 索取账单票据状态：
     *1为未索要，
     *2为已索要
     *3为已送达
     */
    public static final String DEMANDBILLSTATUS_ONE="1";
    public static final String DEMANDBILLSTATUS_TWO="2";
    public static final String DEMANDBILLSTATUS_THREE="3";

}
