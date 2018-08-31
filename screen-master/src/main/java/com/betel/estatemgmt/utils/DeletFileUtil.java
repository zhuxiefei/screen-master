package com.betel.estatemgmt.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class DeletFileUtil {
    private static final Logger LOG = LoggerFactory.getLogger(DeletFileUtil.class);
    private static final int TYE_COUNT = 10;

    /***
     * 删除指定文件夹下所有文件
     *
     * @param path 文件夹完整绝对路径
     *             Author: zhangjian  <br/>
     *             Date: 2017/6/29 13:53 <br/>
     *             Version: 1.0 <br/>
     * @return
     */
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.getAbsoluteFile().delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + File.separator + tempList[i]);// 先删除文件夹里面的文件
                delFolder(path + File.separator + tempList[i]);// 再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    /***
     * 删除指定文件夹下所有文件
     *
     * @param path 文件夹完整绝对路径
     *             Author: zhangjian  <br/>
     *             Date: 2017/6/29 13:53 <br/>
     *             Version: 1.0 <br/>
     * @return
     */
    public static boolean delAllFileOfGarbageCollection(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.getAbsoluteFile().delete();
                forceDelete(temp);
            }
            if (temp.isDirectory()) {
                delAllFile(path + File.separator + tempList[i]);// 先删除文件夹里面的文件
                delFolder(path + File.separator + tempList[i]);// 再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    /***
     * 删除文件夹
     * Author: zhangjian  <br/>
     * Date: 2017/6/29 13:53 <br/>
     * Version: 1.0 <br/>
     *
     * @param folderPath 文件夹完整绝对路径
     */
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); // 删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            myFilePath.getAbsoluteFile().delete(); // 删除空文件夹
        } catch (Exception e) {
            LOG.error("=========web/material/v1/delFolder !=========" + e);
        }
    }


    public static boolean forceDelete(File file) {
        boolean result = file.delete();
        int tryCount = 0;
        while (!result && tryCount++ < TYE_COUNT) {
            System.gc();    //回收资源
            result = file.delete();
        }
        return result;
    }

    public static boolean forceDeleteOne(File file) {
        boolean result = file.delete();
        if (!result) {
            System.gc();    //回收资源
            file.delete();
        }
        return result;
    }
}
