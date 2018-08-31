package com.betel.estatemgmt.utils;

import java.util.regex.Pattern;

/**
 * <p>
 * 用于字符校验
 * </p>
 * ClassName: Validate <br/>
 * Author: zhouye  <br/>
 * Date: 2017/5/17 10:52 <br/>
 * Version: 1.0 <br/>
 */
public class Validate {

    /**
     * 正则表达式：验证密码(包含大小写字母和数字的组合，可使用字符 !@#$%^&*，长度在6-20之间)
     */
    private static final String REGEX_PASSWORD = "([A-Z]|[a-z]|[0-9]|[,./!@#$%^&]){6,20}$";

    /**
     * 正则表达式：验证手机号
     */
    private static final String REGEX_MOBILE = "^1[3|4|5|7|8|9][0-9]\\d{8}$";

    /**
     * 正则表达式：验证昵称
     */
    public static final String REGEX_NAME = "^[0-9A-Za-z\\u4e00-\\u9fa5_\\-]+$";

    /**
     * 验证18位身份证
     */
    private static final String APPLICANT_ID_NUM2 = "^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0[1-9])|(1[0-2]))(0[1-9]|([1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[X])$)$";

    /**
     * 验证价格的正则
     */
    private static final String PRICE = "(^[1-9]\\d*(\\.\\d{1,2})?$)|(^0(\\.\\d{1,2})?$)";

    /**
     * 真实姓名的格式检验
     */
    private static final String REAL_NAME = "^[a-zA-Z\\u4e00-\\u9fa5 ]{1,10}$";

    /**
     * 数字的格式检验
     */
    private static final String NUMBER = "^[0-9]*$";

    /**
     * 邮箱的校验
     */
//    private static final String EMAIL = "^([0-9A-Za-z\\-_\\.]+)@([0-9a-z]+\\.[a-z]{2,3}(\\.[a-z]{2})?)$";
    private static final String EMAIL = "[^\\\\<>%'\"]{6,64}";
    /**
     * 时间格式yyyy-MM-dd hh:mm:ss
     */
    public static final String TIME_TYPE = "yyyy-MM-dd hh:mm:ss";
    /**
     * 时间格式yyyy-MM-dd
     */
    public static final String TIME_TYPE_YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * <p>
     * 验证价格格式
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/8/22 10:43
     *
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPrice(String price) {
        price.trim();
        return Pattern.matches(PRICE, price);
    }

    /**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        mobile = mobile.trim();
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    /**
     * 校验图片格式
     *
     * @param imageType
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isImage(String imageType) {
        imageType = imageType.toLowerCase();
        if ("bmp".equals(imageType) || "jpg".equals(imageType) || "jpeg".equals(imageType) || "png".equals(imageType)) {
            return true;
        }
        return false;
    }

    /**
     * 校验密码
     *
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }

    /**
     * 不包含包含特殊符号
     * 长度不大于length
     * 校验通过返回true，否则返回false
     *
     * @param name
     * @param length
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isCommonString(String name, int length) {
        name = name.trim();
        boolean flag = true;
        if (name.length() <= length) {
            for (int i = 0; i < name.length(); i++) {
                if (name.charAt(i) == '\\' || name.charAt(i) == '<'
                        || name.charAt(i) == '>' || name.charAt(i) == '"'
                        || name.charAt(i) == '%') {
                    flag = false;
                    break;
                }
            }
        } else {
            flag = false;
        }
        return flag;
    }

    /**
     * <p>
     * 验证汉字 长度1-10
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/8/2 14:48
     * return response
     */
    public static boolean isChinese(String name, int length) {
        name = name.trim();
        if (name.length() > length) {
            return false;
        }
        return Pattern.matches(REAL_NAME, name);
    }

    /**
     * 检验身份证号码
     *
     * @param name
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isApplicantIdNum(String name) {
        name = name.trim();
        return Pattern.matches(APPLICANT_ID_NUM2, name);
    }

    /**
     * <p>
     * 验证纯数字 1-size之间
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/11/8 16:36
     */
    public static boolean isNum(String num, int size) {
        num = num.trim();
        if (Pattern.matches(NUMBER, num)) {
            int s = Integer.valueOf(num);
            if (s <= size && s > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * <p>
     * 验证纯数字 1-size之间
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/11/8 16:36
     */
    public static boolean isSearchNum(String num, int size) {
        num = num.trim();
        if (Pattern.matches(NUMBER, num)) {
            int s = Integer.valueOf(num);
            if (s <= size && s >= 0) {
                return true;
            }
        }
        return false;
    }

    private static final int sizeEmailA = 6;
    private static final int sizeEmailB = 64;

    /**
     * <p>
     * 验证邮箱
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/11/8 19:18
     */
    public static boolean isEmail(String email) {
        email = email.trim();
        return email.matches(EMAIL);
    }

    /**
     * <p>
     * 验证姓名的格式
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/8/2 14:48
     * return response
     */
    public static boolean isPeopleName(String name) {
        return Pattern.matches(REAL_NAME, name);
    }

    /**
     * <p>
     * 验证纯数字
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/11/8 16:36
     */
    public static boolean isSearchNum(String num) {
        num = num.trim();
        if (Pattern.matches(NUMBER, num)) {
            return true;
        }
        return false;
    }
}
