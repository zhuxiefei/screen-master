package com.betel.estatemgmt.utils.pagination.helper;


import com.betel.estatemgmt.utils.pagination.dialect.DatabaseDialectShortName;
import com.betel.estatemgmt.utils.pagination.dialect.Dialect;
import com.betel.estatemgmt.utils.pagination.dialect.DialectFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName:DialectHelper
 * @Description:
 * @author:CUI.xx
 * @Date:2016年9月26日上午10:43:34
 * @version 1.1
 */
public abstract class DialectHelper {
    private static Map<DatabaseDialectShortName, Dialect> MAPPERS = new HashMap<DatabaseDialectShortName, Dialect>();

    public static Dialect getDialect(DatabaseDialectShortName dialectShortName) {
        if (MAPPERS.containsKey(dialectShortName)) {
            return MAPPERS.get(dialectShortName);
        } else {
            Dialect dialect = DialectFactory.buildDialect(dialectShortName);
            MAPPERS.put(dialectShortName, dialect);
            return dialect;
        }
    }
}
