package com.betel.estatemgmt.business.web.house.constant;


import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.common.ConfigManager;
import com.betel.estatemgmt.common.RedisManager;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: HouseDataValidation <br/>
 * Author: xiayanxin <br/>
 * Date: 2017/6/19 13:54 <br/>
 * Version: 1.0 <br/>
 */
public interface HouseDataValidation {
    /**
     * 关键字字符长度和格式校验
     */
    String KEY_WORD_RULE = "[^\\\\<>%'\"]{1,60}";

    /**
     * “其他”标识
     */
    Integer IS_OTHERS_FLAG = 1;

    /**
     * 预览图片标识
     */
    Integer PICTURE_FLAG = 0;

    /**
     * CAD标识
     */
    Integer CAD_FLAG = 1;

    /**
     * 房号长度和格式校验
     */
    String HOUSE_NUM_RULE = "[^\\\\<>%'\"]{1,6}";

    /**
     * 房屋描述最大字数校验
     */
    Integer HOUSE_DESC_SIZE = 100;

    /**
     * 批量导入房屋描述最大字数校验
     */
    Integer IMPORT_HOUSEDESC_SIZE = 100;

    /**
     * EXCEL格式校验
     */
    String EXCEL_RULE = "xls";

    /**
     * EXCEL大小校验
     */
    Long EXCEL_MAX_SIZE = 15 * 1024 * 1024L;

    /**
     * 预览图片格式校验
     */
    String PICTURE_RULE = "(bmp|jpg|jpeg|BMP|JPG|JPEG|png)";

    /**
     * CAD文件格式校验
     */
    String CAD_RULE = "(dwt|DWT)";

    /**
     * 预览图片大小校验
     */
    Long PICTURE_MAX_SIZE = 5 * 1024 * 1024L;

    /**
     * CAD大小校验
     */
    Long CAD_MAX_SIZE = 5 * 1024 * 1024L;

    /**
     * 推送类型
     */
    String SEND_TYPE = "smart02";

    /**
     * 推送类型编号
     */
    String SEND_NO = "02";

    /**
     * 文件服务器路径
     */
    String FILE_SERVER_PATH = ConfigManager.read(ConfigName.FILE_SERVER);

    /**
     * 文件物理路径
     */
    String FILE_DIR_PATH = ConfigManager.read(ConfigName.FILE_DIR);

    /**
     * 房屋模板标识
     */
    Integer HOUSE_TEMPLATE_FLAG = 0;

    /**
     * 建材模板标识
     */
    Integer MATERIAL_TEMPLATE_FLAG = 1;

    /**
     * 房屋图纸模板标识
     */
    Integer HOUSEPIC_TEMPLATE_FLAG = 2;

    /**
     * 房屋模板url
     */
    String HOUSE_TEMPLATE_URL = "template/houseTemplate.xls";

    /**
     * 建材模板url
     */
    String MATERIAL_TEMPLATE_URL = "template/materialTemplate.zip";

    /**
     * 房屋图纸模板url
     */
    String HOUSEPIC_TEMPLATE_URL = "template/housePicTemplate.zip";

    /**
     * 图片原始文件名长度
     */
    Integer PIC_NAME_SIZE = 120;

    /**
     * 面积格式校验
     */
    String AREA_RULE = "(^0\\.\\d{1,2}$)|(^\\d{1,5}\\.\\d{1,2}$)|(^\\d{1,5}$)";

    /**
     * 只能输入整数和小数
     */
    String NUMBER_ONLY = "^(^[0-9]+([.]{1}[0-9]+){0,1}$)$";

    /**
     * 不能为0
     */
    String NOT_ZERO = "([1-9]\\d*(\\.\\d*[1-9])?)|(0\\.\\d*[1-9])";

    /**
     * zip大小
     */
    Integer ZIP_SIZE = 500*1024*1024;

    /**
     * 产权车位
     */
    Integer BUY_PARKING_SPACE = 1;

    /**
     * 租赁车位
     */
    Integer RENT_PARKING_SPACE = 2;

    /**
     * 房屋空置状态
     */
    Integer HOUSE_IS_EMPTY = 2;

    /**
     * 未养宠物状态
     */
    Integer PET_IS_EMPTY = 0;
    /**
     * 车位状态码
     */
    String SPACE_TYPE_RULE="^[0-1]$";

    /**
     * 最多产权车位数
     */
    Integer BUY_PARKING_SIZE = 5;

    /**
     * 最多租赁车位数
     */
    Integer RENT_PARKING_SIZE = 5;

    /**
     * 车位号字符长度限制
     */
    Integer SPACE_NUM_LENGTH = 64;

    /**
     * 车牌号字符长度限制
     */
    Integer LICENSE_PLATE_LENGTH = 16;

    /**
     * 排序字段最大长度
     */
    Integer ORDER_MAX_LENGTH = 10000;
    /**
     * 删除成员/用户被禁言 发送通知类型
     */
    String SENDTYPE = "smart02";
    /**
     *  通知类型 用户被管理员取消禁言
     */
    int NOTICE_TYPE_NOBAN = 19;
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

}
