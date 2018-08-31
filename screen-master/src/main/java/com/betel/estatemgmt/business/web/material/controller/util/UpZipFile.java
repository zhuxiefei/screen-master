package com.betel.estatemgmt.business.web.material.controller.util;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Enumeration;

/**
 * <p>
 * 解压zip包
 * </p>
 * ClassName: UpZipFile <br/>
 * Author: zhangjian  <br/>
 * Date: 2017/6/28 13:53 <br/>
 * Version: 1.0 <br/>
 */
public class UpZipFile {
    private static final Logger LOG = LoggerFactory.getLogger(UpZipFile.class);

    /**
     * 并发
     *
     * @param zipFileName 源文件
     * @param targetPath  指定目录
     * @throws Exception
     */
    public static synchronized void unzip(String zipFileName, String targetPath) throws Exception {
        unZipFiles(zipFileName, targetPath);
    }

    /**
     * 解压zip格式的压缩文件到指定位置
     *
     * @param zipFileName 压缩文件
     * @param extPlace    解压目录
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static boolean unZipFiles(String zipFileName, String extPlace) throws Exception {
        System.setProperty("sun.zip.encoding", System.getProperty("sun.jnu.encoding"));
        try {
            (new File(extPlace)).mkdirs();
            File f = new File(zipFileName);
            ZipFile zipFile = new ZipFile(zipFileName, "GBK"); // 处理中文文件名乱码的问题
            if ((!f.exists()) && (f.length() <= 0)) {
                throw new Exception("要解压的文件不存在!");
            }
            String strPath, gbkPath, strtemp;
            File tempFile = new File(extPlace);
            strPath = tempFile.getAbsolutePath();
            Enumeration<?> e = zipFile.getEntries();
            while (e.hasMoreElements()) {
                ZipEntry zipEnt = (ZipEntry) e.nextElement();
                gbkPath = zipEnt.getName();
                if (zipEnt.isDirectory()) {
                    strtemp = strPath + File.separator + gbkPath;
                    File dir = new File(strtemp);
                    dir.mkdirs();
                    continue;
                } else {
                    // 读写文件
                    InputStream is = zipFile.getInputStream(zipEnt);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    gbkPath = zipEnt.getName();
                    strtemp = strPath + File.separator + gbkPath;

                    // 建目录
                    String strsubdir = gbkPath;
                    for (int i = 0; i < strsubdir.length(); i++) {
                        if ("/".equalsIgnoreCase(strsubdir.substring(i, i + 1))) {
                            String temp = strPath + File.separator + strsubdir.substring(0, i);
                            File subdir = new File(temp);
                            if (!subdir.exists()) {
                                subdir.mkdir();
                            }
                        }
                    }
                    FileOutputStream fos = new FileOutputStream(strtemp);
                    BufferedOutputStream bos = new BufferedOutputStream(fos);
                    int c;
                    while ((c = bis.read()) != -1) {
                        bos.write((byte) c);
                    }
                    bos.close();
                    fos.close();
                }
            }
            f.delete();
            return true;
        } catch (Exception e) {
            LOG.error("========web/user/v1/解压zip  error========", e);
            return false;
        }

    }
}
