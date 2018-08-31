/*
 *   ©2016 ALL Rights Reserved DHX
 *  　　   ┏┓   ┏┓
 *  　　 ┏━┛┻━━━┛┻━┓
 *   　　┃         ┃
 *   　　┃    ━    ┃
 *   　　┃  ┳┛ ┗┳  ┃
 *   　　┃         ┃
 *   　　┃    ┻    ┃
 *   　　┗━┓     ┏━┛
 *         ┃    ┃  Code is far away from bug with the animal protecting
 *         ┃    ┃    神兽保佑,代码无bug
 *         ┃    ┗━━━━━┓
 *         ┃          ┣┓
 *         ┃          ┏┛
 *         ┗┓┓┏━━━━┓┓┏┛
 *          ┃┫┫    ┃┫┫
 *          ┗┻┛    ┗┻┛
 *   ━━━━━━感觉萌萌哒━━━━━━
 *
 */

package com.betel.estatemgmt.utils;

import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.common.ConfigManager;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * <p>
 * 文件管理工具类
 * </p>
 * ClassName: FileUtil <br/>
 * Author: Du.Hx  <br/>
 * Date: 2017/5/17 13:44 <br/>
 * Version: 1.0 <br/>
 */
public class FileUtil {

    private static final String PRIVACY_FILE_PATH = ConfigManager.read(ConfigName.PRIVACY_FILE_DIR);

    /**
     * <p>
     * 文件上传
     * </p>
     * Author: Du.hx <br/>
     * Date: 2017/5/17 14:24
     *
     * @param multipartFile 文件流
     * @param fileDir       存储文件夹
     * @return 上传后的新文件名称
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public static String uploadFile(MultipartFile multipartFile, String fileDir)
            throws IOException, NoSuchAlgorithmException {

        // 获取文件原始名称
        String fileName = multipartFile.getOriginalFilename();
        // 处理文件名获得新名称
        fileName = getFileName(fileName);
        File targetFile = new File(fileDir, fileName);

        if (!targetFile.exists()) {
            if (targetFile.mkdirs()) {
                // 保存
                multipartFile.transferTo(targetFile);
            }
        }
        return fileName;
    }

    /**
     * <p>
     * 下载文件
     * </p>
     * Author: Du.hx <br/>
     * Date: 2017/5/17 14:23
     *
     * @param response 当前response
     * @param filePath 文件物理路径
     */
    public static void downloadFile(HttpServletResponse response, String filePath) {

        File downloadFile = new File(filePath);

        InputStream in = null;
        OutputStream os = null;
        try {
            in = new FileInputStream(downloadFile);
            response.setCharacterEncoding("utf-8");

            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition",
                    "attachment;fileName=" + new String(downloadFile.getName().getBytes("utf-8"), "iso8859-1"));
            // response.setContentLengthLong(downloadFile.length());
            os = response.getOutputStream();
            byte[] b = new byte[6 * 1024];
            int length;
            while ((length = in.read(b)) > 0) {
                os.write(b, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭io流
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(os);

        }
    }

    /**
     * <p>
     * 删除文件
     * </p>
     * Author: Du.hx <br/>
     * Date: 2017/5/17 14:37
     *
     * @param filePath 文件物理路径
     * @return 删除结果，true删除成功，false删除失败
     */
    public static boolean deletefile(String filePath) {
        boolean result = true;
        File file = new File(filePath);

        if (!file.isDirectory()) {
            result = file.delete();
        } else if (file.isDirectory()) {
            String[] fileList = file.list();
            if (null != fileList && fileList.length != 0) {
                for (int i = 0; i < fileList.length; i++) {
                    File delFile = new File(filePath + "/" + fileList[i]);
                    if (!delFile.isDirectory()) {
                        result = delFile.delete();
                    } else if (delFile.isDirectory()) {
                        deletefile(filePath + "/" + fileList[i]);
                    }
                }
                result = file.delete();
            }
        }
        return result;
    }

    /**
     * <p>
     * 使用MD5生成唯一文件名
     * </p>
     * Author: Du.hx <br/>
     * Date: 2017/5/17 14:23
     *
     * @param fileStr 原始文件名
     * @return 唯一文件名
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public static String getFileName(String fileStr) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        int lastIndexOfDot = fileStr.lastIndexOf('.');
        int fileNameLength = fileStr.length();
        // 获取文件后缀名
        final String extension = fileStr.substring(lastIndexOfDot + 1, fileNameLength);
        //uuid生成文件名
        return UUID.randomUUID() + "." + extension;
    }

    /**
     * <p>
     * 将String字符串写入txt文件
     * </p>
     * Author: Xia.yx <br/>
     * Date:  9:22
     *
     * @param fileContent 保存内容
     * @param filePath    保存txt文件
     * @return 保存txt文件
     */
    public static String uploadFile(String filePath, String fileContent) throws Exception {
        File file = new File(PRIVACY_FILE_PATH + filePath);
        File fileParent = file.getParentFile();
        if (!fileParent.exists()) {
            fileParent.mkdirs();
        }
        file.createNewFile();
        PrintStream printStream = new PrintStream(new FileOutputStream(file));
        printStream.print(fileContent);
        if (null!=printStream){
            IOUtils.closeQuietly(printStream);
        }
        return filePath;
    }

    /**
     * <p>
     * 读取txt
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/9/4 9:57
     *
     * @Param filePath 文件路径
     * @Return 文件内容
     */
    public static String readTxt(String filePath) throws Exception {
        StringBuilder result = null;
        File file = new File(filePath);
        result = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String s = null;
        while ((s = br.readLine()) != null) {
            result.append(s);
        }
        return result.toString();
    }
}
