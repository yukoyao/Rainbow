package org.rainbow.example.j2cache.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 缓存注解
 *
 * @author: K
 * @date: 2022/03/19 15:08
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Cache {

  String region() default "rx";

  String key() default "";

  String params() default "";
}
