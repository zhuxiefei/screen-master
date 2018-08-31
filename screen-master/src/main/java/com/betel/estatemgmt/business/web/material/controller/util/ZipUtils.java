package com.betel.estatemgmt.business.web.material.controller.util;

import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.business.web.material.code.BuildMaterialCode;
import com.betel.estatemgmt.business.web.material.constant.BuildMaterialValidation;
import com.betel.estatemgmt.business.web.material.model.BuildMaterialFileStream;
import com.betel.estatemgmt.common.ConfigManager;
import com.betel.estatemgmt.common.RedisManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * zip工具包
 * </p>
 * ClassName: UpZipFile <br/>
 * Author: zhangjian  <br/>
 * Date: 2017/6/28 13:53 <br/>
 * Version: 1.0 <br/>
 */
public class ZipUtils {
    private static final Logger LOG = LoggerFactory.getLogger(ZipUtils.class);

    /**
     * @param targetPath 解压zip后的存放文件路径
     * @return zip里文件流集合对象
     * @throws FileNotFoundException
     */
    public static List<BuildMaterialFileStream> readUnzipFileStream(String targetPath) throws FileNotFoundException {
        //创建容器
        List<BuildMaterialFileStream> fileMaps = new ArrayList<>();
        //测试专用
//        targetPath="e:/20170625104702";
        //由指定路径创建目标文件
        File file = new File(targetPath);
        //获得指定路径下的文件集合，包含文件夹
        File[] tempList = file.listFiles();
        LOG.info("======web/material/v1/importBuildMaterial start========该目标目录下文件个数：" + tempList.length);
        if (tempList.length == 0) {
            LOG.info("======web/material/v1/importBuildMaterial start========文件为空");
            return null;
        }
        //遍历文件集合，解析目标文件集合内容
        for (int i = 0; i < tempList.length; i++) {
            String fileName = tempList[i].getName(); //文件名
            Long fileSize = tempList[i].length();//文件长度
            InputStream in = new FileInputStream(tempList[i]);//获得文件流
            BuildMaterialFileStream fs = new BuildMaterialFileStream();
            fs.setFileName(fileName);
            fs.setFileSize(fileSize);
            fs.setIn(in);
            if (tempList[i].isFile()) {
                fs.setFile(true);
            } else if (tempList[i].isDirectory()) {
                fs.setFile(false);
            }
            fileMaps.add(fs);
        }
        return fileMaps;
    }

    /**
     * zip里图片名称集合
     *
     * @param bmfs 建材zip集合流
     * @return
     */
    public static List<String> getImagelList(List<BuildMaterialFileStream> bmfs) {
        List<String> zipPicNames = new ArrayList<>();
        for (int i = 0; i < bmfs.size(); i++) {
            if (checkImage(bmfs.get(i).getFileName())) {
                zipPicNames.add(bmfs.get(i).getFileName());
            }

        }
        return zipPicNames;
    }

