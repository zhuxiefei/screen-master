package com.betel.estatemgmt.business.userapp.user.code;

/**
 * <p>
 * 返回码
 * </p>
 * ClassName: IndexCode <br/>
 * Author: zhouye  <br/>
 * Date: 2017/5/15 14:58 <br/>
 * Version: 1.0 <br/>
 */
public class UserCode {
    /*
    查询结果为空
     */
    public static final String PHONENUMNULL = "U0050";
    /*
    号码格式错误
     */
    public static final String PHONEFORMATWRONG = "U0051";
    /*
    短信验证码为空
     */
    public static final String SMSCODENULL = "U0052";
    /*
    短信验证码错误
     */
    public static final String SMSCODEWRONG = "U0054";
    /*
    号码已注册
     */
    public static final String PHONEUSED = "U0055";
    /*
    密码为空
     */
    public static final String PASSWORDNULL = "U0056";
    /*
    密码格式错误
     */
    public static final String PASSWORFORMATWRONG = "U0057";
    /*
    昵称为空
     */
    public static final String USERNAMENULL = "U0058";
    /*
    昵称格式错误
     */
    public static final String USERNAMEFORMATWRONG="U0059";
    /*
    昵称重复
     */
    public static final String USERNAMEUSED="U0060";
    /*
    性别为空
     */
    public static final String SXENULL="U0061";
    /*
    性别格式错误
     */
    public static final String SEXFORMATWRONG="U0062";
    /*
    图片为空
     */
    public static final String IMAGENULL="U0063";
    /*
    文件太大
     */
    public static final String IMAGEOUTSIZE="U0064";
    /*
    图片格式错误
     */
    public static final String IMAGEFORMATWRONG="U0065";
    /*
    旧密码为空
     */
    public static final String OLDPASSWORDNULL="U0066";
    /*
    旧密码错误
     */
    public static final String OLDPASSWORDWRONG="U0067";
    /*
    新密码为空
     */
    public static final String NEWPASSWORDNULL="U0068";
    /*
    新密码格式错误
     */
    public static final String NEWPASSWORDWRONG = "U0069";
    /*
    号码未注册
     */
    public static final String PHONEUNUSED = "U0070";
    /*
    不是本人
     */
    public static final String NOTMYSELF = "U0071";
    /*
    鉴别字符串错误
     */
    public static final String WRONGCODE = "U0072";
    /*
    短信发送失败
    */
    public static final  String SMS_SEND_FAILURE = "U0074";
    /*
    短信发送频繁
    */
    public static final  String SMS_SEND_TOO_FREQUENT = "U0075";

    /*
   密码长度有误
   */
    public static final  String PASSWORD_LENGTH_WRONG = "U0076";
    /*
    图片名称过长
     */
    public static final  String IMAGE_NAME_LENGTH_OUT = "P0005";
}
