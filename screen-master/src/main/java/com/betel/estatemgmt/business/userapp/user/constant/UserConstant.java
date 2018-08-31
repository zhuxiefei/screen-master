package com.betel.estatemgmt.business.userapp.user.constant;

import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.common.ConfigManager;
import com.betel.estatemgmt.common.RedisManager;

/**
 * <p>
 * 用户个人信息模块常量
 * </p>
 * ClassName: ForumConstant <br/>
 * Author: geyf  <br/>
 * Date: 2017/5/16 14:13 <br/>
 * Version: 1.0 <br/>
 */
public interface UserConstant {


    /**
     * 图片大小
     */
    int IMAGE_LENGTH_MAX = 20*1024*1024;

    /**
     * 用户状态
     */
    int USER_STATUS = 1;

    /**
     * 性别男
     */
    String SEX_BOY = "1";
    /**
     * 性别女
     */
    String SEX_GIRL = "2";

    /**
     * 账号类型
     */
    int ACCOUNTNAME_ACCTTYPE = 1;

    /**
     * 默认角色Id
     */
    Long ROLEID_DEFAULT = 1L;

    /**
     * 文件物理路径
     */
    String FILE_DIR_PATH = ConfigManager.read(ConfigName.FILE_DIR);

    /**
     *文件服务器路径
     */
    String FILE_SERVER_PATH = ConfigManager.read(ConfigName.FILE_SERVER);

    /**
     * 鉴定码保存时间
     */
    Integer TIME = 5*60;

    /*
    用户身份非业主
     */
    Integer USERTYPE_COMMON = 3;

    /*
    用户身份户主
     */
    Integer USERTYPE_OWNER = 2;
    /*
   用户身份成员
    */
    Integer USERTYPE_MEMBER = 1;

    /*
    头像名称长度
     */
    Integer IMAGE_NAME_LENGTH = 120;

    /*
    密码最短长度
     */
    Integer PAWWWORD_MINLENGTH = 6;
    /*
    密码最长长度
     */
    Integer PAWWWORD_MAXLENGTH =20;

    /**
     * 数字2
     */
    int TWO = 2;

    /**
     * 数字3
     */
    int THREE = 3;
}
