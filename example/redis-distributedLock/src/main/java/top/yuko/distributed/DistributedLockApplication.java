package top.yuko.distributed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 启动类
 *
 * @author: K
 * @date: 2022/02/22 10:24
 */
@EnableCaching
@SpringBootApplication
public class DistributedLockApplication {

  public static void main(String[] args) {
    SpringApplication.run(DistributedLockApplication.class);
  }

}
