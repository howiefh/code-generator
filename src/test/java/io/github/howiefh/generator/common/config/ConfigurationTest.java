package io.github.howiefh.generator.common.config;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author fenghao on 2016/5/21
 * @version 1.0
 * @since 1.0
 */
public class ConfigurationTest {

    @Test
    public void testInit() throws Exception {
        Config config = Configuration.init("test.config.json");
        Assert.assertNotNull(config.getAuthor());
        Assert.assertEquals(Config.DEFAULT_AUTHOR, config.getAuthor());
        Assert.assertEquals(Config.DEFAULT_VERSION, config.getVersion());
        Assert.assertEquals(Config.DEFAULT_SINCE, config.getSince());
        Assert.assertEquals(Config.DEFAULT_TEMPLATE_DIR, config.getTemplateDir());
    }
}