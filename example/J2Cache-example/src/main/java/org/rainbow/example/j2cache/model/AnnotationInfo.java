package org.rainbow.example.j2cache.model;

import lombok.Data;
import java.lang.annotation.Annotation;

/**
 * Cache 信息包装
 *
 * @author: K
 * @date: 2022/03/19 16:39
 */
@Data
public class AnnotationInfo<T extends Annotation> {

  private T annotation;

  private String key;

  private String region;
}
