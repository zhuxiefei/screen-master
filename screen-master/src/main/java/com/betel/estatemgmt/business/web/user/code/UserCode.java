package com.betel.estatemgmt.business.web.user.code;

/**
 * <p>
 * 用户管理返回码
 * </p>
 * ClassName: UserCode <br/>
 * Author: Zhang Li <br/>
 * Date: 2017/5/17 08.55 <br/>
 * Version: 1.0 <br/>
 */
public interface UserCode {
    //账号为空
    String ACCOUNT_IS_NULL = "U1001";
    //账号格式错误
    String ACCOUNT_FORMAT_ERROR = "U1002";
    //账号错误
    String ACCOUNT_ERROR = "U1003";
    //账号已存在
    String ACCOUNT_IS_EXIST = "U1004";
    //token过期
    String TOKEN_EXPIRED = "U1005";
    //头像大小超过限制
    String HEAD_SIZE_OVER_LIMITE = "U1006";
    //头像格式错误
    String HEAD_FORMAT_ERROR = "U1007";
    //部分用户已被删除
    String USERS_DELETED = "U1008";
    //昵称为空
    String USERNAME_IS_NULL = "U1009";
    //昵称格式错误
    String USERNAME_FORMAT_ERROR = "U1010";
    //用户昵称已存在
    String USERNAME_IS_EXIST = "U1011";
    //用户id为空
    String USER_ID_NULL = "U1016";
    //关键字查询不符合输入要求
    String USER_KEYWORD_RULE = "U1017";
    //查询失败
    String SELECT_FAILURE= "U1018";
    //用户性别为空
    String USERGENDER_IS_NULL= "U1019";
    //用户未登录
    String LOGIN_IS_NULL = "U1020";
    //头像为空
    String HEAD_EMPTY = "U1021";
    //用户不存在
    String USER_NOT_EXIST = "U1022";
    //用户状态为空
    String USER_STATUS_NULL = "U1023";








}
