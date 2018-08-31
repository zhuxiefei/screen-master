package com.betel.estatemgmt.utils.pagination.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Objects;


/**
 * @ClassName:StringUtils
 * @Description:
 * @author:CUI.xx
 * @Date:2016年9月26日上午10:43:34
 * @version 1.1
 */
public abstract class StringUtils {
	
	private final static Logger LOG = LoggerFactory.getLogger(StringUtils.class);

	/**
     * Checks if a CharSequence is empty ("") or null.
     * 
     * @author:CUI.xx
     * @Date:2016年9月26日上午10:44:32
     * @param cs
     * @return
     */
    public static boolean isEmpty(final String cs) {
        return cs == null || cs.length() == 0 || Objects.equals(cs, "null");
    }


    /**
     * Checks if a CharSequence is whitespace, empty ("") or null.
     * 
     * @author:CUI.xx
     * @Date:2016年9月26日上午10:44:32
     * @param cs
     * @return
     */
    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }

        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }

        return true;
    }


    /**
     * 打印实体类信息
     * 
     * @author:CUI.xx
     * @Date:2016年9月26日上午10:44:32
     * @param o
     * @return
     * @throws Exception 
     */
    public static String beanToString(Object o) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StringBuilder sb = new StringBuilder();
        sb.append("Bean [");
        Field[] farr = o.getClass().getDeclaredFields();
        for (Field field : farr) {
            try {
                field.setAccessible(true);
                sb.append(field.getName());
                sb.append("=");
                if (field.get(o) instanceof Date) {
                    // 日期的处理
                    sb.append(sdf.format(field.get(o)));
                } else {
                    sb.append(field.get(o));
                }
                sb.append("|");
            } catch (Exception e) {
                throw e;
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * map对象转字符串
     * 
     * @author:CUI.xx
     * @Date:2016年9月26日上午10:44:32
     * @param map
     * @return
     */
    public static String mapToString(Map<String,Object> map) {
        StringBuilder sb = new StringBuilder();
        sb.append("Map:{");
        for (Object obj : map.entrySet()) {
            @SuppressWarnings("rawtypes")
			Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            Object value = entry.getValue();
            sb.append(key).append("=").append(value);
            sb.append(" ");
        }
        sb.append("}");
        return sb.toString();
    }

}
