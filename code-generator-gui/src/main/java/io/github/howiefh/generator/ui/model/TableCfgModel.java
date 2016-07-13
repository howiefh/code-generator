package io.github.howiefh.generator.ui.model;

import io.github.howiefh.generator.common.config.TableCfg;
import io.github.howiefh.generator.common.util.CollectionUtils;
import io.github.howiefh.generator.ui.util.WrapList;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * @author fenghao, 2016/7/11
 * @version 1.0
 * @since 1.0
 */
public class TableCfgModel extends AbstractModel<TableCfg> {
    private static final long serialVersionUID = 2605151232541360423L;
    private TableCfg tableCfg;
    private List<TypeCfgModel> types;


    public TableCfgModel() {
        this(new TableCfg());
    }

    public TableCfgModel(TableCfg tableCfg) {
        this.tableCfg = tableCfg;
        if (tableCfg.getTypes() == null) {
            types = new WrapList<TypeCfgModel>();
            tableCfg.setTypes(((WrapList)types).getEntries());
        } else {
            try {
                types = new WrapList<TypeCfgModel>(tableCfg.getTypes(), TypeCfgModel.class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public TableCfg getEntry() {
        return tableCfg;
    }

    /**
     * @param tableCfg
     */
    @Override
    public void setEntry(TableCfg tableCfg) {
        this.tableCfg = tableCfg;
    }

    /**
     * 表名
     *
     * @return name
     */
    public String getName() {
        return tableCfg.getName();
    }

    /**
     * 表名
     *
     * @param name
     */
    public void setName(String name) {
        String oldValue = getName();
        tableCfg.setName(name);
        changeSupport.firePropertyChange("name", oldValue, name);
    }

    /**
     * 类名
     *
     * @return className
     */
    public String getClassName() {
        return tableCfg.getClassName();
    }

    /**
     * 类名
     *
     * @param className
     */
    public void setClassName(String className) {
        String oldValue = getClassName();
        tableCfg.setClassName(className);
        changeSupport.firePropertyChange("className", oldValue, className);
    }

    /**
     * 主键,配置的主键会覆盖从数据库中的主键
     *
     * @return pks
     */
    public List<String> getPks() {
        return CollectionUtils.convertSetToList(tableCfg.getPks());
    }

    /**
     * 主键,配置的主键会覆盖从数据库中的主键
     *
     * @param pks
     */
    public void setPks(List<String> pks) {
        List<String> oldValue = getPks();
        tableCfg.setPks(CollectionUtils.convertListToSet(pks));
        changeSupport.firePropertyChange("pks", oldValue, pks);
    }

    /**
     * 更新字段集合
     *
     * @return updates
     */
    public List<String> getUpdates() {
        return CollectionUtils.convertSetToList(tableCfg.getUpdates());
    }

    /**
     * 更新字段集合
     *
     * @param updates
     */
    public void setUpdates(List<String> updates) {
        List<String> oldValue = getUpdates();
        tableCfg.setUpdates(CollectionUtils.convertListToSet(updates));
        changeSupport.firePropertyChange("updates", oldValue, updates);
    }

    /**
     * 查询字段映射
     *
     * @return queries
     */
    public List<Map.Entry<String, String>> getQueries() {
        return CollectionUtils.convertMapToEntryList(tableCfg.getQueries());
    }

    /**
     * 查询字段映射
     *
     * @param queries
     */
    public void setQueries(List<Map.Entry<String, String>> queries) {
        List<Map.Entry<String, String>> oldValue = getQueries();
        tableCfg.setQueries(CollectionUtils.convertEntryListToMap(queries));
        changeSupport.firePropertyChange("queries", oldValue, queries);
    }

    /**
     * 显示类型映射
     *
     * @return showTypes
     */
    public List<Map.Entry<String, String>> getShowTypes() {
        return CollectionUtils.convertMapToEntryList(tableCfg.getShowTypes());
    }

    /**
     * 显示类型映射
     *
     * @param showTypes
     */
    public void setShowTypes(List<Map.Entry<String, String>> showTypes) {
        List<Map.Entry<String, String>> oldValue = getQueries();
        tableCfg.setShowTypes(CollectionUtils.convertEntryListToMap(showTypes));
        changeSupport.firePropertyChange("showTypes", oldValue, showTypes);
    }

    /**
     * 忽略类型集合
     *
     * @return ignoreTypes
     */
    public List<String> getIgnoreTypes() {
        return CollectionUtils.convertSetToList(tableCfg.getIgnoreTypes());
    }

    /**
     * 忽略类型集合
     *
     * @param ignoreTypes
     */
    public void setIgnoreTypes(List<String> ignoreTypes) {
        List<String> oldValue = getIgnoreTypes();
        tableCfg.setIgnoreTypes(CollectionUtils.convertListToSet(ignoreTypes));
        changeSupport.firePropertyChange("ignoreTypes", oldValue, ignoreTypes);
    }

    /**
     * 实现类型集合
     *
     * @return types
     */
    public List<TypeCfgModel> getTypes() {
        return types;
    }

    /**
     * 实现类型集合
     *
     * @param types
     */
    public void setTypes(List<TypeCfgModel> types) {
        List<String> oldValue = getIgnoreTypes();
        this.types = types;
        changeSupport.firePropertyChange("types", oldValue, types);
    }

    /**
     * 扩展字段
     *
     * @return
     */
    public Map<String, Object> getAttributes() {
        return tableCfg.getAttributes();
    }

    /**
     * 扩展字段
     *
     * @param attributes
     */
    public void setAttributes(Map<String, Object> attributes) {
        Map<String, Object> oldValue = getAttributes();
        tableCfg.setAttributes(attributes);
        changeSupport.firePropertyChange("attributes", oldValue, attributes);
    }
}
