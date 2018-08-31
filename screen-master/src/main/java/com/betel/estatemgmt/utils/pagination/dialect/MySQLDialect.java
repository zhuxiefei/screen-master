package com.betel.estatemgmt.utils.pagination.dialect;

/**
 * @ClassName:MySQLDialect
 * @Description:
 * @author:CUI.xx
 * @Date:2016年9月26日上午10:43:34
 * @version 1.1
 */
public class MySQLDialect extends Dialect {
    @Override
    public boolean supportsLimit() {
        return true;
    }

    @Override
    public String getLimitString(final String sql, final int offset, final int limit) {
        return getLimitString(sql, offset, Integer.toString(offset), Integer.toString(limit));
    }

    private String getLimitString(final String sql, final int offset,
                                  final String offsetPlaceholder, final String limitPlaceholder) {
        StringBuilder stringBuilder = new StringBuilder(sql);
        stringBuilder.append(" limit ");
        if (offset > 0) {
            stringBuilder.append(offsetPlaceholder).append(',').append(limitPlaceholder);
        } else {
            stringBuilder.append(limitPlaceholder);
        }

        return stringBuilder.toString();
    }
}
