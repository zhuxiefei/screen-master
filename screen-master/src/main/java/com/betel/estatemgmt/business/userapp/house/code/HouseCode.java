package com.betel.estatemgmt.business.userapp.house.code;

/**
 * <p>
 * 返回码
 * </p>
 * ClassName: HouseCode <br/>
 * Author: zhouye  <br/>
 * Date: 2017/6/19 16:30 <br/>
 * Version: 1.0 <br/>
 */
public interface HouseCode {

    /**
     * 房屋id为空
     */
    String HOUSEIDNULL = "H1004";

    /**
     * 已申请过该房间
     */
    String REAPPLY = "H1005";

    /**
     * 已是该房间户主
     */
    String ISWONER = "H1006";

    /**
     * 姓名为空
     */
    String USERNAMENULL = "H1007";

    /**
     * 姓名格式错误
     */
    String USERNAMEFORMATWRONG = "H1008";

    /**
     * 房屋成员id为空
     */
    String MEMBERIDNULL = "H1009";

    /**
     * 身份证号为空
     */
    String APPLICATIONNULL = "H1010";

    /**
     * 认证申请Id为空
     */
    String AUTHIDNULL = "H1015";

    /**
     * 审核已结束
     */
    String AUTHEND = "H1016";

    /**
     * 请求已撤销
     */
    String AUTHREVOKE = "H1017";

    /**
     * 请求已删除
     */
    String AUTHDELETE = "H1018";

    /**
     * 审核意见不许为空
     */
    String NOTNULLAUTHCONTENT = "H1019";

    /**
     * 审核内容长度超过限制
     */
    String AUTHLENGTHOUTOFSIZE = "H1020";

    /**
     * 审核结果不许为空
     */
    String AUTHCONSULENULL = "H1021";

    /**
     * 图纸id为空
     */
    String HOUSEPICIDNULL = "H1022";

    /**
     * 材料id为空
     */
    String MATERIAL_ID_NULL = "H1023";

    /**
     * 下载分享图纸的密码为空
     */
    String SHAREPWDNULL = "H1024";

    /**
     * 下载分享图纸的密码错误
     */
    String SHAREPWDWRONG = "H1025";

    /**
     * 分享图纸的链接失效
     */
    String SHAREOUTOFTIME = "H1026";

    /**
     * 该图纸不存在
     */
    String FILEPATHWRONG = "H1027";

    /**
     * 用户不是该房户主
     */
    String NOTTHISHOUSEOWNER = "H1030";

    /**
     * 用户不是户主
     */
    String NOTOWNER = "H1031";

    /**
     * 无权限审核该用户
     */
    String OUTOFAUTH = "H1032";

    /**
     * 认证申请不是认证中
     */
    String NOTAUTHING = "H1033";

    /**
     * 认证申请不是认证中
     */
    String NOTAUTHDELETE = "H1034";

    /**
     * 房间没有户主
     */
    String NOOWNER = "H1035";

    /**
     * 已是房屋成员
     */
    String ISMENMBER = "H1036";

    /**
     * 通知id为空
     */
    String NOTICE_ID_NULL = "H1037";

    /**
     * 审核结果格式不正确
     */
    String AUTHCONSULEFORMAT = "H1038";

    /**
     * 房屋已删除
     */
    String HOUSEDELETE = "H1042";

    /**
     * 图片数量过多
     */
    String IMAGEOUTOFNUM = "H1040";
    /**
     * 材料已删除
     */
    String MATERIALDELETE = "H1041";

    /**
     * 此用户已被管理员删除
     */
    String MANAGER_DELETE_MEMBER = "H1043";
    /**
     * 非房屋成员
     */
    String NOT_HOUSE_MEMBER = "H1045";

    /**
     * 号码为空
     */
    String PHONENUM_NULL = "H1046";

    /**
     * 号码格式错误
     */
    String PHONENUM_FORMAT = "H1047";

    /**
     * 户主激活信息不匹配
     */
    String NAME_PHONE_NOT_MATCH = "H1048";

    /**
     * 验证码错误
     */
    String VALIDATE_CODE_ERROR = "H1049";

    /**
     * 已存在该成员
     */
    String MEMBER_IS_EXIST = "H1050";

    /**
     * 该用户已经是户主
     */
    String MEMBER_IS_OWNER = "H1051";

    /**
     * 成员认证已通过，不可撤销申请
     */
    String MEMBER_AUTH_SUCCESS = "H1052";
}