    /**
     * Zip里实体图片流Map集合
     *
     * @param bmfs 建材zip集合流
     * @return
     */
    public static Map<String, Object> getImagelMapStrem(List<BuildMaterialFileStream> bmfs) {
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < bmfs.size(); i++) {
            if (checkImage(bmfs.get(i).getFileName())) {
                map.put(bmfs.get(i).getFileName(), bmfs.get(i).getIn());
            }
        }
        return map;
    }


    /**
     * Zip里实体图片流ListMap集合
     *
     * @param bmfs
     * @return
     */
    public static List<Map<String, Object>> getImagelListMap(List<BuildMaterialFileStream> bmfs) {
        List<Map<String, Object>> zipPicNameStremList = new ArrayList<>();
        for (int i = 0; i < bmfs.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            if (checkImage(bmfs.get(i).getFileName())) {
                map.put(bmfs.get(i).getFileName(), bmfs.get(i).getIn());
                zipPicNameStremList.add(map);
            }
        }
        return zipPicNameStremList;
    }

    /**
     * 并发
     *
     * @param zipFileUrl 源文件
     * @throws Exception
     */
    public static synchronized String archivedZipFiles(MultipartFile zipFileUrl) {
        return archivedOfZipFiles(zipFileUrl);
    }

    /**
     * 转存
     *
     * @param zipFileUrl 文件流
     * @return
     */
    public static String archivedOfZipFiles(MultipartFile zipFileUrl) {
        String newFilePath = "";
        //把spring文件上传的MultipartFile转换成CommonsMultipartFile类型
        CommonsMultipartFile cf = (CommonsMultipartFile) zipFileUrl; //获取本地存储路径
        File file = new File(ConfigManager.read(ConfigName.FILE_DIR) + "importBuildMaterial/");
        //创建一个目录 （它的路径名由当前 File 对象指定，包括任一必须的父路径。）
        if (!file.exists()) {
            file.mkdirs();
        }
        newFilePath = ConfigManager.read(ConfigName.FILE_DIR) + "importBuildMaterial/" + zipFileUrl.getOriginalFilename();
        //新建一个文件
        File file1 = new File(newFilePath);
        //将上传的文件写入新建的文件中
        try {
            cf.getFileItem().write(file1);
        } catch (Exception e) {
            LOG.error("========web/user/v1/转存zip  error========", e);
        }
        return newFilePath;
    }

    /**
     * 校验zip文件里内容是否合法
     *
     * @param buildMaterialFileStreamList 建材zip集合流
     * @return
     */
    public static String checZipFile(List<BuildMaterialFileStream> buildMaterialFileStreamList) {
        String code = "";
        int count2003 = 0;
        //zip是空的内容，只是不会出现的情况
        if (buildMaterialFileStreamList == null) {
            return code = BuildMaterialCode.METERIAL_TARGET_FILE_IS_NULL;
        }

        //zip是里有文件夹存在
        for (BuildMaterialFileStream buildMaterialFileStream : buildMaterialFileStreamList) {
            if (!buildMaterialFileStream.isFile()) {
                return code = BuildMaterialCode.METERIAL_UPLOAD_ZIP_CONTAIN_IS_DIRECTORY;
            }
            //文件excel文件的格式和个数，已经图片的大小格式
            if (checkExcel2007(buildMaterialFileStream.getFileName())) {
                return code = BuildMaterialCode.METERIAL_UPLOAD_ZIP_CONTAIN_EXCEL2007;
            }
            if (checkExcel2003(buildMaterialFileStream.getFileName())) {
                count2003++;
            }
            if (checkImage(buildMaterialFileStream.getFileName())) {
                if (buildMaterialFileStream.getFileSize() > BuildMaterialValidation.MATERIAL_PIC_LENGTH_MAX_RULE) {
                    return code = BuildMaterialCode.MATERIAL_PICTURE_SIZE_OVER_LIMITE;
                }
            }

        }
        if (count2003 == 0) {
            return code = BuildMaterialCode.METERIAL_UPLOAD_ZIP_CONTAIN_EXCEL2003_NOT_EXIST;
        }
        if (count2003 > 1) {
            return code = BuildMaterialCode.METERIAL_UPLOAD_ZIP_CONTAIN_EXCEL2003_MORE_LIMIT;

        }


        return code;
    }

    /**
     * 检查文件是否为图片
     *
     * @param fileName 文件名
     * @return
     */
    public static boolean checkImage(String fileName) {
        List<String> supportedImgType = new ArrayList();
        supportedImgType.add("png");
        supportedImgType.add("jpg");
        supportedImgType.add("bmp");
        supportedImgType.add("jpeg");
        String[] fileNameArr = fileName.split("\\.");
        if (fileNameArr.length > 1 && supportedImgType.contains(fileNameArr[fileNameArr.length - 1])) {
            return true;
        }
        return false;
    }

    /**
     * 检查文件是否为excel2003 文件名
     *
     * @param fileName
     * @return
     */
    public static boolean checkExcel2003(String fileName) {
        List<String> supportedExcelType = new ArrayList();
        supportedExcelType.add("xls");
        String[] fileNameArr = fileName.split("\\.");
        if (fileNameArr.length > 1 && supportedExcelType.contains(fileNameArr[fileNameArr.length - 1])) {
            return true;
        }
        return false;
    }

    /**
     * 检查文件是否为excel2007
     *
     * @param fileName 文件名
     * @return
     */
    public static boolean checkExcel2007(String fileName) {
        List<String> supportedExcelType = new ArrayList();
        supportedExcelType.add("xlsx");
        String[] fileNameArr = fileName.split("\\.");
        if (fileNameArr.length > 1 && supportedExcelType.contains(fileNameArr[fileNameArr.length - 1])) {
            return true;
        }
        return false;
    }
}
