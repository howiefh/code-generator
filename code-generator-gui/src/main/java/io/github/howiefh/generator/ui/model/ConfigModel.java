package io.github.howiefh.generator.ui.model;

import io.github.howiefh.generator.common.config.Config;
import io.github.howiefh.generator.common.config.Configuration;
import io.github.howiefh.generator.common.config.TableCfg;
import io.github.howiefh.generator.common.config.TypeCfg;

import java.util.Map;
import java.util.Set;

/**
 * @author fenghao, 2016/7/9
 * @version 1.0
 * @since 1.0
 */
public class ConfigModel extends AbstractModel<Config> {
    private static final long serialVersionUID = 1349713680725743246L;
    private Config config;

    public ConfigModel(Config config) {
        this.config = config;
    }

    public ConfigModel() {
        this(Configuration.getConfig());
    }

    @Override
    public Config getEntry() {
        return config;
    }

    @Override
    public void setEntry(Config config) {
        this.config = config;
    }

    /**
     * 作者
     *
     * @param author
     */
    public void setAuthor(String author) {
        String oldValue = config.getAuthor();
        config.setAuthor(author);
        changeSupport.firePropertyChange("author", oldValue, author);
    }

    /**
     * 作者
     *
     * @return author
     */
    public String getAuthor() {
        return config.getAuthor();
    }

    /**
     * 版本
     *
     * @param version
     */
    public void setVersion(String version) {
        String oldValue = config.getVersion();
        config.setVersion(version);
        changeSupport.firePropertyChange("version", oldValue, version);
    }

    /**
     * 版本
     *
     * @return version
     */
    public String getVersion() {
        return config.getVersion();
    }

    /**
     * 起始
     *
     * @param since
     */
    public void setSince(String since) {
        String oldValue = config.getSince();
        config.setSince(since);
        changeSupport.firePropertyChange("since", oldValue, since);
    }

    /**
     * 起始
     *
     * @return since
     */
    public String getSince() {
        return config.getSince();
    }

    /**
     * 模板目录
     *
     * @param templateDir
     */
    public void setTemplateDir(String templateDir) {
        String oldValue = config.getTemplateDir();
        config.setTemplateDir(templateDir);
        changeSupport.firePropertyChange("templateDir", oldValue, templateDir);
    }

    /**
     * 模板目录
     *
     * @return templateDir
     */
    public String getTemplateDir() {
        return config.getTemplateDir();
    }
    
    /**
     * 数据库类型
     *
     * @param database
     */
    public void setDatabase(String database) {
        String oldValue = config.getDatabase();
        config.setDatabase(database);
        changeSupport.firePropertyChange("database", oldValue, database);
    }

    /**
     * 数据库类型
     *
     * @return database
     */
    public String getDatabase() {
        return config.getDatabase();
    }

    /**
     * 数据库驱动
     *
     * @param jdbcDriver
     */
    public void setJdbcDriver(String jdbcDriver) {
        String oldValue = config.getJdbcDriver();
        config.setJdbcDriver(jdbcDriver);
        changeSupport.firePropertyChange("jdbcDriver", oldValue, jdbcDriver);
    }

    /**
     * 数据库驱动
     *
     * @return jdbcDriver
     */
    public String getJdbcDriver(){
        return config.getJdbcDriver();
    }

    /**
     * 数据库URL
     *
     * @param jdbcUrl
     */
    public void setJdbcUrl(String jdbcUrl) {
        String oldValue = config.getJdbcUrl();
        config.setJdbcUrl(jdbcUrl);
        changeSupport.firePropertyChange("jdbcUrl", oldValue, jdbcUrl);
    }

    /**
     * 数据库URL
     *
     * @return
     */
    public String getJdbcUrl() {
        return config.getJdbcUrl();
    }
    
    /**
     * 数据库用户名
     *
     * @param jdbcUsername
     */
    public void setJdbcUsername(String jdbcUsername) {
        String oldValue = config.getJdbcUsername();
        config.setJdbcUsername(jdbcUsername);
        changeSupport.firePropertyChange("jdbcUsername", oldValue, jdbcUsername);
    }

    /**
     * 数据库用户名
     *
     * @return
     */
    public String getJdbcUsername() {
        return config.getJdbcUsername();
    }

    /**
     * 数据库密码
     *
     * @param jdbcPassword
     */
    public void setJdbcPassword(String jdbcPassword) {
        String oldValue = config.getJdbcPassword();
        config.setJdbcPassword(jdbcPassword);
        changeSupport.firePropertyChange("jdbcPassword", oldValue, jdbcPassword);
    }

    /**
     * 数据库密码
     *
     * @return
     */
    public String getJdbcPassword() {
        return config.getJdbcPassword();
    }

    /**
     * 类型集合
     *
     * @param types
     */
    public void setTypes(Set<TypeCfg> types) {
        Set<TypeCfg> oldValue = config.getTypes();
        config.setTypes(types);
        changeSupport.firePropertyChange("types", oldValue, types);
    }

    /**
     * 类型集合
     *
     * @return types
     */
    public Set<TypeCfg> getTypes() {
        return config.getTypes();
    }

    /**
     * 忽略的表集合
     *
     * @param ignoreTables
     */
    public void setIgnoreTables(Set<String> ignoreTables) {
        Set<String> oldValue = config.getIgnoreTables();
        config.setIgnoreTables(ignoreTables);
        changeSupport.firePropertyChange("ignoreTables", oldValue, ignoreTables);
    }

    /**
     * 忽略的表集合
     *
     * @return tables
     */
    public Set<String> getIgnoreTables() {
        return config.getIgnoreTables();
    }

    /**
     * 表集合
     *
     * @param tables
     */
    public void setTables(Set<TableCfg> tables) {
        Set<TableCfg> oldValue = config.getTables();
        config.setTables(tables);
        changeSupport.firePropertyChange("tables", oldValue, tables);
    }

    /**
     * 表集合
     *
     * @return tables
     */
    public Set<TableCfg> getTables() {
        return config.getTables();
    }

    /**
     * 扩展字段
     *
     * @param attributes
     */
    public void setAttributes(Map<String, Object> attributes) {
        Map<String, Object> oldValue = config.getAttributes();
        config.setAttributes(attributes);
        changeSupport.firePropertyChange("attributes", oldValue, attributes);
    }

    /**
     * 扩展字段
     *
     * @return
     */
    public Map<String, Object> getAttributes() {
        return config.getAttributes();
    }

}
