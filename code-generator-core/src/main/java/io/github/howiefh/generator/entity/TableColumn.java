package io.github.howiefh.generator.entity;

import com.google.common.base.Objects;
import io.github.howiefh.generator.common.entity.BasicEntity;
import io.github.howiefh.generator.common.util.StringUtils;
import io.github.howiefh.generator.types.DefaultJdbcTypeResolver;

/**
 * 业务表字段Entity
 *
 * @author fenghao on 2016/5/17
 * @version 1.0
 * @since 1.0
 */
public class TableColumn extends BasicEntity {

    private static final long serialVersionUID = 1L;
    /**
     * 归属表
     */
    private Table table;
    /**
     * 列名
     */
    private String name;
    /**
     * 描述
     */
    private String comments;
    /**
     * JDBC类型
     */
    private String jdbcType;
    /**
     * JAVA类型
     */
    private String javaType;
    /**
     * JAVA字段名
     */
    private String javaField;
    /**
     * 是否主键（1：主键）
     */
    private boolean pk;
    /**
     * 是否可为空（1：可为空；0：不为空）
     */
    private boolean nil;
    /**
     * 是否为插入字段（1：插入字段）
     */
    private boolean insert;
    /**
     * 是否编辑字段（1：编辑字段）
     */
    private boolean edit;
    /**
     * 是否列表字段（1：列表字段）
     */
    private boolean list;
    /**
     * 是否查询字段（1：查询字段）
     */
    private boolean query;
    /**
     * 查询方式（等于、不等于、大于、小于、范围、左LIKE、右LIKE、左右LIKE）
     */
    private String queryType;
    /**
     * 字段显示类型（文本框、文本域、下拉框、复选框、单选框、字典选择、人员选择、部门选择、区域选择）
     */
    private String showType;
    /**
     * 字典类型
     */
    private String dictType;
    /**
     * 排序（升序）
     */
    private Integer sort;

    public TableColumn() {
        super();
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public String getName() {
        return StringUtils.lowerCase(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments == null ? getName() : comments.trim();
    }

    public String getJdbcType() {
        return StringUtils.lowerCase(jdbcType);
    }

    public void setJdbcType(String jdbcType) {
        this.jdbcType = jdbcType;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public String getJavaField() {
        return javaField;
    }

    public void setJavaField(String javaField) {
        this.javaField = javaField;
    }

    public boolean isPk() {
        return pk;
    }

    public void setPk(boolean pk) {
        this.pk = pk;
    }

    public boolean isNull() {
        return nil;
    }

    public void setNull(boolean aNull) {
        nil = aNull;
    }

    public boolean isNotNull() {
        return !isNull();
    }

    public void setNotNull(boolean notNull) {
        setNull(!notNull);
    }


    public boolean isInsert() {
        return insert;
    }

    public void setInsert(boolean insert) {
        this.insert = insert;
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public boolean isList() {
        return list;
    }

    public void setList(boolean list) {
        this.list = list;
    }

    public boolean isQuery() {
        return query;
    }

    public void setQuery(boolean query) {
        this.query = query;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public String getDictType() {
        return dictType == null ? "" : dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 获取列名和说明
     *
     * @return
     */
    public String getNameAndComments() {
        return getName() + (comments == null ? "" : "  :  " + comments);
    }

    /**
     * 获取字符串长度
     *
     * @return
     */
    public String getDataLength() {
        String[] ss = StringUtils.split(StringUtils.substringBetween(getJdbcType(), "(", ")"), ",");
        if (ss != null && ss.length == 1) {
            return ss[0];
        }
        return "0";
    }

    /**
     * 获取长度.
     *
     * @return
     */
    public Integer getLength() {
        String dataLength = getDataLength();
        return Integer.valueOf(dataLength);
    }

    /**
     * 获取精度.
     *
     * @return
     */
    public Integer getScale() {
        String[] ss = StringUtils.split(StringUtils.substringBetween(getJdbcType(), "(", ")"), ",");
        String scale = "0";
        if (ss != null && ss.length == 2) {
            scale = ss[1];
        }
        return Integer.valueOf(scale);
    }

    /**
     * 获取简写Java类型
     *
     * @return
     */
    public String getSimpleJavaType() {
        return StringUtils.indexOf(getJavaType(), ".") != -1
                ? StringUtils.substringAfterLast(getJavaType(), ".")
                : getJavaType();
    }

    /**
     * 获取简写Java字段
     *
     * @return
     */
    public String getSimpleJavaField() {
        return StringUtils.substringBefore(getJavaField(), ".");
    }


    /**
     * 获取简写的JDBC类型
     *
     * @return
     */
    public String getSimpleJdbcType() {
        int index = jdbcType.indexOf('(');
        String simpleJdbcType = (index == -1 ? jdbcType : jdbcType.substring(0, index)).toUpperCase();
        simpleJdbcType = DefaultJdbcTypeResolver.getInstance().calculateJdbcType(simpleJdbcType);
        return simpleJdbcType;
    }

    /**
     * 获取Java字段，如果是对象，则获取对象.附加属性1
     *
     * @return
     */
    public String getJavaFieldId() {
        return StringUtils.substringBefore(getJavaField(), "|");
    }

    /**
     * 获取Java字段，如果是对象，则获取对象.附加属性2
     *
     * @return
     */
    public String getJavaFieldName() {
        String[][] ss = getJavaFieldAttrs();
        if (ss == null) {
            return "";
        }
        return ss.length > 0 ? getSimpleJavaField() + "." + ss[0][0] : "";
    }

    /**
     * 获取Java字段，所有属性名
     *
     * @return
     */
    public String[][] getJavaFieldAttrs() {
        String[] ss = StringUtils.split(StringUtils.substringAfter(getJavaField(), "|"), "|");
        if (ss == null) {
            return null;
        }
        String[][] sss = new String[ss.length][2];
        for (int i = 0; i < ss.length; i++) {
            sss[i][0] = ss[i];
            sss[i][1] = StringUtils.toUnderScoreCase(ss[i]);
        }
        return sss;
    }

    /**
     * 是否是基类字段
     *
     * @return
     */
    public Boolean isNotBaseEntityField() {
        return !StringUtils.equals(getSimpleJavaField(), "id")
                && !StringUtils.equals(getSimpleJavaField(), "version")
                && !StringUtils.equals(getSimpleJavaField(), "isValid")
                && !StringUtils.equals(getSimpleJavaField(), "createdDate")
                && !StringUtils.equals(getSimpleJavaField(), "modifiedDate");
    }

    /**
     * 是否是基类字段
     *
     * @return
     */
    public Boolean isNotBaseField() {
        return !StringUtils.equals(getSimpleJavaField(), "createdDate")
                && !StringUtils.equals(getSimpleJavaField(), "modifiedDate");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TableColumn that = (TableColumn) o;
        return Objects.equal(table, that.table) &&
                Objects.equal(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), table, name);
    }
}
