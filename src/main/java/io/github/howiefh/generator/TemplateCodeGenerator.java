package io.github.howiefh.generator;

import com.google.common.collect.Maps;
import freemarker.ext.beans.BeansWrapperBuilder;
import io.github.howiefh.generator.common.config.*;
import io.github.howiefh.generator.common.exception.ConfigInitException;
import io.github.howiefh.generator.common.util.StringUtils;
import io.github.howiefh.generator.common.util.CollectionUtils;
import io.github.howiefh.generator.entity.Table;
import io.github.howiefh.generator.common.util.DBUtils;
import io.github.howiefh.generator.common.util.FreemarkerUtils;
import io.github.howiefh.generator.common.util.GeneratorUtils;
import io.github.howiefh.generator.dao.TableMetaDataDao;
import org.apache.commons.lang3.RandomUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * @author fenghao on 2016/5/17
 * @version 1.0
 * @since 1.0
 */
public class TemplateCodeGenerator {

    public static final Logger LOGGER = LoggerFactory.getLogger(TemplateCodeGenerator.class);

    private static Config config;

    public static void main(String[] args) {
        try {
            config = Configuration.init("config.json");
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            SqlSession session = sqlSessionFactory.openSession();
            try {
                TableMetaDataDao tableMetaDataDao = session.getMapper(TableMetaDataDao.class);
                for (TableCfg tableCfg : config.getTables()) {
                    if (config.getIgnoreTables() != null && config.getIgnoreTables().contains(tableCfg.getName())){
                        LOGGER.info("Table {} is ignored!", tableCfg.getName() );
                        continue;
                    }
                    Table table = DBUtils.fetchTableFormDb(tableMetaDataDao, tableCfg);
                    if (table == null || table.getName() == null) {
                        LOGGER.warn("Table {} not exits!", tableCfg.getName() );
                        continue;
                    }
                    for (TypeCfg typeCfg : config.getTypes()) {
                        if (tableCfg.getIgnoreTypes() != null && tableCfg.getIgnoreTypes().contains(typeCfg.getName())){
                            LOGGER.info("Type {} is ignored!", typeCfg.getName() );
                            continue;
                        }
                        TypeCfg type = GeneratorUtils.unionIfContains(tableCfg.getTypes(), typeCfg);
                        if (CollectionUtils.isNotEmpty(type.getImpls())) {
                            for (ImplementCfg implementCfg : type.getImpls()) {
                                if (type.getIgnoreImpls() != null && type.getIgnoreImpls().contains(implementCfg.getName())){
                                    LOGGER.info("Implement {} is ignored!", implementCfg.getName() );
                                    continue;
                                }
                                GeneratorContext context = new GeneratorContext(tableCfg, typeCfg, implementCfg, table);
                                generateTemplate(context);
                            }
                        } else {
                            GeneratorContext context = new GeneratorContext(tableCfg, typeCfg, table);
                            generateTemplate(context);
                        }
                    }
                }
            } catch (CloneNotSupportedException e) {
                LOGGER.error("Error {}", e.getMessage());
            } finally{
                session.close();
            }
        } catch (IOException e){
            LOGGER.error("IO error. {}", e.getMessage());
        } catch (ConfigInitException e) {
            LOGGER.error("Config init error. {}", e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Unknown error. {}", e.getMessage());
        }
    }


    /**
     * 生成模板
     * @param context
     */
    private static void generateTemplate(GeneratorContext context) throws CloneNotSupportedException {
        TypeCfg typeCfg = context.getTypeCfg();
        Map model = generateModel(context);
        String filepath = generateTargetFile(context);

        File dir = mkdirsIfNotExists(typeCfg);
        if (dir != null) {
            FreemarkerUtils.generate(model, config.getTemplateDir(), typeCfg.getTemplate(), filepath);
            LOGGER.info("Generate {}", filepath);
        } else {
            LOGGER.error("Create {} fails!", dir);
        }
    }
    /**
     * 生成数据模型
     * @return
     */
    private static Map<String, Object> generateModel(GeneratorContext context) throws CloneNotSupportedException {
        TableCfg tableCfg = context.getTableCfg();
        TypeCfg typeCfg = context.getTypeCfg();
        ImplementCfg implementCfg = context.getImplementCfg();
        Table table = context.getTable();

        Map<String, Object> model = Maps.newHashMap();

        BeansWrapperBuilder beansWrapperBuilder = new BeansWrapperBuilder(freemarker.template.Configuration.VERSION_2_3_21);
        model.put("statics", beansWrapperBuilder.build().getStaticModels());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        model.put("date", simpleDateFormat.format(new Date()));
        model.put("serialVersion", (RandomUtils.nextInt(0, 1)==0?"-":"") + RandomUtils.nextLong(1 ,Long.MAX_VALUE) + "L");

        model.put("author", config.getAuthor());
        model.put("version", config.getVersion());
        model.put("since", config.getSince());
        model.put("configAttrs", config.getAttributes());
        for (TypeCfg type : config.getTypes()) {
            model.put(StringUtils.lowerCase(type.getName()) + "Pkg", type.getPkg());
        }

        model.put("tableAttrs", tableCfg.getAttributes());

        model.put("package", typeCfg.getPkg());
        model.put("target", typeCfg.getTarget());
        Map<String, Set<String>> map = GeneratorUtils.parseDependencies(context);
        if (map != null) {
            Set<String> dependencies = map.get("dependencies");
            dependencies.addAll(table.getImports());
            model.put("dependencies", dependencies);
            model.put("impls", map);
        }

        if (implementCfg != null) {
            model.put("implColumns", implementCfg.getColumns());
            model.put("implClassName", implementCfg.getName());
        }

        model.put("className", StringUtils.uncapitalize(table.getClassName()));
        model.put("ClassName", StringUtils.capitalize(table.getClassName()));
        model.put("function", table.getComments());

        model.put("table", table);
        return model;
    }

    /**
     * 如果目标文件存放目录不存在则创建
     * @param type
     * @return 如果文件目录存在或创建成功返回File对象，否则返回null
     */
    private static File mkdirsIfNotExists(TypeCfg type){
        String path = type.getTarget()
                + File.separator
                + StringUtils.replaceEach(type.getPkg(), new String[]{"."}, new String[]{File.separator});
        File dir = new File(path);
        if (!dir.exists()) {
            if (dir.mkdirs()) {
                return dir;
            }
            return null;
        } else {
            return dir;
        }
    }

    private static String generateTargetFile(GeneratorContext context){
        TypeCfg type = context.getTypeCfg();
        ImplementCfg implementCfg = context.getImplementCfg();
        Table table = context.getTable();
        if (implementCfg == null) {
            return generateTargetFile(type, table.getClassName());
        } else {
            return generateTargetFile(type, implementCfg.getName());
        }
    }

    /**
     * 生成目标文件名
     * @param type
     * @param filename
     * @return
     */
    private static String generateTargetFile(TypeCfg type, String filename){
        if (type == null || filename == null)
            throw new NullPointerException("Generate target file error, please check config! type:" + type + "filename:" + filename);
        return  type.getTarget()
                + File.separator
                + StringUtils.replaceEach(type.getPkg(), new String[]{"."}, new String[]{File.separator})
                + File.separator
                + filename
                + type.getSuffix();
    }
}
