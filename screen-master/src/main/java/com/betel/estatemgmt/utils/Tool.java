package com.betel.estatemgmt.utils;

import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.common.ConfigManager;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 工具类
 * </p>
 * Author: zhangjian  <br/>
 * Date: 2017/8/18 13:53 <br/>
 * Version: 1.0 <br/>
 */
public class Tool {
    private static final Logger LOG = LoggerFactory.getLogger(Tool.class);
    private static final String PRIVACY_FILE_PATH = ConfigManager.read(ConfigName.PRIVACY_FILE_DIR);
    /**
     * <p>
     * 批量时获得主键集合
     * </p>
     *
     * @param idsString 前端传入id集合
     * @return 去空格的Long类型的id集合
     * Author: zhangjian  <br/>
     * Date: 2017/8/18 13:53 <br/>
     * Version: 1.0 <br/>
     */
    public static List<Long> getIdList(String idsString) {
        List<Long> ids = new ArrayList<>();
        if (!StringUtil.isBlank(idsString)) {
            String[] curIdArr = idsString.trim().split(",");
            for (int i = 0; i < curIdArr.length; i++) {
                try {
                    ids.add(Long.valueOf(curIdArr[i].trim()));
                } catch (Exception e) {
                    LOG.error("===============Integer转换异常=======" + e);
                }
            }
        }
        return ids;
    }

    /**
     * <p>
     * 批量时获得主键集合
     * </p>
     *
     * @param idsString 前端传入id集合
     * @return 去空格的List<String>类型的id集合
     * Author: zhangjian  <br/>
     * Date: 2017/8/18 13:53 <br/>
     * Version: 1.0 <br/>
     */
    public static List<String> getIdStringArrayList(String idsString) {
        List<String> ids = new ArrayList<>();
        if (!StringUtil.isBlank(idsString)) {
            String[] curIdArr = idsString.trim().split(",");
            for (int i = 0; i < curIdArr.length; i++) {
                if (!StringUtil.isBlank(curIdArr[i])) {
                    ids.add(curIdArr[i].trim());
                }
            }
        }
        return ids;
    }

    /**
     * 获得id数组
     *
     * @param idsString
     * @return
     */
    public static String[] getIdArrOfStringType(String idsString) {
        if (!StringUtil.isBlank(idsString)) {
            String[] arrIds = idsString.trim().split(",");
            return arrIds;
        }
        return null;
    }


    /**
     * <p>
     * 判断两个数字大小
     * </p>
     *
     * @param minNum
     * @param maxNum
     * @return true  最小数大于最大数  如：20<30
     * Author: zhangjian  <br/>
     * Date: 2017/8/18 13:53 <br/>
     * Version: 1.0 <br/>
     */
    public static boolean compareNumber(String minNum, String maxNum) {
        if (StringUtil.isBlank(minNum) || StringUtil.isBlank(maxNum)) {
            return false;
        }
        try {
            int min = Integer.valueOf(minNum.trim()).intValue();
            int max = Integer.valueOf(maxNum.trim()).intValue();
            if (min > max) {
                return true;
            } else if (min <= max) {
                return false;
            }
        } catch (Exception e) {
            LOG.error("=========web/v1/非数字转换错误 error!=========", e);
            return false;
        }
        return false;
    }


    /**
     * <p>
     * 校验id是否为空
     * </p>
     *
     * @param idsStr id字符串且以逗号隔开
     * @return null  id为空
     * Author: zhangjian  <br/>
     * Date: 2017/8/22 13:53 <br/>
     * Version: 1.0 <br/>
     */
    public static String checkIdIsNull(String idsStr) {
        String code = "1";
        if (StringUtil.isBlank(idsStr)) {
            return null;
        }
        //校验帖子id
        String ids = idsStr.trim();
        String[] idsArr = ids.split(",");
        for (int i = 0; i < idsArr.length; i++) {
            if (StringUtil.isBlank(idsArr[i].trim())) {
                return null;
            }
        }
        return code;
    }

    /**
     * 上传文件
     *
     * @param fullPath
     * @param fullPath
     * @return
     * @throws Exception
     * @author jians.z
     */
    public static void uploadFile(InputStream inputStream, String fullPath) {
        //创建文件对象
        File file = new File(fullPath);
        //上传文件(url)
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                try {
                    if (null != inputStream) {
                        inputStream.close();
                    }
                } catch (IOException e1) {
                    LOG.error("---------------createNewFile--------------" + e1);
                }
                LOG.error("---------------createNewFile--------------" + e);
            }
        }
        //上传文件(url)
        try {
            FileUtils.copyInputStreamToFile(inputStream, file);
        } catch (IOException e) {
            LOG.error("---------------uploadFile---------------" + e);
        } finally {
            try {
                if (null != inputStream) {
                    inputStream.close();
                }
            } catch (IOException e) {
                LOG.error("---------------uploadFile---------------" + e);
            }
        }
    }
}
