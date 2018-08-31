/*
 *   ©2016 ALL Rights Reserved DHX
 *  　　   ┏┓   ┏┓
 *  　　 ┏━┛┻━━━┛┻━┓
 *   　　┃         ┃
 *   　　┃    ━    ┃
 *   　　┃  ┳┛ ┗┳  ┃
 *   　　┃         ┃
 *   　　┃    ┻    ┃
 *   　　┗━┓     ┏━┛
 *         ┃    ┃  Code is far away from bug with the animal protecting
 *         ┃    ┃    神兽保佑,代码无bug
 *         ┃    ┗━━━━━┓
 *         ┃          ┣┓
 *         ┃          ┏┛
 *         ┗┓┓┏━━━━┓┓┏┛
 *          ┃┫┫    ┃┫┫
 *          ┗┻┛    ┗┻┛
 *   ━━━━━━感觉萌萌哒━━━━━━
 *
 */

package com.betel.estatemgmt.utils.encrypt;

import com.betel.estatemgmt.utils.StringUtil;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <p>
 * Md5加密工具
 * </p>
 * ClassName: Md5Util <br/>
 * Author: Du.Hx  <br/>
 * Date: 2017/5/17 14:05 <br/>
 * Version: 1.0 <br/>
 */
public class Md5Util {

    // 全局数组
    private final static String[] STR_DIGITS = { "J", "1", "2", "R", "4", "5", "6", "S", "8", "9", "a", "b", "N", "d",
            "e", "K" };

    public Md5Util() {
    }

    // 返回形式为数字跟字符串
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        // System.out.println("iRet="+iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return STR_DIGITS[iD1] + STR_DIGITS[iD2];
    }

    // 转换字节数组为16进制字串
    private static String byteToString(byte[] bByte) {
        StringBuilder sb = new StringBuilder();
        for (byte aBByte : bByte) {
            sb.append(byteToArrayString(aBByte));
        }
        return sb.toString();
    }

    /**
     * <p>
     * 获取MD5值
     * </p>
     * Author: Du.hx <br/>
     * Date: 2017/5/17 14:06
     *
     * @param strObj 待加密字符串
     * @return MD5加密
     */
    public static String getMd5Code(String strObj) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        if (StringUtil.isEmpty(strObj)) {
            return strObj;
        }
        MessageDigest md = MessageDigest.getInstance("MD5");
        // md.digest() 该函数返回值为存放哈希值结果的byte数组
        strObj = byteToString(md.digest(strObj.getBytes("UTF-8")));

        return strObj;
    }
}
