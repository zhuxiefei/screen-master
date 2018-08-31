package com.betel.estatemgmt.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * <p>
 * 生成账单金额计算工具类
 * </p>
 * ClassName: BillAmountUtil <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/11/21 11:50 <br/>
 * Version: 1.0 <br/>
 */
public class BillAmountUtil {
    private static final Logger LOG = LoggerFactory.getLogger(BillAmountUtil.class);

    /**
     * 计算公摊水电费
     * @param allPrice 总价格
     * @param allFloorArea 总建筑面积
     * @param singleFloorArea 单个建筑面积
     * @return
     */
    public static BigDecimal countPublicAmount(double allPrice,double allFloorArea,double singleFloorArea){
        //公摊水电费——产生的公摊水电费总金额（6/月） ÷ 小区所有房屋的建筑面积 x 每户建筑面积 + 滞纳金
        return new BigDecimal(allPrice / allFloorArea * singleFloorArea).setScale(2,BigDecimal.ROUND_UP);
    }

    /**
     * 计算能耗费
     * @param allPrice 总价格
     * @param allInterArea 总套内面积
     * @param singleInterArea 单个套内面积
     * @return
     */
    public static BigDecimal countEnergyAmount(double allPrice,double allInterArea,double singleInterArea){
        //能耗费——产生的能耗费总金额（6/月） ÷ 小区所有房屋的套内面积 x 每户套内面积 + 滞纳金
        return new BigDecimal(allPrice / allInterArea * singleInterArea).setScale(2,BigDecimal.ROUND_UP);
    }

    /**
     * 计算物业费
     * @param itemPrice 单价
     * @param singleFloorArea 单个房屋的建筑面积
     * @param itemCycle 计费周期
     * @return
     */
    public static String countPropertyAmount(double itemPrice,double singleFloorArea,Integer itemCycle){
        //物业费=每平米每个月物业费价格*房屋建筑面积*计费周期
        return String.format("%.2f",itemPrice * singleFloorArea * itemCycle);
    }

    /**
     * 计算购买/租赁停车费
     * @param itemPrice 单价
     * @param itemCycle 计费周期
     * @param counts 车位数
     * @return
     */
    public static String countSpaceAmount(double itemPrice,Integer itemCycle,Integer counts){
        //停车费=每平米每个月停车费价格*计费周期*车位数
        return String.format("%.2f",itemPrice * itemCycle * counts);
    }
}
