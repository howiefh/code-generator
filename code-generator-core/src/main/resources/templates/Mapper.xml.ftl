<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${daoPkg}.${ClassName}Dao">
    <#assign util = statics["io.github.howiefh.generator.common.util.StringUtils"] >
    <#assign aliasName = util.toFirstChars(table.name) >
    <#assign wherePk>
        <#list table.pks as pk>
        ${pk.name} = ${"#"}{${pk.javaFieldId}} AND
        </#list>
    </#assign>
    <resultMap id="${className}" type="${modelPkg}.${ClassName}" >
    <#list table.columns as c>
        <result column="${aliasName}_${c.name}" property="${c.simpleJavaField}" jdbcType="${c.simpleJdbcType}" />
    </#list>
    </resultMap>
    <#-- 输出字段列 -->
    <sql id="${className}Columns">
    <#assign columnField>
        <#list table.columns as c>
            ${aliasName}.${c.name} AS ${aliasName}_${c.name},
        </#list>
    </#assign>
    ${columnField?substring(0, columnField?last_index_of(","))?trim}
    </sql>

    <sql id="${className}Where">
        <where>
    <#list table.columns as c>
        <#if c.isQuery()>
            <if test="${c.javaFieldId} != null and ${c.javaFieldId} != ''">
            <#if c.queryType?? && c.queryType == 'like'>
                AND ${aliasName}.${c.name} LIKE
                CONCAT('%',${"#"}{${c.javaFieldId}},'%')
            <#elseif c.queryType ?? && c.queryType == 'right_like'>
                AND ${aliasName}.${c.name} LIKE
                CONCAT(${"#"}{${c.javaFieldId}},'%')
            <#elseif c.queryType ?? && c.queryType == 'left_like'>
                AND ${aliasName}.${c.name} LIKE
                CONCAT('%',${"#"}{${c.javaFieldId}})
            <#else>
                AND ${aliasName}.${c.name} ${c.queryType} ${"#"}{${c.javaFieldId}}
            </#if>
            </if>
        </#if>
    </#list>
            AND ${aliasName}.is_valid = ${"#"}{VALID}
        </where>
    </sql>

    <select id="findOne" resultMap="${className}">
        SELECT
        <include refid="${className}Columns"/>
        FROM ${table.name} ${aliasName}
        <#assign wherePkAlias>
            <#list table.pks as pk>
            ${pk.name} = ${"#"}{${pk.javaFieldId}} AND
            </#list>
        </#assign>
        WHERE ${wherePkAlias?substring(0, wherePkAlias?last_index_of(" AND"))?trim}
        AND ${aliasName}.is_valid = ${"#"}{VALID}
    </select>

    <select id="countBy" resultType="long">
        SELECT count(0)
        FROM ${table.name} ${aliasName}
        <include refid="${className}Where"/>
    </select>

    <select id="findBy" resultMap="${className}">
        SELECT
        <include refid="${className}Columns"/>
        FROM ${table.name} ${aliasName}
        <include refid="${className}Where"/>
    </select>

    <select id="findPageBy" resultMap="${className}">
        select
        <include refid="${className}Columns" />
        FROM ${table.name} ${aliasName}
        <include refid="${className}Where"/>
        order by ${aliasName}.id DESC
        limit ${"#"}{offset}, ${"#"}{rows}
    </select>

    <insert id="save">
        INSERT INTO ${table.name}(
    <#assign insertField>
        <#list table.columns as c>
            <#if c.isInsert()>
            ${c.name},
            </#if>
        </#list>
    </#assign>
    ${insertField?substring(0, insertField?last_index_of(","))}
        ) VALUES (
    <#assign insertJavaField>
        <#list table.columns as c>
            <#if c.isInsert()>
                <#if c.isNotBaseField()>
            ${"#"}{${c.javaFieldId}},
                <#else >
            now(),
                </#if>
            </#if>
        </#list>
    </#assign>
    ${insertJavaField?substring(0, insertJavaField?last_index_of(","))}
        )
    </insert>

    <update id="update">
        UPDATE ${table.name}
        <set>
        <#list table.columns as c>
            <#if c.isEdit()>
                <#if c.name == 'version' || c.name == 'created_date' || c.name == 'creator'>
                <#elseif c.isNotBaseField() && c.javaType == 'java.lang.String'>
            <if test="${c.javaFieldId} != null and ${c.javaFieldId} != ''"> ${c.name} = ${"#"}{${c.javaFieldId}},</if>
                <#elseif c.isNotBaseField()>
            <if test="${c.javaFieldId} != null"> ${c.name} = ${"#"}{${c.javaFieldId}},</if>
                <#else >
             ${c.name} = now(),
                </#if>
            </#if>
        </#list>
            <if test="version != null"> version = version + 1,</if>
        </set>
        WHERE ${wherePk?substring(0, wherePk?last_index_of(" AND"))?trim}
        and version = ${"#"}{version}
    </update>

    <update id="delete">
        UPDATE ${table.name}
        <set>
            <if test="version != null"> version = version + 1,</if>
            is_valid = ${"#"}{INVALID}
        </set>
        WHERE ${wherePk?substring(0, wherePk?last_index_of(" AND"))?trim}
        and version = ${"#"}{version}
    </update>
</mapper>