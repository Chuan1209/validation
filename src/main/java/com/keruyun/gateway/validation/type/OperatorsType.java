package com.keruyun.gateway.validation.type;


/**
 * SQL 操作枚举类型
 */
public enum OperatorsType {
    /**
     * 大于
     */
    MORE(">"),
    /**
     * 小于
     */
    LESS("<"),
    /**
     * 等于
     */
    EQUAL("="),
    /**
     *大于等于
     */
    MORE_OR_EQUAL(">="),
    /**
     * 小于等于
     */
    LESS_OR_EQUAL("<="),
//    /**
//     * IN
//     */
//    IN("IN"),
    /**
     * 模糊查询
     */
    LIKE("LIKE"),
    /**
     * 左匹配
     */
    _LIKE("_LIKE"),
    /**
     * 倒序
     */
    DESC("DESC"),
    /**
     * 升序
     */
    ASC("ASC");
    private String key;

    OperatorsType(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public static OperatorsType getType(String key){
        OperatorsType[] operatorsTypes = OperatorsType.values();
        for (OperatorsType operatorsType : operatorsTypes) {
            if(operatorsType.getKey().equals(key)){
                return operatorsType;
            }
        }
        throw new IllegalArgumentException("No matching constant for [" + key + "]");
    }

}