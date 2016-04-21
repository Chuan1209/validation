package com.keruyun.gateway.validation.annotation;


import java.lang.annotation.*;

/**
 *
 * 作用到方法参数上
 * Created by youngtan99@163.com on 15/11/9.
 */
@Target({ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidateMapping {
}
