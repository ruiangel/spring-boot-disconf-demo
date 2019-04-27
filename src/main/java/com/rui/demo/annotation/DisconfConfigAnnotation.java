package com.rui.demo.annotation;

import java.lang.annotation.*;

/**
 * disconf 属性配置注解
 *
 * @author rui
 * @datetime 2019-04-27 16:18
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DisconfConfigAnnotation {
}
