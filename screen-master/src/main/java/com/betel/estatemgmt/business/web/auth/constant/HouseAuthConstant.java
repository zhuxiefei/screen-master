package com.betel.estatemgmt.business.web.auth.constant;

import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.common.ConfigManager;
import com.betel.estatemgmt.common.RedisManager;


/**
 * <p>
 * 认证常量
 * </p>
 * ClassName: HouseAuthConstant <br/>
 * Author: zhanglei  <br/>
 * Date: 2017/6/20 11:23 <br/>
 * Version: 1.0 <br/>
 */
public interface HouseAuthConstant {

    /**
     * 认证状态：认证中 1
     */
    int AUTH_STATUS_UNDERWAY  = 1;

    /**
     * 认证状态：认证成功 2
     */
    int AUTH_STATUS_SUCCEED = 2;

    /**
     * 认证状态：认证失败 3
     */
    int AUTH_STATUS_FAILURE = 3;

    /**
     * 认证状态：撤销 4
     */
    int AUTH_STATUS_BACKOUT = 4;

    /**
     * 认证状态：删除 5
     */
    int AUTH_STATUS_DELETED = 5;

    /**
     * 文件下载根目录
     */
    String PICTURE_FILE_SERVER = "file.server";

    /**
     * 隐私文件物理路径
     */
    String PRIVACY_FILE_DIR_PATH = ConfigManager.read(ConfigName.PRIVACY_FILE_DIR);

    /**
     * 审核内容长度
     */
    int AUTH_CONTENT_LENGTH=400;

    /**
     * 通知类型：认证通知 2
     */
    int NOTICE_TYPE_AUTH=2;

    /**
     * 通知状态：未读 1
     */
    int NOTICE_STATUS_UNREAD=1;

    /**
     * 通知状态：已读 2
     */
    int NOTICE_STATUS_READ=2;

    /**
     * 通知状态：删除 3
     */
    int NOTICE_STATUS_DELETEDE=3;

    /**
     * 指令类型：户主认证 smart03
     */
    String SEND_TYPE_AUTH="smart02";

    /**
     * 指令编号：户主认证 3
     */
    String SEND_NO_HOUSEOWNER="3";

    /**
     * 数字10
     */
    int NUM_OFFEN_TEN=10;

    /**
     * 数字50
     */
    int NUM_OFFEN_FIFTY=50;


}
