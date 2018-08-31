package com.betel.estatemgmt.business.web.user.constant;

import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.common.ConfigManager;
import com.betel.estatemgmt.common.RedisManager;

/**
 * 用户常量
 * ClassName: UserConstant
 * Author: Zhang li
 * Date: 2017/5/15 13:20
 * Version: 1.0
 */
public interface UserConstant {

    /**
     * 用户状态
     * Author:Zhang Li
     * Date: 2017/5/15 14:36
     *
     * @return
     */
    //有效
    static Integer USERSTATUS_EFFECTIVE = 1;
    //禁言
    static Integer USERSTATUS_GAG = 2;
    //禁止登录
    static Integer USERSTATUS_NO_LOGIN = 3;
    //已删除
    static Integer USERSTATUS_DELETED = 4;

    /**
     * 用户性别
     * Author:Zhang Li
     * Date: 2017/5/15 14:38
     *
     * @return
     */
    //男
    static Integer MALE = 1;
    //女
    static Integer FEMALE = 2;

    /**
     * 账号类型
     * Author:Zhang Li
     * Date: 2017/5/15 14:43
     *
     * @return
     */

    //手机号
    static Integer ACCTTYPE_CELL_PHONE = 1;
    //用户名
    static Integer ACCTTYPE_USERNAME = 2;

    public static final String IMAGE_URL = "IMAGE_URL";

    /**
     * 文件物理路径
     */
    String FILE_DIR_PATH = ConfigManager.read(ConfigName.FILE_DIR);

    /**
     *文件服务器路径
     */
    String FILE_SERVER_PATH = ConfigManager.read(ConfigName.FILE_SERVER);

    /**
     * 图片大小
     */
    Long IMAGE_LENGTH_MAX = 20*1024*1024L;

    /**
     * 删除成员/用户被禁言 发送通知类型
     */
    String SENDTYPE = "smart02";

    /**
     * 删除户主成员被移除发送通知编号
     */
    String MEMBERSEND = "6";

    /**
     * 移除房屋成员标题
     */
    String SENDTITLE = "移除房屋成员";

    /**
     *  认证状态 未读
     */
    int NOTICESTATUS= 1;

    /**
     *  通知类型 系统移除房屋成员
     */
    int NOTICETYPE = 6;

    int JOINACISDETELE = 14;

    /**
     *  通知类型 用户被管理员禁言
     */
    int NOTICE_TYPE_USER = 15;

    /**
     *  通知类型 用户被管理员取消禁言
     */
    int NOTICE_TYPE_NOBAN = 19;
    /**
     *  用户被管理员禁言 发送通知编号
     */
    String SENDNO = "15";

    /**
     *  取消禁言 发送通知编号
     */
    String BANNO_SENDNO = "19";

    /**
     * 用户被管理员禁言标题
     */
    String SEND_USER_TITLE = "系统禁言";

    /**
     * 取消禁言标题
     */
    String SEND_BANNO_TITLE = "取消禁言";

    /**
     * 取消禁止登录
     */
    String SEND_NOTBAN_TITLE = "取消禁止登录";

    /**
     *  取消禁止登录 发送通知编号
     */
    String BANNO_SEND_NO = "20";

    /**
     *  通知类型 用户被管理员取消禁止登录
     */
    int NOTICETYPE_NOBAN = 20;

    Integer OWNER_ONE = 1;

    Integer OWNER_TWO = 2;
}
