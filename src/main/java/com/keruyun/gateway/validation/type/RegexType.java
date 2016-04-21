package com.keruyun.gateway.validation.type;
 
/**
 * 数据类型枚举
 * @author youngtan99@163.com
 * @since 2015年2月27日
 */
public enum RegexType {
    /**
     * 无
     */
    NONE,
    /**
     * 字符串
     */
    CHARACTER,
    /**
     * 整型
     */
    INT,
    /**
     * 长整型
     */
    LONG,
    /**
     * 精度型
     */
    DECIMAL,
    /**
     * 电子邮件
     */
    EMAIL,
    /**
     * IP
     */
    IP, 
    /**
     * 网址
     */
    URL,
    /**
     * 日期
     */
    DATE,
    /**
     * 电话号码
     */
    PHONENUMBER,
    /**
     * 数组
     */
    ARRAY,
    /**
     *对象
     */
    OBJECT,
    /**
     * MAP
     */
    MAP,
    /**
     * boolean型
     */
    BOOL,
    /**
     * 枚举类型
     */
    ENUM
}