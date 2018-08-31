package com.betel.estatemgmt.business.web.housestatus.constant;

/**
 * <p>
 * Describe this class...
 * </p>
 *
 * @className: HouseStatusConstant <br/>
 * @author: jian.z  <br/>
 * @date: 2017/11/13 17:28 <br/>
 * @version: 1.0
 */
public interface HouseStatusConstant {
    /**
     * 成员认证三种状态：1为正常， 4为已撤销， 5为已删除
     * <p>
     * 审核状态：1为待审核， 2为核准， 3为驳回
     * <p>
     * 房屋状态：
     * 1为自住(默认)，
     * 2为空置，
     * 3为出租
     */
    String STATUS_ZERO = "0";
    String STATUS_ONE = "1";
    String STATUS_TWO = "2";
    String STATUS_THREE = "3";
    /**
     * 宗教信仰:
     * 1为无（默认），
     * 2为佛教，
     * 3为基督教，
     * 4为伊斯兰教
     * 5为其他
     */
    String RELIGION_ONE = "1";
    String RELIGION_TWO = "2";
    String RELIGION_THREE = "3";
    String RELIGION_FOUR = "4";
    String RELIGION_FIVE = "5";

    int ZERO = 0;

    /**
     * 系统通知sndType
     */
    String SENDTYPE = "smart02";
    /**
     * 户主变更：推送给户主的sendNo
     */
    String SENDNO_OWNER_UPDATE = "28";
    /**
     * 户主变更时成员关系解除：推送给成员的sendNo
     */
    String SENDNO_MEMBER_DELETE_SEND_TO_MEMBER = "29";
    /**
     * 管理员添加成员成员认证：推送给当前户主sendNo
     */
    String SENDNO_MEMBER_SEND_TO_OWNER = "1";

    /**
     * 认证通知sndType
     */
    String MEMBER_AUTH_SENDTYPE = "smart03";
    /**
     * 管理添加未注册成员（发给当前户主）
     */
    String REGISTER_MEMBER_SEND_TO_NOW_OWNER = "30";

    /**
     * -1
     */
    String MINUS_ONE="-1";
}
