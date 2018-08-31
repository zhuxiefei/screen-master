package com.betel.estatemgmt.business.propertyapp.lease.code;

/**
 * <p>
 * 文件说明
 * </p>
 * Author: geyf  <br/>
 * Date: 2018/5/8 9:54 <br/>
 * Version: 1.0 <br/>
 */
public interface LeaseCode {

    /**
     * 租赁公司名称长度为1-50字符
     */
    String COMPANYNAME_FORMAT_ERROR = "LE001";

    /**
     * 租客对接人不能输入大于20个字
     */
    String TENANT_FORMAT_ERROR = "LE002";

    /**
     * 电话不能超过20位
     */
    String TENANTPHONE_FORMAT_ERROR = "LE003";

    /**
     * 长度为1-50字符
     */
    String ADDRESS_FORMAT_ERROR = "LE004";

    /**
     * 不能为空
     */
    String ACREAGE_FORMAT_ERROR = "LE005";

    /**
     * 单价不能为0~100W,可以为小数(最多小数点后两位)
     */
    String RENTUNITPRICE_ERROR = "LE006";

    /**
     * 计租时间必须大于等于合同签订时间
     */
    String STARTTIME_ERROR = "LE007";

    /**
     * 月数1~1000
     */
    String RENTCYCLE_ERROR = "LE008";

    /**
     *名称长度为1-30字符
     */
    String PROPERTYCOMPANY_FORMAT_ERROR = "LE009";

    /**
     * 单价不能为0~1000,可以为小数(最多小数点后两位)
     */
    String PROPERTYUNITPRICE_ERROR = "LE010";

    /**
     * 长度最大限制20
     */
    String PROPERTYPHONE_FORMAT_ERROR = "LE011";

    /**
     * 合同到期时间错误（数值正确且不能小于等于当前）
     */
    String ENDTIME_ERROR = "LE012";

    /**
     * 时间不能为空
     */
    String TIME_NULL = "LE013";

    /**
     * 房屋编号格式错误
     */
    String HOUSENUMBER_ERROR = "LE014";


}