package com.betel.estatemgmt.business.web.material.model;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 关于家装建材的类型集合
 * </p>
 * ClassName: MaterialTypeList <br/>
 * Author: zhangjian <br/>
 * Date: 2017/6/21 14:21 <br/>
 * Version: 1.0 <br/>
 */
public class MaterialTypeList {

    public static List<String> materialTypeList = new ArrayList<>();

    static {
        materialTypeList.add("全屋定制");
        materialTypeList.add("卫浴用品");
        materialTypeList.add("厨房用品");
        materialTypeList.add("墙地面");
        materialTypeList.add("灯具灯饰");
        materialTypeList.add("电子五金");
    }
}
