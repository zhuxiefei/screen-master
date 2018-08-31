package com.betel.estatemgmt.business.propertyapp.lease.constant;

/**
 * <p>
 * 文件说明
 * </p>
 * Author: geyf  <br/>
 * Date: 2018/5/8 9:49 <br/>
 * Version: 1.0 <br/>
 */
public interface LeaseConstant {


    String LEASE_FORMAT = "[^\\\\<>%'\"]{1,30}";

    /**
     * 最多保留两位小数
     */
    String PRICE_FORMAT = "^\\d{0,8}\\.{0,1}(\\d{1,2})?$";
}