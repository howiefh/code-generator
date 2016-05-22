package ${modelPkg};

<#if dependencies??>
    <#list dependencies as i>
    import ${i};
    </#list>
</#if>

/**
 * ${function}
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
        this.${c.simpleJavaField} = ${c.simpleJavaField};
    }
    </#if>
</#list>
}