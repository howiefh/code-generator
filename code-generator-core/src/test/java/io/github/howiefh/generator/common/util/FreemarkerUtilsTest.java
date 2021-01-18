package io.github.howiefh.generator.common.util;

import com.google.common.collect.Maps;
import io.github.howiefh.generator.common.exception.GeneratorException;
import org.junit.Test;

import java.io.File;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @author fenghao on 2019/1/3
 * @version 1.0
 * @since 1.0
 */
public class FreemarkerUtilsTest {

    @Test
    public void testGenerate() throws GeneratorException {
        String path = "E:\\jdjr\\jci-git\\generator\\code-generator-core\\src\\main\\resources\\templates";
        String file = "View.vue.ftl";
        String out = "E:\\jdjr\\jci-git\\generator\\code-generator-core\\src\\main\\resources\\templates\\View.vue.html";
        Map<String, Object> map = Maps.newHashMap();
        map.put("one", 1);
        map.put("three", 3);
        map.put("decimal", new BigDecimal("12.982314"));
        Person person = new Person();
        person.setName("tony");
        person.setAmount(new BigDecimal("120000.2"));
        map.put("person", person);
        FreemarkerUtils.generate(map, path, file, new File(out));
    }

    public static class Person {
        private String name;
        private BigDecimal amount;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public BigDecimal getAmount() {
            return amount == null ? null : amount.divide(new BigDecimal("1000"));
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }
    }
}

