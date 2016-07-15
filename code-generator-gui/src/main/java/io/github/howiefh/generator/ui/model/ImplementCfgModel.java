package io.github.howiefh.generator.ui.model;

import io.github.howiefh.generator.common.config.ImplementCfg;
import io.github.howiefh.generator.common.entity.NamedModel;
import io.github.howiefh.generator.common.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @author fenghao, 2016/7/11
 * @version 1.0
 * @since 1.0
 */
public class ImplementCfgModel extends AbstractModel<ImplementCfg> implements NamedModel {
    private static final long serialVersionUID = -1383291859250632681L;
    
    private ImplementCfg implementCfg;

    public ImplementCfgModel() {
        this(new ImplementCfg());
    }

    public ImplementCfgModel(ImplementCfg implementCfg) {
        this.implementCfg = implementCfg;
    }

    @Override
    public ImplementCfg getEntry() {
        return implementCfg;
    }

    /**
     * @param implementCfg
     */
    @Override
    public void setEntry(ImplementCfg implementCfg) {
        this.implementCfg = implementCfg;
    }

    /**
     * 实现名称
     *
     * @return name
     */
    public String getName() {
        return implementCfg.getName();
    }

    /**
     * 实现名称
     *
     * @param name
     */
    public void setName(String name) {
        String oldValue = getName();
        implementCfg.setName(name);
        changeSupport.firePropertyChange("name", oldValue, name);
    }

    /**
     * 实现的所需要的字段
     *
     * @return columns
     */
    public List<String> getColumns() {
        return CollectionUtils.convertSetToList(implementCfg.getColumns());
    }

    /**
     * 实现的所需要的字段
     *
     * @param columns
     */
    public void setColumns(List<String> columns) {
        List<String> oldValue = getColumns();
        implementCfg.setColumns(CollectionUtils.convertListToSet(columns));
        changeSupport.firePropertyChange("columns", oldValue, columns);
    }

    /**
     * 扩展字段
     *
     * @return
     */
    public Map<String, Object> getAttributes() {
        return implementCfg.getAttributes();
    }

    /**
     * 扩展字段
     *
     * @param attributes
     */
    public void setAttributes(Map<String, Object> attributes) {
        Map<String, Object> oldValue = getAttributes();
        implementCfg.setAttributes(attributes);
        changeSupport.firePropertyChange("attributes", oldValue, attributes);
    }

}
