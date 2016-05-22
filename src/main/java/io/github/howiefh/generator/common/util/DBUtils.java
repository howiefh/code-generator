package io.github.howiefh.generator.common.util;

import io.github.howiefh.generator.common.config.TableCfg;
import io.github.howiefh.generator.dao.TableMetaDataDao;
import io.github.howiefh.generator.entity.Table;
import io.github.howiefh.generator.entity.TableColumn;

import java.util.ArrayList;
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
    public static Table fetchTableFormDb(TableMetaDataDao tableMetaDataDao, TableCfg tableCfg){
        // 如果有表名，则获取物理表
        Table table = null;
        if (StringUtils.isNotBlank(tableCfg.getName())){
            Table t = new Table();
            t.setName(tableCfg.getName());
            List<Table> list = tableMetaDataDao.findTableList(t);
            if (list.size() > 0){
                table = list.get(0);

                // 配置中的类名优先，如果没有配置，使用表名的驼峰式命名
                String className = tableCfg.getClassName();
                if (StringUtils.isBlank(className)) {
                    table.setClassName(StringUtils.toCapitalizeCamelCase(table.getName()));
                } else {
                    table.setClassName(className);
                }

                // 添加列
                List<TableColumn> columns = tableMetaDataDao.findTableColumnList(table);
                table.setColumns(columns);

                // 获取主键
                table.setPks(tableMetaDataDao.findTablePK(table));
                // 配置中的主键覆盖数据库的
                String pk = tableCfg.getPk();
                if (StringUtils.isNotBlank(pk)) {
                    if (CollectionUtils.isEmpty(table.getPks())){
                        table.getPks().clear();
                        table.getPks().add(pk);
                    }
                }

                initColumnField(table, tableCfg);
            }
        }
        return table;
    }

    /**
     * 初始化列属性字段
     * @param table
     */
    public static void initColumnField(Table table, TableCfg tableCfg){
        Set<String> edits = tableCfg.getUpdates();
        Map<String,String> queries = tableCfg.getQueries();
        for (TableColumn column : table.getColumns()){
            // 设置java类型
            if (StringUtils.startsWithIgnoreCase(column.getJdbcType(), "CHAR")
                    || StringUtils.startsWithIgnoreCase(column.getJdbcType(), "VARCHAR")
                    || StringUtils.startsWithIgnoreCase(column.getJdbcType(), "NARCHAR")){
                column.setJavaType("String");
            }else if (StringUtils.startsWithIgnoreCase(column.getJdbcType(), "DATETIME")
                    || StringUtils.startsWithIgnoreCase(column.getJdbcType(), "DATE")
                    || StringUtils.startsWithIgnoreCase(column.getJdbcType(), "TIMESTAMP")){
                column.setJavaType("java.util.Date");
                column.setShowType("dateselect");
            }else if (StringUtils.startsWithIgnoreCase(column.getJdbcType(), "BIGINT")
                    || StringUtils.startsWithIgnoreCase(column.getJdbcType(), "NUMBER")){
                // 如果是浮点型
                String[] ss = StringUtils.split(StringUtils.substringBetween(column.getJdbcType(), "(", ")"), ",");
                if (ss != null && ss.length == 2 && Integer.parseInt(ss[1])>0){
                    column.setJavaType("Double");
                }
                // 如果是整形
                else if (ss != null && ss.length == 1 && Integer.parseInt(ss[0])<=10){
                    column.setJavaType("Integer");
                }
                // 长整形
                else{
                    column.setJavaType("Long");
                }
            }else if (StringUtils.startsWithIgnoreCase(column.getJdbcType(), "TINYINT")) {
                column.setJavaType("Boolean");
            }

            // 设置java字段名
            column.setJavaField(StringUtils.toCamelCase(column.getName()));

            // 配置中的主键覆盖数据库中的，如果数据库、配置都没有配主键，主键默认是读到的第一列
            // 是否是主键
            if (table.getPks() == null) {
                table.setPks(new ArrayList<String>());
                column.setPk(true);
                table.setPk(column);
            } else if (table.getPks().contains(column.getName())) {
                column.setPk(true);
                table.setPk(column);
            } else {
                column.setPk(false);
            }

            // 插入字段
            column.setInsert(true);

            // 编辑字段
            if (edits.contains(column.getName())){
                column.setEdit(true);
            }
            // 查询字段
            String queryType = queries.get(column.getName());
            if (queryType != null){
                column.setQuery(true);
                // 设置默认查询类型为=
                if (queryType.trim().length() == 0) {
                    column.setQueryType(DEFAULT_QUERY_TYPE);
                } else {
                    column.setQueryType(queryType);
                }
            }
        }
    }

}
