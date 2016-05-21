package ${dtoPkg};

<#-- TODO 完善导入 -->
<#list table.importList as i>
import ${i};
</#list>
import java.io.Serializable;

/**
 * ${function}
 * @author ${author} on ${date}
 * @version ${version}
 * @since ${since}
 */
public class ${implClassName} implements Serializable {
    private static final long serialVersionUID = ${serialVersion};
<#-- 生成字段属性 -->
<#list table.columnList as c>
    <#if implColumns?seq_contains(c.name) >
        <#if c.comments??>
    /** ${c.comments} */
        </#if>
    private ${c.simpleJavaType} ${c.simpleJavaField};
    </#if>
</#list>
    <#-- 构造方法 -->
    public ${implClassName}() {
    }

<#-- 生成get和set方法 -->
<#list table.columnList as c>
    <#if implColumns?seq_contains(c.name) >
<#-- 如果不是基类属性 -->
    /**
     * Returns the value of the database column ${table.name}.${c.name}
     *
     * @return ${c.simpleJavaField} <#if c.comments??> ${c.comments}  </#if>
     */
    public ${c.simpleJavaType} get${c.simpleJavaField?cap_first}() {
        return ${c.simpleJavaField};
    }

    /**
     * Sets the value of the database column ${table.name}.${c.name}
     *
     * @param ${c.simpleJavaField} <#if c.comments??> ${c.comments}  </#if>
     */
    public void set${c.simpleJavaField?cap_first}(${c.simpleJavaType} ${c.simpleJavaField}) {
        this.${c.simpleJavaField} = ${c.simpleJavaField};
    }
    </#if>
</#list>
}