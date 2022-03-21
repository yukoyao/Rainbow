package org.rainbow.example.j2cache;

import org.rainbow.example.j2cache.aop.CacheMethodInterceptor;
import org.springframework.context.annotation.Import;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 开启 J2Cache 缓存注解
 *
 * @author: K
 * @date: 2022/03/21 16:13
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(CacheMethodInterceptor.class)
public @interface EnableJ2Cache {

}
