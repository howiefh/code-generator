package io.github.howiefh.generator.ui.model;

import io.github.howiefh.generator.common.config.Config;
import io.github.howiefh.generator.common.config.Configuration;
import io.github.howiefh.generator.common.config.TableCfg;
import io.github.howiefh.generator.common.config.TypeCfg;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Map;
import java.util.Set;

/**
 * @author fenghao, 2016/7/9
 * @version 1.0
 * @since 1.0
 */
public class ConfigModel extends Config{
    private static final long serialVersionUID = 1349713680725743246L;
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    /**
     * 作者
     *
     * @param author
     */
    @Override
    public void setAuthor(String author) {
        String oldValue = Configuration.getConfig().getAuthor();
        Configuration.getConfig().setAuthor(author);
        changeSupport.firePropertyChange("author", oldValue, author);
    }

    /**
     * 作者
     *
     * @return author
     */
    @Override
    public String getAuthor() {
        return Configuration.getConfig().getAuthor();
    }

    /**
     * 版本
     *
     * @param version
     */
    @Override
    public void setVersion(String version) {
        String oldValue = Configuration.getConfig().getVersion();
        Configuration.getConfig().setVersion(version);
        changeSupport.firePropertyChange("version", oldValue, version);
    }

    /**
     * 版本
     *
     * @return version
     */
    @Override
    public String getVersion() {
        return Configuration.getConfig().getVersion();
    }

    /**
     * 起始
     *
     * @param since
     */
    @Override
    public void setSince(String since) {
        String oldValue = Configuration.getConfig().getSince();
        Configuration.getConfig().setSince(since);
        changeSupport.firePropertyChange("since", oldValue, since);
    }

    /**
     * 起始
     *
     * @return since
     */
    @Override
    public String getSince() {
        return Configuration.getConfig().getSince();
    }

    /**
     * 模板目录
     *
     * @param templateDir
     */
    @Override
    public void setTemplateDir(String templateDir) {
        String oldValue = Configuration.getConfig().getTemplateDir();
        Configuration.getConfig().setTemplateDir(templateDir);
        changeSupport.firePropertyChange("templateDir", oldValue, templateDir);
    }

    /**
     * 模板目录
     *
     * @return templateDir
     */
    @Override
    public String getTemplateDir() {
        return Configuration.getConfig().getTemplateDir();
    }
    
    /**
     * 数据库类型
     *
     * @param database
     */
    @Override
    public void setDatabase(String database) {
        String oldValue = Configuration.getConfig().getDatabase();
        Configuration.getConfig().setDatabase(database);
        changeSupport.firePropertyChange("database", oldValue, database);
    }

    /**
     * 数据库类型
     *
     * @return database
     */
    @Override
    public String getDatabase() {
        return Configuration.getConfig().getDatabase();
    }

    /**
     * 数据库驱动
     *
     * @param jdbcDriver
     */
    @Override
    public void setJdbcDriver(String jdbcDriver) {
        String oldValue = Configuration.getConfig().getJdbcDriver();
        Configuration.getConfig().setJdbcDriver(jdbcDriver);
        changeSupport.firePropertyChange("jdbcDriver", oldValue, jdbcDriver);
    }

    /**
     * 数据库驱动
     *
     * @return jdbcDriver
     */
    @Override
    public String getJdbcDriver(){
        return Configuration.getConfig().getJdbcDriver();
    }

    /**
     * 数据库URL
     *
     * @param jdbcUrl
     */
    @Override
    public void setJdbcUrl(String jdbcUrl) {
        String oldValue = Configuration.getConfig().getJdbcUrl();
        Configuration.getConfig().setJdbcUrl(jdbcUrl);
        changeSupport.firePropertyChange("jdbcUrl", oldValue, jdbcUrl);
    }

    /**
     * 数据库URL
     *
     * @return
     */
    @Override
    public String getJdbcUrl() {
        return Configuration.getConfig().getJdbcUrl();
    }
    
    /**
     * 数据库用户名
     *
     * @param jdbcUsername
     */
    @Override
    public void setJdbcUsername(String jdbcUsername) {
        String oldValue = Configuration.getConfig().getJdbcUsername();
        Configuration.getConfig().setJdbcUsername(jdbcUsername);
        changeSupport.firePropertyChange("jdbcUsername", oldValue, jdbcUsername);
    }

    /**
     * 数据库用户名
     *
     * @return
     */
    @Override
    public String getJdbcUsername() {
        return Configuration.getConfig().getJdbcUsername();
    }

    /**
     * 数据库密码
     *
     * @param jdbcPassword
     */
    @Override
    public void setJdbcPassword(String jdbcPassword) {
        String oldValue = Configuration.getConfig().getJdbcPassword();
        Configuration.getConfig().setJdbcPassword(jdbcPassword);
        changeSupport.firePropertyChange("jdbcPassword", oldValue, jdbcPassword);
    }

    /**
     * 数据库密码
     *
     * @return
     */
    @Override
    public String getJdbcPassword() {
        return Configuration.getConfig().getJdbcPassword();
    }

    /**
     * 类型集合
     *
     * @param types
     */
    @Override
    public void setTypes(Set<TypeCfg> types) {
        Set<TypeCfg> oldValue = Configuration.getConfig().getTypes();
        Configuration.getConfig().setTypes(types);
        changeSupport.firePropertyChange("types", oldValue, types);
    }

    /**
     * 类型集合
     *
     * @return types
     */
    @Override
    public Set<TypeCfg> getTypes() {
        return Configuration.getConfig().getTypes();
    }

    /**
     * 忽略的表集合
     *
     * @param ignoreTables
     */
    @Override
    public void setIgnoreTables(Set<String> ignoreTables) {
        Set<String> oldValue = Configuration.getConfig().getIgnoreTables();
        Configuration.getConfig().setIgnoreTables(ignoreTables);
        changeSupport.firePropertyChange("ignoreTables", oldValue, ignoreTables);
    }

    /**
     * 忽略的表集合
     *
     * @return tables
     */
    @Override
    public Set<String> getIgnoreTables() {
        return Configuration.getConfig().getIgnoreTables();
    }

    /**
     * 表集合
     *
     * @param tables
     */
    @Override
    public void setTables(Set<TableCfg> tables) {
        Set<TableCfg> oldValue = Configuration.getConfig().getTables();
        Configuration.getConfig().setTables(tables);
        changeSupport.firePropertyChange("tables", oldValue, tables);
    }

    /**
     * 表集合
     *
     * @return tables
     */
    @Override
    public Set<TableCfg> getTables() {
        return Configuration.getConfig().getTables();
    }

    /**
     * 扩展字段
     *
     * @param attributes
     */
    @Override
    public void setAttributes(Map<String, Object> attributes) {
        Map<String, Object> oldValue = Configuration.getConfig().getAttributes();
        Configuration.getConfig().setAttributes(attributes);
        changeSupport.firePropertyChange("attributes", oldValue, attributes);
    }

    /**
     * 扩展字段
     *
     * @return
     */
    @Override
    public Map<String, Object> getAttributes() {
        return Configuration.getConfig().getAttributes();
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
}
