
package com.betel.estatemgmt.utils;

import java.util.Random;

/**
 * <p>
 * 字符串工具
 * </p>
 * ClassName: StringUtil <br/>
 * Author: Du.Hx  <br/>
 * Date: 2017/5/10 19:19 <br/>
 * Version: 1.0 <br/>
 */
public class StringUtil {

    /**
     * 默认随机数范围
     */
    public static final String DEFAULT_RANDOM_NUM = "0123456789";

    /**
     * 默认随机数字字母范围
     */
    public static final String DEFAULT_RANDOM_NUM_AND_LETTER =
            "0123456789abcdefghijklmnopqrstuvwxyz" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * <p>
     * 判断字符或字符串串是否为空（null或""）
     * </p>
     * <pre>
     * StringUtil.isEmpty(null)      = true
     * StringUtil.isEmpty("")        = true
     * StringUtil.isEmpty(" ")       = false
     * StringUtil.isEmpty("bob")     = false
     * StringUtil.isEmpty("  bob  ") = false
     * </pre>
     * Author: Du.hx <br/>
     * Date: 2017/5/10 19:21
     *
     * @param str 判断的字符串
     * @return 如果字符串是空（null或""），返回true
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * <p>
     * 判断字符串是否为空白（null、""或" "）
     * </p>
     * <pre>
     *     StringUtils.isBlank(null)      = true
     *     StringUtils.isBlank("")        = true
     *     StringUtils.isBlank(" ")       = true
     *     StringUtils.isBlank("bob")     = false
     *     StringUtils.isBlank("  bob  ") = false
     * </pre>
     * Author: Du.hx <br/>
     * Date: 2017/5/10 19:44
     *
     * @param str 判断的字符串
     * @return 如果字符串是空白（null、""或" "），返回true
     */
    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>
     * 根据指定字符分割字符串并生成数组
     * </p>
     * Author: Du.hx <br/>
     * Date: 2017/5/10 19:51
     *
     * @param str    待分割字符串
     * @param symbol 分割符
     * @return 分割后数组
     */
    public static String[] splitStr2Array(String str, String symbol) {
        if (!isBlank(str) && null != symbol) {
            return str.split(symbol);
        }
        return null;
    }

    /**
     * <p>
     * 根据指定字符组合字符串数组并生成字符串
     * </p>
     * Author: Du.hx <br/>
     * Date: 2017/5/10 19:58
     *
     * @param array  待组合数组
     * @param symbol 组合符
     * @return 以组合符连接数组元素的字符串
     */
    public static String uniteArray2Str(String[] array, String symbol) {
        StringBuilder sb = new StringBuilder();
        String result = "";
        if (null != array && null != symbol) {
            for (String temp : array) {
                if (null != temp && temp.trim().length() > 0) {
                    sb.append(temp);
                    sb.append(symbol);
                }
            }
            if (sb.length() > 1 && !isEmpty(symbol)) {
                result = sb.toString();
                result = result.substring(0, result.length() - symbol.length());
            }
        }
        return result;
    }

    /**
     * <p>
     * 指定长度，随机生成包含大小写字母、数字的字符串
     * </p>
     * Author: Du.hx <br/>
     * Date: 2017/5/12 11:40
     *
     * @param length   指定生成的长度
     * @param strRange 随机字符范围
     * @return 返回定长的随机字符串
     */
    public static String getRandomStr(int length, String strRange) {
        StringBuilder buffer = new StringBuilder(strRange);
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int range = buffer.length();
        for (int i = 0; i < length; i++) {
            sb.append(buffer.charAt(random.nextInt(range)));
        }
        return sb.toString();
    }



}
