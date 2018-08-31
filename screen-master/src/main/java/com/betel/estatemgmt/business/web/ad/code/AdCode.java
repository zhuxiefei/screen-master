package com.betel.estatemgmt.business.web.ad.code;

/**
 * <p>
 * 广告返回码
 * </p>
 * ClassName: AdCode <br/>
 * Author: xiayanxin <br/>
 * Date: 2017/5/17 08.55 <br/>
 * Version: 1.0 <br/>
 */
public interface AdCode {
    /**
     * 创建人ID不能为空
     */
    String AUTHOR_ID_NULL = "A0001";

    /**
     * 广告标题不能为空
     */
    String AD_TITLE_NULL = "A0002";

    /**
     * 广告内容不能为空
     */
    String AD_CONTENT_NULL = "A0003";

    /**
     * 读取文件失败
     */
    String READ_FILE_FAILUER = "A0004";

    /**
     * 广告标题字符格式错误
     */
    String AD_TITLE_RULE = "A0005";

    /**
     * 广告内容不能超过10000个字符
     */
    String AD_CONTENT_SIZE = "A0006";

    /**
     * 文件格式不符合要求
     */
    String AD_FILE_RULE = "A0007";

    /**
     * 广告图编号不能为空
     */
    String ADPIC_ID_NULL = "A0008";

    /**
     * 广告编号数组不能为空
     */
    String ADID_ARRAY_NULL = "A0009";

    /**
     * 广告编号不能为空
     */
    String AD_ID_NULL = "A0010";

    /**
     * 置顶状态不能为空
     */
    String TOP_STATUS_NULL = "A0019";

    /**
     * 置顶状态格式错误
     */
    String TOP_STATUS_RULE = "A0020";

    /**
     * 文件大小不能超过20M
     */
    String AD_FILE_SIZE = "A0021";

    /**
     * 查询标题格式错误
     */
    String KEY_WORD_RULE = "A0022";

    /**
     * 文件为空
     */
    String AD_FILE_NULL = "A0023";

    /**
     * 用户编号为空
     */
    String USER_ID_NULL = "A0024";

    /**
     * 该广告已经置顶
     */
    String AD_IS_TOP  = "A0025";

    /**
     * 该广告已经取消置顶
     */
    String AD_NOT_TOP = "A0026";

    /**
     * 该广告已被删除
     */
    String AD_IS_DELETE = "A0027";

    /**
     * 最多展示三条广告
     */
    String AD_MAX_TOP = "A0029";

    /**
     * 图片原始名称长度超过120
     */
    String PICTURE_NAME_MAX = "P0005";
}
