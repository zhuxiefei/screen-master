package com.betel.estatemgmt.business.web.announce.code;

/**
 * <p>
 * 公告返回码
 * </p>
 * ClassName: AnnounceCode <br/>
 * Author: xiayanxin  <br/>
 * Date: 2017/5/15 18:39 <br/>
 * Version: 1.0 <br/>
 */
public interface AnnounceCode {
    /**
     * 创建人ID不能为空
     */
    String AUTHOR_ID_NULL = "A0001";

    /**
     * 公告编号不能为空
     */
    String ANNOUNCE_ID_NULL = "A0011";

    /**
     * 消息推送失败
     */
    String MESSAGE_PUSH_FAILURE = "A0012";

    /**
     * 公告发送状态修改失败
     */
    String ANNOUNCESTATUS_UPDATE_FAILURE = "A0013";

    /**
     * 公告标题不能为空
     */
    String ANNOUNCE_TITLE_NULL = "A0014";

    /**
     * 公告内容不能为空
     */
    String ANNOUNCE_CONTENT_NULL = "A0015";

    /**
     * 公告标题字符格式错误
     */
    String ANNOUNCE_TITLE_RULE = "A0016";

    /**
     * 公告内容不能超过1500个字符
    */
    String ANNOUNCE_CONTENT_SIZE = "A0017";

    /**
     * 公告编号数组不能为空
     */
    String ANNOUNCEID_ARRAY_NULL = "A0018";

    /**
     * 查询标题格式错误
     */
    String KEY_WORD_RULE = "A0022";

    /**
     * 用户编号为空
     */
    String USER_ID_NULL = "A0024";

    /**
     * 该公告已经被删除
     */
    String ANNOUNCE_IS_DELETE = "A0028";

    /**
     * 置顶状态为空
     */
    String TOP_STATUS_NULL = "A0030";

    /**
     * 置顶状态错误
     */
    String TOP_STATUS_RULE = "A0031";

    /**
     * 该公告已经置过顶
     */
    String ANNOUNCE_IS_TOP = "A0033";

    /**
     * 没有置顶公告
     */
    String NO_TOP_ANNOUNCE = "A0034";

    /**
     * 该公告已经取消置顶
     */
    String ANNOUNCE_NOT_TOP = "A0035";
}
