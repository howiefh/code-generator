<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${daoPkg}.${ClassName}Dao">
    <#assign aliasName=table.name?substring(0, 1)>
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
        </where>
    </sql>

    <sql id="${className}PksWhere">
        <where>
        <#list table.pks as pk>
            <if test="${pk.javaFieldId} != null and ${pk.javaFieldId} != ''">
                AND ${aliasName}.${pk.name} = ${"#"}{${pk.javaFieldId}}
            </if>
        </#list>
        </where>
    </sql>

    <select id="count" resultType="long">
        SELECT count(0)
        FROM ${table.name}
    </select>

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
    </select>

    <select id="findAll" resultMap="${className}">
        SELECT
        <include refid="${className}Columns"/>
        FROM ${table.name} ${aliasName}
    </select>

    <select id="findBy" resultMap="${className}">
        SELECT
        <include refid="${className}Columns"/>
        FROM ${table.name} ${aliasName}
        <include refid="${className}Where"/>
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
                <#if c.isNotBaseField()>
            <if test="${c.javaFieldId} != null"> ${c.name} = ${"#"}{${c.javaFieldId}},</if>
                <#else >
            ${c.name} = now(),
                </#if>
            </#if>
        </#list>
        </set>
        WHERE ${wherePk?substring(0, wherePk?last_index_of(" AND"))?trim}
    </update>

    <delete id="delete" parameterType="java.lang.String">
        DELETE FROM ${table.name}
        <include refid="${className}PksWhere"/>
    </delete>
</mapper>