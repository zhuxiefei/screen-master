package com.betel.estatemgmt.business.web.material.controller.util;

import com.betel.estatemgmt.business.web.material.service.BuildMaterialService;
import com.betel.estatemgmt.common.BeanFactory;
import com.betel.estatemgmt.common.model.house.HouseMaterial;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 读取建材类型和名称工具
 * </p>
 * ClassName: MaterialUtil <br/>
 * Author: zhangjian  <br/>
 * Date: 2017/6/29 13:53 <br/>
 * Version: 1.0 <br/>
 */
public class MaterialUtil {
    private static final Logger LOG = LoggerFactory.getLogger(MaterialUtil.class);
    public static List<String> materialTypeList = new ArrayList<>();
    public static List<String> materialNameList = new ArrayList<>();

    /**
     * <p>
     * 判断建材类型是否存在
     * </p>
     *
     * @param curTypeName
     * @return true存在  false不存在
     */
    public static boolean findCurMaterialTypeNameIsExist(String curTypeName) {
        boolean isExcel = false;
        //如果类型集合为null，到数据库查询一下
        try {
            if (materialTypeList.size() == 0) {
                materialTypeList = BeanFactory.getBean(BuildMaterialService.class).findMaterialTypeNameList();
            }
            if (materialTypeList.size() > 0) {
                //第一次查询内存
                for (int i = 0; i < materialTypeList.size(); i++) {
                    if (curTypeName.equalsIgnoreCase(materialTypeList.get(i))) {
                        isExcel = true;
                        break;
                    }
                }
                //第二次查询数据库
                if (!isExcel) {
                    int count = BeanFactory.getBean(BuildMaterialService.class).selectMaterialTypeNameIsExcel(curTypeName);
                    if (count != 1) {
                        return false;
                    }
                }
                //返回
                if (isExcel) {
                    return true;
                }
            }

        } catch (Exception e) {
            LOG.error("read materialTypeName: [" + curTypeName + "] error", e);
        }
        return false;
    }

    /**
     * <p>
     * 判断建材是否重复
     * </p>
     *
     * @param curMaterialName
     * @return true重复  false不重复
     */
    public static boolean findCurMaterialNameListIsExist(String curMaterialName) {
        boolean isExcel = false;
        //如果类型集合为null，到数据库查询一下
        try {
            if (materialNameList.size() == 0) {
                materialNameList = BeanFactory.getBean(BuildMaterialService.class).findMaterialNameList();
            }
            if (materialNameList.size() > 0) {
                //第一次查询内存
                for (int i = 0; i < materialNameList.size(); i++) {
                    if (curMaterialName.equalsIgnoreCase(materialNameList.get(i))) {
                        isExcel = true;
                        break;
                    }
                }
                //第二次查询数据库
                if (!isExcel) {
                    HouseMaterial houseMaterial = new HouseMaterial();
                    houseMaterial.setMaterialName(curMaterialName);
                    int count = BeanFactory.getBean(BuildMaterialService.class).checkBuildMaterialIsExist(houseMaterial);
                    if (count > 0) {
                        return false;
                    }
                }
                //返回
                if (isExcel) {
                    return true;
                }
            }

        } catch (Exception e) {
            LOG.error("read materialTypeName: [" + curMaterialName + "] error", e);
        }
        return false;
    }
}
