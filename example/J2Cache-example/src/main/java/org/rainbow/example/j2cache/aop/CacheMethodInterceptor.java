package org.rainbow.example.j2cache.aop;

import cn.hutool.core.annotation.AnnotationUtil;
import org.aopalliance.intercept.Interceptor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.rainbow.example.j2cache.annotation.Cache;
import org.rainbow.example.j2cache.annotation.CacheEvictor;
import org.rainbow.example.j2cache.processor.AbstractCacheAnnotationProcessor;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * 缓存拦截器
 *
 * @author: K
 * @date: 2022/03/19 16:44
 */
@Aspect
@Component
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class CacheMethodInterceptor implements Interceptor {

  @Around("@annotation(org.rainbow.example.j2cache.annotation.Cache)")
  public Object invokeCacheAllMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
    Cache cache = AnnotationUtil.getAnnotation(methodSignature.getMethod(), Cache.class);
    if (cache != null) {
      AbstractCacheAnnotationProcessor processor
          = AbstractCacheAnnotationProcessor.getProcessor(proceedingJoinPoint, cache);
      return processor.process(proceedingJoinPoint);
    }
    return proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
  }

  @Around("@annotation(org.rainbow.example.j2cache.annotation.CacheEvictor)")
  public Object invokeCacheEvictorAllMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
    CacheEvictor cacheEvictor = AnnotationUtil.getAnnotation(methodSignature.getMethod(), CacheEvictor.class);
    if (cacheEvictor != null) {
      AbstractCacheAnnotationProcessor processor
          = AbstractCacheAnnotationProcessor.getProcessor(proceedingJoinPoint, cacheEvictor);
      return processor.process(proceedingJoinPoint);
    }
    return proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
  }
}
