package io.github.howiefh.generator.entity;

import com.google.common.collect.Lists;
import io.github.howiefh.generator.common.entity.BasicEntity;

import java.util.List;

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
	private List<TableColumn> columnList = Lists.newArrayList();

    /** 当前表主键列表*/
    private List<String> pkList;

    /** TODO 暂时默认没有联合主键的情况 */
    private TableColumn pk;

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

	public List<String> getPkList() {
		return pkList;
	}

	public void setPkList(List<String> pkList) {
		this.pkList = pkList;
	}

    public TableColumn getPk() {
        return pk;
    }

    public void setPk(TableColumn pk) {
        this.pk = pk;
    }

	public List<TableColumn> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<TableColumn> columnList) {
		this.columnList = columnList;
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
	public List<String> getImportList(){
		List<String> importList = Lists.newArrayList(); // 引用列表
		for (TableColumn column : getColumnList()){
			if (column.isNotBaseField()){
				// 导入类型依赖包， 如果类型中包含“.”，则需要导入引用。
				if (indexOf(column.getJavaType(), ".") != -1 && !importList.contains(column.getJavaType())){
					importList.add(column.getJavaType());
				}
			}
		}
		return importList;
	}
	
	/**
	 * 是否存在created_date列
	 * @return
	 */
	public Boolean isCreatedDateExists(){
		for (TableColumn c : columnList){
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
		for (TableColumn c : columnList){
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
		for (TableColumn c : columnList){
			if ("del_flag".equals(c.getName())){
				return true;
			}
		}
		return false;
	}

    public String getDbType() {
		//TODO 完善
        return "mysql";
    }
}


