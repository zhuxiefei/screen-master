package com.betel.estatemgmt.utils;

import java.util.UUID;

/**
 * <p>
 * UUID生成工具
 * </p>
 * ClassName: UuidUtil <br/>
 * Author: Du.Hx  <br/>
 * Date: 2017/9/21 15:30 <br/>
 * Version: 1.0
 */
public class UuidUtil {

    /**
     * <p>
     * 创建UUID
     * </p>
     * Author: Du.hx <br/>
     * Date: 2017/9/21 15:33
     *
     * @return 去除“-”的UUID
     */
    public static String create() {
        String uuidStr = UUID.randomUUID().toString();
        uuidStr = uuidStr.replaceAll("-", "");
        return uuidStr;
    }
}
