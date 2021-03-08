package ${package};

import com.google.common.base.Objects;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import ${basePackage}.domain.BaseEntity;
import java.util.Date;
<#if dependencies??>
    <#list dependencies as i>
import ${i};
    </#list>
</#if>

/**
 * ${comments}
 * @author ${author} ${date}
 * @version ${version}
 * @since ${since}
 */
public class ${ClassName} extends BaseEntity {
    private static final long serialVersionUID = ${serialVersion};
<#-- 生成字段属性 -->
<#list table.columns as c>
    <#if c.isNotBaseEntityField() >
        <#if c.comments??>
    /** ${c.comments} */
        </#if>
        <#if c.isNotNull()>
    <#--@NotNull(message = "${c.comments}不能为空")-->
        </#if>
        <#if c.javaType == 'java.lang.String'>
    <#--@Length(max = ${c.dataLength}, message = "${c.comments}长度不能超过${c.dataLength}")-->
        </#if>
    private ${c.simpleJavaType} ${c.simpleJavaField};
    </#if>
</#list>
    <#-- 构造方法 -->
    public ${ClassName}() {
    }

<#-- 生成get和set方法 -->
<#list table.columns as c>
<#-- 如果不是基类属性 -->
    <#if c.isNotBaseEntityField() >
    /**
     * 获取 <#if c.comments??> ${c.comments}  </#if>
     *
     * @return ${c.simpleJavaField} <#if c.comments??> ${c.comments}  </#if>
     */
    public ${c.simpleJavaType} get${c.simpleJavaField?cap_first}() {
        return ${c.simpleJavaField};
    }

    /**
     * 设置 <#if c.comments??> ${c.comments}  </#if>
     *
     * @param ${c.simpleJavaField} <#if c.comments??> ${c.comments}  </#if>
     */
    public void set${c.simpleJavaField?cap_first}(${c.simpleJavaType} ${c.simpleJavaField}) {
    <#if c.javaType == 'java.lang.String'>
        this.${c.simpleJavaField} = ${c.simpleJavaField} == null ? null : ${c.simpleJavaField}.trim();
    <#else>
        this.${c.simpleJavaField} = ${c.simpleJavaField};
    </#if>
    }
    </#if>
</#list>

    @Override
    public String toString() {
        <#assign fields>
            <#list table.columns as c>
                <#if c.isNotBaseEntityField() >
            + "'${c.javaFieldId}': '" + ${c.javaFieldId} + "',"
                </#if>
            </#list>
        </#assign>
        return "{" ${fields?substring(0, fields?last_index_of(" + \"',\""))?trim} + "'}";
    }
}
