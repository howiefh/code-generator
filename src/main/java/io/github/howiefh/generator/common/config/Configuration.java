package io.github.howiefh.generator.common.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessor;
import com.google.common.collect.Sets;
import com.google.common.io.Resources;
import io.github.howiefh.generator.common.exception.ConfigInitException;
import io.github.howiefh.generator.common.exception.ValidationException;
import io.github.howiefh.generator.common.util.StringUtils;
import io.github.howiefh.generator.common.validation.Rule;
import io.github.howiefh.generator.common.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.IntrospectionException;
import java.io.*;
import java.net.URISyntaxException;
import java.util.Set;

/**
 * @author fenghao on 2016/5/20
 * @version 1.0
 * @since 1.0
 */
public class Configuration {
    private static final Logger LOGGER = LoggerFactory.getLogger(Configuration.class);
    /**
     * 配置
     */
    private static Config config = null;
    public static final String DEFAULT_CONFIG = "config.json";

    public static Config getConfig() {
        if (config == null) {
            throw new NullPointerException("Config is null. Please init config.");
        }
        return config;
    }

    public static Config init() throws ConfigInitException {
        return init(DEFAULT_CONFIG);
    }

    public static Config init(String configFile) throws ConfigInitException {
        try {
            if (StringUtils.isBlank(configFile)) {
                configFile = DEFAULT_CONFIG;
            }
            load(configFile);
            validate();
        } catch (IntrospectionException e) {
            LOGGER.error("Can not get field. {}", e.getMessage());
            throw new ConfigInitException("Can not get field.", e);
        } catch (ValidationException e) {
            LOGGER.error("Validation error. {}", e.getMessage());
            throw new ConfigInitException("Validation error.", e);
        }
        return config;
    }

    private static void load(String configFile) throws ConfigInitException {
        if (config != null) {
            return;
        }
        Reader reader = null;
        try {
            reader = new FileReader(new File(Resources.getResource(configFile).toURI()));
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line = null;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            parse(stringBuilder.toString());
        } catch (FileNotFoundException e) {
            LOGGER.error("File not Found. {}", e.getMessage());
            throw new ConfigInitException("File not Found.", e);
        } catch (IOException e) {
            LOGGER.error("IO error. {}", e.getMessage());
            throw new ConfigInitException("IO error.", e);
        } catch (JSONException e) {
            LOGGER.error("Parse json error. {}", e.getMessage());
            throw new ConfigInitException("Parse json error.", e);
        } catch (URISyntaxException e) {
            LOGGER.error("Parse file URI error. {}", e.getMessage());
            throw new ConfigInitException("Parse file URI error.", e);
        } catch (Exception e) {
            LOGGER.error("Unknown error. {}", e.getMessage());
            throw new ConfigInitException("Unknown error.", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * attributes字段必须有默认值
     *
     * @param context
     */
    private static void parse(String context) {
        ExtraProcessor processor = new ExtraProcessor() {
            public void processExtra(Object object, String key, Object value) {
                if (object instanceof Config) {
                    Config config = (Config) object;
                    config.getAttributes().put(key, value);
                } else if (object instanceof TableCfg) {
                    TableCfg tableCfg = (TableCfg) object;
                    tableCfg.getAttributes().put(key, value);
                } else if (object instanceof TypeCfg) {
                    TypeCfg typeCfg = (TypeCfg) object;
                    typeCfg.getAttributes().put(key, value);
                } else if (object instanceof ImplementCfg) {
                    ImplementCfg implementCfg = (ImplementCfg) object;
                    implementCfg.getAttributes().put(key, value);
                }
            }
        };

        config = JSON.parseObject(context, Config.class, processor, Feature.AllowComment);
    }

    private static void validate() throws IntrospectionException, ValidationException, ConfigInitException {
        String tableTypes = "table.types";
        config = DefaultConfig.initDefaultConfig(config);

        Validator.register(Rule.REQUIRED, Config.class, null);
        Validator.register(Rule.REQUIRED, TableCfg.class, Sets.newHashSet("name"));
        Validator.register(Rule.REQUIRED, tableTypes, TypeCfg.class, Sets.newHashSet("name"));
        Validator.register(Rule.REQUIRED, TypeCfg.class, Sets.newHashSet("name", "template"));
        Validator.register(Rule.REQUIRED, ImplementCfg.class, Sets.newHashSet("name", "columns"));

        Validator.validate(config);
        validateTypes(config.getTypes(), null);
        for (TableCfg tableCfg : config.getTables()) {
            Validator.validate(tableCfg);
            validateTypes(tableCfg.getTypes(), tableTypes);
        }
    }

    private static void validateTypes(Set<TypeCfg> typeCfgs, String typeGroup) throws ValidationException {
        for (TypeCfg typeCfg : typeCfgs) {
            Validator.validate(typeCfg, typeGroup);
            for (ImplementCfg implementCfg : typeCfg.getImpls()) {
                Validator.validate(implementCfg);
            }
        }
    }

    private static void error(Throwable e, String msg) {
        System.err.println(msg + " : " + e.getMessage());
    }
}
