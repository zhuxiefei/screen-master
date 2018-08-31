package com.betel.estatemgmt.business.web.announce.constant;

/**
 * <p>
 * 公告数据校验
 * </p>
 * ClassName: AnnounceDataValidation <br/>
 * Author: xiayanxin <br/>
 * Date: 2017/5/16 10:52 <br/>
 * Version: 1.0 <br/>
 */
public interface AnnounceDataValidation {

    /**
     * 关键字字符长度和格式校验
     */
    String KEY_WORD_RULE = "[^\\\\<>%'\"]{1,60}";

    /**
     * 公告标题字符长度和格式校验
     */
    String ANNOUNCE_TITLE_RULE = "[^\\\\<>%'\"]{1,60}";

    /**
     * 公告内容长度校验
     */
    Integer ANNOUNCE_CONTENT_SIZE = 1500;

    /**
     * 公告状态：未发送
     */
    Integer STATUS_NOT_SEND = 1;

    /**
     * 公告状态：已发送
     */
    Integer STATUS_IS_SEND = 2;

    /**
     * 公告状态：已删除
     */
    Integer STATUS_IS_DELETE = 3;

    /**
     * 推送类型
     */
    String SEND_TYPE = "smart04";

    /**
     * 推送类型编号
     */
    String SEND_NO = "1";

    /**
     * 置顶状态
     */
    Integer TOP_STATUS = 1;

    /**
     * 非置顶状态
     */
    Integer NOT_TOP_STATUS = 0;

    /**
     * 最多置顶条数
     */
    Integer MAX_TOP_SIZE = 2;

    /**
     * 公告删除状态
     */
    Integer ANNOUNCE_DELETE_STATUS = 3;

    /**
     * 标识
     */
    String BETEL_TIP = "社区小贴";
}
