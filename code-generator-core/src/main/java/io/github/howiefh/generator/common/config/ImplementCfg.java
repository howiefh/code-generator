package io.github.howiefh.generator.common.config;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import io.github.howiefh.generator.common.entity.NamedModel;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import static io.github.howiefh.generator.common.util.CollectionUtils.emptyMapIfNull;

/**
 * 1. name, columns为必填
 * 2. attributes非必填，默认空映射
 *
 * @author fenghao on 2016/5/20
 * @version 1.0
 * @since 1.0
 */
public class ImplementCfg implements Serializable, NamedModel {
    private static final long serialVersionUID = 9036889977947537906L;
    /**
     * 实现名称
     */
    private String name;
    /**
     * 实现的所需要的字段
     */
    private Set<String> columns;
    /**
     * 扩展字段
     */
    private Map<String, Object> attributes;

    /**
     * 实现名称
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 实现名称
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 实现的所需要的字段
     *
     * @return columns
     */
    public Set<String> getColumns() {
        return columns;
    }

    /**
     * 实现的所需要的字段
     *
     * @param columns
     */
    public void setColumns(Set<String> columns) {
        this.columns = columns;
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImplementCfg implement = (ImplementCfg) o;
        return Objects.equal(name, implement.name) &&
                Objects.equal(columns, implement.columns) &&
                Objects.equal(attributes, implement.attributes);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, columns, attributes);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("columns", columns)
                .add("attributes", attributes)
                .toString();
    }
}
