package com.betel.estatemgmt.business.web.user.constant;

/**
 * @ClassName:
 * @Description:
 * @author:
 * @Date:
 */
public interface UserDataValidation {

    /**
     * 用户账号格式
     */
    String ACCTNAME_FORMAT="[^1[3,4,5,7,8]\\d{9}$]";

    /**
     * 用户昵称格式 2-15个字符，支持中英文、数字、“_”和减号
     */
    String USERNAME_FORMAT="[\\u4e00-\\u9fa5_a-zA-Z0-9-]{2,15}$";

    /**
     * 用户账号查询格式 账号不能包含\，<，>，’，”和% 且长度不能大于11
     */
    String USER_SELECT_ACCTNAME = "[^\\<>%'\"]{1,11}";

    /**
     * 用户昵称查询格式 账号不能包含\，<，>，’，”和% 且长度不能大于15
     */
    String USER_SELECT_USERNAME = "[^\\<>%'\"]{1,15}";
}
