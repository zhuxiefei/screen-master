package com.betel.estatemgmt.business.propertyapp.task.constant;

import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.common.ConfigManager;
import com.betel.estatemgmt.common.RedisManager;

/**
 * <p>
 * 任务常量
 * </p>
 * ClassName: TaskConstant <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/12/21 17:22 <br/>
 * Version: 1.0 <br/>
 */
public interface TaskConstant {

    /**
     * 拒绝操作
     */
    String DO_REFUSE = "1";

    /**
     * 接受操作
     */
    String DO_ACCEPT = "2";

    /**
     * 完成操作
     */
    String DO_FINISH = "3";

    /**
     * 文件服务器路径
     */
    String FILE_SERVER = ConfigManager.read(ConfigName.FILE_SERVER);

    /**
     * 文件物理路径
     */
    String FILE_PATH = ConfigManager.read(ConfigName.FILE_DIR);

    /**
     * 待接受状态
     */
    Integer TASK_WAIT_ACCEPT = 1;

    /**
     * 进行中状态
     */
    Integer TASK_IS_DOING = 2;

    /**
     * 已完成状态
     */
    Integer TASK_IS_FINISHED = 3;

    /**
     * 超时未完成状态
     */
    Integer TASK_IS_OVER = 4;

    /**
     * 拒绝理由最大长度
     */
    Integer REASON_MAX_LENGTH = 500;

    /**
     * 图片最多个数
     */
    Integer PIC_MAX_SIZE = 9;

    /**
     * 拒绝任务推送sendNo
     */
    Integer REFUSE_TASK_SENDNO = 6;

    /**
     * 接受任务推送sendNo
     */
    Integer ACCEPT_TASK_SENDNO = 7;

    /**
     * 完成任务推送sendNo
     */
    Integer FINISH_TASK_SENDNO = 8;

    /**
     * sendMessage模型sendTitle
     */
    String REFUSE_TASK_SEND_TITLE = "拒绝任务";

    /**
     * sendMessage模型sendTitle
     */
    String ACCEPT_TASK_SEND_TITLE = "接受任务";

    /**
     * sendMessage模型sendTitle
     */
    String FINISH_TASK_SEND_TITLE = "完成任务";

    /**
     * 系统通知sendContent
     */
    String REFUSE_TASK_SEND_CONTENT = "您发出的任务已被";

    /**
     * 系统通知sendContent
     */
    String ACCEPT_TASK_SEND_CONTENT = "您发出的任务已被";

    /**
     * 系统通知sendContent
     */
    String FINISH_TASKSEND_CONTENT = "您发出的任务已被";

    /**
     * 发系统通知任务概述最大长度
     */
    Integer TASK_DESC_NOTICE_LENGTH = 200;

    /**
     * 头像名称长度120
     */
    Integer IMAGE_NAME_LENGTH = 120;

    /**
     * 头像大小20M
     */
    int IMAGE_LENGTH_MAX = 20*1024*1024;

    /**
     * 系统通知sendType
     */
    String PUSH_SENDTYPE = "smart05";
}
