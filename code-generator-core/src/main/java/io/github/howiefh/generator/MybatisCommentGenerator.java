package io.github.howiefh.generator;

import io.github.howiefh.generator.common.config.Config;
import io.github.howiefh.generator.common.config.Configuration;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.DefaultCommentGenerator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * @author fenghao on 2016/5/17
 * @version 1.0
 * @since 1.0
 */
public class MybatisCommentGenerator extends DefaultCommentGenerator {

    public MybatisCommentGenerator() {
        super();
    }

    @Override
    public void addComment(XmlElement xmlElement) {

    }

    /**
     * 类注释
     */
    @Override
    public void addClassComment(InnerClass innerClass,
                                IntrospectedTable introspectedTable) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Config config = Configuration.getConfig();
        innerClass.addJavaDocLine("/**");
        innerClass.addJavaDocLine(" * " + introspectedTable.getFullyQualifiedTable());
        innerClass.addJavaDocLine(" * ");
        innerClass.addJavaDocLine(" * @author " + config.getAuthor() + " on " + sdf.format(new Date()));
        innerClass.addJavaDocLine(" * @version " + config.getVersion());
        innerClass.addJavaDocLine(" * @since " + config.getSince());
        innerClass.addJavaDocLine(" */");
    }

    @Override
    public void addFieldComment(Field field,
                                IntrospectedTable introspectedTable,
                                IntrospectedColumn introspectedColumn) {
        if (introspectedColumn.getRemarks() != null
                && !"".equals(introspectedColumn.getRemarks())) {
            field.addJavaDocLine("/** " + introspectedColumn.getRemarks() + " */");
        }
    }

    @Override
    public void addGetterComment(Method method,
                                 IntrospectedTable introspectedTable,
                                 IntrospectedColumn introspectedColumn) {
        StringBuilder sb = new StringBuilder();

        method.addJavaDocLine("/**");

        sb.append(" * Returns the value of the database column ");
        sb.append(introspectedTable.getFullyQualifiedTable());
        sb.append('.');
        sb.append(introspectedColumn.getActualColumnName());
        sb.append(" - ");
        sb.append(introspectedColumn.getRemarks());
        method.addJavaDocLine(sb.toString());

        method.addJavaDocLine(" *");

        sb.setLength(0);
        sb.append(" * @return the value of ");
        sb.append(introspectedTable.getFullyQualifiedTable());
        sb.append('.');
        sb.append(introspectedColumn.getActualColumnName());
        method.addJavaDocLine(sb.toString());

        method.addJavaDocLine(" */");
    }

    @Override
    public void addSetterComment(Method method,
                                 IntrospectedTable introspectedTable,
                                 IntrospectedColumn introspectedColumn) {
        StringBuilder sb = new StringBuilder();

        method.addJavaDocLine("/**");
        sb.append(" * Sets the value of the database column ");
        sb.append(introspectedTable.getFullyQualifiedTable());
        sb.append('.');
        sb.append(introspectedColumn.getActualColumnName());
        sb.append(" - ");
        sb.append(introspectedColumn.getRemarks());
        method.addJavaDocLine(sb.toString());

        method.addJavaDocLine(" *");

        Parameter param = method.getParameters().get(0);
        sb.setLength(0);
        sb.append(" * @param ");
        sb.append(param.getName());
        sb.append(" the value for ");
        sb.append(introspectedTable.getFullyQualifiedTable());
        sb.append('.');
        sb.append(introspectedColumn.getActualColumnName());
        method.addJavaDocLine(sb.toString());

        method.addJavaDocLine(" */");
    }

    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
        method.addJavaDocLine("/**");

        String methodName = method.getName();
        List<Parameter> parameters = method.getParameters();
        if ("insert".equals(methodName)) {
            method.addJavaDocLine(" * 将实体存入数据库");
        } else if ("selectAll".equals(methodName)) {
            method.addJavaDocLine(" * 返回找到的所有实体的集合");
        }
        for (Parameter param : parameters) {
            StringBuilder sb = new StringBuilder();
            sb.append(" * @param ");
            sb.append(param.getName());
            sb.append(" the value for database table ");
            sb.append(introspectedTable.getFullyQualifiedTable());
            method.addJavaDocLine(sb.toString());
        }

        method.addJavaDocLine(" */");
    }
}

