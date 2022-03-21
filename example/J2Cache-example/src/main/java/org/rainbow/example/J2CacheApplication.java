package org.rainbow.example;

import org.rainbow.example.j2cache.EnableJ2Cache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 启动类
 *
 * @author: K
 * @date: 2022/03/19 15:08
 */
@EnableCaching
@EnableJ2Cache
@SpringBootApplication
public class J2CacheApplication {

  public static void main(String[] args) {
    SpringApplication.run(J2CacheApplication.class, args);
  }
}
