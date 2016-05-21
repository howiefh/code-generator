package io.github.howiefh.generator.common.validation;

import com.google.common.collect.Sets;
import io.github.howiefh.generator.common.config.Config;
import io.github.howiefh.generator.common.exception.ValidationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.beans.IntrospectionException;

/**
 * @author fenghao on 2016/5/21
 * @version 1.0
 * @since 1.0
 */
public class ValidationTest {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();
    private Config config = new Config();
    private Validation validation = new Validation(Config.class);
    @Before
    public void setUp() throws IntrospectionException {
        validation.register(Sets.newHashSet("author"),Sets.newHashSet("version","since","templateDir","types","ignoreTables","tables","attributes"));
    }
    @Test
    public void testValidateFail() throws Exception {
        expectedEx.expect(ValidationException.class);
        validation.validate(config);
    }
    @Test
    public void testValidateSuccess() throws Exception {
        config.setAuthor("fenghao");
        Assert.assertTrue(validation.validate(config));
    }
}