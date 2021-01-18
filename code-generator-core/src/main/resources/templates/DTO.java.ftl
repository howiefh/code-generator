package ${dtoPkg};

<#if dependencies??>
    <#list dependencies as i>
    import ${i};
    </#list>
</#if>
import java.io.Serializable;

/**
 * ${comments}
 * @author ${author} on ${date}
 * @version ${version}
 * @since ${since}
 */
public class ${implClassName} implements Serializable {
    private static final long serialVersionUID = ${serialVersion};
<#-- 生成字段属性 -->
<#list table.columns as c>
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
<#list table.columns as c>
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
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ${implClassName} ${className} = (${implClassName}) o;
        <#assign fields>
            <#list table.columns as c>
                <#if implColumns?seq_contains(c.name) >
            Objects.equal(${c.javaFieldId}, ${className}.${c.javaFieldId}) &&
                </#if>
            </#list>
        </#assign>
        <#if fields?last_index_of("&&") != -1 >
        return ${fields?substring(0, fields?last_index_of("&&"))?trim};
        <#else>
        return ${fields?trim};
        </#if>
    }

    @Override
    public int hashCode() {
        <#assign fields>
            <#list table.columns as c>
                <#if implColumns?seq_contains(c.name) >
            ${c.javaFieldId},
                </#if>
            </#list>
        </#assign>
        <#if fields?last_index_of(",") != -1 >
        return Objects.hashCode(${fields?substring(0, fields?last_index_of(","))?trim});
        <#else>
        return Objects.hashCode(${fields?trim});
        </#if>
    }

    @Override
    public String toString() {
        <#assign fields>
            <#list table.columns as c>
                <#if implColumns?seq_contains(c.name) >
            + "'${c.javaFieldId}': '" + ${c.javaFieldId} + "',"
                </#if>
            </#list>
        </#assign>
        <#if fields?last_index_of(" + \"',\"") != -1 >
        return ${implClassName}.class  + "{" ${fields?substring(0, fields?last_index_of(" + \"',\""))?trim} + "'}";
        <#else>
        return ${implClassName}.class  + "{" ${fields?trim} + "'}";
        </#if>
    }
}