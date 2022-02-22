package top.yuko.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 启动器
 *
 * @author: K
 * @date: 2022/02/17 09:47
 */
@SpringBootApplication
@EnableCaching
@EnableSwagger2
public class CacheBreakdownApplication {

  public static void main(String[] args) {
    SpringApplication.run(CacheBreakdownApplication.class, args);
  }
}
