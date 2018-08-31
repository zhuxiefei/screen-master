package com.betel.estatemgmt.business.web.auth.code;

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
    /*
    图片为空
     */

     String IMAGENULL = "H1001";
    /*
    图片格式错误
     */
    String IMAGEFORMATWRONG = "H1002";
    /*
    图片过大
     */
    String IMAGEOUTOFSIZE = "H1003";
    /*
    房屋id为空
     */
    String HOUSEIDNULL = "H1004";
    /*
    已申请过该房间
     */
    String REAPPLY = "H1005";
    /*
    已是该房间户主
     */
    String ISWONER = "H1006";
    /*
    姓名为空
     */
    String USERNAMENULL = "H1007";
    /*
    姓名格式错误
     */
   String USERNAMEFORMATWRONG = "H1008";

    /*
    房屋成员id为空
     */
    String MEMBERIDNULL="H1009";
    /*
    身份证号为空
     */
    String APPLICATIONNULL="H1010";
    /*
    身份证格式错误
     */
    String APPLICATIONFORMATWRONG="H1011";

    /*
    身份证正面照没有上传
     */
   String APPLICATIONIMAGE="H1012";
    /*
    身份证反面照没有上传
     */
    String APPLICATIONBACKIMAGE="H1013";
    /*
    不动产权证照没有上传
     */
    String REALESTATEPICNULL="H1014";
    /*
    认证申请Id为空
     */
    String AUTHIDNULL="H1015";
    /*
    审核已结束
     */
    String AUTHEND="H1016";
    /*
     *请求已撤销
     */
    String AUTHREVOKE = "H1017";
    /*
    请求已删除
     */
    String AUTHDELETE = "H1018";
    /*
    审核意见不许为空
     */
    String NOTNULLAUTHCONTENT = "H1019";
    /*
    审核内容长度超过限制
     */
    String AUTHLENGTHOUTOFSIZE = "H1020";
    /*
    审核结果不许为空
     */
    String AUTHCONSULENULL = "H1021";

    /*
    图纸id为空
     */
    String HOUSEPICIDNULL = "H1022";

    /*
    下载分享图纸的密码为空
     */
    String SHAREPWDNULL = "H1024";

    /*
   下载分享图纸的密码错误
    */
    String SHAREPWDWRONG = "H1025";

    /*
   分享图纸的链接失效
    */
    String SHAREOUTOFTIME = "H1026";

    /*
    数据库数据不存在图纸和cad两个图片
   */
    String FILEPATHWRONG = "H1027";

    /*
    用户不是该房户主
     */
    String NOTTHISHOUSEOWNER = "H1030";
    /*
    用户不是户主
     */
    String NOTOWNER = "H1031";
    /*
    无权限审核该用户
     */
     String OUTOFAUTH = "H1032";
    /*
    认证申请不是认证中
     */
    String NOTAUTHING = "H1033";

    /*
    认证申请不是认证中
     */
    String NOTAUTHDELETE = "H1034";
    /*
    房间没有户主
     */
    String NOOWNER = "H1035";
    /*
    已是房屋成员
     */
   String ISMENMBER = "H1036";

    /*
    认证信息不存在
    */
    String AUTHINFONULL = "H1050";

    /*
    申请姓名过长
    */
    String APPLICANT_NAME_OUT_LENGTH = "H1051";
    /*
   申请姓名存在特殊符号
   */
    String APPLICANT_NAME_FORMAT_WRONG = "H1052";

    /*
    房屋名称过长
     */
    String HOUSEINFO_OUTOFLENGTH = "H1053";

    /*
   房屋名称格式错误
    */
    String HOUSEINFO_FORMATWRONG = "H1054";
}
