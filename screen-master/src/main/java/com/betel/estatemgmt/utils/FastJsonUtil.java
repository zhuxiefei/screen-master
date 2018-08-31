package com.betel.estatemgmt.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;


import java.util.List;

/**
 * Created by zhangjian on 2018/2/26.
 */
public class FastJsonUtil {
    /**
     * 将对象转换成json字符串。
     * <p>Title: objectToJson</p>
     * <p>Description: </p>
     *
     * @param data
     * @return
     */
    public static String objectToJson(Object data) {
        try {
            String string = JSON.toJSONString(data);
            return string;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将json数据转换成pojo对象list
     * <p>Title: jsonToList</p>
     * <p>Description: </p>
     *
     * @param jsonData
     * @param beanType
     * @return
     */
    public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) {
        try {
            List<T> list = JSON.parseArray(jsonData, beanType);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将json结果集转化为对象
     *
     * @param jsonData json数据
     * @return
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        try {
            T t = JSON.parseObject(jsonData, beanType);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将json字符串转化成List<String>对象
     *
     * @param jsonData
     * @return
     */
    public static List<String> jsonToStringList(String jsonData) {
        try {
            List<String> list2 = JSON.parseObject(jsonData, new TypeReference<List<String>>() {
            });
            return list2;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
