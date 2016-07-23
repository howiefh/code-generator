package io.github.howiefh.generator.service;

import io.github.howiefh.generator.common.config.Configuration;
import io.github.howiefh.generator.dao.TableMetaDataDao;
import io.github.howiefh.generator.entity.Table;
import io.github.howiefh.generator.entity.TableColumn;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * @author fenghao,  2016/7/19.
 * @version 1.0
 * @since 1.0
 */
public class TableMetaDataService {
    private static TableMetaDataService instance = new TableMetaDataService();
    private SqlSessionFactory sqlSessionFactory = Configuration.getSqlSessionFactory();

    /**
     * @return sqlSessionFactory
     */
    public SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    /**
     * @return instance
     */
    public static TableMetaDataService getInstance() {
        return instance;
    }

    /**
     * 查询表列表
     *
     * @param table
     * @return
     */
    public List<Table> findTableList(Table table) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            TableMetaDataDao tableMetaDataDao = session.getMapper(TableMetaDataDao.class);
            return tableMetaDataDao.findTableList(table);
        } finally {
            session.close();
        }
    }

    /**
     * 获取数据表字段
     *
     * @param table
     * @return
     */
    public List<TableColumn> findTableColumnList(Table table) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            TableMetaDataDao tableMetaDataDao = session.getMapper(TableMetaDataDao.class);
            return tableMetaDataDao.findTableColumnList(table);
        } finally {
            session.close();
        }
    }

    /**
     * 获取数据表字段
     *
     * @param table
     * @return
     */
    public List<String> findColumnList(String table) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            TableMetaDataDao tableMetaDataDao = session.getMapper(TableMetaDataDao.class);
            return tableMetaDataDao.findColumnList(table, Configuration.getConfig().getDatabase());
        } finally {
            session.close();
        }
    }

    /**
     * 获取数据表主键
     *
     * @param table
     * @return
     */
    public List<TableColumn> findTablePks(Table table){
        SqlSession session = sqlSessionFactory.openSession();
        try {
            TableMetaDataDao tableMetaDataDao = session.getMapper(TableMetaDataDao.class);
            return tableMetaDataDao.findTablePks(table);
        } finally {
            session.close();
        }
    }
}
