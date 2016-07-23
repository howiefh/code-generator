package io.github.howiefh.generator.common;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author fenghao,  2016/7/19.
 * @version 1.0
 * @since 1.0
 */
public class QueryType {
    private static final List<String> queryTypes = Lists.newArrayList("=", "like", "right_like", "left_like");

    /**
     * @return queryTypes
     */
    public static List<String> getQueryTypes() {
        return queryTypes;
    }
}
