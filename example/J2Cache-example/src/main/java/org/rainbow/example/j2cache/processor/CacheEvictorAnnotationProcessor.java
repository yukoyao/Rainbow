package org.rainbow.example.j2cache.processor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.rainbow.example.j2cache.annotation.Cache;
import org.rainbow.example.j2cache.annotation.CacheEvictor;
import org.rainbow.example.j2cache.model.AnnotationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * 失效缓存注解处理器
 *
 * @author: K
 * @date: 2022/03/21 15:31
 */
public class CacheEvictorAnnotationProcessor extends AbstractCacheAnnotationProcessor {

  private static final Logger logger = LoggerFactory.getLogger(CacheEvictorAnnotationProcessor.class);

  private List<AnnotationInfo<Cache>> cacheList = new ArrayList<>();

  public CacheEvictorAnnotationProcessor(ProceedingJoinPoint proceedingJoinPoint, Annotation annotation) {
    super();
    CacheEvictor cacheEvictor = (CacheEvictor) annotation;
    for (Cache cache : cacheEvictor.value()) {
      AnnotationInfo<Cache> annotationInfo = getAnnotationInfo(proceedingJoinPoint, cache);
      cacheList.add(annotationInfo);
    }
  }

  @Override
  public Object process(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    for (AnnotationInfo<Cache> item : cacheList) {
      try {
        cacheChannel.evict(item.getRegion(), item.getKey());
      } catch (Throwable throwable) {
        logger.error("失效缓存时出错");
      }
    }
    return proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
  }
}
