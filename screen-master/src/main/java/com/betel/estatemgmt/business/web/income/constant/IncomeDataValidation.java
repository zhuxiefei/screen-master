package com.betel.estatemgmt.business.web.income.constant;

/**
 * <p>
 * 收入明细常量
 * </p>
 * ClassName: IncomeDataValidation <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/9/13 11:55 <br/>
 * Version: 1.0 <br/>
 */
public interface IncomeDataValidation {
    /**
     * 昵称长度和格式校验
     */
    String USERNAME_RULE = "[^\\\\<>%'\"]{1,10}";

    /**
     * 房号长度和格式校验
     */
    String HOUSENUM_RULE = "[^\\\\<>%'\"]{1,6}";
}
