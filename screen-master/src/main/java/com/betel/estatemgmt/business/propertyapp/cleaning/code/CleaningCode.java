package com.betel.estatemgmt.business.propertyapp.cleaning.code;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: CleaningCode <br/>
 * Author: Cui.xx  <br/>
 * Date: 2018/2/28 13:42 <br/>
 * Version: 1.0 <br/>
 */
public interface CleaningCode {

    /**
     * 未上传图片
     */
    String IMAGE_NULL = "CL001";

    /**
     * 图片数量超过限制（9个）
     */
    String IMAGE_OUT_OF_NUM = "CL002";

    /**
     * 图片名称过长（120以内）
     */
    String IMAGE_NAME_LENGTH_OUT = "CL003";

    /**
     * 图片格式错误（bmp/jpg/jpeg/png）
     */
    String IMAGE_FORMAT_WRONG="CL004";

    /**
     * 图片大小超过20M
     */
    String IMAGE_OUT_SIZE="CL005";
}
