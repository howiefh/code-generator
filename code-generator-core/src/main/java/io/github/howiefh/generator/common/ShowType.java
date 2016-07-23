package io.github.howiefh.generator.common;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author fenghao,  2016/7/19.
 * @version 1.0
 * @since 1.0
 */
public class ShowType {
    private static final List<String> showTypes = Lists.newArrayList("select","input-text","input-tel",
            "input-email","input-url","input-password","input-date");

    /**
     * @return showTypes
     */
    public static List<String> getShowTypes() {
        return showTypes;
    }
}
