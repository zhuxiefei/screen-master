package com.betel.estatemgmt.business.web.material.constant;

import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.common.ConfigManager;
import com.betel.estatemgmt.common.RedisManager;

/**
 * <p>
 * 家装建材静态文科
 * </p>
 * ClassName: BuildMaterialConstant <br/>
 * Author: zhangjian  <br/>
 * Date: 2017/6/21 13:56 <br/>
 * Version: 1.0 <br/>
 */
public interface BuildMaterialConstant {
    /**
     * 文件物理路径
     */
    String FILE_DIR_PATH = ConfigManager.read(ConfigName.FILE_DIR);
    /**
     * 文件服务器路径
     */
    String FILE_SERVER_PATH = ConfigManager.read(ConfigName.FILE_SERVER);
    /**
     * 操作类型
     */
    int ACTION_TYPE = 2;
    /**
     * 建材的长度
     */
    int LENGTH_VALUE = 128;
    /**
     * 建材名称长度
     */
    int NAME_LENGTH = 20;
    /**
     * 删除建材不存在时返回值常量
     */
    int FLAG_MATERIAL_NOT_EXIST = -2;
}
