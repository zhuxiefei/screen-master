package com.betel.estatemgmt.business.userapp.auth.constant;

/**
 * <p>
 * 文件说明
 * </p>
 * ClassName: AuthConstant <br/>
 * Author: geyf  <br/>
 * Date: 2017/6/23 9:34 <br/>
 * Version: 1.0 <br/>
 */
public interface AuthConstant {

    /**
     * 认证已撤销
     */
    int AUTHISREVOKE = 4;

    /**
     * 认证已删除
     */
    int AUTHISDELETE = 5;

    /**
     * 通知已读
     */
    int NOTICEISREAD = 2;

    /**
     * 同意认证
     */
    String AUTHINFOAGREE = "1";

    /**
     * 拒绝认证
     */
    String AUTHINFOREFUSE = "2";

    /**
     * 认证通过
     */
    int AUTHAGREE = 2;

    /**
     * 认证不通过
     */
    int AUTHREFUSE = 3;

    /**
     * 认证已撤销
     */
    int AUTHREVOKE = 4;

    /**
     * 认证已删除
     */
    int AUTHDELETE = 5;

    /**
     * 指令类型：成员认证 smart03
     */
    String SEND_TYPE_AUTH = "smart02";

    /**
     * 指令编号：成员认证 03
     */
    String SEND_NO_MEMBER = "4";

    /**
     * 1
     */
    String ONE = "1";

    /**
     * 2
     */
    String TWO = "2";
}
