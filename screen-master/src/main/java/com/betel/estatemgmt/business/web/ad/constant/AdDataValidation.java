package com.betel.estatemgmt.business.web.ad.constant;

/**
 * <p>
 * 广告数据校验类
 * </p>
 * ClassName: AdDataValidation <br/>
 * Author: xiayanxin <br/>
 * Date: 2017/5/16 15:21 <br/>
 * Version: 1.0 <br/>
 */
public interface AdDataValidation {

    /**
     * 广告标题字符长度和格式校验
     */
    String AD_TITLE_RULE = "[^\\\\<>%'\"]{1,10}";

    /**
     * 广告内容长度校验
     */
    Integer AD_CONTENT_SIZE = 100000;

    /**
     * 广告置顶状态
     */
    Integer AD_IS_TOP = 1;

    /**
     * 广告非置顶状态
     */
    Integer AD_NOT_TOP = 0;

    /**
     * 最多置顶广告条数
     */
    Integer MAX_TOPAD_SIZE = 6;

    /**
     * 图片格式校验
     */
    String PIC_TYPE_RULE = "(bmp|jpg|jpeg|BMP|JPG|JPEG|png)";

    /**
     * 上传图片大小不超过20M
     */
    Long UPLOAD_PIC_SIZE = 20*1024*1024L;

    /**
     * 文件删除失败
     */
    String FILE_DELETE_FAILURE = "文件删除失败";

    /**
     * 图片原始文件名长度
     */
    Integer PIC_NAME_SIZE = 120;

    /**
     * 推送类型编号
     */
    String SEND_NO = "2";

    /**
     * 推送类型
     */
    String SEND_TYPE = "smart04";
}
