package com.betel.estatemgmt.business.web.task.constant;


import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.common.ConfigManager;
import com.betel.estatemgmt.common.RedisManager;

/**
 * <p>
 * 任务常量
 * </p>
 * ClassName: TaskConstant <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/12/21 8:56 <br/>
 * Version: 1.0 <br/>
 */
public interface TaskConstant {

    /**
     * 任务类型最大长度
     */
    Integer TYPE_MAX_LENGTH = 50;

    /**
     * 任务概述最大长度
     */
    Integer TASK_DESC_MAX_LENGTH = 5000;

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
    Integer TASK_OVER_TIME = 4;

    /**
     * 未删除标识
     */
    Integer NO_DELETE = 0;

    /**
     * 已删除标识
     */
    Integer IS_DELETE = 1;

    /**
     * 发布人/指派人筛选最大长度
     */
    Integer NAME_MAX_LENGTH = 32;

    /**
     * 文件服务器路径
     */
    String FILE_SERVER = ConfigManager.read(ConfigName.FILE_SERVER);

    /**
     * 文件物理路径
     */
    String FILE_PATH = ConfigManager.read(ConfigName.FILE_DIR);

    /**
     * 编辑任务入参
     */
    String UPDATE_TASK_TYPE = "1";

    /**
     * 指派任务入参
     */
    String ASSIGN_TASK_TYPE = "2";

    /**
     * 添加/指派任务推送sendNo
     */
    Integer ADD_DAILY_SENDNO = 5;

    /**
     * 删除任务推送sendNo
     */
    Integer DELETE_TASK_SENDNO = 9;

    /**
     * 系统通知sendType
     */
    String PUSH_SENDTYPE = "smart05";

    /**
     * sendMessage模型sendTitle
     */
    String ADD_TASK_SEND_TITLE = "管理员添加任务";

    /**
     * sendMessage模型sendTitle
     */
    String APP_ADD_TASK_SEND_TITLE = "物管人员添加任务";

    /**
     * sendMessage模型sendTitle
     */
    String ASSIGN_TASK_SEND_TITLE = "管理员指派任务";

    /**
     * sendMessage模型sendTitle
     */
    String DELETE_TASK_SEND_TITLE = "管理员删除任务";

    /**
     * sendMessage模型sendTitle
     */
    String APP_ASSIGN_TASK_SEND_TITLE = "物管人员指派任务";

    /**
     * 系统通知sendContent
     */
    String ADD_DAILY_SEND_CONTENT = "您有一个新的任务，请及时处理";

    /**
     * 系统通知sendContent
     */
    String DELETE_TASK_SEND_CONTENT = "您发出的任务已被管理员删除，";

    /**
     * 任务编号最大长度
     */
    Integer TASK_NO_MAX = 22;

    /**
     * 发系统通知任务概述最大长度
     */
    Integer TASK_DESC_NOTICE_LENGTH = 200;

    /**
     * 单号长度和格式校验
     */
    String TASKNO_RULE = "[^\\\\<>%'\"]{1,22}";

    /**
     * 数字9999
     */
    int SEARCH_NUM_MAX = 9999;

    /**
     * 数字10
     */
    int TEN = 10;

    Integer PHONE_MIN_LENGTH = 1;

    Integer PHONE_MAX_LENGTH = 11;
}
