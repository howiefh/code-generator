package io.github.howiefh.generator.types;

import io.github.howiefh.generator.common.util.StringUtils;
import io.github.howiefh.generator.entity.TableColumn;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    protected boolean forceBigDecimals;
    protected boolean useJSR310Types;

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
        typeMap.put("MEDIUMINT", Integer.class.getName());
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
        typeMap.put("YEAR", Short.class.getName());
        typeMap.put("TINYTEXT", String.class.getName());
        typeMap.put("TEXT", String.class.getName());
        typeMap.put("MEDIUMTEXT", String.class.getName());
        typeMap.put("LONGTEXT", String.class.getName());
        typeMap.put("TINYBLOB", "byte[]");
        typeMap.put("MEDIUMBLOB", "byte[]");
        typeMap.put("LONGBLOB", "byte[]");
        typeMap.put("NUMERIC", BigDecimal.class.getName());
        typeMap.put("DECIMAL", BigDecimal.class.getName());
    }

    public String calculateJavaType(TableColumn column) {
        String javaType = typeMap.get(column.getSimpleJdbcType());

        if (javaType != null) {
            javaType = overrideDefaultType(column, javaType);
        }

        return javaType;
    }

    private String overrideDefaultType(TableColumn column, String defaultJavaType) {
        String javaType = defaultJavaType;

        switch (column.getJdbcType()) {
            case "BIT":
                javaType = calculateBitReplacement(column, defaultJavaType);
                break;
            case "DATE":
                javaType = calculateDateType(column, defaultJavaType);
                break;
            case "DECIMAL":
            case "NUMERIC":
                javaType = calculateBigDecimalReplacement(column, defaultJavaType);
                break;
            case "TIME":
                javaType = calculateTimeType(column, defaultJavaType);
                break;
            case "TIMESTAMP":
                javaType = calculateTimestampType(column, defaultJavaType);
                break;
            default:
                break;
        }

        return javaType;
    }

    protected String calculateDateType(TableColumn column, String defaultType) {
        String javaType;

        if (useJSR310Types) {
            javaType = LocalDate.class.getName();
        } else {
            javaType = defaultType;
        }

        return javaType;
    }

    protected String calculateTimeType(TableColumn column, String defaultType) {
        String javaType;

        if (useJSR310Types) {
            javaType = LocalTime.class.getName();
        } else {
            javaType = defaultType;
        }

        return javaType;
    }

    protected String calculateTimestampType(TableColumn column, String defaultType) {
        String javaType;

        if (useJSR310Types) {
            javaType = LocalDateTime.class.getName();
        } else {
            javaType = defaultType;
        }

        return javaType;
    }

    protected String calculateBitReplacement(TableColumn column, String defaultType) {
        String javaType;

        if (column.getLength() > 1) {
            javaType = "byte[]";
        } else {
            javaType = defaultType;
        }

        return javaType;
    }

    protected String calculateBigDecimalReplacement(TableColumn column, String defaultType) {
        String javaType;

        if (column.getScale() > 0 || column.getLength() > 18 || forceBigDecimals) {
            javaType = defaultType;
        } else if (column.getLength() > 9) {
            javaType = Long.class.getName();
        } else if (column.getLength() > 4) {
            javaType = Integer.class.getName();
        } else {
            javaType = Short.class.getName();
        }

        return javaType;
    }
}
