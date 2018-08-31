package com.betel.estatemgmt.business.web.repair.constant;

import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.common.ConfigManager;
import com.betel.estatemgmt.common.RedisManager;

/**
 * <p>
 * 维修管理常量
 * </p>
 * ClassName: RepairDataValidation <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/9/13 16:12 <br/>
 * Version: 1.0 <br/>
 */
public interface RepairDataValidation {

    /**
     * 昵称长度和格式校验
     */
    String USERNAME_RULE = "[^\\\\<>%'\"]{1,10}";

    /**
     * 房号长度和格式校验
     */
    String HOUSENUM_RULE = "[^\\\\<>%'\"]{1,6}";

    /**
     * 单号长度和格式校验
     */
    String ORDERNO_RULE = "[^\\\\<>%'\"]{1,22}";

    /**
     *文件服务器路径
     */
    String FILE_SERVER_PATH = ConfigManager.read(ConfigName.FILE_SERVER);

    /**
     * 手机号格式
     */
    String PHONE_RULE = "^1[3|4|5|7|8][0-9]\\d{8}$";

    /**
     * 维修内容长度
     */
    Integer REPAIRDESC_MAX_LENGTH = 100;

    /**
     * 生成单号自增数
     */
    Integer ADDNO_SEQUENCE = 0;

    /**
     * 生成单号自增长度
     */
    Integer ADDNO_LENGTH = 6;

    /**
     * 维修单待维修状态
     */
    Integer ORDER_WAITREPAIR_STATUS = 1;

    /**
     * 维修单已接单状态
     */
    Integer ORDER_INORDER_STATUS = 2;

    /**
     * 维修单已完成状态
     */
    Integer ORDER_FINISH_STATUS = 3;
    /**
     * 维修单已取消状态
     */
    Integer ORDER_QUXIAO_STATUS = 4;
    /**
     * 个人
     */
    Integer ORDER_AREA_PERSONAL = 1;

    /**
     * 公共区域
     */
    Integer ORDER_AREA_PUBLIC = 2;

    /**
     * 系统通知sndType
     */
    String SENDTYPE = "smart02";

    /**
     * 指派维修单snedNo
     */
    String ORDER_SENDNO = "21";

    /**
     * 维修收费标准
     */
    String REPAIR_CHARGE_STANDARD = "repairChargeStandard";

    /**
     * 维修收费标准长度限制
     */
    Integer STANDARD_MAX_LENGTH = 100000;

    /**
     * 图片原始文件名最大长度限制
     */
    Integer PICNAME_MAX_LENGTH = 120;

    /**
     * 图片大小限制
     */
    Integer PIC_MAX_SIZE = 20*1024*1024;

    /**
     * 图片格式校验
     */
    String PIC_TYPE_RULE = "(bmp|jpg|jpeg|png)";

    /**
     * 广告富文本编辑图片标识
     */
    String AD_EDIT_FLAG = "1";

    /**
     * 收费标准富文本编辑图片标识
     */
    String EXPENSE_EDIT_FLAG = "2";

    /**
     * 维修标准富文本编辑图片标识
     */
    String REPAIR_EDIT_FLAG = "3";
    /**
     * 新增待处理消息
     */
    String SENDNO_DAIBAOXIUCHULI = "31";

    /**
     * httpClient调用域名
     */
    String OA_PROJECT_URL = ConfigManager.read(ConfigName.OA_PROJECT_URL);

    /**
     * 添加/指派报修单sendNo
     */
    String ADD_REPAIR_SENDNO = "4";

    String ADD_REPAIR_SENDTITLE = "管理员新增报修单";

    String ASSIGN_REPAIR_SENDTITLE = "管理员指派报修单";

    String ADD_REPAIR_SENDCONTENT = "您有一个新的待维修单，请及时处理";

    /**
     * WEB标识
     */
    String WEB = "Web";

    /**
     * APP标识
     */
    String APP = "App";

    /**
     * 系统通知sendType
     */
    String PUSH_SENDTYPE = "smart05";

    String PROPERTY_ESTATE_TYPE = "1";

}
