package org.rainbow.example.j2cache.processor;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import net.oschina.j2cache.CacheChannel;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.rainbow.example.j2cache.annotation.Cache;
import org.rainbow.example.j2cache.annotation.CacheEvictor;
import org.rainbow.example.j2cache.model.AnnotationInfo;
import org.rainbow.example.j2cache.utils.CacheKeyBuilder;
import org.springframework.cache.annotation.CacheEvict;
import java.lang.reflect.Method;

/**
 * 抽象注解处理器
 *
 * @author: K
 * @date: 2022/03/19 16:54
 */
public abstract class AbstractCacheAnnotationProcessor {

  protected CacheChannel cacheChannel;

  public AbstractCacheAnnotationProcessor() {
    this.cacheChannel = SpringUtil.getBean(CacheChannel.class);
  }

  /**
   * 转化为注解信息
   *
   * @param proceedingJoinPoint 切点对象
   * @param cache               缓存信息
   * @return 注解信息
   */
  protected AnnotationInfo<Cache> getAnnotationInfo(ProceedingJoinPoint proceedingJoinPoint, Cache cache) {
    AnnotationInfo<Cache> annotationInfo = new AnnotationInfo<>();
    annotationInfo.setAnnotation(cache);
    annotationInfo.setRegion(cache.region());
    annotationInfo.setKey(cache.key());
    return annotationInfo;
  }

  /**
   * 生成 key 字符串
   *
   * @param proceedingJoinPoint 切点对象
   * @param cache               缓存信息
   * @return key
   */
  protected String generateKey(ProceedingJoinPoint proceedingJoinPoint, Cache cache) throws IllegalAccessException {
    String key = cache.key();
    if (StrUtil.isNotBlank(key)) {
      String className = proceedingJoinPoint.getTarget().getClass().getSimpleName();
      MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
      Method method = methodSignature.getMethod();
      key = className + ":" + method.getName();
    }
    key = CacheKeyBuilder.generate(key, cache.params(), proceedingJoinPoint.getArgs());
    return key;
  }

  /**
   * 获取注解处理器
   *
   * @param proceedingJoinPoint 切点信息
   * @param cache               注解信息
   * @return 注解处理器
   */
  public static AbstractCacheAnnotationProcessor getProcessor(ProceedingJoinPoint proceedingJoinPoint, Cache cache) {
    return new CachesAnnotationProcessor(proceedingJoinPoint, cache);
  }

  /**
   * 获取注解处理器
   *
   * @param proceedingJoinPoint 切点信息
   * @param cacheEvictor        注解信息
   * @return 注解处理器
   */
  public static AbstractCacheAnnotationProcessor getProcessor(ProceedingJoinPoint proceedingJoinPoint,
      CacheEvictor cacheEvictor) {
    return new CacheEvictorAnnotationProcessor(proceedingJoinPoint, cacheEvictor);
  }

  /**
   * 处理
   *
   * @param proceedingJoinPoint 切点
   * @return 处理结果
   */
  public abstract Object process(ProceedingJoinPoint proceedingJoinPoint) throws Throwable;

}
