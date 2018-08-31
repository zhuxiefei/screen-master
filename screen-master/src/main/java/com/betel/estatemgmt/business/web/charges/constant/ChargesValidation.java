package com.betel.estatemgmt.business.web.charges.constant;

import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.common.ConfigManager;
import com.betel.estatemgmt.common.RedisManager;

/**
 * Created by zhangjian on 2017/9/19.
 */
public interface ChargesValidation {
    /**
     * 收费项目名称校验
     */
    String ITEM_NAME_RULE = "[^\\\\<>%'\"]{0,10}";
    /**
     * 价格的校验:价格 大于0 小于50000的且小数最多2位
     */
    String PRICE_RULE = "^(?!^0$)(?!^0\\.0?0$)^[0-4][0-9]{0,4}(?:\\.\\d{1,2})?$|^50000$|^50000.0|^50000.00$|^(5|6|7|8|9)[0-9]{0,3}(?:\\.\\d{1,2})?$";
    /**
     * //滞纳金：大于0小于100的且小数最多2位
     */
    String OVER_FINE_PRICE = "(?!^0$)(?!^0\\.0?0$)^[0-9][0-9]?(\\.[0-9]{1,2})?$";
    /**
     * 缴费周期（天） 大于9小于100不可以小数
     */
    String CHARGE_CYCLE = "^[1-9][0-9]$";
    /**
     * 计费的周期(月)
     */
    String ITEM_CYCLE = "^(([1-9])|(1[0-9])|(2[0-4]))$";//大于0小于等于24不可以小数

    /**
     * 系统交付时间
     */
    String SYSTEM_DELIVER_TIME = ConfigManager.read(ConfigName.SYSTEM_DELIVER_TIME);

    /**
     * 公摊水电费/能耗费规则
     */
    String OTHER_PRICE_RULR = "^((([1-9]\\d{0,5})(\\.\\d{1,2})?)|1000000|1000000.0|1000000.00)$";

    /**
     * 收费标准的长度
     */
    int STANDARD_LENGTH = 100000;
    /**
     * 计费方式:1 建面计费
     */
    String CHARGETYPE_ONE = "1";
    /**
     * 操作类型
     */
    int ACTION_TYPE = 2;
    /**
     * 收费项目类型:
     * 1为物业费，
     * 2为公摊水电费
     * 3为购买停车费
     * 4为租赁停车费
     * 5为能耗费
     */
    String ITEM_TYPE_ONE = "1";
    String ITEM_TYPE_TWO = "2";
    String ITEM_TYPE_THREE = "3";
    String ITEM_TYPE_FOUR = "4";
    String ITEM_TYPE_FIVE = "5";
}
