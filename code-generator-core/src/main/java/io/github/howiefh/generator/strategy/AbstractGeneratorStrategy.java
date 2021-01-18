package io.github.howiefh.generator.strategy;

import com.google.common.io.Files;
import freemarker.ext.beans.BeansWrapperBuilder;
import io.github.howiefh.generator.GeneratorContext;
import io.github.howiefh.generator.common.config.*;
import io.github.howiefh.generator.common.exception.ConfigInitException;
import io.github.howiefh.generator.common.exception.GeneratorException;
import io.github.howiefh.generator.common.util.*;
import io.github.howiefh.generator.entity.Table;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * @author fenghao on 2016/5/23
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractGeneratorStrategy implements GeneratorStrategy {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractGeneratorStrategy.class);
    public static final String UTF8 = "UTF-8";
    private static Config config = Configuration.getConfig();
    protected GeneratorContext context;

    public AbstractGeneratorStrategy() {
    }

    @Override
    public void generate() throws GeneratorException {
        try {
            beforeGenerate();
            try {
                for (TableCfg tableCfg : config.getTables()) {
                    if (config.getIgnoreTables() != null && config.getIgnoreTables().contains(tableCfg.getName())) {
                        LOGGER.info("Table {} is ignored!", tableCfg.getName());
                        continue;
                    }
                    Table table = DBUtils.fetchTableFormDb(tableCfg);
                    if (table == null || table.getName() == null) {
                        LOGGER.warn("Table {} not exits!", tableCfg.getName());
                        continue;
                    }
                    for (TypeCfg typeCfg : config.getTypes()) {
                        if (tableCfg.getIgnoreTypes() != null && tableCfg.getIgnoreTypes().contains(typeCfg.getName())) {
                            LOGGER.info("Type {} is ignored!", typeCfg.getName());
                            continue;
                        }
                        TypeCfg type = GeneratorUtils.unionIfContains(tableCfg.getTypes(), typeCfg);
                        if (CollectionUtils.isNotEmpty(type.getImpls())) {
                            for (ImplementCfg implementCfg : type.getImpls()) {
                                if (type.getIgnoreImpls() != null && type.getIgnoreImpls().contains(implementCfg.getName())) {
                                    LOGGER.info("Implement {} is ignored!", implementCfg.getName());
                                    continue;
                                }
                                context = new GeneratorContext(tableCfg, type, implementCfg, table);
                                generateTemplate();
                            }
                        } else {
                            context = new GeneratorContext(tableCfg, type, table);
                            generateTemplate();
                        }
                    }
                }
                afterGenerate();
            } catch (CloneNotSupportedException e) {
                LOGGER.error("Error {}", e.getMessage());
            }
        } catch (ConfigInitException e) {
            LOGGER.error("Config init error. {}", e.getMessage());
        } catch (GeneratorException e) {
            LOGGER.error("Generate error. {}", e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Unknown error. {}", Arrays.toString(e.getStackTrace()));
        }
    }

    public void generateTemplate() throws GeneratorException {
        beforeTemplateGenerate();
        TypeCfg typeCfg = context.getTypeCfg();
        Map<String, Object> model = generateModel();
        File file = generateTargetFile(model);

        try {
            Files.createParentDirs(file);
            FreemarkerUtils.generate(model, config.getTemplateDir(), typeCfg.getTemplate(), file);
            LOGGER.info("Generate {}", file);
        } catch (IOException e) {
            LOGGER.error("Create {}'s parent dir fails!", file);
        }
        afterTemplateGenerate();
    }

    /**
     * 生成数据模型
     *
     * @return
     */
    private Map<String, Object> generateModel() throws GeneratorException {
        TableCfg tableCfg = context.getTableCfg();
        TypeCfg typeCfg = context.getTypeCfg();
        ImplementCfg implementCfg = context.getImplementCfg();
        Table table = context.getTable();

        Map<String, Object> model = context.getModel();

        BeansWrapperBuilder beansWrapperBuilder = new BeansWrapperBuilder(freemarker.template.Configuration.VERSION_2_3_21);
        model.put("statics", beansWrapperBuilder.build().getStaticModels());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        model.put("date", simpleDateFormat.format(new Date()));
        model.put("serialVersion", (RandomUtils.nextInt(0, 1) == 0 ? "-" : "") + RandomUtils.nextLong(1, Long.MAX_VALUE) + "L");

        model.put("config", config);
        model.put("author", config.getAuthor());
        model.put("version", config.getVersion());
        model.put("since", config.getSince());
        model.put("baseTarget", config.getBaseTarget());
        model.put("basePackage", config.getBasePackage());
        model.put("configAttrs", config.getAttributes());
        model.put("tableCfg", tableCfg);
        model.put("typeCfg", typeCfg);
        model.put("implementCfg", implementCfg);

        model.put("tableAttrs", tableCfg.getAttributes());
        model.put("typeAttrs", typeCfg.getAttributes());
        model.put("implementAttrs", implementCfg.getAttributes());

        model.put("implColumns", implementCfg.getColumns());
        model.put("implClassName", implementCfg.getName());

        model.put("className", StringUtils.uncapitalize(table.getClassName()));
        model.put("ClassName", StringUtils.capitalize(table.getClassName()));
        model.put("comments", table.getComments());

        model.put("table", table);

        model.put("target", typeCfg.getTarget(model));
        for (TypeCfg type : config.getTypes()) {
            model.put(StringUtils.lowerCase(type.getName()) + "Pkg", type.getPkg(model));
        }
        model.put("package", typeCfg.getPkg(model));
        Map<String, Set<String>> map = GeneratorUtils.parseDependencies(context);
        if (map != null) {
            Set<String> dependencies = map.get("dependencies");
            dependencies.addAll(table.getImports());
            model.put("dependencies", dependencies);
            model.put("impls", map);
        }
        return model;
    }

    /**
     * 生成目标文件名
     *
     * @return
     */
    private File generateTargetFile(Map<String, Object> model) throws GeneratorException {
        TypeCfg type = context.getTypeCfg();
        return generateTargetFile(model, type);
    }

    /**
     * 生成目标文件名
     *
     * @param type
     * @return
     */
    protected abstract File generateTargetFile(Map<String, Object> model, TypeCfg type) throws GeneratorException;

    protected abstract void beforeTemplateGenerate() throws GeneratorException;

    protected abstract void afterTemplateGenerate() throws GeneratorException;

    protected abstract void beforeGenerate() throws GeneratorException;

    protected abstract void afterGenerate() throws GeneratorException;
}
