package io.github.howiefh.generator.common.config;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import io.github.howiefh.generator.common.util.DBUtils;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import static io.github.howiefh.generator.common.util.CollectionUtils.emptyMapIfNull;
import static io.github.howiefh.generator.common.util.CollectionUtils.emptySetIfNull;

/**
 * 1. name字段为必填
 * 2. updates、queries、ignoreTypes、types、attributes都是非必填，有默认值
 * 3. className、pk非必填，没有默认值, className如果为空，则设置为表名的驼峰式名称，pk如果数据库和配置都空，设置第一列为主键
 * {@link DBUtils#fetchTableFormDb(io.github.howiefh.generator.dao.TableMetaDataDao, io.github.howiefh.generator.common.config.TableCfg)},
 * {@link DBUtils#initColumnField(io.github.howiefh.generator.entity.Table, io.github.howiefh.generator.common.config.TableCfg)}
 *
 * 1. updates、ignoreTypes、types默认空集合
 * 2. queries、attributes默认空映射
 *
 * @author fenghao on 2016/5/20
 * @version 1.0
 * @since 1.0
 * @see DBUtils#fetchTableFormDb(io.github.howiefh.generator.dao.TableMetaDataDao, io.github.howiefh.generator.common.config.TableCfg)
 * @see DBUtils#initColumnField(io.github.howiefh.generator.entity.Table, io.github.howiefh.generator.common.config.TableCfg)
 */
public class TableCfg implements Serializable{
    private static final long serialVersionUID = -1593705685818161200L;
    /** 表名 */
    private String name;
    /** 类名 */
    private String className;
    /** 主键,配置的主键会覆盖从数据库中的主键 */
    private String pk;
    /** 更新字段集合 */
    private Set<String> updates;
    /** 查询字段集合 */
    private Map<String, String> queries;
    /** 忽略的生成类型集合 */
    private Set<String> ignoreTypes;
    /** 类型集合 */
    private Set<TypeCfg> types;
    /** 扩展字段*/
    private Map<String, Object> attributes;

    /**
     * 表名
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 表名
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 类名
     * @return className
     */
    public String getClassName() {
        return className;
    }

    /**
     * 类名
     * @param className
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * 主键,配置的主键会覆盖从数据库中的主键
     * @return pk
     */
    public String getPk() {
        return pk;
    }

    /**
     * 主键,配置的主键会覆盖从数据库中的主键
     * @param pk
     */
    public void setPk(String pk) {
        this.pk = pk;
    }

    /**
     * 更新字段集合
     * @return updates
     */
    public Set<String> getUpdates() {
        return updates;
    }

    /**
     * 更新字段集合
     * @param updates
     */
    public void setUpdates(Set<String> updates) {
        this.updates = emptySetIfNull(updates);
    }

    /**
     * 查询字段集合
     * @return queries
     */
    public Map<String, String> getQueries() {
        return queries;
    }

    /**
     * 查询字段集合
     * @param queries
     */
    public void setQueries(Map<String, String> queries) {
        this.queries = emptyMapIfNull(queries);
    }

    /**
     * 忽略类型集合
     * @return ignoreTypes
     */
    public Set<String> getIgnoreTypes() {
        return ignoreTypes;
    }

    /**
     * 忽略类型集合
     * @param ignoreTypes
     */
    public void setIgnoreTypes(Set<String> ignoreTypes) {
        this.ignoreTypes = emptySetIfNull(ignoreTypes);
    }

    /**
     * 实现类型集合
     * @return types
     */
    public Set<TypeCfg> getTypes() {
        return types;
    }

    /**
     * 实现类型集合
     * @param types
     */
    public void setTypes(Set<TypeCfg> types) {
        this.types = emptySetIfNull(types);
    }

    /**
     * 扩展字段
     * @return
     */
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    /**
     * 扩展字段
     * @param attributes
     */
    public void setAttributes(Map<String, Object> attributes){
        this.attributes = emptyMapIfNull(attributes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TableCfg tableCfg = (TableCfg) o;
        return Objects.equal(name, tableCfg.name) &&
                Objects.equal(className, tableCfg.className) &&
                Objects.equal(pk, tableCfg.pk) &&
                Objects.equal(updates, tableCfg.updates) &&
                Objects.equal(queries, tableCfg.queries) &&
                Objects.equal(ignoreTypes, tableCfg.ignoreTypes) &&
                Objects.equal(types, tableCfg.types) &&
                Objects.equal(attributes, tableCfg.attributes);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, className, pk, updates, queries, ignoreTypes, types, attributes);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("className", className)
                .add("pk", pk)
                .add("updates", updates)
                .add("queries", queries)
                .add("ignoreTypes", ignoreTypes)
                .add("types", types)
                .add("attributes", attributes)
                .toString();
    }
}
