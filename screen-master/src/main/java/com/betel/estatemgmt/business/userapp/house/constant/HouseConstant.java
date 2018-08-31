package com.betel.estatemgmt.business.userapp.house.constant;

import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.common.ConfigManager;
import com.betel.estatemgmt.common.RedisManager;


/**
 * <p>
 * app我的家模块常量
 * </p>
 * ClassName: HouseController <br/>
 * Author: zhouye  <br/>
 * Date: 2017/6/19 16:41 <br/>
 * Version: 1.0 <br/>
 */
public interface HouseConstant {

    /**
     * 申请类型：1户主
     */
    int AUTH_OWNER =1;

    /**
     * 申请类型：2用户
     */
    int AUTH_MEMBER =2;
    /**
     * 认证申请中
     */
    int AUTHSTATUSING =1;

    /**
     * 认证申请中
     */
    int AUTHSTATUS_COMMON =1;

    /**
     * 认证审核中
     */
    int REVIEWSTATUSCOMMON = 1;

    /**
     * 认证成功
     */
    int AUTHSTATUSSUCCUSS =2;

    /**
     * 认证申请失败
     */
    int AUTHSTATUSFAILED =3;

    /**
     * 认证申请删除
     */
    int AUTHSTATUSDELETE =5;

    /**
     * 认证申请撤销
     */
    int AUTHSTATUSREVOKE =4;

    /**
     * 图片大小
     */
    int IMAGE_LENGTH_MAX = 20*1024*1024;

    /**
     * 文件物理路径
     */
    //String FILE_DIR_PATH = ConfigManager.read(FILE_DIR)+"auth/";

    /**
     * 隐私文件物理路径
     */
    String PRIVACY_FILE_DIR_PATH = ConfigManager.read(ConfigName.PRIVACY_FILE_DIR);

    /**
     *文件服务器路径
     */
    String FILE_SERVER_PATH = ConfigManager.read(ConfigName.FILE_SERVER)+"auth/";

    /**
     *压缩包保存地址
     */
    String ZIP = ConfigManager.read(ConfigName.FILE_DIR)+"shareZip/";

    /**
     *用户认证通知
     */
    String NOTICE = "尊敬的用户，你有一个成员认证请求，请查看!";

    /**
     *  用户认证 认证状态 未读
     */
    int NOTICETYPE = 1;
    /**
     * 成员认证发送类型
     */
     String SEND_TYPE = "smart03";

    /**
     * 删除成员发送通知类型
     */
    String MEMBER_SEND_TYPE = "smart02";

    /**
     * 删除成员发送通知编号
     */
    String MEMBERSEND_NO = "5";


    /**
     *成员认证发送通知编号
     */
     String SEND_NO = "1";
    /**
     * 成员认证标题
     */
     String SEND_TITLE = "成员认证";

    /**
     * 标题
     */
    String MEMBER_SEND_TITLE = "户主认证结果";

    /**
     * 分享的配置文件
     */
    String SHARE = "share";

    /**
     * 全部材料
     */
    String FUNCTION_ALL = "全部";
    /**
     * 全部户型的材料
     */
    String FUNCTIONTYPE_ALL = "全户型";

    /**
     * 全部材料搜索条件
     */
    Long FUNCTION_ALL_ID = -1L;

    /**
     * 全部户型的材料的搜索搜索条件
     */
    Long FUNCTIONTYPE_ALL_ID = 0L;

    /**
     * 数字4
     */
    int FOUR = 4;

    /**
     * 数字1
     */
    int ONE = 1;

    /**
     * 数字2
     */
    int TWO = 2;

    /**
     * 户主姓名最大长度
     */
    Integer OWNER_NAME_LENGTH = 10;

    /**
     * 户主已激活状态
     */
    Integer OWNER_IS_STATUS = 2;

    /**
     * 户主添加成员（成员无账号）snedNo
     */
    Integer OWNER_ADD_MEMBER_SENDNO = 26;

    /**
     * 户主添加成员（成员有账号）snedNo
     */
    Integer OWNER_ADD_MEMBER_SENDNO2 = 27;

    /**
     * 户主变更
     */
    Integer HOUSE_OWNER_CHANAGE = 28;

    /**
     * 成員刪除
     */
    Integer MEMBER_DELETE_STATUS = 3;
}
