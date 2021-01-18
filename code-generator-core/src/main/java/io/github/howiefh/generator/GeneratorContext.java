package io.github.howiefh.generator;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import io.github.howiefh.generator.common.config.ImplementCfg;
import io.github.howiefh.generator.common.config.TableCfg;
import io.github.howiefh.generator.common.config.TypeCfg;
import io.github.howiefh.generator.common.util.StringUtils;
import io.github.howiefh.generator.entity.Table;
import io.github.howiefh.generator.entity.TableColumn;

import java.util.Map;
import java.util.Set;

/**
 * @author fenghao on 2016/5/20
 * @version 1.0
 * @since 1.0
 */
public class GeneratorContext {
    /**
     * 表设置
     */
    private TableCfg tableCfg;
    /**
     * 类型设置
     */
    private TypeCfg typeCfg;
    /**
     * 实现设置
     */
    private ImplementCfg implementCfg;
    /**
     * 表
     */
    private Table table;

    private Map<String, Object> model = Maps.newHashMap();

    public GeneratorContext(TableCfg tableCfg, TypeCfg typeCfg, Table table) {
        this(tableCfg, typeCfg, null, table);
    }

    public GeneratorContext(TableCfg tableCfg, TypeCfg typeCfg, ImplementCfg implementCfg, Table table) {
        if (implementCfg == null) { // 如果没有设置类型的实现，则设置默认类型实现
            implementCfg = new ImplementCfg();
            implementCfg.setName(table.getClassName() + StringUtils.toUpperCaseFirstChar(typeCfg.getName()));
            Set<String> columns = Sets.newHashSet();
            for (TableColumn column: table.getPks()) {
                columns.add(column.getName());
            }
            implementCfg.setColumns(columns);
        }
        this.tableCfg = tableCfg;
        this.typeCfg = typeCfg;
        this.implementCfg = implementCfg;
        this.table = table;
    }

    /**
     * 表设置
     *
     * @return tableCfg
     */
    public TableCfg getTableCfg() {
        return tableCfg;
    }

    /**
     * 表设置
     *
     * @param tableCfg
     */
    public void setTableCfg(TableCfg tableCfg) {
        this.tableCfg = tableCfg;
    }

    /**
     * 类型设置
     *
     * @return typeCfg
     */
    public TypeCfg getTypeCfg() {
        return typeCfg;
    }

    /**
     * 类型设置
     *
     * @param typeCfg
     */
    public void setTypeCfg(TypeCfg typeCfg) {
        this.typeCfg = typeCfg;
    }

    /**
     * 实现设置
     *
     * @return implementCfg
     */
    public ImplementCfg getImplementCfg() {
        return implementCfg;
    }

    /**
     * 实现设置
     *
     * @param implementCfg
     */
    public void setImplementCfg(ImplementCfg implementCfg) {
        this.implementCfg = implementCfg;
    }

    /**
     * 表
     *
     * @return table
     */
    public Table getTable() {
        return table;
    }

    /**
     * 表
     *
     * @param table
     */
    public void setTable(Table table) {
        this.table = table;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public void setModel(Map<String, Object> model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("tableCfg", tableCfg)
                .add("typeCfg", typeCfg)
                .add("implementCfg", implementCfg)
                .add("table", table)
                .toString();
    }
}
