package io.github.howiefh.generator.common.config;

import com.google.common.base.Objects;
import io.github.howiefh.generator.common.enums.DBType;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import static io.github.howiefh.generator.common.util.CollectionUtils.emptyMapIfNull;
import static io.github.howiefh.generator.common.util.CollectionUtils.emptySetIfNull;
import static io.github.howiefh.generator.common.util.StringUtils.isBlank;

/**
 * Config所有字段都非必填，不填的话使用默认值。
 * <p/>
 * 1. author默认系统用户
 * 2. version默认1.0
 * 3. since默认1.0
 * 4. templateDir默认templates
 * 5. types, ignoreTables, tables默认空集合
 * 6. attributes默认空映射
 *
 * @author fenghao on 2016/5/20
 * @version 1.0
 * @since 1.0
 */
public class Config implements Serializable {
    private static final long serialVersionUID = 7773763753328286897L;
    public static final String DEFAULT_AUTHOR = System.getProperty("user.name");
    public static final String DEFAULT_VERSION = "1.0";
    public static final String DEFAULT_SINCE = DEFAULT_VERSION;
    public static final String DEFAULT_TEMPLATE_DIR = "templates";
    public static final String DEFAULT_BASE_PACKAGE = "";
    public static final String DEFAULT_PREFIX = "";
    public static final String DEFAULT_DATABASE = DBType.MYSQL.getCode();
    public static final String DEFAULT_JDBC_DRIVER = "com.mysql.jdbc.Driver";
    public static final String DEFAULT_JDBC_USERNAME = "root";
    public static final String DEFAULT_JDBC_PASSWORD = "";
    public static final String DEFAULT_CONFIG = "config.json";
    private String configPath = DEFAULT_CONFIG;
    /**
     * 作者
     */
    private String author;
    /**
     * 版本
     */
    private String version;
    /**
     * 起始
     */
    private String since;
    /**
     * 模板目录
     */
    private String templateDir;
    /**
     * 数据库类型
     */
    private String database;
    /**
     * 数据库驱动
     */
    private String jdbcDriver;
    /**
     * 数据库Url
     */
    private String jdbcUrl;
    /**
     * 数据库用户名
     */
    private String jdbcUsername;
    /**
     * 数据库密码
     */
    private String jdbcPassword;
    /**
     * 包路径
     */
    private String basePackage;
    /**
     * 目标目录
     */
    private String baseTarget;
    /**
     * 表前缀
     */
    private String prefix;
    /**
     * 覆盖
     */
    private boolean override;
    /**
     * 类型集合
     */
    private Set<TypeCfg> types;
    /**
     * 忽略的表集合
     */
    private Set<String> ignoreTables;
    /**
     * 表集合
     */
    private Set<TableCfg> tables;
    /**
     * 扩展字段
     */
    private Map<String, Object> attributes;

    public String getConfigPath() {
        return configPath;
    }

    public void setConfigPath(String configPath) {
        this.configPath = configPath;
    }

    /**
     * 作者
     *
     * @return author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * 作者
     *
     * @param author
     */
    public void setAuthor(String author) {
        this.author = isBlank(author) ? DEFAULT_AUTHOR : author;
    }

    /**
     * 版本
     *
     * @return version
     */
    public String getVersion() {
        return version;
    }

    /**
     * 版本
     *
     * @param version
     */
    public void setVersion(String version) {
        this.version = isBlank(version) ? DEFAULT_VERSION : version;
    }

    /**
     * 起始
     *
     * @return since
     */
    public String getSince() {
        return since;
    }

    /**
     * 起始
     *
     * @param since
     */
    public void setSince(String since) {
        this.since = isBlank(since) ? DEFAULT_SINCE : since;
    }

    /**
     * 模板目录
     *
     * @return templateDir
     */
    public String getTemplateDir() {
        return templateDir;
    }

    /**
     * 模板目录
     *
     * @param templateDir
     */
    public void setTemplateDir(String templateDir) {
        this.templateDir = isBlank(templateDir) ? DEFAULT_TEMPLATE_DIR : templateDir;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = isBlank(basePackage) ? DEFAULT_BASE_PACKAGE : basePackage;
    }

    public String getBaseTarget() {
        return baseTarget;
    }

    public void setBaseTarget(String baseTarget) {
        this.baseTarget = isBlank(baseTarget) ? DEFAULT_BASE_PACKAGE : baseTarget;
    }

    public String getPrefix() {
        return prefix == null ? DEFAULT_PREFIX : prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = isBlank(prefix) ? DEFAULT_PREFIX : prefix;
    }

