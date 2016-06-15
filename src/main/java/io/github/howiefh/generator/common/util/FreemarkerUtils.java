package io.github.howiefh.generator.common.util;

import freemarker.cache.*;
import freemarker.template.Configuration;
import freemarker.template.Template;
import io.github.howiefh.generator.common.exception.GeneratorException;

import java.io.*;
import java.util.Map;

/**
 * freemarker通用类
 *
 * @author fenghao on 2016/5/12
 * @version 1.0
 * @since 1.0
 */
public class FreemarkerUtils {

    /**
     * 通过模板输出文件
     *
     * @param model   模板输入参数
     * @param ftlPath 模板文件路径
     * @param ftlName 模板文件名
     * @param outFile 输出文件
     * @return true/false
     */
    public static boolean generate(Map model, String ftlPath, String ftlName, File outFile) throws GeneratorException {
        boolean result = false;

        Writer out = null;
        try {
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_21);
            FileTemplateLoader ftl = null;
            File ftlDir = new File(ftlPath);
            if (ftlDir.exists()) {
                ftl = new FileTemplateLoader(ftlDir);
            } else {
                ftl = new FileTemplateLoader(new File("."));
            }
            ClassTemplateLoader ctl = new ClassTemplateLoader(FreemarkerUtils.class, "/" + ftlPath);
            TemplateLoader[] loaders = new TemplateLoader[]{ftl, ctl};
            MultiTemplateLoader mtl = new MultiTemplateLoader(loaders);
            configuration.setTemplateLoader(mtl);
            Template template = configuration.getTemplate(ftlName);
            out = new OutputStreamWriter(new FileOutputStream(outFile));
            template.process(model, out);
            result = true;
        } catch (Exception e) {
            throw new GeneratorException("Generate " + outFile + " with " + ftlName + " error.", e);
        } finally {
            try {
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public static String generateString(Map<String, Object> model, String templateString, String charset) throws GeneratorException {
        Writer out = null;
        try {
            String templateName = "generatorTemplate" + templateString.hashCode();
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_21);
            StringTemplateLoader stringLoader = new StringTemplateLoader();
            stringLoader.putTemplate(templateName, templateString);
            configuration.setTemplateLoader(stringLoader);
            Template template = configuration.getTemplate(templateName, charset);
            // 不需要close http://grepcode.com/file/repository.grepcode.com/java/root/jdk/openjdk/7-b147/java/io/StringWriter.java#StringWriter.close%28%29
            out = new StringWriter(2048);
            template.process(model, out);
            return out.toString();
        } catch (Exception e) {
            throw new GeneratorException("Generate " + templateString + " error.", e);
        }
    }
}