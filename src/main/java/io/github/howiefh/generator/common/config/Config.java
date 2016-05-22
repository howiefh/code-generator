package io.github.howiefh.generator.common.config;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import static io.github.howiefh.generator.common.util.CollectionUtils.emptyMapIfNull;
import static io.github.howiefh.generator.common.util.CollectionUtils.emptySetIfNull;
import static io.github.howiefh.generator.common.util.StringUtils.isBlank;

/**
 * Config所有字段都非必填，不填的话使用默认值。
 *
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
public class Config implements Serializable{
    private static final long serialVersionUID = 7773763753328286897L;
    public static final String DEFAULT_AUTHOR = System.getProperty("user.name");
    public static final String DEFAULT_VERSION = "1.0";
    public static final String DEFAULT_SINCE = DEFAULT_VERSION;
    public static final String DEFAULT_TEMPLATE_DIR = "templates";
    public static final String DEFAULT_DATABASE = "mysql";
    /** 作者 */
    private String author;
    /** 版本 */
    private String version;
    /** 起始 */
    private String since;
    /** 模板目录 */
    private String templateDir;
    /** 数据库类型 */
    private String database;
    /** 类型集合 */
    private Set<TypeCfg> types;
    /** 忽略的表集合 */
    private Set<String> ignoreTables;
    /** 表集合 */
    private Set<TableCfg> tables;
    /** 扩展字段*/
    private Map<String, Object> attributes;

    /**
     * 作者
     * @return author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * 作者
     * @param author
     */
    public void setAuthor(String author) {
        this.author = isBlank(author) ? DEFAULT_AUTHOR : author;
    }

    /**
     * 版本
     * @return version
     */
    public String getVersion() {
        return version;
    }

    /**
     * 版本
     * @param version
     */
    public void setVersion(String version) {
        this.version = isBlank(version) ? DEFAULT_VERSION : version;
    }

    /**
     * 起始
     * @return since
     */
    public String getSince() {
        return since;
    }

    /**
     * 起始
     * @param since
     */
    public void setSince(String since) {
        this.since = isBlank(since) ? DEFAULT_SINCE: since;
    }

    /**
     * 模板目录
     * @return templateDir
     */
    public String getTemplateDir() {
        return templateDir;
    }

    /**
     * 模板目录
     * @param templateDir
     */
    public void setTemplateDir(String templateDir) {
        this.templateDir = isBlank(templateDir) ? DEFAULT_TEMPLATE_DIR : templateDir;
    }

    /**
     * 数据库类型
     * @return database
     */
    public String getDatabase() {
        return database;
    }

    /**
     * 数据库类型
     * @param database
     */
    public void setDatabase(String database) {
        this.database = isBlank(database) ? DEFAULT_DATABASE : database;
    }

    /**
     * 类型集合
     * @return types
     */
    public Set<TypeCfg> getTypes() {
        return types;
    }

    /**
     * 类型集合
     * @param types
     */
    public void setTypes(Set<TypeCfg> types) {
        this.types = emptySetIfNull(types);
    }

    /**
     * 忽略的表集合
     * @return tables
     */
    public Set<String> getIgnoreTables() {
        return ignoreTables;
    }

    /**
     * 忽略的表集合
     * @param ignoreTables
     */
    public void setIgnoreTables(Set<String> ignoreTables) {
        this.ignoreTables = emptySetIfNull(ignoreTables);
    }

    /**
     * 表集合
     * @return tables
     */
    public Set<TableCfg> getTables() {
        return tables;
    }

    /**
     * 表集合
     * @param tables
     */
    public void setTables(Set<TableCfg> tables) {
        this.tables = emptySetIfNull(tables);
    }

    /**
     * 扩展字段
     * @return
     */
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    /**
     * 扩展字段
     * @param attributes
     */
    public void setAttributes(Map<String, Object> attributes){
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
                Objects.equal(types, config.types) &&
                Objects.equal(ignoreTables, config.ignoreTables) &&
                Objects.equal(tables, config.tables) &&
                Objects.equal(attributes, config.attributes);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(author, version, since, templateDir, types, ignoreTables, tables, attributes);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("author", author)
                .add("version", version)
                .add("since", since)
                .add("templateDir", templateDir)
                .add("types", types)
                .add("ignoreTables", ignoreTables)
                .add("tables", tables)
                .add("attributes", attributes)
                .toString();
    }
}
