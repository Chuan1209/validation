package com.keruyun.gateway.validation.annotation;


import java.lang.annotation.*;

/**
 * 验证器注解
 *
 * @author tany@shishike.com
 * @since 2015年4月7日
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Validator {

    /**
     * 是否需签名
     * @return
     */
    boolean isSign() default true;
}
