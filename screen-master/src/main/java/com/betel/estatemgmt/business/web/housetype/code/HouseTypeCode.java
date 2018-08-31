package com.betel.estatemgmt.business.web.housetype.code;

/**
 * <p>
 * 户型维护接口返回码描述
 * </p>
 * ClassName: HouseTypeCode <br/>
 * Author: geyf  <br/>
 * Date: 2017/6/20 10:10 <br/>
 * Version: 1.0 <br/>
 */
public interface HouseTypeCode {

    /**
     * 户型名称格式错误
     */
    String HOUSETYPE_FORMAT_ERROR = "G0001";

    /**
     * 户型名称不能超过20字符
     */
    String HOUSETYPE_TOLONG_ERROR = "G0002";

    /**
     * 户型名称不能为空
     */
    String HOUSETYPE_ISNULL_ERROR = "G0003";

    /**
     * 户型名称已存在
     */
    String HOUSETYPE_ISREPEAT_ERROR = "G0004";

    /**
     * 功能区域名称已存在
     */
    String FUNCTION_ISREPEAT_ERROR = "G0005";

    /**
     * 户型ID为空
     */
    String HOUSETYPE_ID_ISNULL_ERROR = "G0006";

    /**
     * 户型下存在房屋不能删除
     */
    String HOUSETYPE_EXISTENCEHOUSE_ERROR = "G0007";

    /**
     * 功能区域ID为空
     */
    String FUNCTION_ID_ISNULL_ERROR = "G0008";

    /**
     * 户型已被删除
     */
    String HOUSETYPE_ISDETELE_ERROR = "G0009";

    /**
     * 户型建材ID为空
     */
    String HOUSETYPEMATERIAL_ID_ISNULL_ERROR = "G0010";

    /**
     * 建材ID为空
     */
    String MATERIAL_ID_ISNULL_ERROR = "G0011";

    /**
     * 该建材已添加
     */
    String MATERIAL_ISADD_ERROR = "G0012";

    /**
     * 关键词格式错误
     */
    String KEYWORD_FORMAT_ERROR = "G0013";

    /**
     * 关键词不能超过60字符
     */
    String KEYWORD_TOLONG_ERROR = "G0014";

    /**
     * 功能区域名称格式错误
     */
    String FUNCTION_FORMAT_ERROR = "G0015";

    /**
     * 功能区域名称不能超过20字符
     */
    String FUNCTION_TOLONG_ERROR = "G0016";

    /**
     * 功能区域名称不能为空
     */
    String FUNCTION_ISNULL_ERROR = "G0017";

    /**
     * 建材已被删除
     */
    String MATERIAL_ISDELETE_ERROR = "G0018";

    /**
     * 功能区域名称不能为全户型和所有
     */
    String FUNCTIONNAME_SENSITIVE_WORDS_ERROR = "G0019";

    /**
     * 功能区域已被删除
     */
    String FUNCTION_DELETE_ERROR = "G0021";

    /**
     *  批量添加时，提示您所选的建材有被删除
     */
    String MATERIALS_ISDELETE_ERROR = "G0022";

    /**
     *  批量删除户型建材时，提示部分功能区域被删除
     */
    String FUNCTIONS_ISDELETE_ERROR = "G0023";


}
