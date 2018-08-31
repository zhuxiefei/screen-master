package com.betel.estatemgmt.business.web.lease.code;

/**
 * <p>
 * 文件说明
 * </p>
 * Author: geyf  <br/>
 * Date: 2018/5/8 15:52 <br/>
 * Version: 1.0 <br/>
 */
public interface WebLeaseCode {
    /**
     * 不能出现\%<>’” 字符（英文），并保证前后去空格，名称长度为1-50字符
     */
    String COMPANYNAME_FORMAT_ERROR = "LE001";

    /**
     * 不能出现\%<>’” 字符（英文），并保证前后去空格，名称长度为1-20字符
     */
    String TENANT_FORMAT_ERROR = "LE002";

    /**
     * 以1开头的11位数字
     */
    String TENANTPHONE_FORMAT_ERROR = "LE003";

    /**
     * 不能出现\%<>’” 字符（英文），并保证前后去空格，长度为1-50字符
     */
    String ADDRESS_FORMAT_ERROR = "LE004";

    /**
     * 不能为空
     */
    String ACREAGE_FORMAT_ERROR = "LE005";

    /**
     * 单价不能为0~100W,可以为小数
     */
    String RENTUNITPRICE_ERROR = "LE006";

    /**
     * 计租时间必须大于等于合同签订时间
     */
    String STARTTIME_ERROR = "LE007";

    /**
     * 月数0~1000
     */
    String RENTCYCLE_ERROR = "LE008";

    /**
     * 不能出现\%<>’” 字符（英文），并保证前后去空格，名称长度为1-30字符
     */
    String PROPERTYCOMPANY_FORMAT_ERROR = "LE009";

    /**
     * 单价不能为0~1000,可以为小数
     */
    String PROPERTYUNITPRICE_ERROR = "LE010";

    /**
     * 长度最大限制20
     */
    String PROPERTYPHONE_FORMAT_ERROR = "LE011";

    /**
     * 校验计租时间加上合同周期是否等于合同到期时间
     */
    String ENDTIME_ERROR = "LE012";

    /**
     * 时间不能为空
     */
    String TIME_NULL = "LE013";

    /**
     * 房屋编号错误
     */
    String HOUSENUMBER_ERROR = "LE014";

    /**
     * 不能出现\%<>’” 字符（英文），并保证前后去空格，长度为0-50字符
     */
    String WEBCOMPANYNAMEANDCOMPANYNAME_FORMAT = "LE015";

    /**
     * 参数为空
     */
    String PARMA_NULL = "LE016";

    /**
     * 租赁时间冲突
     */
    String TIME_CONFLICT = "LE017";

    /**
     * 不能出现\%<>’” 字符（英文），并保证前后去空格，文件名称长度不能超过50字符
     */
    String FILENAME_FORMAT = "LE018";

    /**
     * 文件只能为pdf
     */
    String FILETYPE_FORMAT = "LE019";

    /**
     * 最大50M
     */
    String FILE_SIZE_ERROR = "LE020";

    /**
     * 房屋被删除
     */
    String HOUSE_DELETE = "LE021";
}