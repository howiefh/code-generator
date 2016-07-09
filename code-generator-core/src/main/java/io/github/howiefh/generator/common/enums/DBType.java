package io.github.howiefh.generator.common.enums;

/**
 * @author fenghao, 2016/7/9
 * @version 1.0
 * @since 1.0
 */
public enum DBType {
    MYSQL("mysql"),MSSQL("mssql"),ORACLE("oracle")
    ;

    private String code;

    DBType(String code){
        this.code = code;
    }

    /**
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

}
