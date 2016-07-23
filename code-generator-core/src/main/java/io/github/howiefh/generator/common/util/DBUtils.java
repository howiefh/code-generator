package io.github.howiefh.generator.common.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import io.github.howiefh.generator.common.config.TableCfg;
import io.github.howiefh.generator.entity.Table;
import io.github.howiefh.generator.entity.TableColumn;
import io.github.howiefh.generator.service.TableMetaDataService;
import io.github.howiefh.generator.types.DefaultJavaTypeResolver;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author fenghao on 2016/5/17
 * @version 1.0
 * @since 1.0
 */
public class DBUtils {
    private static final String DEFAULT_QUERY_TYPE = "=";
    private static final String DEFAULT_SHOW_TYPE = "input-text";
    private static DefaultJavaTypeResolver javaTypeResolver = new DefaultJavaTypeResolver();

    public static Table fetchTableFormDb(TableCfg tableCfg) {
        // 如果有表名，则获取物理表
        Table table = null;
        if (StringUtils.isNotBlank(tableCfg.getName())) {
            Table t = new Table();
            t.setName(tableCfg.getName());
            List<Table> list = TableMetaDataService.getInstance().findTableList(t);
            if (list.size() > 0) {
                table = list.get(0);

                // 配置中的类名优先，如果没有配置，使用表名的驼峰式命名
                String className = tableCfg.getClassName();
                if (StringUtils.isBlank(className)) {
                    table.setClassName(StringUtils.toCapitalizeCamelCase(table.getName()));
                } else {
                    table.setClassName(className);
                }

                // 添加列
                List<TableColumn> columns = TableMetaDataService.getInstance().findTableColumnList(table);
                table.setColumns(columns);

                // 获取主键
                table.setPks(TableMetaDataService.getInstance().findTablePks(table));
                // 配置中的主键覆盖数据库的
                Set<String> pks = tableCfg.getPks();
                if (CollectionUtils.isNotEmpty(pks)) {
                    // 清空table.pks，设置为配置文件的pks
                    if (table.getPks() == null) {
                        table.setPks(Lists.<TableColumn>newArrayList());
                    }
                    table.getPks().clear();
                    for (TableColumn column : columns) {
                        if (tableCfg.getPks().contains(column.getName())) {
                            table.getPks().add(column);
                        }
                    }
                }

                initColumnField(table, tableCfg);
            }
        }
        return table;
    }

    /**
     * 初始化列属性字段
     *
     * @param table
     */
    public static void initColumnField(Table table, TableCfg tableCfg) {
        Set<String> edits = tableCfg.getUpdates() == null ? tableCfg.getUpdates() : Sets.<String>newHashSet();
        Map<String, String> queries = tableCfg.getQueries() == null ? tableCfg.getQueries() : Maps.<String, String>newHashMap();
        Map<String, String> showTypes = tableCfg.getShowTypes() == null ? tableCfg.getShowTypes() : Maps.<String, String>newHashMap();

        boolean isUseDefaultEdits = false;
        boolean isUseDefaultQueries = false;
        if (edits.size() == 0) {
            isUseDefaultEdits = true;
            tableCfg.setUpdates(edits);
        }
        if (queries.size() == 0) {
            isUseDefaultQueries = true;
            tableCfg.setQueries(queries);
        }

        for (TableColumn column : table.getColumns()) {
            // 设置java类型
            column.setJavaType(javaTypeResolver.calculateJavaType(column));

            // 设置java字段名
            column.setJavaField(StringUtils.toCamelCase(column.getName()));

            // 配置中的主键覆盖数据库中的，如果数据库、配置都没有配主键，主键默认是读到的第一列
            // 同时设置列是否是主键
            if (CollectionUtils.isEmpty(table.getPks())) {
                // 配置文件配置了主键io.github.howiefh.generator.common.util.DBUtils.fetchTableFormDb方法会覆盖数据库中的主键
                // 配置文件和数据库中都没有主键，主键默认用第一列
                table.setPks(Lists.<TableColumn>newArrayList());
                column.setPk(true);
                table.getPks().add(column);
            } else if (table.getPks().contains(column)) {
                column.setPk(true);
            } else {
                column.setPk(false);
            }

            // 插入字段
            column.setInsert(true);

            // 编辑字段
            if (!isUseDefaultEdits && edits.contains(column.getName())) {
                column.setEdit(true);
            } else if (isUseDefaultEdits && !column.isPk()) { //如果没有配置update字段，则更新除主键外的字段
                column.setEdit(true);
                edits.add(column.getName());
            }
            // 查询字段
            String queryType = queries.get(column.getName());
            if (!isUseDefaultQueries) {
                if (StringUtils.isBlank(queryType)) {
                    // 设置默认查询类型为=
                    column.setQuery(false);
                    column.setQueryType(DEFAULT_QUERY_TYPE);
                } else {
                    column.setQuery(true);
                    column.setQueryType(queryType);
                }
            } else { //如果没有配置查询字段，默认主键为查询字段
                if (column.isPk()) {
                    column.setQuery(true);
                    column.setQueryType(DEFAULT_QUERY_TYPE);
                    queries.put(column.getName(), DEFAULT_QUERY_TYPE);
                }
            }
            // 显示类型
            String showType = showTypes.get(column.getName());
            if (StringUtils.isBlank(showType)) {
                // 设置默认显示类型为input-text
                column.setShowType(DEFAULT_SHOW_TYPE);
            } else {
                column.setShowType(showType);
            }
        }
    }

}