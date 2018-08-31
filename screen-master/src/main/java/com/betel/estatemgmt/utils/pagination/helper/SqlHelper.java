package com.betel.estatemgmt.utils.pagination.helper;

import com.betel.estatemgmt.utils.pagination.dialect.Dialect;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * @ClassName:SqlHelper
 * @Description:
 * @author:CUI.xx
 * @Date:2016年9月26日上午10:43:34
 * @version 1.1
 */
public abstract class SqlHelper {
    private final static Logger LOG = LoggerFactory.getLogger(SqlHelper.class);

    public static int getCount(final MappedStatement ms, final Connection connection,
                               final Object parameterObject, Dialect dialect) throws SQLException {
        BoundSql boundSql = ms.getBoundSql(parameterObject);
        String countSql = dialect.getCountString(boundSql.getSql());

        LOG.debug("Total count SQL [{}]", countSql);
        LOG.debug("Parameters: {} ", parameterObject);

        PreparedStatement stmt = null;
        ResultSet rs;
        try {
            stmt = connection.prepareStatement(countSql);
            DefaultParameterHandler handler = new DefaultParameterHandler(ms, parameterObject, boundSql);
            handler.setParameters(stmt);
            rs = stmt.executeQuery();

            int count = 0;
            if (rs.next()) {
                count = rs.getInt(1);
            }

            return count;
        } finally {
            closeStatement(stmt);
        }
    }

    private static void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }
}
