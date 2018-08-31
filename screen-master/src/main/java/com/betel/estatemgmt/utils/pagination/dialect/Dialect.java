package com.betel.estatemgmt.utils.pagination.dialect;

/**
 * @ClassName:Dialect
 * @Description:
 * @author:CUI.xx
 * @Date:2016年9月26日上午10:43:34
 * @version 1.1
 */
public abstract class Dialect {
    /**
     * 数据库本身是否支持分页查询
     *
     * @return {@code true} 支持分页查询
     */
    public abstract boolean supportsLimit();

    /**
     * 将sql包装成数据库支持的特有查询语句
     *
     * @param sql    SQL语句
     * @param offset 开始位置
     * @param limit  每页显示的记录数
     * @return 数据库专属分页查询sql
     */
    public abstract String getLimitString(String sql, int offset, int limit);

    /**
     * 将sql包装成统计总数SQL
     *
     * @param sql SQL语句
     * @return 统计总数SQL
     */
    public String getCountString(String sql) {
        return "select count(1) from (" + sql + ") tmp_count";
    }
}
