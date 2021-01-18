<#assign util = statics["io.github.howiefh.generator.common.util.StringUtils"] >
package ${convertPkg};

<#if dependencies??>
    <#list dependencies as i>
import ${i};
    </#list>
</#if>
import ${modelPkg}.${ClassName};

/**
 * ${comments} 转换器
 * @author ${author} on ${date}
 * @version ${version}
 * @since ${since}
 */
public class ${ClassName}Convertor {

    <#list impls?keys as impl>
        <#assign implProps = impls[impl] >
        <#if impl?ends_with("Request")>
    /**
     * Convert ${impl} to ${ClassName}
     *
     * @param ${impl?uncap_first} ${impl} Entity
     */
    public static ${ClassName} to${ClassName}(${impl} ${impl?uncap_first}) {
        ${ClassName} ${className} = new ${ClassName}();
        <#list implProps as c>
        <#assign property = util.toCapitalizeCamelCase(c) >
        ${className}.set${property}(${impl?uncap_first}.get${property}());
        </#list>
        return ${className};
    }
        <#elseif impl?ends_with("Response")>
    /**
     * Convert ${ClassName} to ${impl}
     *
     * @param ${className} ${comments} Entity
     */
    public static ${impl} to${impl}(${ClassName} ${className}) {
            <#assign implVar= impl?uncap_first >
        ${impl} ${implVar} = new ${impl}();
            <#list implProps as c>
                <#assign property = util.toCapitalizeCamelCase(c) >
        ${implVar}.set${property}(${className}.get${property}());
            </#list>
        return ${implVar};
    }
        </#if>
    </#list>
}
