package io.github.howiefh.generator.common.config;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author fenghao on 2016/5/22
 * @version 1.0
 * @since 1.0
 */
public class DefaultConfigTest {

    @Test
    public void testDefaultConfig() throws Exception {
        Config config = DefaultConfig.initDefaultConfig(null);
        Assert.assertNotNull(config);
        Assert.assertNotNull(config.getAuthor());
        Assert.assertNotNull(config.getVersion());
        Assert.assertNotNull(config.getSince());
        Assert.assertNotNull(config.getTemplateDir());
        Assert.assertNotNull(config.getDatabase());
        Assert.assertNotNull(config.getTypes());
        Assert.assertNotNull(config.getTables());
        Assert.assertNotNull(config.getAttributes());
    }
}