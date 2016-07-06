package io.github.howiefh.generator.types;

import io.github.howiefh.generator.common.util.StringUtils;
import io.github.howiefh.generator.entity.TableColumn;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fenghao on 2016/5/30
 * @version 1.0
 * @since 1.0
 */
public class DefaultJavaTypeResolver {
    protected Map<String, String> typeMap;

    public DefaultJavaTypeResolver() {
        typeMap = new HashMap<String, String>();

        typeMap.put("ARRAY", Object.class.getName());
        typeMap.put("BIGINT", Long.class.getName());
        typeMap.put("BINARY", "byte[]");
        typeMap.put("BIT", Boolean.class.getName());
        typeMap.put("BLOB", "byte[]");
        typeMap.put("BOOLEAN", Boolean.class.getName());
        typeMap.put("CHAR", String.class.getName());
        typeMap.put("CLOB", String.class.getName());
        typeMap.put("DATALINK", Object.class.getName());
        typeMap.put("DATE", Date.class.getName());
        typeMap.put("DISTINCT", Object.class.getName());
        typeMap.put("DOUBLE", Double.class.getName());
        typeMap.put("FLOAT", Double.class.getName());
        typeMap.put("INTEGER", Integer.class.getName());
        typeMap.put("INT", Integer.class.getName());
        typeMap.put("JAVA_OBJECT", Object.class.getName());
        typeMap.put("LONGNVARCHAR", String.class.getName());
        typeMap.put("LONGVARBINARY", "byte[]");
        typeMap.put("LONGVARCHAR", String.class.getName());
        typeMap.put("NCHAR", String.class.getName());
        typeMap.put("NCLOB", String.class.getName());
        typeMap.put("NVARCHAR", String.class.getName());
        typeMap.put("NULL", Object.class.getName());
        typeMap.put("OTHER", Object.class.getName());
        typeMap.put("REAL", Float.class.getName());
        typeMap.put("REF", Object.class.getName());
        typeMap.put("SMALLINT", Short.class.getName());
        typeMap.put("STRUCT", Object.class.getName());
        typeMap.put("DATETIME", Date.class.getName());
        typeMap.put("TIME", Date.class.getName());
        typeMap.put("TIMESTAMP", Date.class.getName());
        typeMap.put("TINYINT", Byte.class.getName());
        typeMap.put("VARBINARY", "byte[]");
        typeMap.put("VARCHAR", String.class.getName());
        typeMap.put("NUMERIC", BigDecimal.class.getName());
        typeMap.put("DECIMAL", BigDecimal.class.getName());
    }

    public String calculateJavaType(
            TableColumn column) {
        String javaType = typeMap.get(column.getSimpleJdbcType());
        if (javaType == null && "NUMBER".equals(column.getSimpleJdbcType())) {
            String[] ss = StringUtils.split(StringUtils.substringBetween(column.getJdbcType(), "(", ")"), ",");
            if (ss != null && (ss.length == 2 && Integer.parseInt(ss[1]) <= 0) || ss.length == 1) {
                if (Integer.parseInt(ss[0]) <= 3) {
                    javaType = Byte.class.getName();
                } else if (Integer.parseInt(ss[0]) <= 5) {
                    javaType = Short.class.getName();
                } else if (Integer.parseInt(ss[0]) <= 10) {
                    javaType = Integer.class.getName();
                } else if (Integer.parseInt(ss[0]) <= 19) {
                    javaType = Long.class.getName();
                }
            } else {
                javaType = BigDecimal.class.getName();
            }
        }
        return javaType;
    }
}
