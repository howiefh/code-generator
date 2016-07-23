package io.github.howiefh.generator.dao;

import io.github.howiefh.generator.entity.Table;
import io.github.howiefh.generator.entity.TableColumn;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author fenghao on 2016/5/17
 * @version 1.0
 * @since 1.0
 */
public interface TableMetaDataDao {
    /**
     * 查询表列表
     *
     * @param table
     * @return
     */
    List<Table> findTableList(Table table);

    /**
     * 获取数据表字段
     *
     * @param table
     * @return
     */
    List<TableColumn> findTableColumnList(Table table);

    /**
     * 获取数据表字段
     *
     * @param name
     * @param dbType
     * @return
     */
    List<String> findColumnList(@Param("name") String name, @Param("dbType") String dbType);

    /**
     * 获取数据表主键
     *
     * @param table
     * @return
     */
    List<TableColumn> findTablePks(Table table);
}
