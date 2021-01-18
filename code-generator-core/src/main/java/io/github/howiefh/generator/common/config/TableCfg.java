package io.github.howiefh.generator.common.config;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import io.github.howiefh.generator.common.entity.NamedModel;
import io.github.howiefh.generator.common.util.DBUtils;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import static io.github.howiefh.generator.common.util.CollectionUtils.emptyMapIfNull;
import static io.github.howiefh.generator.common.util.CollectionUtils.emptySetIfNull;

/**
 * 1. name字段为必填
 * 2. pks、updates、queries、ignoreTypes、types、attributes都是非必填，有默认值，pk如果数据库和配置都空，设置第一列为主键
 * 3. className非必填，没有默认值, className如果为空，则设置为表名的驼峰式名称
 * {@link DBUtils#fetchTableFormDb(TableCfg)},
 * {@link DBUtils#initColumnField(io.github.howiefh.generator.entity.Table, io.github.howiefh.generator.common.config.TableCfg)}
 * <p/>
 * 1. pks、updates、ignoreTypes、types默认空集合
 * 2. queries、attributes默认空映射
 *
 * @author fenghao on 2016/5/20
 * @version 1.0
 * @see DBUtils#fetchTableFormDb(TableCfg)
 * @see DBUtils#initColumnField(io.github.howiefh.generator.entity.Table, io.github.howiefh.generator.common.config.TableCfg)
 * @since 1.0
 */
public class TableCfg implements Serializable, NamedModel {
    private static final long serialVersionUID = -1593705685818161200L;
    /**
     * 表名
     */
    private String name;
    /**
     * 类名
     */
    private String className;
    /**
     * 模块
     */
    private String model;
    /**
     * 主键,配置的主键会覆盖从数据库中的主键
     */
    private Set<String> pks;
    /**
     * 更新字段集合
     */
    private Set<String> updates;
    /**
     * 必填字段集合
     */
    private Set<String> requires;
    /**
     * 查询字段映射
     */
    private Map<String, String> queries;
    /**
     * 显示类型映射
     */
    private Map<String, String> showTypes;
    /**
     * 忽略的生成类型集合
     */
    private Set<String> ignoreTypes;
    /**
     * 类型集合
     */
    private Set<TypeCfg> types;
    /**
     * 扩展字段
     */
    private Map<String, Object> attributes;

    /**
     * 表名
     *
     * @return name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * 表名
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 类名
     *
     * @return className
     */
    public String getClassName() {
        return className;
    }

    /**
     * 类名
     *
     * @param className
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * 模块
     *
     * @return
     */
    public String getModel() {
        return model;
    }

    /**
     * 模块
     *
     * @param model
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * 主键,配置的主键会覆盖从数据库中的主键
     *
     * @return pks
     */
    public Set<String> getPks() {
        return pks;
    }

    /**
     * 主键,配置的主键会覆盖从数据库中的主键
     *
     * @param pks
     */
    public void setPks(Set<String> pks) {
        this.pks = emptySetIfNull(pks);
    }

    /**
     * 更新字段集合
     *
     * @return updates
     */
    public Set<String> getUpdates() {
        return updates;
    }

    /**
     * 更新字段集合
     *
     * @param updates
     */
    public void setUpdates(Set<String> updates) {
        this.updates = emptySetIfNull(updates);
    }

    /**
     * 必填字段集合
     *
     * @return requires
     */
    public Set<String> getRequires() {
        return requires;
    }

    /**
     * 必填字段集合
     *
     * @param requires
     */
    public void setRequires(Set<String> requires) {
        this.requires = requires;
    }

    /**
     * 查询字段映射
     *
     * @return queries
     */
    public Map<String, String> getQueries() {
        return queries;
    }

    /**
     * 查询字段映射
     *
     * @param queries
     */
    public void setQueries(Map<String, String> queries) {
        this.queries = emptyMapIfNull(queries);
    }

    /**
     * 显示类型映射
     *
     * @return showTypes
     */
    public Map<String, String> getShowTypes() {
        return showTypes;
    }

    /**
     * 显示类型映射
     *
     * @param showTypes
     */
    public void setShowTypes(Map<String, String> showTypes) {
        this.showTypes = emptyMapIfNull(showTypes);
    }

    /**
     * 忽略类型集合
     *
     * @return ignoreTypes
     */
    public Set<String> getIgnoreTypes() {
        return ignoreTypes;
    }

    /**
     * 忽略类型集合
     *
     * @param ignoreTypes
     */
    public void setIgnoreTypes(Set<String> ignoreTypes) {
        this.ignoreTypes = emptySetIfNull(ignoreTypes);
    }

    /**
     * 实现类型集合
     *
     * @return types
     */
    public Set<TypeCfg> getTypes() {
        return types;
    }

    /**
     * 实现类型集合
     *
     * @param types
     */
    public void setTypes(Set<TypeCfg> types) {
        this.types = emptySetIfNull(types);
    }

    /**
     * 扩展字段
     *
     * @return
     */
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    /**
     * 扩展字段
     *
     * @param attributes
     */
    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = emptyMapIfNull(attributes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TableCfg tableCfg = (TableCfg) o;
        return Objects.equal(name, tableCfg.name) &&
                Objects.equal(className, tableCfg.className) &&
                Objects.equal(pks, tableCfg.pks) &&
                Objects.equal(updates, tableCfg.updates) &&
                Objects.equal(requires, tableCfg.requires) &&
                Objects.equal(queries, tableCfg.queries) &&
                Objects.equal(showTypes, tableCfg.showTypes) &&
                Objects.equal(ignoreTypes, tableCfg.ignoreTypes) &&
                Objects.equal(types, tableCfg.types) &&
                Objects.equal(attributes, tableCfg.attributes);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, className, pks, updates, requires, queries, showTypes, ignoreTypes, types, attributes);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("className", className)
                .add("pks", pks)
                .add("updates", updates)
                .add("requires", requires)
                .add("queries", queries)
                .add("showTypes", showTypes)
                .add("ignoreTypes", ignoreTypes)
                .add("types", types)
                .add("attributes", attributes)
                .toString();
    }
}