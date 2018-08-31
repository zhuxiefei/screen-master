package com.betel.estatemgmt.utils.wechatpay;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * Created by zhouye on 2017/9/15 0015.
 */
public class MD5Util {
    private static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++){
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {n += 256;}
        int d1 = n / 16;
        int d2 = n % 16;
        return HEX_DIGITS[d1] + HEX_DIGITS[d2];
    }

    public static String md5Encode(String origin, String charsetname) throws UnsupportedEncodingException, NoSuchAlgorithmException {
            String resultString = origin;
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname)){
                resultString = byteArrayToHexString(md.digest(resultString.getBytes("UTF-8")));
            }
            else {
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
            }
        return resultString;
    }

    private static final String[] HEX_DIGITS = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
}