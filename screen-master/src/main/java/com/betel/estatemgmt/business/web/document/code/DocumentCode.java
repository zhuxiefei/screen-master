package com.betel.estatemgmt.business.web.document.code;

/**
 * <p>
 * 资料管理code码
 * </p>
 * ClassName: DocumentCode <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/10/12 15:34 <br/>
 * Version: 1.0 <br/>
 */
public interface DocumentCode {

    /**
     * 类型ID为空
     */
    String TYPE_ID_NULL = "D0100";

    /**
     * 类型不存在
     */
    String TYPE_IS_DELETE = "D0101";

    /**
     * 资料ID为空
     */
    String DOC_ID_NULL = "D0102";

    /**
     * 文件原始名称长度超过120
     */
    String FILE_NAME_MAX = "P0005";

    /**
     * 资料不存在
     */
    String DOC_IS_DELETE = "D0103";

    /**
     * 类别名称为空
     */
    String TYPE_NAME_NULL = "D0104";

    /**
     * 类别名称格式错误
     */
    String TYPENAME_RULE_ERROR = "D0105";

    /**
     * 类别名称重复
     */
    String TYPE_NAME_SAME = "D0106";

    /**
     * 资料名称为空
     */
    String DOC_NAME_NULL = "D0107";

    /**
     * 资料名称格式错误
     */
    String DOCNAME_RULE_ERROR = "D0108";

    /**
     * 资料名称重复
     */
    String DOC_NAME_SAME = "D0109";

    /**
     * 资料内容过长
     */
    String DOC_CONTENT_MAX = "D0110";

    /**
     * 文件为空
     */
    String FILE_IS_NULL = "D0111";

    /**
     * 文件格式错误
     */
    String FILE_RULE_ERROR = "D0112";

    /**
     * 文件大小超过20M
     */
    String FILE_SIZE_MAX = "D0113";

    /**
     * 资料内容为空
     */
    String DOC_CONTENT_NULL = "D0114";
}
