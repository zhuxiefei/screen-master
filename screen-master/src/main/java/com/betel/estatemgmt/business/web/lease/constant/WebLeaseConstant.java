package com.betel.estatemgmt.business.web.lease.constant;

/**
 * <p>
 * 文件说明
 * </p>
 * Author: geyf  <br/>
 * Date: 2018/5/8 15:51 <br/>
 * Version: 1.0 <br/>
 */
public interface WebLeaseConstant {

    String LEASE_FORMAT = "[^\\\\<>%'\"]{1,30}";

    String FILENAME_FORMAT = "[^\\\\<>%'\"]{1,50}";

    Long PDF_FILE_SIZE = 50 * 1024 * 1024L;

    String PHONE_FORMAT = "[0-9- ()]{1,20}";

    /**
     * 租赁公司
     */
    String COMPANYNAME_FORMAT = "[^\\\\<>%'\"]{1,50}";

    /**
     *  对接人
     */
    String TENANT_FORMAT = "[^\\\\<>%'\"]{1,20}";

    /**
     * 对接人电话
     */
    String TENANTPHONE_FORMAT = "[0-9- ()]{1,20}";

    /**
     * 物业公司
     */
    String PROPERTYCOMPANY_FORMAT = "[^\\\\\\\\<>%'\\\"]{1,30}";

    /**
     * 物业电话
     */
    String PROPERTYPHONE_FORMAT = "[0-9- ()]{1,20}";
}