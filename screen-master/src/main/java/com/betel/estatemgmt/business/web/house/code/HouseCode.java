package com.betel.estatemgmt.business.web.house.code;

/**
 * <p>
 * 房屋信息维护返回码
 * </p>
 * ClassName: HouseCode <br/>
 * Author: xiayanxin <br/>
 * Date: 2017/6/19 13:22 <br/>
 * Version: 1.0 <br/>
 */
public interface HouseCode {
    /**
     * 查询内容格式错误
     */
    String SEARCH_CONTENT_RULE = "H0001";

    /**
     * 单元楼宇不匹配
     */
    String UNIT_BUILDING_MATCH = "H0002";

    /**
     * 房号为空
     */
    String HOUSE_NUM_NULL = "H0003";

    /**
     * 房号格式错误
     */
    String HOUSE_NUM_RULE = "H0004";

    /**
     * 该房屋已存在
     */
    String HOUSE_IS_EXIST = "H0005";

    /**
     * 房屋描述字数超过最大限度
     */
    String HOUSE_DESC_LENGTH = "H0006";

    /**
     * 房屋编号为空
     */
    String HOUSE_ID_NULL = "H0007";

    /**
     * 房屋图纸类型为空
     */
    String HOUSE_TYPE_NULL = "H0008";

    /**
     * 文件标识为空
     */
    String FILE_FLAG_NULL = "H0009";

    /**
     * 预览图片格式错误
     */
    String HOUSE_PICTURE_RULE = "H0010";

    /**
     * CAD文件格式错误
     */
    String HOUSE_CAD_RULE = "H0011";

    /**
     * 预览图片大小超过20M
     */
    String HOUSE_PICTURE_SIZE = "H0012";

    /**
     * CAD文件大小超过10M
     */
    String HOUSE_CAD_SIZE = "H0013";

    /**
     * 文件为空
     */
    String HOUSE_FILE_NULL = "H0014";

    /**
     * Excel文件格式错误
     */
    String EXCEL_RULE = "H0015";

    /**
     * Excel文件模型错误
     */
    String EXCEL_MODEL_WRONG = "H0029";

    /**
     * Excel文件大小超过15M
     */
    String EXCEL_SIZE = "H0016";

    /**
     * 该预览图已被删除
     */
    String PICTURE_IS_DELETE = "H0019";

    /**
     * 该CAD文件已被删除
     */
    String CAD_IS_DELETE = "H0020";

    /**
     * 通知发送失败
     */
    String SEND_NOTICE_FAILURE = "H0021";

    /**
     * 该楼宇不存在
     */
    String BUILDING_NOT_EXIST = "H0022";

    /**
     * 该单元不存在
     */
    String UNIT_IS_NULL = "H0023";

    /**
     * 该户型不存在
     */
    String HOUSETYPE_IS_NULL = "H0024";

    /**
     * 该房屋已被删除
     */
    String HOUSE_IS_DELETE = "H0025";

    /**
     * 房屋图纸编号为空
     */
    String HOUSE_PICID_NULL = "H0026";

    /**
     * 读取文件失败
     */
    String READ_FILE_FAILUER = "H0027";

    /**
     * 楼宇为空
     */
    String BUILDING_IS_NULL = "H0028";

    /**
     * 图片原始名称长度超过120
     */
    String PICTURE_NAME_MAX = "P0005";

    /**
     * 交付时间为空
     */
    String DELIVERTIME_IS_NULL = "H0030";

    /**
     * 交付时间格式错误
     */
    String DELIVERTIME_RULE_ERROR = "H0031";

    /**
     * 建筑面积不能为空
     */
    String FLOORAREA_IS_NULL = "H0032";

    /**
     * 套内面积不能为空
     */
    String INTERFLOORAREA_IS_NULL = "H0033";

    /**
     * 建筑面积格式错误
     */
    String FLOORAREA_RULE_ERROR = "H0035";

    /**
     * 套内面积格式错误
     */
    String INTERFLOORAREA_RULE_ERROR = "H0036";

    /**
     * ZIP格式错误
     */
    String ZIP_RULE_ERROR = "H0037";

    /**
     * ZIP大小错误
     */
    String ZIP_SIZE_ERROR = "H0038";

    /**
     * ZIP存在文件夹
     */
    String ZIP_EXIST_DIRECTORY = "H0039";

    /**
     * 只能上传指定格式的文件
     */
    String FILE_RULE_ERROR = "H0040";

    /**
     * 有且只能上传一个Excel
     */
    String ONLY_ONE_EXCEL = "H0041";

    /**
     * 请上传指定格式的图片
     */
    String NO_PICTURE = "H0042";

    /**
     * excel文件内容为空
     */
    String EXCEL_NULL_ERROR = "H0043";
    /**
     * 户主搜索条件出错
     */
    String HOUSE_OWER_FIND_ERROR="H0044";

    /**
     * 产权车位数过多
     */
    String BUY_PARKING_SIZE = "H0045";
    /**
     * 租赁车位数过多
     */
    String RENT_PARKING_SIZE = "H0046";
    /**
     * 产权车号已存在
     */
    String BUY_PARKING_EXIST = "H0047";
    /**
     * 租赁车位号已存在
     */
    String RENT_PARKING_EXIST = "H0048";

    /**
     * 车位号格式错误
     */
    String SPACE_NUM_RULE = "H0049";

    /**
     * 车牌号格式错误
     */
    String LICENSE_PLATE_RULE = "H0050";

    /**
     * 排序格式错误
     */
    String ORDER_RULE = "H0051";

    /**
     * 排序不能为空
     */
    String ORDER_NULL = "H0052";
}
