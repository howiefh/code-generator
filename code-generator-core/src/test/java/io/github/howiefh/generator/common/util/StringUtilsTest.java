package io.github.howiefh.generator.common.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author fenghao, 2016/7/11
 * @version 1.0
 * @since 1.0
 */
public class StringUtilsTest {

    @Test
    public void testToCapitalizeCamelCase() throws Exception {
        String src = "PersonLocal";
        Assert.assertEquals(src, StringUtils.toCapitalizeCamelCase(src));
    }
}