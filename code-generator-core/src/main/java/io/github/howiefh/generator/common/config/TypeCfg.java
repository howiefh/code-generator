package io.github.howiefh.generator.common.config;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import static io.github.howiefh.generator.common.util.CollectionUtils.emptyMapIfNull;
import static io.github.howiefh.generator.common.util.CollectionUtils.emptySetIfNull;

/**
 * 1. name, template为必填项
 * 2. target, pkg, suffix, ignoreImpls, impls, dependencies, attributes非必填，有默认值
 * <p/>
 * 1. target, pkg, suffix默认空串`""`
 * 2. ignoreImpls, impls, dependencies默认空集合
 * 3. attributes默认空映射
 *
 * @author fenghao on 2016/5/20
 * @version 1.0
 * @since 1.0
 */
public class TypeCfg implements Serializable, Cloneable {
    private static final long serialVersionUID = 332289349837091330L;
    public static final String EMPTY = "";
    /**
     * 类型名
     */
    private String name;
    /**
     * 模板文件名
     */
    private String template;
    /**
     * 目标文件存放目录
     */
    private String target;
    /**
     * 目标文件的类包
     */
    private String pkg;
    /**
     * 目标文件后缀
     */
    private String suffix;
    /**
     * 忽略的实现
     */
    private Set<String> ignoreImpls;
    /**
     * 实现集合
     */
    private Set<ImplementCfg> impls;
    /**
     * 依赖集合
     */
    private Set<String> dependencies;
    /**
     * 扩展字段
     */
    private Map<String, Object> attributes;

    /**
     * 类型名
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 类型名
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 模板文件名
     *
     * @return template
     */
    public String getTemplate() {
        return template;
    }

    /**
     * 模板文件名
     *
     * @param template
     */
    public void setTemplate(String template) {
        this.template = template;
    }

    /**
     * 目标文件存放目录
     *
     * @return target
     */
    public String getTarget() {
        return target;
    }

    /**
     * 目标文件存放目录
     *
     * @param target
     */
    public void setTarget(String target) {
        this.target = target == null ? EMPTY : target;
    }

    /**
     * 目标文件的类包
     *
     * @return pkg
     */
    public String getPkg() {
        return pkg;
    }

    /**
     * 目标文件的类包
     *
     * @param pkg
     */
    public void setPkg(String pkg) {
        this.pkg = pkg == null ? EMPTY : pkg;
    }

    /**
     * 目标文件后缀
     *
     * @return suffix
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * 目标文件后缀
     *
     * @param suffix
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix == null ? EMPTY : suffix;
    }

    /**
     * 忽略的实现
     *
     * @return ignoreImpls
     */
    public Set<String> getIgnoreImpls() {
        return ignoreImpls;
    }

    /**
     * 忽略的实现
     *
     * @param ignoreImpls
     */
    public void setIgnoreImpls(Set<String> ignoreImpls) {
        this.ignoreImpls = emptySetIfNull(ignoreImpls);
    }

    /**
     * 实现集合
     *
     * @return impls
     */
    public Set<ImplementCfg> getImpls() {
        return impls;
    }

    /**
     * 实现集合
     *
     * @param impls
     */
    public void setImpls(Set<ImplementCfg> impls) {
        this.impls = emptySetIfNull(impls);
    }

    /**
     * 依赖集合
     *
     * @return dependencies
     */
    public Set<String> getDependencies() {
        return dependencies;
    }

    /**
     * 依赖集合
     *
     * @param dependencies
     */
    public void setDependencies(Set<String> dependencies) {
        this.dependencies = emptySetIfNull(dependencies);
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
        TypeCfg typeCfg = (TypeCfg) o;
        return Objects.equal(name, typeCfg.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, template, target, pkg, suffix, ignoreImpls, impls, dependencies, attributes);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("template", template)
                .add("target", target)
                .add("pkg", pkg)
                .add("suffix", suffix)
                .add("ignoreImpls", ignoreImpls)
                .add("impls", impls)
                .add("dependencies", dependencies)
                .add("attributes", attributes)
                .toString();
    }

    @Override
    public TypeCfg clone() throws CloneNotSupportedException {
        return (TypeCfg) super.clone();
    }
}
