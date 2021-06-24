package io.github.howiefh.generator.service;

import com.google.common.base.Objects;
import com.google.common.collect.Sets;
import io.github.howiefh.generator.common.config.Config;
import io.github.howiefh.generator.common.config.Configuration;
import io.github.howiefh.generator.common.config.TableCfg;
import io.github.howiefh.generator.common.util.DBUtils;
import io.github.howiefh.generator.common.util.StringUtils;
import io.github.howiefh.generator.entity.Table;
import io.github.howiefh.generator.entity.TableColumn;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author fenghao on 2021/1/18
 * @version 1.0
 * @since 1.0
 */
public class GeneratorService {
    /**
     * 星号
     */
    public static final String ASTERISKS = "*";

    private static GeneratorService instance = new GeneratorService();

    /**
     * @return instance
     */
    public static GeneratorService getInstance() {
        return instance;
    }

    public void updateBasicConfig(Config config) throws IOException {
        Configuration.getConfig().setAuthor(config.getAuthor());
        Configuration.getConfig().setSince(config.getSince());
        Configuration.getConfig().setVersion(config.getVersion());
        Configuration.getConfig().setTemplateDir(config.getTemplateDir());
        Configuration.getConfig().setBasePackage(config.getBasePackage());
        Configuration.getConfig().setBaseTarget(config.getBaseTarget());
        Configuration.getConfig().setOverride(config.isOverride());
        Configuration.getConfig().setPrefix(config.getPrefix());
        Configuration.getConfig().setDatabase(config.getDatabase());
        Configuration.getConfig().setJdbcDriver(config.getJdbcDriver());
        Configuration.getConfig().setJdbcUrl(config.getJdbcUrl());
        Configuration.getConfig().setJdbcUsername(config.getJdbcUsername());
        Configuration.getConfig().setJdbcPassword(config.getJdbcPassword());
        Configuration.saveConfig();
        Configuration.initMybatis();
    }


    public List<Table> listTables(String name) {
        Table query = new Table();
        query.setName(name);
        List<Table> tables = TableMetaDataService.getInstance().findTableList(query);
        return tables;
    }

    public Table listColumns(String tableName) {
        Optional<TableCfg> tableCfgOptional = Configuration.getConfig().getTables().stream().filter(v -> Objects.equal(tableName, v.getName())).findFirst();
        TableCfg tableCfg = tableCfgOptional.orElse(new TableCfg());

        if (!tableCfgOptional.isPresent()) {
            tableCfg.setName(tableName);
        }
        Table table = DBUtils.fetchTableFormDb(tableCfg);
        return table;
    }

    public void config(List<TableColumn> columns, final String tableName, String model, String className) {
        Optional<TableCfg> table = Configuration.getConfig().getTables().stream().filter(v -> Objects.equal(tableName, v.getName())).findFirst();
        TableCfg tableCfg = table.orElse(new TableCfg());

        tableCfg.setName(tableName);
        tableCfg.setModel(model);
        tableCfg.setClassName(className);
        tableCfg.setPks(columns.stream().filter(TableColumn::isPk).map(TableColumn::getName).collect(Collectors.toSet()));
        tableCfg.setUpdates(columns.stream().filter(TableColumn::isEdit).map(TableColumn::getName).collect(Collectors.toSet()));
        tableCfg.setRequires(columns.stream().filter(TableColumn::isNotNull).map(TableColumn::getName).collect(Collectors.toSet()));
        tableCfg.setQueries(columns.stream().filter(TableColumn::isQuery).collect(Collectors.toMap(TableColumn::getName, TableColumn::getQueryType, (k1, k2) -> k1)));
        tableCfg.setShowTypes(columns.stream().collect(Collectors.toMap(TableColumn::getName, TableColumn::getShowType, (k1, k2) -> k1)));
        if (!table.isPresent()) {
            Configuration.getConfig().getTables().add(tableCfg);
        }
        Configuration.saveConfig();
    }

    public void updateTableConfig(String tableNames) {
        Set<String> ignoreTables = Collections.emptySet();
        createAllTableConfig();
        if (!StringUtils.equals(tableNames, ASTERISKS) && StringUtils.isNotBlank(tableNames)) {
            Set<String> finalTables = Sets.newHashSet(tableNames.split(","));
            ignoreTables = Configuration.getConfig().getTables().stream()
                    .filter(v -> !finalTables.contains(v.getName())).map(TableCfg::getName).collect(Collectors.toSet());
        }
        Configuration.getConfig().setIgnoreTables(ignoreTables);
        Configuration.saveConfig();
    }

    private void createAllTableConfig() {
        List<Table> tables = TableMetaDataService.getInstance().findTableList(new Table());
        tables.forEach(table -> {
            String tableName = table.getName();
            Table existsTable = listColumns(tableName);
            config(existsTable.getColumns(), existsTable.getName(), existsTable.getModel(), existsTable.getClassName());
        });
    }
}
