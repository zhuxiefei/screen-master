package com.betel.estatemgmt.business.web.cleaning.code;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: CleaningCode <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/2/28 9:50 <br/>
 * Version: 1.0 <br/>
 */
public interface CleaningCode {

    /**
     * 区域名称为空
     */
    String AREA_NAME_NULL = "C0100";

    /**
     * 区域名称格式错误
     */
    String AREA_NAME_ERROR = "C0101";

    /**
     * 区域名称重复
     */
    String AREA_NAME_SAME = "C0102";

    /**
     * 区域下有类型
     */
    String AREA_HAS_TYPE = "C0103";

    /**
     * 保洁类型为空
     */
    String TYPE_NAME_NULL = "C0104";

    /**
     * 保洁类型格式错误
     */
    String TYPE_NAME_ERROR = "C0105";

    /**
     * 同一区域的保洁类型重复
     */
    String TYPE_NAME_SAME = "C0106";

    /**
     * 保洁类型下有保洁项
     */
    String TYPE_HAS_CONTENT = "C0107";

    /**
     * 保洁区域Id为空
     */
    String AREA_ID_NULL = "C0108";

    /**
     * 保洁区域已被删除
     */
    String AREA_IS_DELETE = "C0109";

    /**
     * 类型ID为空
     */
    String TYPE_ID_NULL = "C0110";

    /**
     * 保洁类型被删除
     */
    String TYPE_IS_DELETE = "C0111";

    /**
     * 所在位置为空
     */
    String LOCATION_NULL = "C0112";

    /**
     * 所在位置格式错误
     */
    String LOCATION_ERROR = "C0113";

    /**
     * 保洁周期为空
     */
    String CYCLE_NULL = "C0114";

    /**
     * 保洁内容为空
     */
    String CONTENT_NULL = "C0115";

    /**
     * 保洁内容格式错误
     */
    String CONTENT_ERROR = "C0116";

    /**
     * 起始时间不能大于结束时间
     */
    String START_LATE_END = "C0117";

    /**
     * 保洁编号格式错误
     */
    String CONTENT_NO_ERROR = "C0118";

    /**
     * 时间格式错误
     */
    String TIME_ERROR = "C0119";

    /**
     * 保洁内容Id为空
     */
    String CONTENT_ID_NULL = "C0120";

    /**
     * 保洁内容已删除
     */
    String CONTENT_IS_DELETE = "C0121";
}
