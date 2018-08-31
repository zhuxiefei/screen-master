package com.betel.estatemgmt.utils.pagination.dialect;

/**
 * @ClassName:DialectFactory
 * @Description:
 * @author:CUI.xx
 * @Date:2016年9月26日上午10:43:34
 * @version 1.1
 */
public abstract class DialectFactory {
    public static Dialect buildDialect(DatabaseDialectShortName databaseName) {
        switch (databaseName) {
            case MYSQL:
                return new MySQLDialect();
            case ORACLE:
                return new OracleDialect();
            default:
                throw new UnsupportedOperationException();
        }
    }
}
