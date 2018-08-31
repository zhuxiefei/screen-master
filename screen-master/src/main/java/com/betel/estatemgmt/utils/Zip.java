package com.betel.estatemgmt.utils;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * <p>
 * 压缩文件
 * </p>
 * ClassName: Zip <br/>
 * Author: zhouye  <br/>
 * Date: 2017/6/14 17:51 <br/>
 * Version: 1.0 <br/>
 */
public class Zip {
    /**
     * 实现将多个文件进行压缩，生成指定目录下的指定名字的压缩文件
     * Parameters:
     filename ：指定生成的压缩文件的名称
     temp_path ：指定生成的压缩文件所存放的目录
     list ：List集合：用于存放多个File（文件）
     * */
    public static boolean createZip(String filename, String tempPath, List<File> list) {
        File file = new File(tempPath);
        if(!file.exists()){
            if(!file.mkdir()){
                return false;
            };
        }
        File zipFile = new File(tempPath+File.separator+filename);
        InputStream input = null;
        ZipOutputStream zipOut = null;
        try {
            zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
            //ZipOutputStream:此类为以 ZIP 文件格式写入文件实现输出流过滤器。包括对已压缩和未压缩条目的支持。
            zipOut.setComment(file.getName());
            if (file.isDirectory()) {
                for (int i = 0; i < list.size(); ++i) {
                    input = new FileInputStream(list.get(i));
                    zipOut.putNextEntry(new ZipEntry( file.getName()+ File.separator + list.get(i).getName()));
                    byte[] b= new byte[2048];
                    while ((input.read(b)) != -1) {
                        zipOut.write(b);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(zipOut!=null){
                    zipOut.close();
                }
                if(input!=null){
                    input.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
