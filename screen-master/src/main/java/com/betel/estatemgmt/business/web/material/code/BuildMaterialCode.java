package com.betel.estatemgmt.business.web.material.code;


/**
 * <p>
 * 家装建材业务返回码
 * </p>
 * ClassName: BuildMaterialCode <br/>
 * Author: zhangjian  <br/>
 * Date: 2017/6/21 13:56 <br/>
 * Version: 1.0 <br/>
 */
public interface BuildMaterialCode {
    /**
     *查询内容不合法
     */
    String KEY_WORD_RULE_NOT_LEGAL = "J0001";
    /**
     *建材类型名称为空
     */
    String MATERIAL_TYPE_ISNULL = "J0002";
    /**
     * 材料编号集合为空
     */
    String MATERIAL_IDS_ISNULL="J0025";
    /**
     *材料名称为空
     */
    String MATERIAL_NAME_ISNULL="J0003";
    /**
     *材料名称格式不正确
     */
    String MATERIAL_NAME_RULE_NOT_TRUE="J0004";
    /**
     *该材料名称已存在
     */
    String MATERIAL_NAME_EXIST="J0005";
    /**
     *型号描述字数超过最大限制
     */
    String MATERIAL_MODEL_DESCRIP_WORD_NUM_MORE_MOST="J0006";
    /**
     *品牌描述字数超过最大限制
     */
    String MATERIAL_BRAND_DESCRIP_WORD_NUM_MORE_MOST="J0007";
    /**
     *规格描述字数超过最大限制
     */
    String MATERIAL_SPECIFICATION_DESCRIP_WORD_NUM_MORE_MOST="J0008";
    /**
     *产地描述字数超过最大限制
     */
    String MATERIAL_ORIGIN_DESCRIP_WORD_NUM_MORE_MOST="J0009";
    /**
     *生产日期格式不正确：J0010
     */
    String MATERIAL_PRODUCE_DATE_RULE_ILLEGAL="J0010";
    /**
     *截止日期格式不正确 J0011
     */
    String MATERIAL_WARRANTY_DATE_RULE_ILLEGAL="J0011";
    /**
     *自定义参数名称为空
     */
    String MATERIAL_CUSTOM_PARAM_NAME_ISNULL="J0012";
    /**
     *自定义参数名称格式不正确
     */
    String MATERIAL_CUSTOM_PARAM_NAME_ILLEGAL="J0013";
    /**
     *自定义参数名称重复
     */
    String MATERIAL_CUSTOM_PARAM_NAME_EXIST="J0014";
    /**
     *自定义参数内容为空
     */
    String MATERIAL_CUSTOM_PARAM_CONTENT_ISNULL="J0015";
    /**
     * 自定义参数内容字数超过最大限制：J0016
     */
    String MATERIAL_CUSTOM_PARAM_CONTENT_WORD_NUM_MORE_MOST="J0016";
    /**
     *上传图片为空
     */
    String MATERIAL_PICTURE_EMPTY = "J0022";
    /**
     * 头像格式不正确
     */
    String MATERIAL_PICTURE_FORMAT_ERROR = "J0023";
    /**
     * 建材图片大小超过20M
     */
    String MATERIAL_PICTURE_SIZE_OVER_LIMITE = "J0024";
    /**
     * 判断zip文件是否为空
     */
    String  MATERIAL_UPLOAD_ZIP_ISNULL="J0017";
    /**
     * 判断压缩文件是否是zip文件格式
     */
    String MATERIAL_ZIP_FORMAT_ERROR="J0018";
    /**
     * 判断zip文件的大小
     */
    String MATERIAL_ZIP_SIZE_OVER_MAX_LIMITE="J0019";
    /**
     * zip里excel2003超过数量
     */
    String METERIAL_UPLOAD_ZIP_CONTAIN_EXCEL2003_MORE_LIMIT="J0020";
    /**
     * zip里excel文件还有2007版的
     */
    String METERIAL_UPLOAD_ZIP_CONTAIN_EXCEL2007="J0021";
    /**
     * 判断压缩文件是否在根目录下
     */
    String MATERIAL_ZIP_EXCEL_PIC_NOT_ROOT_DIRECTORY="J0026";
    /**
     *zip里是空的
     */
    String METERIAL_TARGET_FILE_IS_NULL="J0027";
    /**
     * 压缩包里含有文件夹
     */
    String METERIAL_UPLOAD_ZIP_CONTAIN_IS_DIRECTORY="J0028";
    /**
     * zip里不在存在2003版excel
     */
    String METERIAL_UPLOAD_ZIP_CONTAIN_EXCEL2003_NOT_EXIST="J0029";
    /**
     * EXCEL是空数据
     */
    String METERIAL_UPLOAD_ZIP_EXCEL_IS_NULL="J0030";
    /**
     *材料类型输入不正确
     */
    String MATERIAL_TYPE_NAME_ERROR = "J0031";
    /**
     *EXCEL文件里图片名称不合法
     */
    String MATERIAL_ZIP_EXCEL_IMAGE_ISLEGAL="J0032";
    /**
     * Zip包里无实体图片与excel图片名称对应
     */
    String MATERIAL_EXCEL__IMAGE_NOT_IN_ZIP="J0033";

    /**
     * 建材已被删除
     */
    String MATERIAL_HAS_BEEN_DELETE ="J0034";
    /**
     * 生产日期大于截止日期
     */
    String  MATERIAL_PRODUCTDATE_MORE_THAN_WARRANTYDATE = "J0035";


    /**
     * 图片原始名称长度超过60
     */
    String PICTURE_NAME_MAX = "P0005";
    /**
     * 图片原始文件名长度
     */
    int PIC_NAME_SIZE = 120;

    //add
    /**
     * 品牌名称错误
     */
    String MATERIALBRAND_RULE_ERROR="J0036";
    /**
     * 建材型号错误
     */
    String MATERIALMODEL_RULE_ERROR="J0037";
    /**
     * 材料类型名称错误
     */
    String MATERIALTYPE_RULE_ERROR="J0038";












}
