package com.betel.estatemgmt.business.userapp.auth.code;

/**
 * <p>
 * 文件说明
 * </p>
 * ClassName: AuthCode <br/>
 * Author: geyf  <br/>
 * Date: 2017/6/23 9:33 <br/>
 * Version: 1.0 <br/>
 */
public interface AuthCode {

    /**
     * 认证申请Id为空
     */
    String AUTHAPPLICANT_ID_ISNULL = "H1015";

    /**
     * 审核已结束
     */
    String EXAMINE_ISEND = "H1016";

    /**
     * 请求已撤销
     */
    String REQUEST_ISREVOKE = "H1017";

    /**
     * 请求已删除
     */
    String REQUEST_ISDELETE = "H1018";

    /**
     * 审核意见不许为空
     */
    String APPLICANT_OPINION_ISNULL = "H1019";

    /**
     * 审核内容长度超过限制
     */
    String EXAMINECONTENT_TOLONG = "H1020";

    /**
     * 审核结果不许为空
     */
    String EXAMINERESULT_ISNULL = "H1021";

    /**
     * 用户不是该房户主
     */
    String USER_NOTTHISHOUSE_HOUSEOWNER = "H1030";

    /**
     * 用户不是户主
     */
    String USER_ISNOT_HOUSEOWNER = "H1031";

    /**
     * 无权限审核该用户
     */
    String NOPERMISSION_EXAMINE_THISUSER = "H1032";

}