    public boolean isOverride() {
        return override;
    }

    public void setOverride(boolean override) {
        this.override = override;
    }

    /**
     * 数据库类型
     *
     * @return database
     */
    public String getDatabase() {
        return database;
    }

    /**
     * 数据库类型
     *
     * @param database
     */
    public void setDatabase(String database) {
        this.database = isBlank(database) ? DEFAULT_DATABASE : database;
    }

    /**
     * 数据库驱动
     *
     * @return
     */
    public String getJdbcDriver() {
        return jdbcDriver;
    }

    /**
     * 数据库驱动
     *
     * @param jdbcDriver
     */
    public void setJdbcDriver(String jdbcDriver) {
        this.jdbcDriver = isBlank(jdbcDriver) ? DEFAULT_JDBC_DRIVER : jdbcDriver;
    }

    /**
     * 数据库URL
     *
     * @return
     */
    public String getJdbcUrl() {
        return jdbcUrl;
    }

    /**
     * 数据库URL
     *
     * @param jdbcUrl
     */
    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    /**
     * 数据库用户名
     *
     * @return
     */
    public String getJdbcUsername() {
        return jdbcUsername;
    }

    /**
     * 数据库用户名
     *
     * @param jdbcUsername
     */
    public void setJdbcUsername(String jdbcUsername) {
        this.jdbcUsername = isBlank(jdbcUsername) ? DEFAULT_JDBC_USERNAME : jdbcUsername;
    }

    /**
     * 数据库密码
     *
     * @return
     */
    public String getJdbcPassword() {
        return jdbcPassword;
    }

    /**
     * 数据库密码
     *
     * @param jdbcPassword
     */
    public void setJdbcPassword(String jdbcPassword) {
        this.jdbcPassword = jdbcPassword == null ? DEFAULT_JDBC_PASSWORD : jdbcPassword;
    }

    /**
     * 类型集合
     *
     * @return types
     */
    public Set<TypeCfg> getTypes() {
        return types;
    }

    /**
     * 类型集合
     *
     * @param types
     */
    public void setTypes(Set<TypeCfg> types) {
        this.types = emptySetIfNull(types);
    }

    /**
     * 忽略的表集合
     *
     * @return tables
     */
    public Set<String> getIgnoreTables() {
        return ignoreTables;
    }

    /**
     * 忽略的表集合
     *
     * @param ignoreTables
     */
    public void setIgnoreTables(Set<String> ignoreTables) {
        this.ignoreTables = emptySetIfNull(ignoreTables);
    }

    /**
     * 表集合
     *
     * @return tables
     */
    public Set<TableCfg> getTables() {
        return tables;
    }

    /**
     * 表集合
     *
     * @param tables
     */
    public void setTables(Set<TableCfg> tables) {
        this.tables = emptySetIfNull(tables);
    }

    /**
     * 扩展字段
     *
     * @return
     */
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    /**
     * 扩展字段
     *
     * @param attributes
     */
    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = emptyMapIfNull(attributes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Config config = (Config) o;
        return Objects.equal(author, config.author) &&
                Objects.equal(version, config.version) &&
                Objects.equal(since, config.since) &&
                Objects.equal(templateDir, config.templateDir) &&
                Objects.equal(database, config.database) &&
                Objects.equal(jdbcDriver, config.jdbcDriver) &&
                Objects.equal(jdbcUrl, config.jdbcUrl) &&
                Objects.equal(jdbcUsername, config.jdbcUsername) &&
                Objects.equal(jdbcPassword, config.jdbcPassword) &&
                Objects.equal(types, config.types) &&
                Objects.equal(ignoreTables, config.ignoreTables) &&
                Objects.equal(tables, config.tables) &&
                Objects.equal(attributes, config.attributes);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(author, version, since, templateDir, database, jdbcDriver, jdbcUrl, jdbcUsername, jdbcPassword, types, ignoreTables, tables, attributes);
    }

    @Override
    public String toString() {
        return "Config{" +
                "author='" + author + '\'' +
                ", version='" + version + '\'' +
                ", since='" + since + '\'' +
                ", templateDir='" + templateDir + '\'' +
                ", database='" + database + '\'' +
                ", jdbcDriver='" + jdbcDriver + '\'' +
                ", jdbcUrl='" + jdbcUrl + '\'' +
                ", jdbcUsername='" + jdbcUsername + '\'' +
                ", jdbcPassword='" + jdbcPassword + '\'' +
                ", types=" + types +
                ", ignoreTables=" + ignoreTables +
                ", tables=" + tables +
                ", attributes=" + attributes +
                '}';
    }
}
