package com.betel.estatemgmt.business.web.housestatus.code;

/**
 * <p>
 * Describe this class...
 * </p>
 *
 * @className: HouseStatusCode <br/>
 * @author: jian.z  <br/>
 * @date: 2017/11/13 17:24 <br/>
 * @version: 1.0
 */
public interface HouseStatusCode {
    /**
     * o常量
     */
    int ZERO = 0;
    /**
     * 房屋id为空
     */
    String HOUSE_ID_IS_NULL = "F20001";
    /**
     * 是否饲养宠物为空
     */
    String PETSTATUS_IS_NULL = "F20004";
    /**
     * 是否饲养宠物为空
     */
    String PETSTATUS_IS_NOT_TRUE = "F20005";
    /**
     * 户主姓名为空
     */
    String OWNER_NAME_IS_NULL = "F20006";
    /**
     * 户主姓名只能输入1-10汉字，并前后去空格
     */
    String OWNER_NAME_IS_NOT_LEGAL = "F20007";
    /**
     * 户主联系方式为空
     */
    String OWNER_PHONE_NUM_IS_NULL = "F20008";
    /**
     * 户主联系方式格式为13|14|15|17|18开头，长度为11位纯数字，并前后去空格
     */
    String OWNER_PHOME_NUM_IS_NOT_LAGAL = "F20009";
    /**
     * 户主籍贯长度为1-50个字且不能包含特殊字符（英文\<>'"%），并前后去空格
     */
    String OWNER_RELIGION_IS_NOT_LAGAL = "F20011";
    /**
     * 户主民族长度为1-5个汉字且不能包含特殊字符（英文\<>'"%），并前后去空格
     */
    String OWNER_ETHNIC_IS_NOT_LAGAL = "F20013";
    /**
     * 户主宗教信仰状态值数据不存在
     */
    String OWNER_RELIGION_IS_NOT_EXIST = "F20015";
    /**
     * 房屋状态为空
     */
    String HOUSE_STATUS_IS_NULL = "F20016";
    /**
     * 房屋状态不存在
     */
    String HOUSE_STATUS_IS_NOT_EXIST = "F20017";
    /**
     * 租户的姓名为空
     */
    String TENANT_NAME_IS_NULL = "F20018";
    /**
     * 租户姓名只能输入1-10汉字，并前后去空格
     */
    String TENANT_NAME_IS_NOT_LEGAL = "F20019";
    /**
     * 租户联系方式为空
     */
    String TENANT_PHOME_NUM_IS_NULL = "F20020";
    /**
     * 租户联系方式格式为13|14|15|17|18开头，长度为11位纯数字，并前后去空格
     */
    String TENANT_PHOME_NUM_IS_NOT_LAGAL = "F20021";
    /**
     * 租户籍贯长度为1-50个字且不能包含特殊字符（英文\<>'"%），并前后去空格
     */
    String TENANT_RESIDENCY_IS_NOT_LEGAL = "F20023";
    /**
     * 租户民族长度为1-5个汉字且不能包含特殊字符（英文\<>'"%），并前后去空格
     */
    String TENANT_ETHNIC_IS_NOT_LEGAL = "F20025";
    /**
     * 租户宗教信仰系统不存在
     */
    String TENANT_RELIGION_IS_NOT_EXIST = "F20026";
    /**
     * 租户id为空
     */
    String TENANT_ID_IS_NULL = "F20028";
    /**
     * 成员姓名为空
     */
    String MEMBER_NAME_IS_NULL = "F20029";
    /**
     * 成员姓名只能输入1-10汉字，并前后去空格
     */
    String MEMBER_NAME_IS_NOT_LEGAL = "F20030";
    /**
     * 成员联系方式为空
     */
    String MEMBER_PHONE_NUM_IS_NULL = "F20031";
    /**
     * 成员联系方式格式为13|14|15|17|18开头，长度为11位纯数字，并前后去空格
     */
    String MEMBER_PHONE_NUM_IS_NOT_LEGAL = "F20032";
    /**
     * F10033	成员籍贯长度为1-50个字且不能包含特殊字符（英文\<>'"%），并前后去空格
     */
    String MEMBER__RESIDENCY_IS_NOT_LEGAL = "F20033";
    /**
     * 成员民族长度为1-5个汉字且不能包含特殊字符（英文\<>'"%），并前后去空格
     */
    String MEMBER__ETHNIC_IS_NOT_LEGAL = "F20034";
    /**
     * 成员宗教信仰不正确
     */
    String MEMBER_RELIGION_IS_NOT_LEGAL = "F20035";
    /**
     * F10036	成员认证状态为空
     */
    String MEMBER_AUTH_STATUS_IS_NULL = "F20036";
    /**
     * 成员认证状态不正确
     */
    String MEMBER_AUTH_STATUS_IS_NOT_TRUE = "F20037";
    /**
     * F10038	户主姓名和联系方式已经存在，即不能重复添加同一个户主
     */
    String OWNER_PHONE_NUM_NAME_IS_EXIST = "F20038";
    /**
     * F10039 成员姓名和联系方式已经存在，即不能重复添加同一个成员
     */
    String MEMBER_PHONE_NUM_NAME_IS_EXIST = "F20039";
    /**
     * 房屋状态不是出租状态，不能添加租户
     */
    String HOUSE_STATUS_IS_NOT_RENT = "F20040";
    /**
     * 房屋不存在
     */
    String HOUSE_IS_NOT_EXIST = "F20041";
    /**
     * 租户已经存在
     */
    String TEMANT_PHOME_NUM_NAME_IS_EXIST = "F20042";
    /**
     * 此帐号已经注册为户主，不能注册成员
     */
    String ACCOUNT_HAS_REGISTER_OWNER = "F20043";

}
