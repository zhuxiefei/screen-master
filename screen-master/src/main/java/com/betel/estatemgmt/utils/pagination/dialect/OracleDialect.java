package com.betel.estatemgmt.utils.pagination.dialect;

/**
 * @ClassName:OracleDialect
 * @Description:
 * @author:CUI.xx
 * @Date:2016年9月26日上午10:43:34
 * @version 1.1
 */
public class OracleDialect extends Dialect {
    @Override
    public boolean supportsLimit() {
        return true;
    }

    @Override
    public String getLimitString(String sql, int offset, int limit) {
        return getLimiString(sql, offset, Integer.toString(offset), Integer.toString(limit));
    }

    private String getLimiString(final String sql, final int offset,
                                 final String offsetPlaceholder, final String limitPlaceholder) {

        StringBuilder pagingSelect = new StringBuilder(sql.length() + 100);/*-?|ibsmp|张成政|c6|?*/
        if (offset >= 0) {
            pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
        } else {
            pagingSelect.append("select * from ( ");
        }
        pagingSelect.append(sql);
        if (offset >= 0) {
//            String endString = offsetPlaceholder + "+" + limitPlaceholder;
            String endString = String.valueOf(Integer.parseInt(offsetPlaceholder) + Integer.parseInt(limitPlaceholder));
            pagingSelect.append(" ) row_ ) where rownum_ <= ")
                    .append(endString).append(" and rownum_ > ").append(offsetPlaceholder);
        } else {
            pagingSelect.append(" ) where rownum <= ").append(limitPlaceholder);
        }

        return pagingSelect.toString();
    }
}
