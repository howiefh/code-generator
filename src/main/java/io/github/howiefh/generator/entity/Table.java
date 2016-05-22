package io.github.howiefh.generator.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import io.github.howiefh.generator.common.config.Configuration;
import io.github.howiefh.generator.common.entity.BasicEntity;

import java.util.List;
import java.util.Set;

import static io.github.howiefh.generator.common.util.StringUtils.indexOf;
import static io.github.howiefh.generator.common.util.StringUtils.lowerCase;

/**
 * 业务表Entity
 * @author fenghao on 2016/5/17
 * @version 1.0
 * @since 1.0
 */
public class Table extends BasicEntity {
	
	private static final long serialVersionUID = 1L;
    /** 名称*/
	private String name;
    /** 描述*/
	private String comments;
    /** 实体类名称*/
    private String className;
    /** 表列*/
	private List<TableColumn> columns = Lists.newArrayList();
    /** 当前表主键列表*/
    private List<TableColumn> pks;

	public Table() {
		super();
	}

	public String getName() {
		return lowerCase(name);
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

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<TableColumn> getPks() {
		return pks;
	}

	public void setPks(List<TableColumn> pks) {
		this.pks = pks;
	}

	public List<TableColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<TableColumn> columns) {
		this.columns = columns;
	}

	/**
	 * 获取列名和说明
	 * @return
	 */
	public String getNameAndComments() {
		return getName() + (comments == null ? "" : "  :  " + comments);
	}

	/**
	 * 获取导入依赖包字符串
	 * @return
	 */
	public Set<String> getImports(){
		Set<String> imports = Sets.newHashSet(); // 引用列表
		for (TableColumn column : getColumns()){
			if (column.isNotBaseField()){
				// 导入类型依赖包， 如果类型中包含“.”，则需要导入引用。
				if (indexOf(column.getJavaType(), ".") != -1 && !imports.contains(column.getJavaType())){
					imports.add(column.getJavaType());
				}
			}
		}
		return imports;
	}
	
	/**
	 * 是否存在created_date列
	 * @return
	 */
	public Boolean isCreatedDateExists(){
		for (TableColumn c : columns){
			if ("created_date".equals(c.getName())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 是否存在modified_date列
	 * @return
	 */
	public Boolean isModifiedDateExists(){
		for (TableColumn c : columns){
			if ("modified_date".equals(c.getName())){
				return true;
			}
		}
		return false;
	}

	/**
	 * 是否存在del_flag列
	 * @return
	 */
	public Boolean isDelFlagExists(){
		for (TableColumn c : columns){
			if ("del_flag".equals(c.getName())){
				return true;
			}
		}
		return false;
	}

    public String getDbType() {
        return Configuration.getConfig().getDatabase();
    }
}


