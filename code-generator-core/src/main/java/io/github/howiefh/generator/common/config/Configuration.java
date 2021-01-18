package io.github.howiefh.generator.common.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessor;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.base.Charsets;
import com.google.common.collect.Sets;
import com.google.common.io.Files;
import io.github.howiefh.generator.common.exception.ConfigInitException;
import io.github.howiefh.generator.common.exception.ValidationException;
import io.github.howiefh.generator.common.util.ResourceUtils;
import io.github.howiefh.generator.common.util.StringUtils;
import io.github.howiefh.generator.common.validation.Rule;
import io.github.howiefh.generator.common.validation.Validator;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.IntrospectionException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URISyntaxException;
import java.util.Properties;
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

    private static SqlSessionFactory sqlSessionFactory = null;

    public static Config getConfig() {
        if (config == null) {
            LOGGER.warn("Config is null. Init default config.");
            try {
                config = DefaultConfig.initDefaultConfig(null);
            } catch (ConfigInitException e) {
                LOGGER.warn("Init default config error.");
            }
        }
        return config;
    }

    public static SqlSessionFactory getSqlSessionFactory(){
        if (sqlSessionFactory == null) {
            try {
                initMybatis();
            } catch (IOException e) {
                LOGGER.error("Init Mybatis error.");
            }
        }
        return sqlSessionFactory;
    }

    public static Config init(String configFile) throws ConfigInitException {
        try {
            if (StringUtils.isBlank(configFile)) {
                configFile = Config.DEFAULT_CONFIG;
            }
            load(configFile);
            validate();
            initMybatis();
        } catch (IntrospectionException e) {
            LOGGER.error("Can not get field. {}", e.getMessage());
            throw new ConfigInitException("Can not get field.", e);
        } catch (ValidationException e) {
            LOGGER.error("Validation error. {}", e.getMessage());
            throw new ConfigInitException("Validation error.", e);
        } catch (IOException e) {
            LOGGER.error("Init mybatis error. {}", e.getMessage());
            throw new ConfigInitException("Init Mybatis error.", e);
        }
        return config;
    }

    public static void initMybatis() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream, Configuration.jdbcProperties());
    }

    private static void load(String configFileName) throws ConfigInitException {
        if (config != null) {
            return;
        }
        Reader reader = null;
        try {
            File configFile = ResourceUtils.getResourceFileOrCopyFileIfNotExists(configFileName);
            LOGGER.info("Begin load config from file {}", configFile.getAbsolutePath());
            reader = new FileReader(configFile);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line = null;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            parse(configFileName, stringBuilder.toString());
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

    public static void saveConfig() {
        try {
            if (config == null) {
                return;
            }
            File configFile = ResourceUtils.getResourceFile(config.getConfigPath());
            LOGGER.info("#saveConfig config:{}, configFile:{}", config, configFile.getAbsolutePath());
            JSON.writeJSONString(Files.newWriter(configFile, Charsets.UTF_8), config, SerializerFeature.PrettyFormat);
        } catch (FileNotFoundException e1) {
            LOGGER.warn("#saveConfig config:{}, configFile:{}, e:{}", config, config.getConfigPath(), e1);
        } catch (URISyntaxException e) {
            LOGGER.warn("#saveConfig config:{}, configFile:{}, e:{}", config, config.getConfigPath(), e);
        }
    }

    /**
     * attributes字段必须有默认值
     *
     * @param configFileName
     * @param context
     */
    private static void parse(String configFileName, String context) {
        ExtraProcessor processor = (object, key, value) -> {
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
        };

        config = JSON.parseObject(context, Config.class, processor, Feature.AllowComment);
        if (config != null) {
            config.setConfigPath(configFileName);
        }
    }

    private static Properties jdbcProperties(){
        Properties properties = new Properties();
        properties.setProperty("jdbc.driver", config.getJdbcDriver());
        properties.setProperty("jdbc.url", config.getJdbcUrl());
        properties.setProperty("jdbc.username", config.getJdbcUsername());
        properties.setProperty("jdbc.password", config.getJdbcPassword());
        return properties;
    }

    private static void validate() throws IntrospectionException, ValidationException, ConfigInitException {
        String tableTypes = "table.types";
        config = DefaultConfig.initDefaultConfig(config);

        Validator.register(Rule.REQUIRED, Config.class, Sets.<String>newHashSet("jdbcUrl"));
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
