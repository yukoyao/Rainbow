package org.rainbow.example.j2cache.processor;

import net.oschina.j2cache.CacheObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.rainbow.example.j2cache.annotation.Cache;
import org.rainbow.example.j2cache.model.AnnotationInfo;
import org.rainbow.example.j2cache.model.CacheHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.annotation.Annotation;

/**
 * Cache 注解处理器
 *
 * @author: K
 * @date: 2022/03/19 16:56
 */
public class CachesAnnotationProcessor extends AbstractCacheAnnotationProcessor {

  private static final Logger logger = LoggerFactory.getLogger(CachesAnnotationProcessor.class);

  private AnnotationInfo<Cache> annotationInfo;

  /**
   * 初始化缓存注解处理器
   *
   * @param proceedingJoinPoint 切点信息
   * @param annotation          注解
   */
  public CachesAnnotationProcessor(ProceedingJoinPoint proceedingJoinPoint, Annotation annotation) {
    super();
    annotationInfo = getAnnotationInfo(proceedingJoinPoint, (Cache) annotation);
  }

  @Override
  public Object process(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    Object result = null;
    boolean readCache = false;
    //获取缓存数据
    CacheHolder cacheHolder = getCache(annotationInfo);
    if (cacheHolder.isExistsCache()) {
      result = cacheHolder.getValue();
      readCache = true;
    }
    if (!readCache) {
      //调用目标方法
      result = doInvoke(proceedingJoinPoint);
      //设置缓存数据
      setCache(result);
    }
    return result;
  }

  /**
   * 尝试获取值
   *
   * @param proceedingJoinPoint 切点
   * @return 结果
   * @throws Throwable 异常时抛出
   */
  private Object doInvoke(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    return proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
  }

  /**
   * 设置缓存
   *
   * @param result 数据
   * @throws Throwable 异常时抛出
   */
  private void setCache(Object result) throws Throwable {
    try {
      String key = annotationInfo.getKey();
      String region = annotationInfo.getRegion();
      cacheChannel.set(region, key, result);
    } catch (Throwable throwable) {
      logger.error("设置缓存时出错");
    }
  }

  /**
   * 读取缓存
   *
   * @param annotationInfo 缓存信息
   * @return 缓存封装
   */
  private CacheHolder getCache(AnnotationInfo<Cache> annotationInfo) {
    String region = annotationInfo.getRegion();
    String key = annotationInfo.getKey();
    Object value = null;
    boolean exists = cacheChannel.exists(region, key);
    if (exists) {
      CacheObject cacheObject = cacheChannel.get(region, key);
      value = cacheObject.getValue();
      return CacheHolder.newResult(value, true);
    }
    return CacheHolder.newResult(value, false);
  }
}
