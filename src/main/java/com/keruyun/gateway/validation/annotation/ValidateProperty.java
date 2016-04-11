package com.keruyun.gateway.validation.annotation;


import com.keruyun.gateway.validation.type.RegexType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 校验规则
 * @author tany@shishike.com
 * @since 2015年2月27日
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
public @interface ValidateProperty {
    
    // 是否可以为空
    boolean nullable() default false;
    
    // 最大长度
    int maxLength() default 0;
    
    // 最小长度
    int minLength() default 0;

    /**
     * 长度
     * @return
     */
    int length() default 0;

    /**
     * 类 当RegexType为ARRAY、OBJECT、ENUM 传入
     *
     * @return
     */
    Class clazz() default Object.class;

    // 提供几种常用的正则验证
    RegexType regexType() default RegexType.NONE;
    
    // 自定义正则验证
    String regexExpression() default "";
    
    // 参数或者字段描述,这样能够显示友好的异常信息
    String description() default "";


}