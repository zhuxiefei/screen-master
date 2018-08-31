package com.betel.estatemgmt.business.web.document.constant;

import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.common.ConfigManager;
import com.betel.estatemgmt.common.RedisManager;

/**
 * <p>
 * 资料管理常量
 * </p>
 * ClassName: DocumentDataValidation <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/10/12 15:35 <br/>
 * Version: 1.0 <br/>
 */
public interface DocumentDataValidation {

    /**
     * 资料名称格式
     */
    String DOC_NAME_RULE = "[^\\\\<>%'\"]{1,30}";

    /**
     * 资料内容最大长度
     */
    Integer DOC_CONTENT_SIZE = 100000;

    /**
     * 资料类别名称格式
     */
    String TYPE_NAME_RULE = "[^\\\\<>%'\"]{1,6}";

    /**
     * 图片格式校验
     */
    String PIC_TYPE_RULE = "(doc|docx|pdf)";

    /**
     * 文件大小不超过20M
     */
    Long FILE_SIZE = 20*1024*1024L;

    /**
     * 原始文件名长度
     */
    Integer FILE_NAME_SIZE = 120;

    /**
     * 文件上传物理路径
     */
    String FILE_PATH = ConfigManager.read(ConfigName.FILE_DIR);

    /**
     * 文件下载服务器路径
     */
    String FILE_SERVER_PATH = ConfigManager.read(ConfigName.FILE_SERVER);

    /**
     * 0表示能下载标识
     */
    Integer IS_DOWNLOAD = 0;

    /**
     * 1表示能下载标识
     */
    Integer NOT_DOWNLOAD = 1;
}
