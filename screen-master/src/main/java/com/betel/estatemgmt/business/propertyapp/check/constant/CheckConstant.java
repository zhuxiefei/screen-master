package com.betel.estatemgmt.business.propertyapp.check.constant;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: CheckConstant <br/>
 * Author: Cui.xx  <br/>
 * Date: 2017/12/5 17:47 <br/>
 * Version: 1.0 <br/>
 */
public interface CheckConstant {

    /**
     * 图片上传最大数量9
     */
    Integer IMAGE_MAX_NUM = 9;

    /**
     * 问题记录最大字符限制
     */
    Integer DESC_MAX_NUM = 500;

    /**
     * 头像名称长度120
     */
    Integer IMAGE_NAME_LENGTH = 120;

    /**
     * 头像大小20M
     */
    int IMAGE_LENGTH_MAX = 20*1024*1024;
    /**
     * 待巡检标题
     */
    String TO_CHECK_TITLE = "巡检任务";
    /**
     * 待巡检任务
     */
    String TO_CHECK_SENDNO = "13";
    /**
     * 系统通知sendType
     */
    String PUSH_SENDTYPE = "smart05";
    /**
     * 系统通知sendContent
     */
    String TO_CHECK__SEND_CONTENT = "您有一个待巡检的任务需处理";

}
