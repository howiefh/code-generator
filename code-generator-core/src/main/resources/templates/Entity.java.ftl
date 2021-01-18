package ${modelPkg};

import com.google.common.base.Objects;

<#if dependencies??>
    <#list dependencies as i>
    import ${i};
    </#list>
</#if>

/**
 * ${comments}
 * @author ${author} on ${date}
 * @version ${version}
 * @since ${since}
 */
public class ${ClassName} extends BasicEntity {
    private static final long serialVersionUID = ${serialVersion};
<#-- 生成字段属性 -->
<#list table.columns as c>
    <#if c.isNotBaseField() >
        <#if c.comments??>
    /** ${c.comments} */
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
    <#if c.isNotBaseField() >
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
    <#if c.javaType == 'java.lang.String'>
        this.${c.simpleJavaField} = ${c.simpleJavaField} == null ? null : ${c.simpleJavaField}.trim();
    <#else>
        this.${c.simpleJavaField} = ${c.simpleJavaField};
    </#if>
    }
    </#if>
</#list>

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ${ClassName} ${className} = (${ClassName}) o;
        <#assign fields>
            <#list table.columns as c>
                <#if c.isNotBaseField() >
                Objects.equal(${c.javaFieldId}, ${className}.${c.javaFieldId}) &&
                </#if>
            </#list>
        </#assign>
        return ${fields?substring(0, fields?last_index_of("&&"))?trim};
    }

    @Override
    public int hashCode() {
    <#assign fields>
        <#list table.columns as c>
            <#if c.isNotBaseField() >
        ${c.javaFieldId},
            </#if>
        </#list>
    </#assign>
        return Objects.hashCode(${fields?substring(0, fields?last_index_of(","))?trim});
    }

    @Override
    public String toString() {
        <#assign fields>
            <#list table.columns as c>
                <#if c.isNotBaseField() >
            + "'${c.javaFieldId}': '" + ${c.javaFieldId} + "',"
                </#if>
            </#list>
        </#assign>
        return ${ClassName}.class + "{" ${fields?substring(0, fields?last_index_of(" + \"',\""))?trim} + "'}";
    }
}