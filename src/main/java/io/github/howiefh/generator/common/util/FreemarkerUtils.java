package io.github.howiefh.generator.common.util;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.*;
import java.util.Map;

/**
 * freemarker通用类
 * @author fenghao on 2016/5/12
 * @since 1.0
 * @version 1.0
 */
public class FreemarkerUtils {

    /**
     * 通过模板输出文件
     *
     * @param model 模板输入参数
     * @param ftlPath 模板文件路径
     * @param ftlName 模板文件名
     * @param outFile 输出文件
     * @return true/false
     */
    public static boolean generate(Map model, String ftlPath, String ftlName, String outFile) {
        boolean result = false;

        Writer out = null;
        try{
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_21);
            FileTemplateLoader ftl = null;
            File ftlDir = new File(ftlPath);
            if (ftlDir.exists()){
                ftl = new FileTemplateLoader(ftlDir);
            } else {
                ftl = new FileTemplateLoader(new File("."));
            }
            ClassTemplateLoader ctl = new ClassTemplateLoader(FreemarkerUtils.class, "/" + ftlPath);
            TemplateLoader[] loaders = new TemplateLoader[] { ftl, ctl };
            MultiTemplateLoader mtl = new MultiTemplateLoader(loaders);
            configuration.setTemplateLoader(mtl);
            Template template = configuration.getTemplate(ftlName);
            File file = new File(outFile);
            out = new OutputStreamWriter(new FileOutputStream(file));
            template.process(model, out);
            result = true;
        } catch(Exception e) {
            e.printStackTrace();
        } finally{
            try{
                out.close();
            } catch(Exception e){
                e.printStackTrace();
            }
        }

        return result;
    }
}