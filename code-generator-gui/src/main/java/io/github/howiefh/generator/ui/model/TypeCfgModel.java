package io.github.howiefh.generator.ui.model;

import io.github.howiefh.generator.common.config.ImplementCfg;
import io.github.howiefh.generator.common.config.TypeCfg;
import io.github.howiefh.generator.common.entity.NamedModel;
import io.github.howiefh.generator.common.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @author fenghao, 2016/7/10
 * @version 1.0
 * @since 1.0
 */
public class TypeCfgModel extends AbstractModel<TypeCfg> implements NamedModel {
    private static final long serialVersionUID = -2446985629137449536L;

    private TypeCfg typeCfg;

    public TypeCfgModel() {
        this(new TypeCfg());
    }

    public TypeCfgModel(TypeCfg typeCfg) {
        this.typeCfg = typeCfg;
    }

    /**
     * @return typeCfg
     */
    @Override
    public TypeCfg getEntry() {
        return typeCfg;
    }

    /**
     * @param typeCfg
     */
    @Override
    public void setEntry(TypeCfg typeCfg) {
        this.typeCfg = typeCfg;
    }

    /**
     * 类型名
     *
     * @return name
     */
    public String getName() {
        return typeCfg.getName();
    }

    /**
     * 类型名
     *
     * @param name
     */
    public void setName(String name) {
        String oldValue = getName();
        typeCfg.setName(name);
        changeSupport.firePropertyChange("name", oldValue, name);
    }

    /**
     * 模板文件名
     *
     * @return template
     */
    public String getTemplate() {
        return typeCfg.getTemplate();
    }

    /**
     * 模板文件名
     *
     * @param template
     */
    public void setTemplate(String template) {
        String oldValue = getTemplate();
        typeCfg.setTemplate(template);
        changeSupport.firePropertyChange("template", oldValue, template);
    }

    /**
     * 目标文件存放目录
     *
     * @return target
     */
    public String getTarget() {
        return typeCfg.getTarget();
    }

    /**
     * 目标文件存放目录
     *
     * @param target
     */
    public void setTarget(String target) {
        String oldValue = getTarget();
        typeCfg.setTarget(target);
        changeSupport.firePropertyChange("target", oldValue, target);
    }

    /**
     * 目标文件的类包
     *
     * @return pkg
     */
    public String getPkg() {
        return typeCfg.getPkg();
    }

    /**
     * 目标文件的类包
     *
     * @param pkg
     */
    public void setPkg(String pkg) {
        String oldValue = getPkg();
        typeCfg.setPkg(pkg);
        changeSupport.firePropertyChange("pkg", oldValue, pkg);
    }

    /**
     * 目标文件后缀
     *
     * @return suffix
     */
    public String getSuffix() {
        return typeCfg.getSuffix();
    }

    /**
     * 目标文件后缀
     *
     * @param suffix
     */
    public void setSuffix(String suffix) {
        String oldValue = getSuffix();
        typeCfg.setSuffix(suffix);
        changeSupport.firePropertyChange("suffix", oldValue, suffix);
    }

    /**
     * 忽略的实现
     *
     * @return ignoreImpls
     */
    public List<String> getIgnoreImpls() {
        return CollectionUtils.convertSetToList(typeCfg.getIgnoreImpls());
    }

    /**
     * 忽略的实现
     *
     * @param ignoreImpls
     */
    public void setIgnoreImpls(List<String> ignoreImpls) {
        List<String> oldValue = getIgnoreImpls();
        typeCfg.setIgnoreImpls(CollectionUtils.convertListToSet(ignoreImpls));
        changeSupport.firePropertyChange("ignoreImpls", oldValue, ignoreImpls);
    }

    /**
     * 实现集合
     *
     * @return impls
     */
    public List<ImplementCfg> getImpls() {
        return CollectionUtils.convertSetToList(typeCfg.getImpls());
    }

    /**
     * 实现集合
     *
     * @param impls
     */
    public void setImpls(List<ImplementCfg> impls) {
        List<ImplementCfg> oldValue = getImpls();
        typeCfg.setImpls(CollectionUtils.convertListToSet(impls));
        changeSupport.firePropertyChange("impls", oldValue, impls);
    }

    /**
     * 依赖集合
     *
     * @return dependencies
     */
    public List<String> getDependencies() {
        return CollectionUtils.convertSetToList(typeCfg.getDependencies());
    }

    /**
     * 依赖集合
     *
     * @param dependencies
     */
    public void setDependencies(List<String> dependencies) {
        List<String> oldValue = getDependencies();
        typeCfg.setDependencies(CollectionUtils.convertListToSet(dependencies));
        changeSupport.firePropertyChange("dependencies", oldValue, dependencies);
    }

    /**
     * 扩展字段
     *
     * @return
     */
    public Map<String, Object> getAttributes() {
        return typeCfg.getAttributes();
    }

    /**
     * 扩展字段
     *
     * @param attributes
     */
    public void setAttributes(Map<String, Object> attributes) {
        Map<String, Object> oldValue = getAttributes();
        typeCfg.setAttributes(attributes);
        changeSupport.firePropertyChange("attributes", oldValue, attributes);
    }
}
