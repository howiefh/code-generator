/*
 * @(#)DefaultJdbcTypeResolver 1.0 2021/6/16
 *
 * Copyright 2021 JDT All Rights Reserved.
 * PROPRIEKARY/CONFIDENKIAL. Use is subject to license terms.
 * Author Email: fenghao1@jd.com
 */
package io.github.howiefh.generator.types;

import io.github.howiefh.generator.entity.TableColumn;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 解决 jdbctype 到 mybatis 支持的 jdbctype 映射.
 *
 * @author fenghao on 2021/6/16
 * @version 1.0
 * @since 1.0
 */
public class DefaultJdbcTypeResolver {
    protected Map<String, String> typeMap;

    private static final DefaultJdbcTypeResolver INSTANCE = new DefaultJdbcTypeResolver();

    private DefaultJdbcTypeResolver() {
        typeMap = new HashMap<String, String>();

        typeMap.put("INT", "INTEGER");
        typeMap.put("MEDIUMINT", "INTEGER");
        typeMap.put("DATETIME", "TIMESTAMP");
        typeMap.put("YEAR", "TIMESTAMP");
        typeMap.put("TINYTEXT", "LONGVARCHAR");
        typeMap.put("TEXT", "LONGVARCHAR");
        typeMap.put("MEDIUMTEXT", "LONGVARCHAR");
        typeMap.put("LONGTEXT", "LONGVARCHAR");
        typeMap.put("TINYBLOB", "BLOB");
        typeMap.put("MEDIUMBLOB", "BLOB");
        typeMap.put("LONGBLOB", "BLOB");
    }

    public static DefaultJdbcTypeResolver getInstance() {
        return INSTANCE;
    }

    public String calculateJdbcType(String defaultJdbcType) {
        String jdbcType = typeMap.get(defaultJdbcType);

        if (jdbcType == null) {
            return defaultJdbcType;
        }

        return jdbcType;
    }

}
